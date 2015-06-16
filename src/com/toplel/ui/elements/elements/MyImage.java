package com.toplel.ui.elements.elements;

import com.toplel.ui.elements.MyElement;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Vector2f;

public class MyImage extends MyElement {

    MyTexture texture;
    MyShaderProgram shaderProgram;
    MyVertexObject vertexObject;

    public float alpha = 1f;

    public MyImage(String image, Vector2f position, Vector2f size){
        super(position, size);
        texture = MyTexture.addTexture(image);
        vertexObject = MyVertexObject.createSquare(0f, 0f, 1f, 1f);
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");
    }

    @Override
    public void render(){
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        shaderProgram.setUniform1f("alpha", alpha);
        texture.bind();
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        texture.unbind();
        shaderProgram.unbind();
    }

}
