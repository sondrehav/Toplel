package com.toplel.main;

import com.toplel.math.MyMatrix4f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

public abstract class MyContext {

    private static ArrayList<MyContext> active = new ArrayList<>();

    private final Matrix4f projectionMatrix;
    protected final Matrix4f viewMatrix;
    private final Matrix4f viewProjection;
    private final Matrix4f viewProjectionInverse;

    public MyContext(Matrix4f projectionMatrix, Matrix4f viewMatrix){
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = viewMatrix;
        this.viewProjection = Matrix4f.mul(projectionMatrix, viewMatrix, null);
        this.viewProjectionInverse = Matrix4f.invert(viewProjection, null);
        active.add(this);
    }

    public void recalculate(){
        Matrix4f.mul(projectionMatrix, viewMatrix, viewProjection);
        Matrix4f.invert(viewProjection, viewProjectionInverse);
    }

    public Matrix4f getViewProjection(){
        return viewProjection;
    }

    public Vector2f fromContext(Vector2f in){
        return MyMatrix4f.transformZ(viewProjectionInverse, in, null);
    }

    public Vector2f toContext(Vector2f in){
        return MyMatrix4f.transformZ(viewProjection, in, null);
    }

}
