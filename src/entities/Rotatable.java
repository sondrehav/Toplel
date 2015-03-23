package entities;

import utils.Vector2f;

public class Rotatable extends Entity {
    public float rotation;
    protected Vector2f front = new Vector2f();
    protected Vector2f right = new Vector2f();
    protected Vector2f size = new Vector2f(1f,1f);
    public Rotatable(){
        super();
    }
    @Override
    public void event(){
        super.event();
        this.rotation++;
        front.x = (float)Math.cos(Math.toRadians(rotation));
        front.y = (float)Math.sin(Math.toRadians(rotation));
        right.x = (float)Math.cos(Math.toRadians(rotation + 90f));
        right.y = (float)Math.sin(Math.toRadians(rotation + 90f));
    }
    public float getRotation() {
        return rotation;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public Vector2f getSize() {
        return size;
    }
    public void setSize(Vector2f size) {
        this.size = size;
    }
}
