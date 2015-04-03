package entities;

import main.Main;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Camera {

    private static Rotatable ent = null;

    public static Vector2f pos = new Vector2f(0f,0f);
    public static float rot;

    public static float zoom = 1f;

    public static Matrix4f viewMat = new Matrix4f();

    public static void setEntity(Rotatable e){
        ent = e;
    }

    public static float rotationoffset = -90f;

    public static void event(){
        viewMat = Matrix4f.setIdentity(new Matrix4f());
        viewMat.scale(new Vector3f(Camera.zoom, Camera.zoom, 1f));
        if(ent==null) return;
        pos = ent.getPosition();
        rot = ent.getRotation() + rotationoffset;
//        viewMat.translate(new Vector3f(-Main.width * .05f,-Main.height * .05f, 0f));
//        viewMat.translate(new Vector3f(-Camera.pos.x * ent.size.x, -Camera.pos.y * ent.size.y, 0f));
//        viewMat.m00 = 1f
        viewMat.rotate((float) Math.toRadians(-Camera.rot), new Vector3f(0f,0f,1f));
        viewMat.translate(new Vector3f(-Camera.pos.x, -Camera.pos.y, 0f));
//        System.out.println("pp = " + pp);
//        System.out.println(":::VIEWMAT:::\n" + viewMat + "\n");
    }

    public static Entity getEntity(){
        return ent;
    }

}
