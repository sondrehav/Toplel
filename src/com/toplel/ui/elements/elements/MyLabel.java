package com.toplel.ui.elements.elements;

import com.toplel.math.MyMat3;
import com.toplel.math.MyVec2;
import com.toplel.math.MyVec3;
import com.toplel.ui.elements.MyElement;
import com.toplel.ui.elements.MyFont;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;

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

    public MyLabel(MyVec2 pos, MyVec2 size, String fontPath){
        super(pos, size);
        if(shaderProgram==null){
            shaderProgram = MyShaderProgram.addShaderProgram("res/shader/text/text.vs", "res/shader/text/text.fs");
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
