package mypixelshader;

import old.loaders.TextureLoader;
import old.utils.renderer.Sprite;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.LinkedList;

public abstract class MyRenderer {

    private static int vaoid;

    public static void init(){

        int vboid;
        FloatBuffer vertexData = BufferUtils.createFloatBuffer(24);
        vertexData.put(new float[]{
                -1.0f, -1.0f, 0f, 1.0f,
                1.0f, -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 0f,

                -1.0f, -1.0f, 0f, 1.0f,
                -1.0f, 1.0f, 0f, 0f,
                1.0f, 1.0f, 1.0f, 0f
        });
        vertexData.flip();

        vboid = GL15.glGenBuffers();
        vaoid = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vaoid);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

    }

    public static void draw(MyShaderProgram shaderProgram, float time, float res_x, float res_y, String image){

        shaderProgram.setUniform1f("iGlobalTime", time);
        shaderProgram.setUniform2f("iResolution", res_x, res_y);
        shaderProgram.bind();

        if(image!=null){
            TextureLoader.bind(new Sprite(image));
        }

        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        shaderProgram.unbind();
    }

}
