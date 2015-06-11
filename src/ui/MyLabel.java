package ui;

import math.MyMat3;
import math.MyVec3;
import renderer.objects.MyShaderProgram;
import renderer.objects.MyVertexObject;

import java.io.IOException;

public class MyLabel extends MyElement {

    public String text = "";
    public float size = 100f; // in pixels
    public MyVec3 color = new MyVec3(1f, 1f, 1f);
    public float alpha = 1f;
    public float verticalSpacing = 0f, horizontalSpacing = .3f;
    public boolean verticalCentered = false, horizontalCentered = false;
    public MyVertexObject vertexObject;

    private static MyShaderProgram shaderProgram = null;

    private MyFont font;

    public MyLabel(String fontPath){
        super();
        if(shaderProgram==null){
            try{
                shaderProgram = MyShaderProgram.addShaderProgram("res/shader/text/text.vs", "res/shader/text/text.fs");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        font = MyFont.addFont(fontPath);
        vertexObject = MyVertexObject.createSquare(0f, 0f, 1f, 1f);
    }

    public static boolean t = true;
    public void render(MyMat3 projectionMatrix) {

        // TODO: Make static

        switch (this.attached){
            case TOP:

                break;
            case TOP_LEFT:

                break;
            case TOP_RIGHT:

                break;
            case BOTTOM:

                break;
            case BOTTOM_LEFT:

                break;
            case BOTTOM_RIGHT:

                break;
            case LEFT:

                break;
            case RIGHT:

                break;
            case CENTER:

                break;
        }

        MyMat3 viewMatrix = MyMat3.getIdentity();
        float aspect = (float) font.YDIM / font.XDIM;
        viewMatrix = viewMatrix.translate(new MyVec3(this.position.vector[0], this.position.vector[1], 0f));
        viewMatrix = viewMatrix.scale(new MyVec3(this.size / aspect, this.size, 1f));

        font.texture.bind();
        shaderProgram.bind();
        shaderProgram.setVec3("in_color", color);
        shaderProgram.setUniform1f("in_alpha", alpha);
        shaderProgram.setUniformMat3("vw_matrix", viewMatrix);
        shaderProgram.setUniformMat3("pr_matrix", projectionMatrix);
        vertexObject.bind();
        for (int i = 0; i < text.length(); i++) {
            MyMat3 modelMatrix = MyMat3.getIdentity();
            char c = text.charAt(i);
            shaderProgram.setRegion("uv_region", font.getNormalizedRegion(c));
            modelMatrix = modelMatrix.translate(new MyVec3((float)i*(1f + horizontalSpacing), 0f, 0f));
            shaderProgram.setUniformMat3("md_matrix", modelMatrix);
            vertexObject.draw();
        }

        vertexObject.unbind();
        shaderProgram.unbind();
        font.texture.unbind();

    }
    float m  = 1f;
}
