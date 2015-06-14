package com.toplel.event.mouse;

import com.toplel.main.MyContext;
import com.toplel.math.MyMat3;

public abstract class MyMouseListener {

    MyListenerInstance instance;
    MyMouseEventHandler.MouseState state = MyMouseEventHandler.MouseState.NONE;
    int button;

    public MyMouseListener(MyListenerInstance instance, int button){
        this.instance = instance;
        this.button = button;
    }

    public void onMouseIn(float mx, float my){}
    public void onMouseOver(float mx, float my){}
    public void onMouseOut(float mx, float my){}
    public void onMouseDown(float mx, float my){}
    public void onMouseUp(float mx, float my){}
    public void onMouseDrag(float mx, float my){}
    public void onMouseDragRelease(float mx, float my){}

}
