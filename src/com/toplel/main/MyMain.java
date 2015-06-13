package com.toplel.main;

import com.toplel.event.mouse.MyMouseEventHandler;
import com.toplel.event.mouse.MyMouseListener;
import com.toplel.events.keyboard.MyKeyboardEventHandler;
import com.toplel.math.MyMat3;
import com.toplel.state.MyMasterState;
import com.toplel.state.MySplashScreen;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class MyMain {

    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() {
        return HEIGHT;
    }

    private static int WIDTH;
    private static int HEIGHT;
    private static int FPS_CAP = 60;

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

    private static MyMat3 projectionMatrix;

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

        projectionMatrix = MyMat3.projection(WIDTH, HEIGHT);

        System.out.println("Current OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION)+".");

        running = true;

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glClearColor(.19f, .16f, .13f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        MyMasterState.switchState(new MySplashScreen());

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            MyKeyboardEventHandler.poll();
            MyMouseEventHandler.poll();

            MyMasterState.stateEvent();
            MyMasterState.stateRender();

            Display.update();
            Display.sync(FPS_CAP);

            int error = GL11.glGetError();
            if(error != GL11.GL_NO_ERROR){
                System.err.println("OpenGL error code: " + GL11.glGetError());
            }
            if(Display.wasResized()){
                WIDTH = Display.getWidth();
                HEIGHT = Display.getHeight();
                projectionMatrix = MyMat3.projection(WIDTH, HEIGHT);
                GL11.glViewport(0, 0, WIDTH, HEIGHT);
                // TODO: Listeners
            }

        }

        System.out.println("Exiting...");
        Display.destroy();
    }

    private static boolean running = false;
    public static void stop(){
        running = false;
    }

    public static MyMat3 getProjection(){
        return projectionMatrix.clone();
    }

    public static void main(String[] args) {
        start(args, 1200, 800, false);
    }

}

