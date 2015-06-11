package ui;

import math.MyMat3;
import math.MyVec2;
import math.MyVec3;
import renderer.objects.MyShaderProgram;
import renderer.objects.MyVertexObject;

import java.io.IOException;

public class MyLabel extends MyElement {

    public String text = "";
    public float size = 50f; // in pixels
    public MyVec3 color = new MyVec3(1f, 1f, 1f);
    public float alpha = 1f;
    public float verticalSpacing = 0f, horizontalSpacing = .3f;
    public boolean verticalCentered = false, horizontalCentered = false;
    private MyVertexObject vertexObject;

    private float calculatedWidth;
    private float calculatedHeight;

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

    @Override
    public void render(MyMat3 projectionMatrix) {

        // TODO: Make static

        float aspect = (float) font.YDIM / font.XDIM;
        calculatedHeight = size;
        float width = (text.length()-1)*(1f + horizontalSpacing) + 1;
        float height = 1f + verticalSpacing;
        calculatedWidth = size * width / aspect;


        MyMat3 viewMatrix = MyMat3.getIdentity();
        MyVec2 internalPos = this.internalPos();
        viewMatrix = viewMatrix.translate(new MyVec3(internalPos.vector[0], internalPos.vector[1], 0f));
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
            if(!verticalCentered&&!horizontalCentered)
                modelMatrix = modelMatrix.translate(new MyVec3((float)i*(1f + horizontalSpacing), 0f, 0f));
            else if(verticalCentered&&!horizontalCentered)
                modelMatrix = modelMatrix.translate(new MyVec3((float)i*(1f + horizontalSpacing)-width*.5f, 0f, 0f));
            else if(verticalCentered&&horizontalCentered)
                modelMatrix = modelMatrix.translate(new MyVec3((float)i*(1f + horizontalSpacing)-width*.5f, -height*.5f, 0f));
            else if(!verticalCentered&&horizontalCentered)
                modelMatrix = modelMatrix.translate(new MyVec3((float)i*(1f + horizontalSpacing), -height*.5f, 0f));
            shaderProgram.setUniformMat3("md_matrix", modelMatrix);
            vertexObject.draw();
        }

        vertexObject.unbind();
        shaderProgram.unbind();
        font.texture.unbind();

    }
}
