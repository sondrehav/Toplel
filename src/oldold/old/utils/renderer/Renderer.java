package oldold.old.utils.renderer;

import oldold.renderer.objects.MyShaderProgram;
import oldold.old.main.Main;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public abstract class Renderer {

    protected static int vaoid;

    public Renderer(){

        int vboid;
        FloatBuffer vertexData = BufferUtils.createFloatBuffer(24);
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
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

    }

    /*public static void draw(Matrix4f modelMatrix, Drawable input){

//        Vector3f pos = new Vector3f(x, y, 0f);
//        Vector3f size = new Vector3f(sx, sy, 1f);
//
//        Matrix4f model = Matrix4f.setIdentity(new Matrix4f());
//        model.translate(pos);
//        model.scale(size);
//        model.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 0f, 1f));
//        model.translate(new Vector3f(-.5f, -.5f, 0f)); // MOVES IT IN THE MIDDLE

        Sprite s = input.getSprite();
        if(s!=null) TextureLoader.bind(s);

        ShaderProgram shaderProgram = input.getShaderProgram();
        if(shaderProgram==null) shaderProgram = defaultShader;

        shaderProgram.setUniform1f("alpha", alpha);
        shaderProgram.setUniformMat4("pr_matrix", Main.getProjection());
        shaderProgram.setUniformMat4("vi_matrix", Camera.getViewMatrix());
        shaderProgram.setUniformMat4("md_matrix", model);
        shaderProgram.bind();

        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }*/

    public static void draw(float x, float y, float rotation, Sprite sprite, float sx, float sy){
        draw(x, y, rotation, sprite, sx, sy, Main.defaultShader(), 1f);
    }

    public static void draw(float x, float y, float rotation, Sprite sprite, float sx, float sy, float alpha){
        draw(x, y, rotation, sprite, sx, sy, Main.defaultShader(), alpha);
    }

    public static void draw(float x, float y, float rotation, Sprite sprite, float sx, float sy, MyShaderProgram myShaderProgram, float alpha){

        Vector3f pos = new Vector3f(x, y, 0f);
        Vector3f size = new Vector3f(sx, sy, 1f);

        Matrix4f model = Matrix4f.setIdentity(new Matrix4f());
        model.translate(pos);
        model.scale(size);
        model.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 0f, 1f));
        model.translate(new Vector3f(-.5f, -.5f, 0f)); // MOVES IT IN THE MIDDLE

//        MyTextureLoader.bind(sprite);

        myShaderProgram.setUniform1f("alpha", alpha);
//        shaderProgram.setUniformMat4("pr_matrix", Main.getProjection());
//        shaderProgram.setUniformMat4("vi_matrix", Camera.getViewMatrix());
//        shaderProgram.setUniformMat4("md_matrix", model);
        myShaderProgram.bind();

        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }

}
