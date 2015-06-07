package renderer;

import math.MyMat3;
import math.MyVec3;
import old.utils.renderer.Renderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyTextRenderer extends Renderer {

    public final int XDIM;
    public final int YDIM;

    private Texture texture = null;
    private int img_x;
    private int img_y;

    private int charPerRow;

    private ShaderProgram shaderProgram = null;

    private int maxCharNum;

    public MyTextRenderer(String font, int xdim, int ydim, String vertexShaderPath, String fragmentShaderPath){

        super();

        XDIM = xdim;
        YDIM = ydim;

        try{
            texture = TextureLoader.getTexture(font.split("\\.")[1], new FileInputStream(new File(font)));
            shaderProgram = ShaderProgram.addShader(vertexShaderPath, fragmentShaderPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

        img_x = texture.getImageWidth();
        img_y = texture.getImageHeight();

        texture.setTextureFilter(GL11.GL_NEAREST);

        charPerRow = (int) Math.floor((float) img_x / (float)XDIM);

        maxCharNum = Math.floorDiv(img_x, XDIM) * Math.floorDiv(img_y, YDIM);
    }

    public void render(String text){

        texture.bind();
        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        shaderProgram.bind();

        MyMat3 proj = MyMat3.getIdentity();
        proj = proj.translate(new MyVec3(xpos, ypos, 0f));
        proj = proj.scale(new MyVec3(size * 800f / 1280f, size * (float) img_x / img_y, 1f)); //TODO: Get aspect ratio
        shaderProgram.setUniformMat3("projection", proj.transpose());
        shaderProgram.setUniform3f("in_color", col.vector[0], col.vector[1], col.vector[2]);
        shaderProgram.setUniform1f("alpha", alpha);

        for (int i = 0; i < text.length(); i++) {

            int codepoint = (int)text.charAt(i) - 32;
            if(codepoint<0||codepoint>=maxCharNum) codepoint = maxCharNum-1;
            int x = codepoint % charPerRow;
            int y = Math.floorDiv(codepoint, charPerRow);
            float fx = (float)x*XDIM/img_x;
            float fy = (float)y*YDIM/img_y;

            shaderProgram.setUniform2f("uv_from", fx, fy);
            shaderProgram.setUniform2f("uv_to", (float) XDIM / img_x, (float) YDIM / img_y);
            float spacing = 0.1f;
            shaderProgram.setUniform1f("offset", (float)i*(1f + spacing));
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);

    }

    public float getXpos() {
        return xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setPos(float xpos, float ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public MyVec3 getColor() {
        return col;
    }

    public void setColor(MyVec3 color) {
        this.col = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    private float xpos = 0f, ypos = 0f, size = 1f;
    private MyVec3 col = new MyVec3(1f, 1f, 1f);
    private float alpha = 1f;
    private float spacing = .01f;

}
