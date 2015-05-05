package main;

import ecs.system.ScriptHandler;
import loaders.ShaderLoader;
import math.MatUtil;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import utils.ShaderProgram;
import utils.renderer.Renderer;

import java.io.IOException;

public class Main {

    public static int width = 800, height = 600;

    private static Matrix4f projMat;
    private static Matrix4f viewMat;

    Main(){
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

        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(.5f,.5f,1f,1f);

        Renderer.init();

        ScriptHandler scriptHandler = new ScriptHandler("res/script/enemy.js");
        scriptHandler.init();

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            scriptHandler.event();
            scriptHandler.render();

            Display.update();
            Display.sync(60);
            stop();
        }
        System.out.println("Exiting...");
        scriptHandler.destroy();
        ShaderLoader.destroyAll();
        Display.destroy();
    }

    public static void main(String[] args) throws Exception{
        new Main();
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

    private static ShaderProgram defaultShader = null;
    public static ShaderProgram defaultShader(){
        if(defaultShader==null){
            try{
                defaultShader = ShaderProgram.addShader("res/shader/vertTest.vs", "res/shader/fragTest.vs");
            } catch(IOException e){
                e.printStackTrace();
                Main.stop();
            }
        }
        return defaultShader;
    }

}
