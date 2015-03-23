package entities;

import org.lwjgl.opengl.GL11;

public abstract class Camera {

    private static Rotatable ent = null;

    public static float height = 100f;

    public static void setEntity(Rotatable e){
        ent = e;
    }

    public static void transform(){
//        GL11.glScalef(1f/ent.size.x,1f/ent.size.y,1f);
//        GL11.glRotatef(ent.rotation, 0f, 0f, 1f);
//        GL11.glTranslatef(-ent.position.x, -ent.position.y, -height);
        GL11.glTranslatef(0f,0f, -height);
    }

    public static Entity getEntity(){
        return ent;
    }

}
