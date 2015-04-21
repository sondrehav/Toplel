package main;

import entities.Scene;
import loaders.ShaderLoader;
import math.MatUtil;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import ecs.component.Sprite;


public abstract class Main {

    public static int width = 800, height = 600;

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

        projMat = MatUtil.projection(0f, (float) width, (float) height, 0f, -1f, 1f);
        viewMat = new Matrix4f();

        Scene scene = Scene.addScene("res/scene/ecsTest.sc");

        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(.5f,.5f,1f,1f);

        boolean wire = false;
//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            if(Keyboard.isKeyDown(Keyboard.KEY_F2) && !wire){
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                wire = true;
            } else if(wire && !Keyboard.isKeyDown(Keyboard.KEY_F2)) {
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                wire = false;
            }

            scene.event();
//            Camera.event();

            scene.render();

            Display.update();
            Display.sync(60);
        }
        System.out.println("Exiting...");
        scene.destroy();
        Sprite.destroy();
        ShaderLoader.destroyAll();
        Display.destroy();

    }

    public static void setTitle(String title){
        Display.setTitle(title);
    }

    public static Matrix4f getProjection(){
        return new Matrix4f(projMat);
    }

    public static Matrix4f getView(){
        return new Matrix4f(viewMat);
    }

    private static boolean running = true;
    public static void stop(){
        running = false;
    }

}
