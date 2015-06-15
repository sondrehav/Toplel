package com.toplel.ui.elements.elements;

import com.toplel.ui.elements.MyElement;
import com.toplel.ui.elements.MyFont;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MyLabel extends MyElement {

    public String text = "";
    public float size = 50f; // in pixels
    public Vector3f color = new Vector3f(1f, 1f, 1f);
    public float alpha = 1f;
    public float verticalSpacing = 0f, horizontalSpacing = .3f;
    public boolean verticalCentered = false, horizontalCentered = false;
    private MyVertexObject vertexObject;

    private float calculatedWidth;
    private float calculatedHeight;

    private static MyShaderProgram shaderProgram = null;

    private MyFont font;

    public MyLabel(Vector2f pos, Vector2f size, String fontPath){
        super(pos, size);
        if(shaderProgram==null){
            shaderProgram = MyShaderProgram.addShaderProgram("res/shader/text/text.vs", "res/shader/text/text.fs");
        }
        font = null;//MyFont.addFont(fontPath);
        vertexObject = MyVertexObject.createSquare(0f, 0f, 1f, 1f);
    }

    @Override
    public void render() {
/*
        // TODO: Make static

        float aspect = (float) font.YDIM / font.XDIM;
        calculatedHeight = size;
        float width = (text.length()-1)*(1f + horizontalSpacing) + 1;
        float height = 1f + verticalSpacing;
        calculatedWidth = size * width / aspect;


        font.texture.bind();
        shaderProgram.bind();
        shaderProgram.setVec3("in_color", color);
        shaderProgram.setUniform1f("in_alpha", alpha);
        shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
        vertexObject.bind();/*
        for (int i = 0; i < text.length(); i++) {
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
*/
    }

}
