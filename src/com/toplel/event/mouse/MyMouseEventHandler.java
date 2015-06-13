package com.toplel.event.mouse;

import com.toplel.events.keyboard.MyKeyListener;
import com.toplel.math.MyVec2;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class MyMouseEventHandler {

    private static ArrayList<MyMouseListener> listeners = new ArrayList<>();

    public static void poll(){
        MyVec2 mousePos_ = new MyVec2((float) Mouse.getX(), (float) Mouse.getY());
        for (MyMouseListener listener : listeners){
            MyVec2 mousePos = listener.getTransformedMouse(mousePos_);
            boolean inside = listener.region.getRegion().isInside(mousePos);
//            System.out.println("listener.region = " + listener.region);
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
