package oldold.old.utils.helpers;

import org.lwjgl.util.vector.Matrix4f;

public abstract class MatUtil {

    public static Matrix4f projection(float l, float r, float t, float b, float n, float f){

        Matrix4f mat = Matrix4f.setIdentity(new Matrix4f());

        float w = r - l;
        float h = t - b;

        mat.m00 = h / (w + h);
        mat.m11 = w / (w + h);

        return mat;

    }

}
