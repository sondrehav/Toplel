package entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public abstract class Camera {

    private static Rotatable ent = null;

    public static float height = 100f;
    public static Vector2f pos = new Vector2f(0f,0f);
    public static float rot;

    public static void setEntity(Rotatable e){
        ent = e;
    }

    public static void event(){
        if(ent==null) return;
        pos = ent.getPosition();
        rot = ent.getRotation();
        System.out.println("pos = " + pos);
        System.out.println("rot = " + rot);
    }

    public static Entity getEntity(){
        return ent;
    }

}
