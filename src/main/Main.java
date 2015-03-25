package main;

import entities.Camera;
import entities.Scene;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public abstract class Main {

    public static final int WIDTH = 800, HEIGHT = 600;

    public static void main(String[] args) throws Exception{
        try{
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+GL11.glGetString(GL11.GL_VERSION)+".");

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        Scene scene = Scene.addScene("res/scene/scene.sc");
        setUpMatrices();

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);

        while (!Display.isCloseRequested()){
            modelMatrix = new Matrix4f();
            viewMatrix = new Matrix4f();
            Matrix4f.translate(Camera.getEntity().getPosition(), viewMatrix, viewMatrix);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            scene.event();
            scene.render();
            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }

    public static FloatBuffer matrixBuffer;

    public static Matrix4f modelMatrix;
    public static Matrix4f viewMatrix;
    public static Matrix4f projectionMatrix = new Matrix4f();

    static float fov = 90f;
    static float aspectRatio = (float)WIDTH / (float)HEIGHT;
    static float nearPlane = .1f;
    static float farPlane = 500f;

    static void setUpMatrices(){

        float y_scale = 1f / (float) Math.tan(Math.toRadians(fov / 2f));
        float x_scale = y_scale / aspectRatio;
        float fustrumLength = farPlane - nearPlane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((farPlane + nearPlane) / fustrumLength);
        projectionMatrix.m23 = -1f;
        projectionMatrix.m32 = -((2 * nearPlane * farPlane) / fustrumLength);
        projectionMatrix.m33 = 0f;

        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        matrixBuffer = BufferUtils.createFloatBuffer(16);

    }

}
