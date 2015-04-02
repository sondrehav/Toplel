package main;

import entities.Camera;
import entities.Scene;
import math.Matrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public abstract class Main {

    public static int width = 800, height = 600;
    public static float nearPlane = -100f;
    public static float farPlane = 100f;

    private static Matrix4f projMat;
    private static Matrix4f viewMat;

    public static void main(String[] args) throws Exception{
        try{
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+GL11.glGetString(GL11.GL_VERSION)+".");

        projMat = Matrix.orthographicProjection(-50f, 50f, 50f, -50f, -1f, 1f);
        viewMat = new Matrix4f();
        Scene scene = Scene.addScene("res/scene/test.sc");

        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(.5f,.5f,1f,1f);

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);

        while (!Display.isCloseRequested()){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            scene.event();
            Camera.event();

            viewMat = new Matrix4f();
            Matrix4f.rotate(-Camera.rot, new Vector3f(0f, 0f, 1f), viewMat, viewMat);
            Matrix4f.translate(new Vector3f(-Camera.pos.x, -Camera.pos.y, -Camera.height), viewMat, viewMat);

            //System.out.println("viewMat = " + viewMat);

            scene.render();

            Display.update();
            Display.sync(20);
        }

        Display.destroy();

    }

    public static Matrix4f getProjection(){
        return new Matrix4f(projMat);
    }

    public static Matrix4f getView(){
        return new Matrix4f(viewMat);
    }

}
