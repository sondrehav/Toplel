package main;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import utils.ShaderProgram;

import java.io.IOException;
import java.nio.FloatBuffer;

public class Test {

    static int width = 800, height = 600;

    static ShaderProgram sp = null;

    public static void main(String[] args){

        try{
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try{
            sp = ShaderProgram.addShader("res/shader/vertTest.vs", "res/shader/fragTest.fs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //setupMatrices();
        GL11.glViewport(0,0,width,height);
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f); // BG

        Quad q = new Quad();

        while(!Display.isCloseRequested()){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            sp.bind();

            q.draw();

            GL20.glUseProgram(0);

            Display.update();
            Display.sync(20);
        }

    }

    public static class Quad{
        FloatBuffer buf = BufferUtils.createFloatBuffer(18);
        int id;
        public Quad(){
            buf.put(new float[]{0f,0f,0f,1f,0f,0f,1f,1f,0f,0f,0f,0f,1f,1f,0f,0f,1f,0f});
            buf.flip();
            id = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        }
        public void draw(){
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
            Matrix4f viewMat = new Matrix4f();
            viewMat.translate(new Vector3f(0f,0f,-30f), viewMat);
            uploadMatrix(sp, Matrix4f.setIdentity(new Matrix4f()),
                    viewMat,
                    Matrix4f.setIdentity(new Matrix4f()));
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 12);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        }
    }

    static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public static void uploadMatrix(ShaderProgram program, Matrix4f projectionMatrix, Matrix4f viewMatrix, Matrix4f modelMatrix){
        projectionMatrix.store(matrixBuffer); matrixBuffer.flip();
        GL20.glUniformMatrix4(program.getProjMatLoc(), false, matrixBuffer);
        viewMatrix.store(matrixBuffer); matrixBuffer.flip();
        GL20.glUniformMatrix4(program.getViewMatLoc(), false, matrixBuffer);
        modelMatrix.store(matrixBuffer); matrixBuffer.flip();
        GL20.glUniformMatrix4(program.getModelMatLoc(), false, matrixBuffer);
    }

//    private static ArrayList<Point> points = new ArrayList<Point>();
//    private static class Point{
//
//        Vector3f pos = new Vector3f();
//
//        static FloatBuffer buf = null;
//
//        static int vbuf;
//
//        static ShaderProgram shader = null;
//
//        Point(float x, float y, float z){
//            if(shader == null){
//                try{
//                    shader = ShaderProgram.addShader(
//                            "res/shader/vertTest.vs",
//                            "res/shader/fragTest.fs");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            pos.x = x; pos.y = y; pos.z = z;
//            if(buf == null){
//                buf = new BufferUtils().createFloatBuffer(72);
//                for(int angle = 0; angle < 8; angle++){
//                    float x0 = (float) Math.cos(intToAngle(angle));
//                    float y0 = (float) Math.sin(intToAngle(angle));
//                    float x1 = (float) Math.cos(intToAngle(angle+1));
//                    float y1 = (float) Math.sin(intToAngle(angle+1));
//                    float z_ = 0f;
//                    buf.put(new float[]{x0, y0, z, x1, y1, z, 0f, 0f, z_});
//                }
//                buf.flip();
//                vbuf = GL15.glGenBuffers();
//                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbuf);
//                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
//                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//            }
//
//        }
//
//        public static void uploadMatrix(ShaderProgram program){
//            projectionMatrix.store(matrixBuffer); matrixBuffer.flip();
//            GL20.glUniformMatrix4(program.getProjMatLoc(), false, matrixBuffer);
//            viewMatrix.store(matrixBuffer); matrixBuffer.flip();
//            GL20.glUniformMatrix4(program.getViewMatLoc(), false, matrixBuffer);
//            modelMatrix.store(matrixBuffer); matrixBuffer.flip();
//            GL20.glUniformMatrix4(program.getModelMatLoc(), false, matrixBuffer);
//        }
//
////        public void draw() {
////            shader.bind();
////            modelMatrix = new Matrix3f();
////            modelMatrix.scale(new Vector3f(10f, 10f, 10f));
////            modelMatrix.rotate((float) (Math.random() * Math.PI * 2.0), new Vector3f(0f, 0f, 1f));
////            modelMatrix.translate(this.pos);
////            uploadMatrix(shader);
////            GL11.glDrawArrays(GL15.GL_ARRAY_BUFFER, vbuf, 72);
////            GL20.glUseProgram(0);
////        }
//
//        private static float intToAngle(int in){
//            return (2f * (float)Math.PI * (float)in / 8f);
//        }
//
//    }
//
//    static Matrix3f projectionMatrix;
//    static Matrix3f viewMatrix;
//    static Matrix3f modelMatrix;
//    static FloatBuffer matrixBuffer;
//
//    private static void setupMatrices() {
//        // Setup projection matrix
//        projectionMatrix = new Matrix3f();
////        float fieldOfView = 60f;
////        float aspectRatio = (float)width / (float)height;
////        float near_plane = 0.1f;
////        float far_plane = 100f;
////
////        float y_scale = 1f / (float)Math.tan(Math.toRadians(fieldOfView / 2f));
////        float x_scale = y_scale / aspectRatio;
////        float frustum_length = far_plane - near_plane;
////
////        projectionMatrix.m00 = x_scale;
////        projectionMatrix.m11 = y_scale;
////        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
////        projectionMatrix.m23 = -1;
////        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
////        projectionMatrix.m33 = 0;
//
//        // Setup view matrix
//        viewMatrix = new Matrix3f();
//
//        // Setup model matrix
//        modelMatrix = new Matrix3f();
//
//        // Create a FloatBuffer with the proper size to store our matrices later
//        matrixBuffer = BufferUtils.createFloatBuffer(12);
//    }

}
