package entities;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;

public class Entity {

    public Vector2f position = new Vector2f(0f,0f);

    public Entity() {
    }

    public void event(){}

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    @Override
    public String toString() {

        return "Entity{" +
                "position=" + position +
                '}';
    }

    public void destroy(){};
}
