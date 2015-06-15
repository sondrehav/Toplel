package com.toplel.events.mouse;

import com.toplel.math.MyMatrix4f;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class MyMouseEventHandler {

    private static ArrayList<MyMouseListener> listeners = new ArrayList<>();

    public static void poll(){
        for (MyMouseListener listener : listeners){
            Vector2f mousePos = new Vector2f(2f * Mouse.getX() / Display.getWidth() - 1f, 2f * Mouse.getY() / Display.getHeight() - 1f);
            mousePos = listener.instance.getContext().fromContext(mousePos);
//            System.out.println("worldPos = " + mousePos);
//            System.out.println("inv_md = \n" + listener.instance.getInvertedModelMatrix());
            Vector2f _mousePos = MyMatrix4f.transformZ(listener.instance.getInvertedModelMatrix(), mousePos, null);
            boolean inside = false;
            if(_mousePos.x >= 0f && _mousePos.x <= 1f &&
                    _mousePos.y >= 0f && _mousePos.y <= 1f){
                inside = true;
            }
            boolean mousedown = Mouse.isButtonDown(listener.button);
            switch (listener.state){
                case NONE:
                    if(inside&&!mousedown){
                        listener.onMouseIn(mousePos.x, mousePos.y);
                        listener.state = MouseState.OVER;
                    }
                    break;
                case OVER:
                    if(!inside&&!mousedown){
                        listener.onMouseOut(mousePos.x, mousePos.y);
                        listener.state = MouseState.NONE;
                    } else if(inside&&mousedown) {
                        listener.onMouseDown(mousePos.x, mousePos.y);
                        listener.state = MouseState.DRAG_IN;
                    } else {
                        listener.onMouseOver(mousePos.x, mousePos.y);
                    }
                    break;
                case DRAG_IN:
                    if(!inside&&mousedown){
                        listener.state = MouseState.DRAG_OUT;
                        listener.onMouseOut(mousePos.x, mousePos.y);
                    } else if(!mousedown&&inside) {
                        listener.state = MouseState.OVER;
                        listener.onMouseUp(mousePos.x, mousePos.y);
                    } else {
                        listener.onMouseDrag(mousePos.x, mousePos.y);
                    }
                    break;
                case DRAG_OUT:
                    if(inside&&mousedown){
                        listener.state = MouseState.DRAG_IN;
                        listener.onMouseIn(mousePos.x, mousePos.y);
                    } else if(!inside&&!mousedown){
                        listener.state = MouseState.NONE;
                        listener.onMouseDragRelease(mousePos.x, mousePos.y);
                    } else {
                        listener.onMouseDrag(mousePos.x, mousePos.y);
                    }
            }
        }
        while(!toAdd.isEmpty()){
            listeners.add(toAdd.poll());
        }
        while(!toRemove.isEmpty()){
            listeners.remove(toRemove.poll());
        }
    }

    private static Queue<MyMouseListener> toAdd = new LinkedList<>();
    public static void addListener(MyMouseListener listener){
        toAdd.add(listener);
    }

    private static Queue<MyMouseListener> toRemove = new LinkedList<>();
    public static void removeListener(MyMouseListener listener){
        toRemove.add(listener);
    }

    static enum MouseState{
        NONE, OVER, DRAG_IN, DRAG_OUT
    }
}
