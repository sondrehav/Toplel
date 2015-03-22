package entities;

import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Rotatable extends Entity {
    public float rotation;
    private Vector2f front = new Vector2f();
    public Rotatable(Vector2f pos, float rot){
        super(pos);
        rotation = rot;
    }
    @Override
    public void event(){
        super.event();
        front.x = (float)Math.cos(Math.toRadians(rotation));
        front.y = (float)Math.sin(Math.toRadians(rotation));
    }
}
