package com.toplel.events.mouse;

import org.lwjgl.util.vector.Vector2f;

interface MyMouseListener {

    default public void onMouseIn(Vector2f mouse){}
    default public void onMouseOver(Vector2f mouse){}
    default public void onMouseOut(Vector2f mouse){}

    default public void onLeftMouseDown(Vector2f mouse){}
    default public void onLeftMouseUp(Vector2f mouse){}

    default public void onRightMouseDown(Vector2f mouse){}
    default public void onRightMouseUp(Vector2f mouse){}

    default public void onLeftMouseDrag(Vector2f mouse){}
    default public void onLeftMouseDragRelease(Vector2f mouse){}

    default public void onRightMouseDrag(Vector2f mouse){}
    default public void onRightMouseDragRelease(Vector2f mouse){}

}
