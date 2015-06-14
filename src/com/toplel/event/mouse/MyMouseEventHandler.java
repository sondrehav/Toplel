package com.toplel.event.mouse;

import com.toplel.math.MyRegion;
import com.toplel.math.MyVec2;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class MyMouseEventHandler {

    private static ArrayList<MyMouseListener> listeners = new ArrayList<>();

    public static void poll(){
        for (MyMouseListener listener : listeners){
            MyVec2 mousePos = MyMousePos.getPos(listener.instance.getContext());
//            System.out.println(mousePos);
//            System.out.println("mousePos = " + mousePos);
            MyRegion orig_region = listener.instance.getRegion();
            MyVec2 r0 = listener.instance.getContext().getView().mult(orig_region.vec_a);
            MyVec2 r1 = listener.instance.getContext().getView().mult(orig_region.vec_b);
            MyRegion region = new MyRegion(r0, r1);
            System.out.println("region = " + region);
            boolean inside = region.isInside(mousePos);
//            System.out.println(inside);
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
