package main;

import com.sun.prism.ps.Shader;
import math.Matrix;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import utils.ShaderProgram;

import java.io.IOException;
import java.nio.FloatBuffer;

public class Test {

    static int width = 800, height = 600;

    public static void main(String[] args){

        try{
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setupMatrices();
        GL11.glViewport(0,0,width,height);
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f); // BG

        Point[] points = new Point[16];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                points[i*4+j] = new Point((float)i, (float)j);
            }
        }

        Vector2f camera = new Vector2f();
        float rot = 0f;

        while(!Display.isCloseRequested()){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            if(Keyboard.isKeyDown(Keyboard.KEY_W)) camera.x++;
            if(Keyboard.isKeyDown(Keyboard.KEY_S)) camera.x--;
            if(Keyboard.isKeyDown(Keyboard.KEY_A)) camera.y--;
            if(Keyboard.isKeyDown(Keyboard.KEY_D)) camera.y++;
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) rot++;
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) rot--;
            System.out.println("camera = " + camera);

            viewMatrix = new Matrix4f();
            viewMatrix.translate(new Vector3f(-camera.x, -camera.y, -10f));
            viewMatrix.rotate(rot, new Vector3f(0f,0f,1f));

            for(Point pw : points){
                pw.draw();
            }

            Display.update();
            Display.sync(20);
        }

    }


    private static class Point{

        Vector2f pos = new Vector2f();

        static FloatBuffer buf = null;

        static int vbuf;

        static ShaderProgram shader;

        Point(float x, float y){
            try{
                shader = ShaderProgram.addShader(
                        "res/shader/defaultVertexShader.vs",
                        "res/shader/defaultFragmentShader.fs");
            } catch (IOException e) {
                e.printStackTrace();
            }
            pos.x = x; pos.y = y;
            if(buf == null){
                buf = new BufferUtils().createFloatBuffer(72);
                for(int angle = 0; angle < 8; angle++){
                    float x0 = (float) Math.cos(intToAngle(angle));
                    float y0 = (float) Math.sin(intToAngle(angle));
                    float x1 = (float) Math.cos(intToAngle(angle+1));
                    float y1 = (float) Math.sin(intToAngle(angle+1));
                    float z = 0f;
                    buf.put(new float[]{x0, y0, z, x1, y1, z, 0f, 0f, z});
                }
                buf.flip();
                vbuf = GL15.glGenBuffers();
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbuf);
                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            }

        }

        public static void uploadMatrix(ShaderProgram program){
            projectionMatrix.store(matrixBuffer); matrixBuffer.flip();
            GL20.glUniformMatrix4(program.getProjMatLoc(), false, matrixBuffer);
            viewMatrix.store(matrixBuffer); matrixBuffer.flip();
            GL20.glUniformMatrix4(program.getViewMatLoc(), false, matrixBuffer);
            modelMatrix.store(matrixBuffer); matrixBuffer.flip();
            GL20.glUniformMatrix4(program.getModelMatLoc(), false, matrixBuffer);
        }

        public void draw() {
            shader.bind();
            Matrix4f stash = new Matrix4f(modelMatrix);
            modelMatrix.scale(new Vector3f(10f,10f,10f));
            modelMatrix.rotate((float) (Math.random() * Math.PI * 2.0), new Vector3f(0f, 0f, 1f));
            modelMatrix.translate(this.pos);
            uploadMatrix(shader);
            GL11.glColor3f(1f,1f,0f);
            GL11.glDrawArrays(GL15.GL_ARRAY_BUFFER, vbuf, 72);
            GL20.glUseProgram(0);
            modelMatrix = stash;
        }

        private static float intToAngle(int in){
            return (2f * (float)Math.PI * (float)in / 8f);
        }

    }

    static Matrix4f projectionMatrix;
    static Matrix4f viewMatrix;
    static Matrix4f modelMatrix;
    static FloatBuffer matrixBuffer;

    private static void setupMatrices() {
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)width / (float)height;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = 1f / (float)Math.tan(Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        // Setup view matrix
        viewMatrix = new Matrix4f();

        // Setup model matrix
        modelMatrix = new Matrix4f();

        // Create a FloatBuffer with the proper size to store our matrices later
        matrixBuffer = BufferUtils.createFloatBuffer(16);
    }

}
