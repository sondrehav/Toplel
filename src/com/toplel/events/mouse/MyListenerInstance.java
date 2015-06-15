package com.toplel.events.mouse;

import com.toplel.main.MyContext;
import org.lwjgl.util.vector.Matrix4f;

public interface MyListenerInstance {

    public Matrix4f getInvertedModelMatrix();
    public MyContext getContext();

}
