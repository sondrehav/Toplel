package com.toplel.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class MyMat3 {

    private float[][] matrix = new float[3][3];

    public MyMat3(){}

    /**
     * This class represent a homogeneous 3x3 matrix for use in 2d graphics. The
     * matrix is stored by a 3x3 float array.
     */

//    private float[][] inverseMatrix = new float[3][3];

    public float getAt(int row, int col){
        return matrix[row][col];
    }
    public void setAt(int row, int col, float val){
        matrix[row][col] = val;
//        inverseMatrix = inverse().matrix;
    }


    /**
     * This static method creates a new identity matrix.
     * @return the identity matrix.
     */

    public static MyMat3 setIdentity(MyMat3 dest){
        if(dest==null) dest = new MyMat3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dest.matrix[i][j] = 0f;
                if(i==j) dest.matrix[i][j] = 1f;
            }
        }
        return dest;
    }

    public static MyMat3 inverse(MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        dest.matrix = MyMath.inverse(src.matrix);
        return dest;
    }

    /**
     * This method clones the current matrix into a new one.
     * @return a new identical matrix.
     */

    public MyMat3 clone(){
        MyMat3 m = new MyMat3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m.matrix[i][j] = this.matrix[i][j];
            }
        }
        return m;
    }

    /**
     * This method transposes the matrix.
     * @return the transposed matrix.
     */
    public static MyMat3 transpose(MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dest.matrix[i][j] = src.matrix[j][i];
            }
        }
        return dest;
    }

    /**
     * This method takes the input matrix and multiplies it with the current
     * matrix according to the standard rules of matrix multiplication.
     * @return the new multiplied matrix.
     */
    public static MyMat3 mult(MyMat3 src, MyMat3 other, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    dest.matrix[i][j] += src.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return dest;
    }

    /**
     * This method translates the matrix by the input vector.
     * @param input is the translation vector.
     * @return the new translated matrix.
     */
    public static MyMat3 translate(MyVec3 input, MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        dest.matrix[0][2] = input.x;
        dest.matrix[1][2] = input.y;
        mult(src, dest, dest);
        return dest;
    }

    /**
     * This method scales the matrix by the input vector.
     * @param input is the scale vector.
     * @return the new scaled matrix.
     */
    public static MyMat3 scale(MyVec3 input, MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        dest.matrix[0][0] = input.x;
        dest.matrix[1][1] = input.y;
        dest.matrix[2][2] = input.z;
        mult(src, dest, dest);
        return dest;
    }

    public static MyMat3 scale(MyVec2 input, MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        dest.matrix[0][0] = input.x;
        dest.matrix[1][1] = input.y;
        dest.matrix[2][2] = 1f;
        mult(src, dest, dest);
    }

    /**
     * This method rotates the matrix.
     * @param rotation is the angle of rotation in degrees.
     * @return the new rotated matrix.
     */
    public static MyMat3 rotate(float rotation, MyMat3 src, MyMat3 dest){
        if(dest == null) dest = new MyMat3();
        float cos = (float)Math.cos(Math.toRadians(rotation));
        float sin = (float)Math.cos(Math.toRadians(rotation));
        dest.matrix[0][0] = cos;
        dest.matrix[0][1] = -sin;
        dest.matrix[1][0] = sin;
        dest.matrix[1][1] = cos;
        mult(src, dest, dest);
    }

    /**
     * This method transforms the input vector by the matrix.
     * @return the new transformed vector.
     */
    public static MyVec3 mult(MyMat3 matrix, MyVec3 src, MyVec3 dest){
        if(dest == null) dest = new MyVec3();
        dest.x = matrix.matrix[0][0] * src.x + matrix.matrix[0][1] * src.y + matrix.matrix[0][2] * src.z;
        dest.y = matrix.matrix[1][0] * src.x + matrix.matrix[1][1] * src.y + matrix.matrix[1][2] * src.z;
        dest.z = matrix.matrix[2][0] * src.x + matrix.matrix[2][1] * src.y + matrix.matrix[2][2] * src.z;
    }

    public static MyVec2 mult(MyMat3 matrix, MyVec2 src, MyVec2 dest){
        if(dest == null) dest = new MyVec2();
        dest.x = matrix.matrix[0][0] * src.x + matrix.matrix[0][1] * src.y + matrix.matrix[0][2];
        dest.y = matrix.matrix[1][0] * src.x + matrix.matrix[1][1] * src.y + matrix.matrix[1][2];
    }

    /**
     * Creates a buffer from the matrix.
     * @return the float buffer.
     */
    public static FloatBuffer store(MyMat3 input){
        FloatBuffer buf = BufferUtils.createFloatBuffer(9);
        buf.put(input.matrix[0][0]);
        buf.put(input.matrix[1][0]);
        buf.put(input.matrix[2][0]);
        buf.put(input.matrix[0][1]);
        buf.put(input.matrix[1][1]);
        buf.put(input.matrix[2][1]);
        buf.put(input.matrix[0][2]);
        buf.put(input.matrix[1][2]);
        buf.put(input.matrix[2][2]);
        return buf;
    }

    /**
     * This matrix creates a standard 2d transformation matrix.
     * @param width Window width.
     * @param height Window height.
     * @return The projection matrix.
     */
    public static MyMat3 projection(float width, float height, MyMat3 dest) {
        if(dest == null) dest = new MyMat3();
        dest.matrix[0][0] = 2f / width;
        dest.matrix[1][1] = 2f / height;
        dest.matrix[0][2] = -1f;
        dest.matrix[1][2] = -1f;
    }

    @Override
    public String toString() {
        String s = "MyMat3{\n";
        for (int i = 0; i < 3; i++) {
            s += "\t"+Arrays.toString(matrix[i])+"\n";
        }
        s+="}";
        return s;
    }
}
