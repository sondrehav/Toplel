package entities;

import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Entity {

    public Vector2f position = null;

    public Entity(Vector2f pos) {
        position = pos;
    }

    public void event(){};

    @Override
    public String toString() {
        return "Entity{" +
                "position=" + position +
                '}';
    }
}
