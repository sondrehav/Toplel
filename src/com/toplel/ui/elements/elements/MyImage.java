package com.toplel.ui.elements.elements;

import com.toplel.main.MyMain;
import com.toplel.math.MyMat3;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.MyElement;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;

public class MyImage extends MyElement {

    MyTexture texture;
    MyShaderProgram shaderProgram;
    MyVertexObject vertexObject;

    public float alpha = 1f;

    public MyImage(String image, MyVec2 position){
        super(position, new MyVec2(100f, 100f));
        texture = MyTexture.addTexture(image);
        vertexObject = MyVertexObject.createSquare(0f, 0f, texture.WIDTH, texture.HEIGHT);
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");
    }

    @Override
    public void render(MyMat3 viewMatrix){
        shaderProgram.bind();
        shaderProgram.setUniformMat3("pr_matrix", MyMain.getProjection());
        shaderProgram.setUniformMat3("vw_matrix", viewMatrix);
        MyMat3 md_matrix = MyMat3.getIdentity();
        md_matrix = md_matrix.translate(this.getPosition());
        shaderProgram.setUniform1f("alpha", alpha);
        shaderProgram.setUniformMat3("md_matrix", md_matrix);
        texture.bind();
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        texture.unbind();
        shaderProgram.unbind();
    }

}
