package math;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import utils.ShaderProgram;

import java.nio.FloatBuffer;
import java.util.Stack;

public abstract class Matrix {

    private static Matrix4f modelMatrix = new Matrix4f();
    private static Matrix4f viewMatrix = new Matrix4f();
    private static Matrix4f projectionMatrix = new Matrix4f();

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private static Stack<Matrix4f> matrixStack = new Stack<Matrix4f>();

    public static void pushMatrix(){
        matrixStack.push(new Matrix4f(modelMatrix));
    }

    public static void popMatrix(){
        modelMatrix = matrixStack.pop();
    }

    public static void resetMatrices(){
        viewMatrix.setZero();
        modelMatrix.setZero();
    }

    public static void setModelMatrix(Vector2f pos, float rot, Vector2f scale, float height){

        // PUSH

        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), modelMatrix, modelMatrix);
        Matrix4f.translate(new Vector3f(pos.x, pos.y, height), modelMatrix, modelMatrix);
        Matrix4f.rotate((float)Math.toRadians(rot), new Vector3f(0f,0f,1f), modelMatrix, modelMatrix);

        // POP

    }

    public static void setViewMatrix(Vector2f pos, float height, float rot){

        Matrix4f.rotate(rot, new Vector3f(0f,0f,1f), viewMatrix, viewMatrix);
        Matrix4f.translate(new Vector3f(pos.x, pos.y, height), viewMatrix, viewMatrix);

    }

    public static void flushMatrix(){

    }

    public static void loadIdentity(Matrix4f matrix){

    }

    public static void uploadMatrix(ShaderProgram program){
        projectionMatrix.store(matrixBuffer); matrixBuffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrixBuffer);
        viewMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
        modelMatrix.store(matrix44Buffer); matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
    }

    public static void setupMatrices(float fieldOfView, int width, int height, float near_plane, float far_plane){

        float aspectRatio = (float)width / (float)height;

        float y_scale = 1f/(float)Math.tan(Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        System.out.println(projectionMatrix);

    }

    public static int stackSize(){ return matrixStack.size(); }

}
