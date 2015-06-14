package com.toplel.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class MyMat3 {

    /**
     * This class represent a homogeneous 3x3 matrix for use in 2d graphics. The
     * matrix is stored by a 3x3 float array.
     */

    float[][] matrix = new float[3][3];

    public float getAt(int row, int col){ return matrix[row][col]; }
    public void setAt(int row, int col, float val){ matrix[row][col] = val; }

    public MyMat3(){}

    /**
     * This static method creates a new identity matrix.
     * @return the identity matrix.
     */

    public static MyMat3 getIdentity(){
        MyMat3 m = new MyMat3();
        m.matrix[0][0] = 1f;
        m.matrix[1][1] = 1f;
        m.matrix[2][2] = 1f;
        return m;
    }

    public MyMat3 inverse(){
        MyMat3 m = this.clone();
        m.matrix = MyMath.inverse(m.matrix);
        return m;
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
    public MyMat3 transpose(){
        MyMat3 m = this.clone();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m.matrix[i][j] = this.matrix[j][i];
            }
        }
        return m;
    }

    /**
     * This method takes the input matrix and multiplies it with the current
     * matrix according to the standard rules of matrix multiplication.
     * @param input is the matrix which the current will be multiplied with.
     * @return the new multiplied matrix.
     */
    public MyMat3 mult(MyMat3 input){
        MyMat3 m = new MyMat3();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    m.matrix[i][j] += this.matrix[i][k] * input.matrix[k][j];
                }
            }
        }
        return m;
    }

    /**
     * This method translates the matrix by the input vector.
     * @param input is the translation vector.
     * @return the new translated matrix.
     */
    public MyMat3 translate(MyVec3 input){
        MyMat3 translationMatrix = MyMat3.getIdentity();
        translationMatrix.matrix[0][2] = input.x;
        translationMatrix.matrix[1][2] = input.y;
        return this.mult(translationMatrix);
    }

    public MyMat3 translate(MyVec2 input){
        return this.translate(new MyVec3(input));
    }

    /**
     * This method scales the matrix by the input vector.
     * @param input is the scale vector.
     * @return the new scaled matrix.
     */
    public MyMat3 scale(MyVec3 input){
        MyMat3 scaleMatrix = MyMat3.getIdentity();
        scaleMatrix.matrix[0][0] = input.x;
        scaleMatrix.matrix[1][1] = input.y;
        return this.mult(scaleMatrix);
    }

    public MyMat3 scale(MyVec2 input){
        return this.scale(new MyVec3(input, 1f));
    }

    /**
     * This method rotates the matrix.
     * @param rotation is the angle of rotation in degrees.
     * @return the new rotated matrix.
     */
    public MyMat3 rotate(float rotation){
        MyMat3 rotationMatrix = MyMat3.getIdentity();
        rotationMatrix.matrix[0][0] = (float)Math.cos(Math.toRadians(rotation));
        rotationMatrix.matrix[0][1] = -(float)Math.sin(Math.toRadians(rotation));
        rotationMatrix.matrix[1][0] = (float)Math.sin(Math.toRadians(rotation));
        rotationMatrix.matrix[1][1] = (float)Math.cos(Math.toRadians(rotation));
        return this.mult(rotationMatrix);
    }

    /**
     * This method transforms the input vector by the matrix.
     * @param input is the vector to be transformed.
     * @return the new transformed vector.
     */
    public MyVec3 mult(MyVec3 input){
        MyVec3 v = new MyVec3();
        v.x = matrix[0][0] * input.x + matrix[0][1] * input.y + matrix[0][2] * input.z;
        v.y = matrix[1][0] * input.x + matrix[1][1] * input.y + matrix[1][2] * input.z;
        v.z = matrix[2][0] * input.x + matrix[2][1] * input.y + matrix[2][2] * input.z;
        return v;
    }

    public MyVec2 mult(MyVec2 input){
        MyVec2 v = new MyVec2();
        v.x = matrix[0][0] * input.x + matrix[0][1] * input.y + matrix[0][2] * 1f;
        v.y = matrix[1][0] * input.x + matrix[1][1] * input.y + matrix[1][2] * 1f;
        return v;
    }

    /**
     * Creates a buffer from the matrix.
     * @return the float buffer.
     */
    public FloatBuffer store(){
        FloatBuffer buf = BufferUtils.createFloatBuffer(9);
        buf.put(this.matrix[0][0]);
        buf.put(this.matrix[1][0]);
        buf.put(this.matrix[2][0]);
        buf.put(this.matrix[0][1]);
        buf.put(this.matrix[1][1]);
        buf.put(this.matrix[2][1]);
        buf.put(this.matrix[0][2]);
        buf.put(this.matrix[1][2]);
        buf.put(this.matrix[2][2]);
        return buf;
    }

    /**
     * This matrix creates a standard 2d transformation matrix.
     * @param width Window width.
     * @param height Window height.
     * @return The projection matrix.
     */
    public static MyMat3 projection(float width, float height) {
        MyMat3 m = MyMat3.getIdentity();
        m.matrix[0][0] = 2f / width;
        m.matrix[1][1] = 2f / height;
        m.matrix[0][2] = -1f;
        m.matrix[1][2] = -1f;
        return m;
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
