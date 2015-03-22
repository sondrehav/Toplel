package entities;

import org.lwjgl.opengl.GL11;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public abstract class Camera {

    private static Rotatable ent = null;

    public static float height = 100f;

    public static void setEntity(Rotatable e){
        ent = e;
    }

    static float r = 0f;
    public static void transform(){
        r++;
        GL11.glRotatef(ent.rotation, 0f, 0f, 1f);
        GL11.glRotatef(90f+r, 0f, 1f, 0f);
        GL11.glRotatef(90f, 1f, 0f, 0f);
        GL11.glTranslatef(-ent.position.x, -ent.position.y, -height);
    }

}
