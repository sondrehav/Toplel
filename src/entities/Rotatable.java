package entities;

import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Rotatable extends Entity {
    public float rotation;
    protected Vector2f front = new Vector2f();
    protected Vector2f right = new Vector2f();
    public Rotatable(Vector2f pos, float rot){
        super(pos);
        rotation = rot;
    }
    @Override
    public void event(){
        super.event();
//        if(this.getClass()!=Player.class){
//            System.out.println("Rotatable.event");
//            rotation++;
//        }
        front.x = (float)Math.cos(Math.toRadians(rotation));
        front.y = (float)Math.sin(Math.toRadians(rotation));
        right.x = (float)Math.cos(Math.toRadians(rotation + 90f));
        right.y = (float)Math.sin(Math.toRadians(rotation + 90f));
    }
}
