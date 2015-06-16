package com.toplel.components;

import com.toplel.context.MyContext;
import com.toplel.events.mouse.IsInside;
import com.toplel.math.MyMatrix4f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MyPosition implements IsInside {

    private final Matrix4f modelMatrix;
    private final Matrix4f inv_modelMatrix;
    private final Vector2f position;
    private final Vector2f size;
    private float rotation;

    protected MyPosition(Vector2f position, float rotation, Vector2f size){
        this.position = position;
        this.rotation = rotation;
        this.size = size;
        this.modelMatrix = new Matrix4f();
        this.inv_modelMatrix = new Matrix4f();
        calcModelMatrix();
    }

    private void calcModelMatrix(){
        Matrix4f.setIdentity(this.modelMatrix);
        Matrix4f.translate(this.position, this.modelMatrix, this.modelMatrix);
        Matrix4f.translate(new Vector2f(this.size.x * .5f, this.size.y * .5f), this.modelMatrix, this.modelMatrix);
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 0f, 1f), this.modelMatrix, this.modelMatrix);
        Matrix4f.translate(new Vector2f(-this.size.x * .5f, -this.size.y * .5f), this.modelMatrix, this.modelMatrix);
        Matrix4f.scale(new Vector3f(this.size.x, this.size.y, 1f), this.modelMatrix, this.modelMatrix);
//        Matrix4f.translate(new Vector2f(this.size.x * .5f, this.size.y * .5f), this.modelMatrix, this.modelMatrix);
        Matrix4f.invert(modelMatrix, inv_modelMatrix);
    }

    public void setPosition(Vector2f position){
        this.position.x = position.x;
        this.position.y = position.y;
        calcModelMatrix();
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
        calcModelMatrix();
    }

    public void setSize(Vector2f size){
        this.size.x = size.x;
        this.size.y = size.y;
        calcModelMatrix();
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public Vector2f getSize() {
        return new Vector2f(size);
    }

    public float getRotation() {
        return rotation;
    }

    public Matrix4f getModelMatrix(){
        return new Matrix4f(modelMatrix);
    }

    public Matrix4f getInvModelMatrix(){
        return new Matrix4f(inv_modelMatrix);
    }

    private MyContext context = null;

    public MyContext getContext() {
        return context;
    }

    public void setContext(MyContext context) {
        this.context = context;
    }

    private boolean isCoordsInside(Vector2f deviceCoords, MyContext inContext){
        Vector2f f = inContext.fromContext(deviceCoords);
        MyMatrix4f.transformZ(getInvModelMatrix(), f, f);
        if(f.x>0&&f.x<1&&f.y>0&&f.y<1) return true;
        return false;
    }

    @Override
    public boolean isInside(Vector2f position) {
        if(context == null) return false;
        return isCoordsInside(position, context);
    }

}
