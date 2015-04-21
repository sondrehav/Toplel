package entities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Camera {

    public static Vector2f pos = new Vector2f(0f,0f);
    public static float rot;
    public static float zoom = 1f;

    private static Matrix4f viewMat = new Matrix4f();

    public static Matrix4f getViewMatrix(){
        Matrix4f.setIdentity(viewMat);
        viewMat.scale(new Vector3f(Camera.zoom, Camera.zoom, 1f));
        viewMat.rotate((float) Math.toRadians(-Camera.rot), new Vector3f(0f,0f,1f));
        viewMat.translate(new Vector3f(-Camera.pos.x, -Camera.pos.y, 0f));
        return new Matrix4f(viewMat);
    }

}
