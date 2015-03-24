package entities;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Entity {

    public Vector2f position = new Vector2f(1f,1f);
    public Matrix3f modelViewMatrix = new Matrix3f();

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
}
