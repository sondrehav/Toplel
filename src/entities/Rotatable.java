package entities;


import org.lwjgl.util.vector.Vector2f;

public class Rotatable extends Entity {
    public float rotation;
    protected Vector2f front = new Vector2f(1f,0f);
    protected Vector2f right = new Vector2f(0f,-1f);
    public Vector2f size = new Vector2f(1f,1f);
    public Rotatable(){
        super();
    }

    @Override
    public void event(){
        super.event();
        front.x = (float)Math.cos(Math.toRadians(rotation));
        front.y = (float)Math.sin(Math.toRadians(rotation));
        right.x = (float)Math.cos(Math.toRadians(90f + rotation));
        right.y = (float)Math.sin(Math.toRadians(90f + rotation));
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
