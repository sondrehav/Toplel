package com.toplel.context;

import com.toplel.main.OnResize;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MyWorld extends MyContext {

    // TODO: Set rotation, fix scale from middle

    public MyWorld(Matrix4f projectionMatrix, Matrix4f viewMatrix){
        super(projectionMatrix, viewMatrix, "world");
    }

    private Vector2f position = new Vector2f(0f, 0f);
    private Vector2f size = new Vector2f(1f, 1f);
    private float rotation = 0f;

    private boolean centered = true;

//    private Vector2f centerScreenVector = new Vector2f();

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public Vector2f getSize() {
        return new Vector2f(size);
    }

    public float getRotation() {
        return rotation;
    }

    public void addPosition(Vector2f addPosition){
        Vector2f.add(position, addPosition, position);
        Matrix4f.translate(new Vector2f(-addPosition.x, -addPosition.y), viewMatrix, viewMatrix);
        projectionMatrix.m30 = 0f;
        projectionMatrix.m31 = 0f;
        recalculate();
    }

    public void setPosition(Vector2f position){
        Matrix4f.translate(this.position, viewMatrix, viewMatrix); // Reset
        Matrix4f.translate(new Vector2f(-position.x, -position.y), viewMatrix, viewMatrix);
        this.position = position;
        recalculate();
    }

    public void setSize(Vector2f size){

        Matrix4f.translate(this.position, viewMatrix, viewMatrix);

        Vector3f negate = new Vector3f(1f / this.size.x, 1f / this.size.y, 1f);
        Matrix4f.scale(negate, viewMatrix, viewMatrix);
        Matrix4f.scale(new Vector3f(size.x, size.y, 1f), viewMatrix, viewMatrix);
        this.size = size;

        Matrix4f.translate(new Vector2f(-this.position.x, -this.position.y), viewMatrix, viewMatrix);
        recalculate();
    }

    public void setRotation(float rotation){
        float rad = (float) Math.toRadians(rotation);
        float negate = (float) Math.toRadians(this.rotation);
        Matrix4f.rotate(negate, new Vector3f(0f,0f,1f), viewMatrix, viewMatrix);
        Matrix4f.rotate(rad, new Vector3f(0f, 0f, 1f), viewMatrix, viewMatrix);
        this.rotation = rotation;
        recalculate();
    }

    OnResize onResize = new OnResize() {
        @Override
        public void onResize(int width, int height, int old_width, int old_height) {
//            centerScreenVector.x = -(float)width*.5f;
//            centerScreenVector.y = -(float)height*.5f;
            recalculate();
        }
    };

}
