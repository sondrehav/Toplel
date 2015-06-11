package renderer;

import loaders.MyTextureLoader;
import main.MyMainClass;
import math.MyMat3;
import math.MyVec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import renderer.objects.MyShaderProgram;

import java.io.IOException;

public class OldMyTextRenderer extends MyRenderer {

    public final int XDIM;
    public final int YDIM;
    public float aspect = (float) MyMainClass.getHeight() / MyMainClass.getWidth();
    private Texture texture = null;
    private int img_x;
    private int img_y;
    private int charPerRow;
    private MyShaderProgram myShaderProgram = null;
    public final String fragmentShaderPath;
    public final String vertexShaderPath;
    private int maxCharNum;
    public final int textOffset;

    public OldMyTextRenderer(String font, int xdim, int ydim){
        this(font, xdim, ydim, 32, "res/shader/text/text.vs", "res/shader/text/text.fs");
    }

    public OldMyTextRenderer(String font, int xdim, int ydim, int textOffset){
        this(font, xdim, ydim, textOffset, "res/shader/text/text.vs", "res/shader/text/text.fs");
    }

    public OldMyTextRenderer(String font, int xdim, int ydim, int textOffset, String vertexShaderPath, String fragmentShaderPath){

        super();

        this.fragmentShaderPath = fragmentShaderPath;
        this.vertexShaderPath = vertexShaderPath;
        this.textOffset = textOffset;

        XDIM = xdim;
        YDIM = ydim;
/*
        try{
            texture = MyTextureLoader.load(font);
            if(myShaderProgram==null)
                myShaderProgram = MyShaderProgram.addShader(vertexShaderPath, fragmentShaderPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
*/
        img_x = texture.getImageWidth();
        img_y = texture.getImageHeight();

        texture.setTextureFilter(GL11.GL_NEAREST);

        charPerRow = (int) Math.floor((float) img_x / (float)XDIM);

        maxCharNum = Math.floorDiv(img_x, XDIM) * Math.floorDiv(img_y, YDIM);

    }

    private String parseText(String text){
        StringBuilder sb = new StringBuilder();
        int lastLineShift = 0;
        for(char i : text.toCharArray()){
            if(i=='\n'||(maxLineWidth>0&&lastLineShift>=maxLineWidth)){
                lastLineShift = 0;
                sb.append('\n');
            } else {
                lastLineShift++;
                sb.append(i);
            }
        }
        return sb.toString();
    }

    public void render(String text){

        String[] finalText = parseText(text).split("\n");

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        myShaderProgram.bind();

        MyMat3 proj = MyMat3.getIdentity();
        proj = proj.translate(new MyVec3(xpos, ypos, 0f));
        proj = proj.rotate(rotation);
        float a = 1f / aspect * (float) YDIM / (float) XDIM;
        proj = proj.scale(new MyVec3(size, size * a, 1f));
        myShaderProgram.setUniformMat3("projection", proj);

        myShaderProgram.setUniform3f("in_color", col.vector[0], col.vector[1], col.vector[2]);
        myShaderProgram.setUniform1f("alpha", alpha);

        float totalHeight = finalText.length*(1f + spacing);
        for(int line = 0; line < finalText.length; line++){
            String currentText = finalText[line];
            float totalWidth = currentText.length()*(1f + spacing);
            for (int i = 0; i < currentText.length(); i++) {
                int codepoint = (int)currentText.charAt(i) - textOffset;
                if(codepoint<0||codepoint>=maxCharNum) codepoint = maxCharNum-1;
                int x = codepoint % charPerRow;
                int y = Math.floorDiv(codepoint, charPerRow);
                float fx = (float)x*XDIM/img_x;
                float fy = (float)y*YDIM/img_y;
                myShaderProgram.setUniform2f("uv_from", fx, fy);
                myShaderProgram.setUniform2f("uv_to", (float) XDIM / img_x, (float) YDIM / img_y);
                if(centered) myShaderProgram.setUniform1f("offset_x", (float)(i)*(1f + spacing) - .5f*totalWidth);
                else myShaderProgram.setUniform1f("offset_x", (float)(i)*(1f + spacing));
                if(vcentered) myShaderProgram.setUniform1f("offset_y", -(float)line*(1f + spacing) + .5f*totalHeight - size * YDIM * .5f);
                else myShaderProgram.setUniform1f("offset_y", -(float)line*(1f + spacing));
                GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
            }
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

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

    public void setPos(int xpos, int ypos) {
        this.xpos = 2f * (float)xpos / MyMainClass.getWidth() - 1f;
        this.ypos = 2f * (float)ypos / MyMainClass.getHeight() - 1f;
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

    public void setMaxLineWidth(int maxLineWidth){
        this.maxLineWidth = maxLineWidth;
    }

    public int getMaxLineWidth(){
        return this.maxLineWidth;
    }

    private int maxLineWidth = 0;
    private float xpos = 0f, ypos = 0f, size = 1f;
    private MyVec3 col = new MyVec3(1f, 1f, 1f);
    private float alpha = 1f;
    private float spacing = .01f;

    public boolean isCentered() {
        return centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public boolean isVerticalCentered() {
        return vcentered;
    }

    public void setVerticalCentered(boolean vcentered) {
        this.vcentered = vcentered;
    }

    private boolean centered = false;
    private boolean vcentered = false;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private float rotation = 0f;

}
