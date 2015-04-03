package utils;

import entities.Camera;
import loaders.TextureLoader;
import main.Main;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.*;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Sprite {

    private static FloatBuffer vertexData = null;
    private static int vboid, vaoid;

    private String path;

    private static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public static Sprite addSprite(String path) throws IOException {
        if(sprites.containsKey(path)){
            return sprites.get(path);
        }
        Sprite p = new Sprite(path);
        sprites.put(path, p);
        return p;
    }

    private Sprite(String path) throws IOException{
        this.path = path;
        if(vertexData==null){
            initBuffer();
        }
        TextureLoader.load(path);
    }

    private static void initBuffer(){

        vertexData = BufferUtils.createFloatBuffer(24);
        vertexData.put(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0.0f,

                0.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f
        });
        vertexData.flip();

        vboid = GL15.glGenBuffers();
        vaoid = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoid);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0); // TODO: If not works, change this..
//        GL20.glEnableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

    }

    static boolean debug = false;
    float r = 0f;
    public void renderAt(Vector2f pos, Vector2f size, Vector3f color, float rot, float depth, ShaderProgram shader){
        r+=0.1f;
        Matrix4f model = Matrix4f.setIdentity(new Matrix4f());
        model.translate(pos);
        model.scale(new Vector3f(size.x, size.y, 1f));
        model.rotate((float) Math.toRadians(rot), new Vector3f(0f, 0f, 1f));
        model.translate(new Vector3f(-.5f, -.5f, 0f)); // MOVES IT IN THE MIDDLE
//        model.translate(pos);
//        model.scale(new Vector3f(size.x, size.y, 1f));
//        model.translate(new Vector3f(pos.x, pos.y, 0f));
//        System.out.println("model = \n" + model);
//        r++;

        if(Keyboard.isKeyDown(Keyboard.KEY_F3) && !debug){
            debug = true;
            System.out.println("MODEL\n");
            System.out.println(model);

            System.out.println("\nVIEW\n");
            System.out.println(Main.getView());

            System.out.println("\nPROJ\n");
            System.out.println(Main.getProjection());

            System.out.println("\nFINAL\n");
            Matrix4f fin = Matrix4f.mul(Main.getProjection(), Matrix4f.mul(Main.getView(), model, null), null);
            System.out.println(fin);

            Vector4f v00 = new Vector4f(0f,0f,0f,1f);
            Vector4f v10 = new Vector4f(0f,1f,0f,1f);
            Vector4f v11 = new Vector4f(1f,1f,0f,1f);
            Vector4f v01 = new Vector4f(1f,0f,0f,1f);

            System.out.println("\nVECTORS\n");
            System.out.println("v00: " + v00 + " -> " + Matrix4f.transform(fin, v00, null));
            System.out.println("v10: " + v10 + " -> " + Matrix4f.transform(fin, v10, null));
            System.out.println("v11: " + v11 + " -> " + Matrix4f.transform(fin, v11, null));
            System.out.println("v01: " + v01 + " -> " + Matrix4f.transform(fin, v01, null));
        } else if(debug && !Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            debug = false;
        }

        TextureLoader.get(path).bind();

        shader.setUniformMat4("pr_matrix", Main.getProjection());
        shader.setUniformMat4("vi_matrix", Camera.viewMat);
        shader.setUniformMat4("md_matrix", model);
        shader.bind();

        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);

    }



    public static void destroy(){

        GL20.glDisableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboid);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoid);

    }

    @Override
    public boolean equals(Object o){
        return this.path.equals(o);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
