package com.toplel.event.mouse;

import com.toplel.math.MyMat3;
import com.toplel.math.MyRegion;
import com.toplel.math.MyVec2;
import com.toplel.math.MyVec3;

public abstract class MyMouseListener {

    MyGetRegion region;
    MyMouseEventHandler.MouseState state = MyMouseEventHandler.MouseState.NONE;
    int button;

    private MyMat3 vw = null;

    public MyMouseListener(MyGetRegion region, int button){
        this.region = region;
        this.button = button;
    }

    MyVec2 getTransformedMouse(MyVec2 mousepos){
        if(vw==null) return mousepos;
        return new MyVec2(vw.mult(new MyVec3(mousepos, 1f)));
    }

    public void setViewMatrix(MyMat3 viewMatrix){
        vw = viewMatrix;
    }

    public void onMouseIn(float mx, float my){}
    public void onMouseOver(float mx, float my){}
    public void onMouseOut(float mx, float my){}
    public void onMouseDown(float mx, float my){}
    public void onMouseUp(float mx, float my){}
    public void onMouseDrag(float mx, float my){}
    public void onMouseDragRelease(float mx, float my){}

}
