package utils;

import loaders.TextureLoader;
import main.Main;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
                0.0f, 0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 1.0f,

                0.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f
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
    static boolean debug = true;
    public void renderAt(Vector2f pos, Vector2f size, Vector3f color, float rot, float depth, ShaderProgram shader){

//        Matrix4f model = Matrix4f.setIdentity(new Matrix4f());
//        Matrix4f.translate(new Vector3f(pos.x, pos.y, 0f), model, model);
//        Matrix4f.translate(new Vector3f(.5f * size.x, .5f * size.y, 0f), model, model);
//        Matrix4f.rotate(rot, new Vector3f(0f, 0f, 1f), model, model);
//        Matrix4f.translate(new Vector3f(-.5f * size.x, -.5f * size.y, 0f), model, model);
//        Matrix4f.scale(new Vector3f(size.x, size.y, 1f), model, model);
//
//        if(debug){
//            Vector4f v = new Vector4f(0f,0f,0f,1f);
//            Vector4f v2 = new Vector4f(1f,1f,0f,1f);
//            Matrix4f temp;
//            temp = Matrix4f.mul(Main.getView(), Main.getProjection(), null);
//            Matrix4f.mul(model, temp, temp);
//            Matrix4f.transform(temp, v, v);
//            Matrix4f.transform(temp, v2, v2);
//            System.out.println(v);
//            System.out.println(v2);
//        }

        TextureLoader.get(path).bind();

        Matrix4f t = Matrix4f.setIdentity(new Matrix4f());
        shader.uploadMatrix(t, t, t);
        shader.bind();
//        shader.uploadMatrix(Main.getProjection(), Main.getView(), model);

        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
//        GL11.glColor3f(1f, 1f, 1f);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 24); // TODO: Check what 6 instead of 24 does..
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
