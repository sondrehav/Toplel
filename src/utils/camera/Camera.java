package utils.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Camera {
    static Vector2f pos = new Vector2f();
    static float rotation = 0f;
    static float zoom = 1f;
    private static Matrix4f mat = new Matrix4f();
    public static Matrix4f getViewMatrix(){
        Matrix4f.setIdentity(mat);
        mat.scale(new Vector3f(zoom, zoom, 1f));
        mat.rotate((float)Math.toRadians(rotation), new Vector3f(0f,0f,1f));
        mat.translate(new Vector3f(-pos.x, -pos.y, 0f));
        return new Matrix4f(mat);
    }
}
