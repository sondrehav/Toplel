package math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class MyRegion {

    public float x0, y0, x1, y1;

    public MyRegion(float x0, float y0, float x1, float y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public String toString() {
        return "MyRegion{" +
                "x0=" + x0 +
                ", y0=" + y0 +
                ", x1=" + x1 +
                ", y1=" + y1 +
                '}';
    }

    public MyRegion clone(){
        return new MyRegion(
                this.x0,
                this.y0,
                this.x1,
                this.y1
        );
    }

    public MyRegion mult(MyVec2 vec){
        MyRegion region = this.clone();
        region.x0 = this.x0 * vec.vector[0];
        region.x1 = this.x1 * vec.vector[0];
        region.y0 = this.y0 * vec.vector[1];
        region.y1 = this.y1 * vec.vector[1];
        return region;
    }

    public MyMat3 getProjectionMatrix(){
        MyMat3 matrix = MyMat3.getIdentity();
        MyVec3 scale = new MyVec3(1f / (x1 - x0), 1f / (y1 - y0), 1f);
        matrix = matrix.scale(scale);
        MyVec3 position = new MyVec3(x0, y0, 0f);
        matrix = matrix.translate(position);
        return matrix;
    }

    public static FloatBuffer store(MyRegion region){
        FloatBuffer buf = BufferUtils.createFloatBuffer(4);
        buf.put(new float[]{
                region.x0, region.x1, region.y0, region.y1
        }).flip();
        return buf;
    }

}
