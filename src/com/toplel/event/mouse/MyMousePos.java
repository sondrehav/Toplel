package com.toplel.event.mouse;

import com.toplel.main.MyContext;
import com.toplel.math.MyRegion;
import com.toplel.math.MyVec2;
import com.toplel.math.MyVec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class MyMousePos {

    public static MyVec2 getPos(MyContext context){
        MyVec2 mousePos = new MyVec2(2f * (float)Mouse.getX() / Display.getWidth() - 1f, 2f * (float)Mouse.getY() / Display.getHeight() - 1f);
        MyVec3 h_mousePos = new MyVec3(mousePos, 1f);
        h_mousePos = context.getView().inverse().mult(h_mousePos);
//        h_mousePos = context.getProjection().inverse().mult(h_mousePos);
        return new MyVec2(h_mousePos);
    }

    public static MyRegion getRegion(MyListenerInstance instance){
        return null;
    }

}
