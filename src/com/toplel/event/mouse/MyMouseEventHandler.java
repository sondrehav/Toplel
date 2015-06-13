package com.toplel.event.mouse;

import com.toplel.events.keyboard.MyKeyListener;
import com.toplel.math.MyVec2;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public abstract class MyMouseEventHandler {

    private static ArrayList<MyMouseListener> listeners = new ArrayList<>();

    public static void poll(){
        MyVec2 mousePos = new MyVec2((float) Mouse.getX(), (float) Mouse.getY());
        for (MyMouseListener listener : listeners){
            boolean inside = listener.region.isInside(mousePos);
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
    }

    public static void addListener(MyMouseListener listener){
        listeners.add(listener);
    }

    public static void removeListener(MyMouseListener listener){
        listeners.remove(listener);
    }

    static enum MouseState{
        NONE, OVER, DRAG_IN, DRAG_OUT
    }
}
