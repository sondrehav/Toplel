package com.toplel.context;

import com.toplel.main.OnResize;
import com.toplel.math.MyMatrix4f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MyContext {

    private static HashMap<String, MyContext> active = new HashMap<>();

    OnResize r = new OnResize() {
        @Override
        public void onResize(int width, int height, int old_width, int old_height) {
            recalculate();
        }
    };

    protected final Matrix4f viewMatrix;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewProjection;
    private final Matrix4f viewProjectionInverse;

    public final String nameIdentifier;

    public MyContext(Matrix4f projectionMatrix, Matrix4f viewMatrix, String nameIdentifier) {
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = viewMatrix;
        this.viewProjection = Matrix4f.mul(projectionMatrix, viewMatrix, null);
        this.viewProjectionInverse = Matrix4f.invert(viewProjection, null);
        this.nameIdentifier = nameIdentifier;
        active.put(nameIdentifier, this);
    }

    public static MyContext get(String s){
        if(active.containsKey(s)) return active.get(s);
        return null;
    }

    public void recalculate(){
        Matrix4f.mul(projectionMatrix, viewMatrix, viewProjection);
        Matrix4f.invert(viewProjection, viewProjectionInverse);
    }

    public Matrix4f getViewProjection(){
        return viewProjection;
    }

    /**
     * Calculates position from device coordinates to context coordinates.
     * @param in Device coordinates.
     * @return Context coordinates.
     */
    public Vector2f fromContext(Vector2f in){
        return MyMatrix4f.transformZ(viewProjectionInverse, in, null);
    }

    /**
     * Calculates position from context coordinates to device coordinates.
     * @param in Context coordinates.
     * @return Device coordinates.
     */
    public Vector2f toContext(Vector2f in){
        return MyMatrix4f.transformZ(viewProjection, in, null);
    }

}