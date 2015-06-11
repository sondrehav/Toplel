package main;

import math.MyMat3;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import state.MySplashScreen;
import state.MyState;
import ui.MyLabel;
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

    private static MyMat3 projectionMatrix;

    private static DisplayMode getDispMode(int width, int height) throws LWJGLException {
        for(DisplayMode displayMode : Display.getAvailableDisplayModes()){
            if(displayMode.isFullscreenCapable()
                    &&displayMode.getWidth()==width
                    &&displayMode.getHeight()==height){
                return displayMode;
            }
        }
        return new DisplayMode(width, height);
    }

    public static void start(String[] args, int width, int height, boolean fullscreen) {
        try{
            Display.setDisplayMode(getDispMode(width, height));
            System.out.print("Display: " + Display.getDisplayMode() + ". ");
            if(Display.getDisplayMode().isFullscreenCapable()){
                System.out.println("Display is fullscreen capable.");
            } else {
                System.out.println("Display is not fullscreen capable.");
            }
            Display.create();
            Display.setFullscreen(fullscreen);
            if(!fullscreen) Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        WIDTH = Display.getWidth(); HEIGHT = Display.getHeight();

        System.out.println("Current OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION)+".");

        running = true;

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glClearColor(.19f, .16f, .13f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        projectionMatrix = MyMat3.projection(WIDTH, HEIGHT);

        myState = new MySplashScreen();

        myState.init();

        while (!Display.isCloseRequested() && running){


            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            MyKeyboardHandler.poll();

            myState.event();
            myState.render(projectionMatrix.clone());

            Display.update();
            Display.sync(60);

            int error = GL11.glGetError();
            if(error != GL11.GL_NO_ERROR){
                System.err.println("OpenGL error code: " + GL11.glGetError());
            }
            if(Display.wasResized()){
                WIDTH = Display.getWidth();
                HEIGHT = Display.getHeight();
                System.out.println("WIDTH = " + WIDTH);
                System.out.println("HEIGHT = " + HEIGHT);
                projectionMatrix = MyMat3.projection(WIDTH, HEIGHT);
                GL11.glViewport(0, 0, WIDTH, HEIGHT);
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
        start(args, 1200, 800, false);
    }

    public static void newState(MyState myState){
        MyMainClass.myState.close();
        MyMainClass.myState = myState;
        MyMainClass.myState.init();
    }

}
