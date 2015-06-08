package renderer;

import math.MyMat3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;

import java.io.IOException;

public class MySpriteRenderer extends MyRenderer {

    private Texture texture = null;

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    private MyShaderProgram shaderProgram = null;

    public MySpriteRenderer(){
        super();
        try{
            shaderProgram = MyShaderProgram.addShader("res/shader/default.vs", "res/shader/default.fs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(){

        texture.bind();
        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        shaderProgram.bind();
        shaderProgram.setUniformMat3("pr_matrix", MyMat3.getIdentity());
        shaderProgram.setUniformMat3("vi_matrix", MyMat3.getIdentity());
        shaderProgram.setUniformMat3("md_matrix", MyMat3.getIdentity());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);

    }

}
