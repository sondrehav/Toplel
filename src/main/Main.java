package main;

import entities.Camera;
import entities.Scene;
import math.Matrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public abstract class Main {

    public static int width = 800, height = 600;
    public static float fov = 90f;
    public static float nearPlane = .1f;
    public static float farPlane = 500f;

    public static void main(String[] args) throws Exception{
        try{
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+GL11.glGetString(GL11.GL_VERSION)+".");


        Scene scene = Scene.addScene("res/scene/scene.sc");

        GL11.glViewport(0, 0, width, height);

        Matrix.setupMatrices(fov, width, height, nearPlane, farPlane);

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);

        while (!Display.isCloseRequested()){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            Matrix.resetMatrices();

            scene.event();
            Camera.event();

            Matrix.setViewMatrix(Camera.pos, Camera.height, Camera.rot);
            scene.render();

            Display.update();
            Display.sync(20);
        }

        Display.destroy();

    }



}
