package com.toplel.ui.elements.elements;

import com.toplel.main.MyMain;
import com.toplel.math.MyMatrix4f;
import com.toplel.ui.elements.MyElement;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class MyPane extends MyElement {

    MyVertexObject vertexObject;
    MyShaderProgram shaderProgram;

    public float alpha = 1f;
    public boolean center = false;

    public MyPane(Vector2f position, Vector2f size){
        super(position, size);
        vertexObject = MyVertexObject.createSquare(
                0f, 0f,
                1f, 1f
        );
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/def_test.vs", "res/shader/def_test.fs");
    }

    @Override
    public void render(){
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        shaderProgram.setUniform1f("alpha", alpha);
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        for(MyElement element : children){
            element.render();
        }
    }

}
