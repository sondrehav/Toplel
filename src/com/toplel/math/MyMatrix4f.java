package com.toplel.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class MyMatrix4f {

    public static Vector3f transform(Matrix4f left, Vector3f right, Vector3f dest) {
        if(dest == null) {
            dest = new Vector3f();
        }
        float x = left.m00 * right.x + left.m10 * right.y + left.m20 * right.z + left.m30 * 1f;
        float y = left.m01 * right.x + left.m11 * right.y + left.m21 * right.z + left.m31 * 1f;
        float z = left.m02 * right.x + left.m12 * right.y + left.m22 * right.z + left.m32 * 1f;
        dest.x = x;
        dest.y = y;
        dest.z = z;
        return dest;
    }

    public static Vector2f transformZ(Matrix4f left, Vector2f right, Vector2f dest) {
        if(dest == null) {
            dest = new Vector2f();
        }
        float x = left.m00 * right.x + left.m10 * right.y + left.m20 * 0f + left.m30 * 1f;
        float y = left.m01 * right.x + left.m11 * right.y + left.m21 * 0f + left.m31 * 1f;
        dest.x = x;
        dest.y = y;
        return dest;
    }

    public static Matrix4f scaleXY(Vector2f scale, Matrix4f left, Matrix4f dest) {
        return Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), left, dest);
    }

    public static Matrix4f orthographicProjection(float left, float right, float top, float bottom, float far, float near, Matrix4f dest){
        if(dest==null) dest = new Matrix4f();
        Matrix4f.setIdentity(dest);
        dest.m00 = 2f / (right - left);
        dest.m11 = 2f / (top - bottom);
        dest.m22 = 2f / (near - far);
        dest.m33 = 1f;
        dest.m30 = -(right + left) / (right - left);
        dest.m31 = (top + bottom) / (bottom - top);
        dest.m32 = (far + near) / (far - near);
        return dest;
    }

}
