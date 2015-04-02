package entities;

import org.lwjgl.util.vector.Vector2f;

public abstract class Camera {

    private static Rotatable ent = null;

    public static float height = 0;
    public static Vector2f pos = new Vector2f(0f,0f);
    public static float rot;

    public static void setEntity(Rotatable e){
        ent = e;
    }

    public static void event(){
        if(ent==null) return;
        pos = ent.getPosition();
        rot = ent.getRotation();
    }

    public static Entity getEntity(){
        return ent;
    }

}
