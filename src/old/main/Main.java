package old.main;

import old.entities.particles.ParticleEmitter;
import loaders.MyShaderLoader;
import org.lwjgl.input.Keyboard;
import old.utils.camera.Camera;
import old.utils.helpers.MatUtil;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import renderer.objects.MyShaderProgram;


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

//        Renderer.init();

//        ScriptHandler scriptHandler = new ScriptHandler("res/script/enemy.js");
//        scriptHandler.init();

        ParticleEmitter particleEmitter = new ParticleEmitter();
        particleEmitter.init();

//        Tile grass = new Tile("res/img/game/tiles/grass_a.png", new Vector2f(0f,0f), new Vector2f(10f,10f), 0f);
//        Tile dirt = new Tile("res/img/game/tiles/dirt_a.png", new Vector2f(-10f,0f), new Vector2f(10f,10f), 0f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        while (!Display.isCloseRequested() && running){

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            particleEmitter.event();

//            grass.render();
//            dirt.render();

            particleEmitter.render();

//            scriptHandler.event();
//            scriptHandler.render();

            if(Keyboard.isKeyDown(Keyboard.KEY_W)) Camera.pos.y+=0.2f;
            if(Keyboard.isKeyDown(Keyboard.KEY_S)) Camera.pos.y-=0.2f;
            if(Keyboard.isKeyDown(Keyboard.KEY_D)) Camera.pos.x+=0.2f;
            if(Keyboard.isKeyDown(Keyboard.KEY_A)) Camera.pos.x-=0.2f;

            if(Keyboard.isKeyDown(Keyboard.KEY_Q)) Camera.zoom*=0.99;
            if(Keyboard.isKeyDown(Keyboard.KEY_E)) Camera.zoom*=1.01;
            if(Keyboard.isKeyDown(Keyboard.KEY_Z)) Camera.rotation += 0.2f;
            if(Keyboard.isKeyDown(Keyboard.KEY_X)) Camera.rotation -= 0.2f;

            if(Keyboard.isKeyDown(Keyboard.KEY_UP)) particleEmitter.position.y+=0.02f;
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) particleEmitter.position.y-=0.02f;
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) particleEmitter.position.x-=0.02f;
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) particleEmitter.position.x+=0.02f;

            Display.update();
            Display.sync(1);
        }
        System.out.println("Exiting...");
        particleEmitter.destroy();
//        scriptHandler.destroy();
//        MyShaderLoader.destroyAll();
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

    private static MyShaderProgram defaultShader = null;
    public static MyShaderProgram defaultShader(){
        if(defaultShader==null){
//            try{
//                defaultShader = MyShaderProgram.addShader("res/shader/vertTest.vs", "res/shader/fragTest.fs");
//            } catch(IOException e){
//                e.printStackTrace();
//                Main.stop();
//            }
        }
        return defaultShader;
    }

}
