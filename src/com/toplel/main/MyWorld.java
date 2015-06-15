package com.toplel.main;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MyWorld extends MyContext {

    // TODO: Set rotation, fix scale from middle

    public MyWorld(Matrix4f projectionMatrix, Matrix4f viewMatrix){
        super(projectionMatrix, viewMatrix);
    }

    private Vector2f position = new Vector2f(0f, 0f);
    private Vector2f size = new Vector2f(1f, 1f);
    private float rotation = 0f;

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public Vector2f getSize() {
        return new Vector2f(size);
    }

    public float getRotation() {
        return rotation;
    }

    public void setPosition(Vector2f position){
        Vector2f negate = new Vector2f(-this.position.x, -this.position.y);
        Matrix4f.translate(negate, viewMatrix, viewMatrix);
        Matrix4f.translate(position, viewMatrix, viewMatrix);
        this.position = position;
        recalculate();
    }

    public void setSize(Vector2f size){

//        System.out.println("size = " + size);
//        System.out.println("this.size = " + this.size);

        Vector2f negatePos = new Vector2f(-this.position.x, -this.position.y);
        Matrix4f.translate(negatePos, viewMatrix, viewMatrix);

        Vector3f negate = new Vector3f(1f / this.size.x, 1f / this.size.y, 1f);
        System.out.println("viewMatrix = " + viewMatrix);
        Matrix4f.scale(negate, viewMatrix, viewMatrix);
        Matrix4f.scale(new Vector3f(size.x, size.y, 1f), viewMatrix, viewMatrix);
        this.size = size;

        Matrix4f.translate(this.position, viewMatrix, viewMatrix);
        recalculate();
    }

    public void setRotation(float rotation){
        float rad = (float) Math.toRadians(rotation);
        float negate = (float) Math.toRadians(this.rotation);
        Matrix4f.rotate(negate, new Vector3f(0f,0f,1f), viewMatrix, viewMatrix);
        Matrix4f.rotate(rad, new Vector3f(0f,0f,1f), viewMatrix, viewMatrix);
        this.rotation = rotation;
        System.out.println("negate = " + negate);
        System.out.println("rad = " + rad);
        recalculate();
    }

}
