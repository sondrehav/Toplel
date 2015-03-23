package utils;

import loaders.TextureLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class Sprite {

    private static FloatBuffer vertexData = null;
    private static FloatBuffer texCoords = null;
    private static int tboid, vboid, vaoid;

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
        if(vertexData==null||texCoords==null){
            initBuffer();
        }
        TextureLoader.load(path);
    }

    public void renderAt(Vector2f pos, Vector2f size, float rot, float depth, ShaderProgram shader){
        GL11.glPushMatrix();
        try{
            shader.bind();
        } catch (Exception e) {
            e.printStackTrace();
            GL20.glUseProgram(0);
        }
        TextureLoader.get(path).bind();
//        GL11.glScalef(size.x, size.y, 1f);
        GL11.glRotatef(rot, 0f, 0f, 1f);
        GL11.glTranslatef(pos.x, pos.y, depth);
        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 18);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
        GL11.glPopMatrix();
    }

    private static void initBuffer(){

        vertexData = BufferUtils.createFloatBuffer(3 * 6);
        vertexData.put(new float[]{-.5f,-.5f,-.5f,.5f,-.5f,-.5f,.5f,.5f,-.5f,-.5f,-.5f,-.5f,.5f,.5f,-.5f,-.5f,.5f,-.5f});
        vertexData.flip();

        texCoords = BufferUtils.createFloatBuffer(2 * 6);
        texCoords.put(new float[]{0f,0f,1f,0f,1f,1f,0f,0f,1f,1f,0f,1f});
        texCoords.flip();

        tboid = GL15.glGenBuffers();
        vboid = GL15.glGenBuffers();
        vaoid = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoid);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0,3,GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoords, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1,2,GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

    }


    public static void destroy(){

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboid);
        GL15.glDeleteBuffers(tboid);
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
