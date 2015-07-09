package com.toplel.ui.elements;

import com.toplel.main.MyMain;
import com.toplel.test.Region;
import com.toplel.test.Tileset;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MyFont {

    private Tileset tileset;
    private int offset;

    private static MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/text/text.vs", "res/shader/text/text.fs");
    private static MyVertexObject[] vertexObjects;

    private float aspectX = 1f;
    private float aspectY = 1f;

    public MyFont(Tileset texture, int offset) {
        this.tileset = texture;
        this.offset = offset;
        vertexObjects = new MyVertexObject[this.tileset.getTilesPerCol()*this.tileset.getTilesPerRow()];
        for (int i = 0; i < this.tileset.getMax(); i++) {
            Region r = this.tileset.getUVRegion(i);
            float[] vertecies = new float[]{0f,0f,1f,0f,1f,1f,0f,1f};
            float[] texCoords = new float[]{r.x0,r.y1,r.x1,r.y1,r.x1,r.y0,r.x0,r.y0};
            int[] indices = new int[]{0, 1, 2, 2, 3, 0};
            vertexObjects[i] = new MyVertexObject(new float[][]{vertecies, texCoords}, indices);
        }
        float aspectRatio = (float)texture.tileHeight / texture.tileWidth;
        if(aspectRatio<1f){
            aspectY = aspectRatio;
        } else {
            aspectX = 1f / aspectRatio;
        }
    }

    public void renderText(float x, float y, float size, String text){

        if(text==null) text = "null";

        Matrix4f matrix = Matrix4f.setIdentity(new Matrix4f());
        Matrix4f.translate(new Vector3f(x, y, -.9f), matrix, matrix);
        Matrix4f.scale(new Vector3f(size * aspectX, size * aspectY, size), matrix, matrix);

        tileset.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", MyMain.getProjectionMatrix());
        shaderProgram.setUniform4f("in_color", 1f, 1f, 1f, 1f);
        for (int i = 0; i < text.length(); i++){
            int id = (int)text.charAt(i) - offset;
            shaderProgram.setUniformMat4("md_matrix", matrix);
            vertexObjects[id].bind();
            vertexObjects[id].draw();
            vertexObjects[id].unbind();
            Matrix4f.translate(new Vector2f(1f, 0f), matrix, matrix);
        }
        shaderProgram.unbind();
        tileset.unbind();

    }

    public TextObject getCongestedText(String text){
        if(text==null) text = "null";
        float[] vertecies = new float[text.length()*4*2];
        float[] texCoords = new float[text.length()*4*2];
        int[] indices = new int[6*text.length()];
        for (int i = 0; i < text.length(); i++) {

            Region r = this.tileset.getUVRegion(text.charAt(i)-offset);
            float[] v = new float[]{0f+i,0f,1f+i,0f,1f+i,1f,0f+i,1f};
            float[] t = new float[]{r.x0,r.y1,r.x1,r.y1,r.x1,r.y0,r.x0,r.y0};
            for (int j = 0; j < 8; j++) {
                vertecies[i*8+j] = v[j];
                texCoords[i*8+j] = t[j];
            }
            int[] ind = new int[]{0+i*4, 1+i*4, 2+i*4, 2+i*4, 3+i*4, 0+i*4};
            for (int j = 0; j < 6; j++) {
                indices[i*6+j] = ind[j];
            }
        }
        return new TextObject(text, this, new MyVertexObject(new float[][]{vertecies, texCoords}, indices));
    }

    public static class TextObject {

        private final MyVertexObject vertexObject;
        public final String text;
        private final MyFont font;

        private Vector2f position = new Vector2f(0f,0f);
        private float[] color = new float[]{1f,1f,1f,1f};
        private float size = 10f;
        private float rotation = 0f;
        private float depth = -.9f;

        private TextObject(String text, MyFont font, MyVertexObject vertexObject) {
            this.vertexObject = vertexObject;
            this.text = text;
            this.font = font;
        }

        public void render(){
            render(position.x, position.y);
        }

        public void render(float x, float y){

            Matrix4f md_matrix = Matrix4f.setIdentity(new Matrix4f());
            Matrix4f.translate(new Vector3f(x, y, depth), md_matrix, md_matrix);
            Matrix4f.rotate((float) Math.toRadians(this.rotation), new Vector3f(0f,0f,1f), md_matrix, md_matrix);
            Matrix4f.scale(new Vector3f(size * font.aspectX, size * font.aspectY,1f), md_matrix, md_matrix);

            this.font.tileset.bind();
            shaderProgram.bind();
            shaderProgram.setUniformMat4("md_matrix", md_matrix);
            shaderProgram.setUniformMat4("prvw_matrix", MyMain.getProjectionMatrix());
            shaderProgram.setUniform4f("in_color", color[0], color[1], color[2], color[3]);
            this.vertexObject.bind();
            this.vertexObject.draw();
            this.vertexObject.unbind();
            shaderProgram.unbind();
            this.font.tileset.unbind();

        }

        public void setColor(float r, float b, float g, float a) {
            this.color[0] = r;
            this.color[1] = b;
            this.color[2] = g;
            this.color[3] = a;
        }

        public void setPosition(Vector2f position) {
            this.position = position;
        }

        public void setSize(float size) {
            this.size = size;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        public void setDepth(float depth) {
            this.depth = depth;
        }
    }


}
