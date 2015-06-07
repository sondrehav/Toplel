package main;

import math.MyMat3;
import math.MyVec3;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import renderer.MyTextRenderer;

public class Main {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 800;

    private static MyVec3 cameraPosition = new MyVec3(0f,0f,1f);
    private static float cameraRotation = 0f;

    private static MyMat3 projectionMatrix = MyMat3.projection(0, WIDTH, HEIGHT, 0);
    private static MyMat3 viewMatrix = MyMat3.getIdentity();

    public static void main(String[] args) {
        try{
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION)+".");
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glClearColor(.5f,.5f,1f,1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        MyTextRenderer myTextRenderer = new MyTextRenderer("res/img/text/bmpfont1.bmp", 5, 11, "res/shader/text/text.vs", "res/shader/text/text.fs");

        int frame = 0;
        float x = -1f, y = -1f;
        while (!Display.isCloseRequested()){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

//            cameraPosition = player.position.clone();
//            cameraRotation = player.rotation;
//
//            viewMatrix = MyMat3.getIdentity();
//            viewMatrix.rotate(cameraRotation);
//            viewMatrix.translate(cameraPosition);

            if(Keyboard.isKeyDown(Keyboard.KEY_UP)) y+= .01f;
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y-= .01f;
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x-= .01f;
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x+= .01f;

            myTextRenderer.setAlpha(.5f);
            myTextRenderer.setPos(-1f, -1f);
            myTextRenderer.setColor(new MyVec3(1f, 0f, 0f));
            myTextRenderer.setSize(.05f);
            myTextRenderer.setSpacing(.01f);
            myTextRenderer.render(String.valueOf("æøåÆØÅ _ frame: " + String.valueOf(frame++)).toUpperCase());

            Display.update();
            Display.sync(60);
        }
        System.out.println("Exiting...");
        Display.destroy();
    }

}
