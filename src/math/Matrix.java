package math;

import org.lwjgl.util.vector.Matrix4f;

public abstract class Matrix {

    public static Matrix4f orthographicProjection(float left, float right, float bottom, float top, float near, float far){

        Matrix4f m = new Matrix4f();
        float x_orth = 2f / (right - left);
        float y_orth = 2f / (top - bottom);
        float z_orth = 2f / (near - far);

        float tx = (left + right) / (left - right);
        float ty = (bottom + top) / (bottom - top);
        float tz = (far + near) / (far - near);

        m.m00 = x_orth;
        m.m11 = y_orth;
        m.m22 = z_orth;
        m.m03 = tx;
        m.m13 = ty;
        m.m23 = tz;
        m.m33 = 1f;

        return m;

    }

}
