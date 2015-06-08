package main;

import loaders.MySimpleFileReader;
import math.MyMat3;
import math.MyVec3;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import renderer.MyTextRenderer;
import util.input.MyKeyboardHandler;
import util.input.MyListener;

import java.io.IOException;

public class Main {

    public static int WIDTH;
    public static int HEIGHT;

    private MyVec3 cameraPosition = new MyVec3(0f,0f,1f);
    private float cameraRotation = 0f;

    private MyMat3 projectionMatrix = MyMat3.projection(0, WIDTH, HEIGHT, 0);
    private MyMat3 viewMatrix = MyMat3.getIdentity();

    private MyKeyboardHandler input = new MyKeyboardHandler();
    private MyTextRenderer myTextRenderer;

    public void start(String[] args, int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        try{
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Current OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION)+".");

        running = true;

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glClearColor(.19f, .16f, .13f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        myTextRenderer = new MyTextRenderer("res/img/text/bmpfont1.bmp", 5, 11);
        myTextRenderer.setMaxLineWidth(50);
        MyTextRenderer myGothicTextRenderer = new MyTextRenderer("res/img/text/czechgotika.bmp", 24, 24, 65);
        String text = null;
        try{
            text = MySimpleFileReader.read("res/lorumipsum.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        input.addListener(new MyListener<Main>(Keyboard.KEY_ESCAPE, MyKeyboardHandler.EventType.BUTTON_DOWN, this) {
            @Override
            public void event() {
                thisObject.stop();
            }
        });
        input.addListener(new MyListener<Main>(Keyboard.KEY_F1, MyKeyboardHandler.EventType.BUTTON_UP, this){
            @Override
            public void event() {
                this.thisObject.frame = 0;
            }
        });
        input.addListener(new MyListener<Main>(Keyboard.KEY_ADD, MyKeyboardHandler.EventType.BUTTON_DOWN, this) {
            @Override
            public void event() {
                thisObject.myTextRenderer.setMaxLineWidth(thisObject.myTextRenderer.getMaxLineWidth()+1);
            }
        });
        input.addListener(new MyListener<Main>(Keyboard.KEY_SUBTRACT, MyKeyboardHandler.EventType.BUTTON_DOWN, this) {
            @Override
            public void event() {
                thisObject.myTextRenderer.setMaxLineWidth(thisObject.myTextRenderer.getMaxLineWidth()-1);
            }
        });

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

//            cameraPosition = player.position.clone();
//            cameraRotation = player.rotation;
//
//            viewMatrix = MyMat3.getIdentity();
//            viewMatrix.rotate(cameraRotation);
//            viewMatrix.translate(cameraPosition);

            input.update();

            myGothicTextRenderer.setAlpha(1f);
            myGothicTextRenderer.setPos(0f, .6f);
            myGothicTextRenderer.setColor(new MyVec3(.64f, .57f, .52f));
            myGothicTextRenderer.setSize(.1f);
            myGothicTextRenderer.setSpacing(-.1f);
            myGothicTextRenderer.setCentered(true);
//            myGothicTextRenderer.render("secret of samara".toUpperCase());
            myGothicTextRenderer.render("radicals".toUpperCase());

            myTextRenderer.setCentered(true);
            myTextRenderer.setAlpha(1f);
            myTextRenderer.setPos(0f, .4f);
            myTextRenderer.setColor(new MyVec3(.64f, .57f, .52f));
            myTextRenderer.setSize(.017f);
            myTextRenderer.setSpacing(.1f);
            myTextRenderer.render(text);

            myTextRenderer.setSize(.02f);
            myTextRenderer.setRotation(2f * (float) Math.sin(Math.toRadians(frame)));
            myTextRenderer.setPos(0f, -.8f);
            myTextRenderer.render("Press ENTER to continue!");
            myTextRenderer.setRotation(0f);

            myTextRenderer.setSize(.02f);
            myTextRenderer.setCentered(false);
            myTextRenderer.setPos(0, 20);
            myTextRenderer.render("frame: " + frame++);

            Display.update();
            Display.sync(60);
        }
        System.out.println("Exiting...");
        Display.destroy();
    }

    int frame = 0;
    boolean running = false;
    public void stop(){
        running = false;
    }

    public static void main(String[] args) {
        new Main().start(args, 1200, 800);
    }

}
