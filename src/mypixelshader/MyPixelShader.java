package mypixelshader;

import old.loaders.TextureLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPixelShader implements Runnable{

    int width = 1920;
    int height = 1080;
    boolean fullscreen = true;
    String filSe = "res/shader/pixelShader/pixelShader2.fs";

    String image = null;

    MyShaderProgram sp;

    boolean running = true;

    static MyPixelShader mpx = null;

    public MyPixelShader(String file){
        mpx = this;
        this.main();
//        new Thread(this).start();
    }

    public void stop(){
        running = false;
        mpx = null;
    }

    @Override
    public void run(){
        main();
    }

    public void main() {
        try{
            boolean s = false;
            for(DisplayMode d : Display.getAvailableDisplayModes()){
                if(d.getHeight()==height&&d.getWidth()==width&&d.isFullscreenCapable()){
                    Display.setDisplayMode(d);
                    Display.setFullscreen(fullscreen);
                    Display.create();
                    s = true;
                    break;
                }
            }
            if(!s){
                Display.setDisplayMode(new DisplayMode(width, height));
                Display.setFullscreen(true);
                Display.create();
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
//        try{
//            TextureLoader.load(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
        System.out.println("Current OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION)+".");
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(.5f,.5f,1f,1f);
        try{
            sp = MyShaderProgram.addShader("res/shader/pixelShader/vert.vs", filSe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyRenderer.init();
        long beginFrame = System.nanoTime();
        float time = (System.nanoTime() - beginFrame) / 1000000000f;
        while (!Display.isCloseRequested()){
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) break;
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            MyRenderer.draw(sp, time, (float) width, (float) height, image);
            Display.update();
            Display.sync(60);
            time = (System.nanoTime() - beginFrame) / 1000000000f;
            synchronized (reload) {
                while(!reload.isEmpty()){
                    String filesa = reload.poll();
                    try{
                        MyShaderLoader.reload(filesa);
                        MyShaderProgram.forceRefresh(sp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                reload.notify();
            }
            if(!running){
                break;
            }
        }
        System.out.println("Exiting...");
        Display.destroy();

        System.exit(0);
    }

    static final LinkedList<String> reload = new LinkedList<>();
    public static void reload(String file){
        synchronized (reload) {
            reload.add(file);
            reload.notify();
        }
    }

    public static void main(String[] args) {
        new MyPixelShader(args[0]);
    }

    private static class Console implements Runnable {
        Scanner in = new Scanner(System.in);
        public Console(){
            new Thread(this).start();
        }
        @Override
        public void run() {
            while(true){
                String nextLine = in.nextLine();
                Matcher func = Pattern.compile("^(\\w*)").matcher(nextLine);
                if(func.find()){
                    String function = func.group(1);
                    switch (function){
                        case "load":
                            Matcher matcher = Pattern.compile("^(\\w*)\\s\\\"(.*)\\\"\\s(\\d+)\\s(\\d+)\\s(true|false)$").matcher(nextLine);
                            if(matcher.find()){
                                String path = matcher.group(2);
                                int width = Integer.parseInt(matcher.group(3));
                                int height = Integer.parseInt(matcher.group(4));
                                boolean fs = Boolean.parseBoolean(matcher.group(5));
                                new MyPixelShader(path);
                                System.out.println("HELLO");
                            }
                            break;
                        case "stop":
                            if(mpx!=null) mpx.stop();
                            break;
                        case "exit":
                            if(mpx!=null) mpx.stop();
                            System.out.println("Exiting program.");
                            System.exit(0);
                    }
                }
            }
        }
    }

}
