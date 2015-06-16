package com.toplel.context;

import org.lwjgl.util.vector.Matrix4f;

public class MyHUD extends MyContext {

    public MyHUD(Matrix4f projectionMatrix){
        super(projectionMatrix, Matrix4f.setIdentity(new Matrix4f()), "hud");
    }

}
