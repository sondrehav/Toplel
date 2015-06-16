package com.toplel.events.mouse;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import java.util.*;

public abstract class MyMouseEventHandler {

    private static ArrayList<OnMouseEvent> listeners = new ArrayList<>();

    public static void poll(){

        for (OnMouseEvent listener : listeners){

            Vector2f mousePos = new Vector2f(2f * Mouse.getX() / Display.getWidth() - 1f, 2f * Mouse.getY() / Display.getHeight() - 1f);

            boolean inside = listener.isInside(mousePos);
            boolean l_mouse_down = Mouse.isButtonDown(0);
            boolean r_mouse_down = Mouse.isButtonDown(1);

            switch (listener.state){
                case NONE:
                    if(inside&&!l_mouse_down&&!r_mouse_down){
                        listener.onMouseIn(mousePos);
                        listener.state = MouseState.OVER;
                    }
                    break;
                case OVER:
                    if(!inside&&!l_mouse_down&&!r_mouse_down){
                        listener.onMouseOut(mousePos);
                        listener.state = MouseState.NONE;
                    } else if(inside&&l_mouse_down&&!r_mouse_down) {
                        listener.onLeftMouseDown(mousePos);
                        listener.state = MouseState.DRAG_IN_LEFT;
                    } else if(inside&&r_mouse_down&&!l_mouse_down) {
                        listener.onRightMouseDown(mousePos);
                        listener.state = MouseState.DRAG_IN_RIGHT;
                    } else {
                        listener.onMouseOver(mousePos);
                    }
                    break;
                case DRAG_IN_LEFT:
                    if(!inside&&l_mouse_down){
                        listener.state = MouseState.DRAG_OUT_LEFT;
                        listener.onMouseOut(mousePos);
                    } else if(!l_mouse_down&&inside) {
                        listener.state = MouseState.OVER;
                        listener.onLeftMouseUp(mousePos);
                    } else {
                        listener.onLeftMouseDrag(mousePos);
                    }
                    break;
                case DRAG_OUT_LEFT:
                    if(inside&&l_mouse_down){
                        listener.state = MouseState.DRAG_IN_LEFT;
                        listener.onMouseIn(mousePos);
                    } else if(!inside&&!l_mouse_down){
                        listener.state = MouseState.NONE;
                        listener.onLeftMouseDragRelease(mousePos);
                    } else {
                        listener.onLeftMouseDrag(mousePos);
                    }
                    break;
                case DRAG_IN_RIGHT:
                    if(!inside&&r_mouse_down){
                        listener.state = MouseState.DRAG_OUT_RIGHT;
                        listener.onMouseOut(mousePos);
                    } else if(!r_mouse_down&&inside) {
                        listener.state = MouseState.OVER;
                        listener.onRightMouseUp(mousePos);
                    } else {
                        listener.onRightMouseDrag(mousePos);
                    }
                    break;
                case DRAG_OUT_RIGHT:
                    if(inside&&r_mouse_down){
                        listener.state = MouseState.DRAG_IN_RIGHT;
                        listener.onMouseIn(mousePos);
                    } else if(!inside&&!r_mouse_down){
                        listener.state = MouseState.NONE;
                        listener.onRightMouseDragRelease(mousePos);
                    } else {
                        listener.onRightMouseDrag(mousePos);
                    }
                    break;
            }
        }
        while(!toAdd.isEmpty()){
            listeners.add(toAdd.poll());
        }
        while(!toRemove.isEmpty()){
            listeners.remove(toRemove.poll());
        }
    }
    
    private static Queue<OnMouseEvent> toAdd = new LinkedList<>();
    public static void addListener(OnMouseEvent listener){
        toAdd.add(listener);
    }

    private static Queue<OnMouseEvent> toRemove = new LinkedList<>();
    public static void removeListener(OnMouseEvent listener){
        toRemove.add(listener);
    }

    static enum MouseState{
        NONE, OVER, DRAG_IN_LEFT, DRAG_OUT_LEFT, DRAG_IN_RIGHT, DRAG_OUT_RIGHT
    }
}
