package main;

import entities.Camera;
import entities.Entity;
import entities.Renderable;
import entities.Scene;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;

public abstract class Main {

    

    public static void main(String[] args) throws Exception{
        try{
            Display.setDisplayMode(new DisplayMode(640, 400));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+GL11.glGetString(GL11.GL_VERSION)+".");

        Scene scene = Scene.addScene("res/scene/scene.sc");
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(80f, 800f / 600f, 0.1f, 1000f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);

        while (!Display.isCloseRequested()){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            scene.event();
            GL11.glPushMatrix();
            Camera.transform();
            GL11.glColor3f(1f,1f,1f);
            scene.render();
            GL11.glPopMatrix();
            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }


}
