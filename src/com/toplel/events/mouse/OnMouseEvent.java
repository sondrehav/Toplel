package com.toplel.events.mouse;

import org.lwjgl.util.vector.Vector2f;

public abstract class OnMouseEvent implements MyMouseListener, Comparable<OnMouseEvent> {

    MyMouseEventHandler.MouseState state = MyMouseEventHandler.MouseState.NONE;
    int layer;
    IsInside entity;

    public OnMouseEvent(IsInside entity){
        this(entity, 0);
    }

    public OnMouseEvent(IsInside entity, int layer){
        this.layer = layer;
        this.entity = entity;
        MyMouseEventHandler.addListener(this);
    }

    final boolean isInside(Vector2f vector){
        return entity.isInside(vector);
    }

    public OnMouseEvent(int button, IsInside entity){
        this(entity, 0);
    }

    final public void disable(){
        MyMouseEventHandler.removeListener(this);
    }

    final public void enable(){
        MyMouseEventHandler.addListener(this);
    }

    /*public void abortDrag(){
        if(isDragging()){

        }
    }*/

    final public boolean isDragging(){
        if(state == MyMouseEventHandler.MouseState.DRAG_IN_LEFT||
                state == MyMouseEventHandler.MouseState.DRAG_IN_RIGHT) return true;
        if(state == MyMouseEventHandler.MouseState.DRAG_OUT_LEFT||
                state == MyMouseEventHandler.MouseState.DRAG_OUT_RIGHT) return true;
        return false;
    }

    @Override
    final public int compareTo(OnMouseEvent o) {
        int change1 = o.layer;
        int change2 = this.layer;
        if (change1 < change2) return -1;
        if (change1 > change2) return 1;
        return 0;
    }
}
