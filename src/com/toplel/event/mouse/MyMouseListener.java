package com.toplel.event.mouse;

import com.toplel.math.MyRegion;

public abstract class MyMouseListener {

    MyRegion region;
    MyMouseEventHandler.MouseState state = MyMouseEventHandler.MouseState.NONE;
    int button;

    public MyMouseListener(MyRegion region, int button){
        this.region = region;
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
