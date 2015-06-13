package com.toplel.ui.elements.elements;

import com.toplel.main.MyMain;
import com.toplel.math.MyMat3;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.MyElement;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;

public class MyPane extends MyElement {

    MyVertexObject vertexObject;
    MyShaderProgram shaderProgram;

    public float alpha = 1f;

    public MyPane(MyVec2 position, MyVec2 size){
        super(position, size);
        vertexObject = MyVertexObject.createSquare(
                0f, 0f,
                size.x, size.y
        );
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/def_test.vs", "res/shader/def_test.fs");
    }

    @Override
    public void render(MyMat3 vw){
        MyMat3 md = MyMat3.getIdentity();
        md = md.translate(this.position);
        shaderProgram.bind();
        shaderProgram.setUniformMat3("pr_matrix", MyMain.getProjection());
        shaderProgram.setUniformMat3("vw_matrix", vw);
        shaderProgram.setUniformMat3("md_matrix", md);
        shaderProgram.setUniform1f("alpha", alpha);
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
    }

}
