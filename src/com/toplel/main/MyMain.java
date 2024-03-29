package com.toplel.main;

import com.toplel.events.inloop.OnRender;
import com.toplel.events.keyboard.KeyboardEventHandler;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.events.mouse.MyMouseEventHandler;
import com.toplel.math.MyMatrix4f;
import com.toplel.state.MyEditor;
import com.toplel.state.MyMasterState;
import com.toplel.util.Console;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

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

    private static Matrix4f projectionMatrix;
    public static Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }

    public static void start(String[] args, int width, int height, boolean fullscreen) {
        try{
            Display.setDisplayMode(getDispMode(width, height));
            Display.setVSyncEnabled(true);
            System.out.print("Display: " + Display.getDisplayMode() + ". ");
            if(Display.getDisplayMode().isFullscreenCapable()){
                Console.printLn("Display is fullscreen capable.");
            } else {
                Console.printLn("Display is not fullscreen capable.");
            }
            Display.create();
            Display.setFullscreen(fullscreen);
            if(!fullscreen) Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        WIDTH = Display.getWidth(); HEIGHT = Display.getHeight();

        projectionMatrix = MyMatrix4f.orthographicProjection(0f, WIDTH, HEIGHT, 0f, -1f, 1f, null);

        Console.printLn("Current OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + ".");

        running = true;

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glClearColor(.30f,.3f,.3f, 1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        MyMasterState.switchState(new MyEditor());

        Console.printLn("Initialization done. Starting main loop.");

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        long time = System.nanoTime();

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            KeyboardEventHandler.poll();
            MyMouseEventHandler.poll();

            MyMasterState.stateEvent();

            OnRender.poll();

            Display.update();
            Display.sync(FPS_CAP);

            int error = GL11.glGetError();
            if(error != GL11.GL_NO_ERROR){
                Console.printErr("OpenGL error code: " + GL11.glGetError());
            }
            if(Display.wasResized()){
                int oldW = WIDTH;
                int oldH = HEIGHT;
                WIDTH = Display.getWidth();
                HEIGHT = Display.getHeight();
                MyMatrix4f.orthographicProjection(0f, WIDTH, HEIGHT, 0f, -1f, 1f, projectionMatrix);
                GL11.glViewport(0, 0, WIDTH, HEIGHT);
                for(OnResize r : OnResize.listeners){
                    r.onResize(WIDTH, HEIGHT, oldW, oldH);
                }
            }

//            System.out.println("Draw calls: " + MyVertexObject.getDrawCallCount());
            MyVertexObject.resetDrawCallCounter();

            long ctime = System.nanoTime();
            long timeElapsed = ctime - time;
            float timeElapsedInSec = (float)timeElapsed / 1000000000f;
            fps = 1f / timeElapsedInSec;
            if(avgFPS<0&&fps>0){
                avgFPS = fps;
            } else if(avgFPS>0) {
                avgFPS = avgFPS * 0.99f + 0.01f / timeElapsedInSec;
            }
            time = ctime;

        }

        Console.printLn("Exiting...");
        Display.destroy();
    }

    private static boolean running = false;
    public static void stop(){
        running = false;
    }
    public static void main(String[] args) {
        start(args, 1920, 1080, true);
    }

    private static float fps = -1f;
    public static float getFPS(){
        return fps;
    }

    private static float avgFPS = -1f;
    public static float getAvgFPS(){
        return avgFPS;
    }

    public static void toggleFullscreen(){
        try{
            Display.setFullscreen(!Display.isFullscreen());
            if(!Display.isFullscreen()){
                Display.setResizable(true);
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    static OnKeyEvent wireframeKey = new OnKeyEvent(Keyboard.KEY_F1) {
        private boolean wf = false;
        @Override
        public void onKeyDown() {
            wf = !wf;
            if(wf){
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                MyTexture.enableTextures(false);
            }
            else{
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                MyTexture.enableTextures(true);
            }
        }
    };

}

