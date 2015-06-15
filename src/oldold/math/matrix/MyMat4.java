package com.toplel.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class MyMat4 {

    /**
     * This class represent a homogeneous 3x3 matrix for use in 2d graphics. The
     * matrix is stored by a 3x3 float array.
     */

    private float[][] matrix = new float[4][4];

    public float getAt(int row, int col){ return matrix[row][col]; }
    public void setAt(int row, int col, float val){ matrix[row][col] = val; }

    public MyMat4 inverse(){
        MyMat4 m = this.clone();
        m.matrix = MyMath.inverse(m.matrix);
        return m;
    }

    public MyMat4(){}

    /**
     * This static method creates a new identity matrix.
     * @return the identity matrix.
     */

    public static MyMat4 getIdentity(){
        MyMat4 m = new MyMat4();
        m.matrix[0][0] = 1f;
        m.matrix[1][1] = 1f;
        m.matrix[2][2] = 1f;
        m.matrix[3][3] = 1f;
        return m;
    }

    /**
     * This method clones the current matrix into a new one.
     * @return a new identical matrix.
     */

    public MyMat4 clone(){
        MyMat4 m = new MyMat4();
        for (int i = 0; i < 16; i++) {
            m.matrix[i] = this.matrix[i];
        }
        return m;
    }

    /**
     * This method transposes the matrix.
     * @return the transposed matrix.
     */
    public MyMat4 transpose(){
        MyMat4 m = this.clone();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
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
    public MyMat4 mult(MyMat4 input){
        MyMat4 m = new MyMat4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
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
    public MyMat4 translate(MyVec3 input){
        MyMat4 translationMatrix = MyMat4.getIdentity();
        translationMatrix.matrix[0][2] = input.x;
        translationMatrix.matrix[1][2] = input.y;
        return this.mult(translationMatrix);
    }

    public MyMat4 translate(MyVec2 input){
        return this.translate(new MyVec3(input));
    }

    /**
     * This method scales the matrix by the input vector.
     * @param input is the scale vector.
     * @return the new scaled matrix.
     */
    public MyMat4 scale(MyVec3 input){
        MyMat4 scaleMatrix = MyMat4.getIdentity();
        scaleMatrix.matrix[0][0] = input.x;
        scaleMatrix.matrix[1][1] = input.y;
        scaleMatrix.matrix[2][2] = input.y;
        return this.mult(scaleMatrix);
    }

    public MyMat4 scale(MyVec2 input){
        return this.scale(new MyVec3(input, 1f));
    }

    /**
     * This method rotates the matrix.
     * @param rotation is the angle of rotation in degrees.
     * @return the new rotated matrix.
     */
    public MyMat4 rotateZ(float rotation){
        MyMat4 rotationMatrix = MyMat4.getIdentity();
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
     * @param matrix is the input matrix.
     * @return the float buffer.
     */
    public static FloatBuffer store(MyMat4 matrix){
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        buf.put(matrix.matrix[0][0]);
        buf.put(matrix.matrix[1][0]);
        buf.put(matrix.matrix[2][0]);
        buf.put(matrix.matrix[3][0]);
        buf.put(matrix.matrix[0][1]);
        buf.put(matrix.matrix[1][1]);
        buf.put(matrix.matrix[2][1]);
        buf.put(matrix.matrix[3][1]);
        buf.put(matrix.matrix[0][2]);
        buf.put(matrix.matrix[1][2]);
        buf.put(matrix.matrix[2][2]);
        buf.put(matrix.matrix[3][2]);
        buf.put(matrix.matrix[0][3]);
        buf.put(matrix.matrix[1][3]);
        buf.put(matrix.matrix[2][3]);
        buf.put(matrix.matrix[3][3]);
        return buf;
    }

    /**
     * This matrix creates a standard 2d transformation matrix.
     * @param width Window width.
     * @param height Window height.
     * @return The projection matrix.
     */
    public static MyMat4 projection(float width, float height, float far, float near) {
        MyMat4 m = MyMat4.getIdentity();
        m.matrix[0][0] = 2f / width;
        m.matrix[1][1] = 2f / height;
        m.matrix[0][3] = -1f;
        m.matrix[1][3] = -1f;
        m.matrix[2][2] = 2f / (near - far);
        m.matrix[2][3] = (far + near) / (far - near);
        return m;
    }

    @Override
    public String toString() {
        String s = "MyMat3{\n";
        for (int i = 0; i < 3; i++) {
            s += "\t{"+matrix[0+4*i]+","+matrix[1+4*i]+","+matrix[2+4*i]+","+matrix[3+4*i]+"}"+"\n";
        }
        s+="}";
        return s;
    }
}
