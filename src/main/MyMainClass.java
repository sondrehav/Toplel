package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import state.MyMainMenu;
import state.MySplashScreen;
import state.MyState;
import util.input.MyKeyboardHandler;

public class MyMainClass {

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    private static int WIDTH;
    private static int HEIGHT;

    private static MyState myState = null;

    public static void start(String[] args, int width, int height) {
        WIDTH = width; HEIGHT = height;
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

        myState = new MySplashScreen();

        myState.init();

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            MyKeyboardHandler.poll();

            myState.event();
            myState.render();

            Display.update();
            Display.sync(60);

            int error = GL11.glGetError();
            if(error != GL11.GL_NO_ERROR){
                System.err.println("OpenGL error code: " + GL11.glGetError());
            }

        }

        myState.close();

        System.out.println("Exiting...");
        Display.destroy();
    }

    private static boolean running = false;
    public static void stop(){
        running = false;
    }

    public static void main(String[] args) {
        start(args, 1200, 800);
    }

    public static void newState(MyState myState){
        MyMainClass.myState.close();
        MyMainClass.myState = myState;
        MyMainClass.myState.init();
    }

}
