package oldold.game;

import oldold.math.MyMat3;
import oldold.math.vector.MyVec2;
import oldold.renderer.objects.MyShaderProgram;
import oldold.renderer.objects.MyTexture;
import oldold.renderer.objects.MyVertexObject;

import java.io.IOException;

public class MyTree extends MyEntity {

    private MyVertexObject vertexObject;
    private MyMat3 md_matrix;

    private static MyTexture texture = null;
    private static MyShaderProgram shaderProgram = null;

    private float hue;

    public MyTree(MyVec2 position, float rotation, float size, float hue){

        texture = MyTexture.addTexture("res/img/oldold.game/objects/tree.png");
        try{
            shaderProgram = MyShaderProgram.addShaderProgram("res/shader/tree.vs", "res/shader/tree.fs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyVec2 _size = new MyVec2(size, size).scale(.5f);
        vertexObject = MyVertexObject.createSquare(position.subtract(_size), position.add(_size));

        md_matrix = MyMat3.getIdentity();
        md_matrix = md_matrix.rotate(rotation);
        md_matrix = md_matrix.translate(position);

        this.hue = hue;

    }

    @Override
    public void render(MyMat3 projectionMatrix, MyMat3 viewMatrix){

        texture.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat3("pr_matrix", projectionMatrix);
        shaderProgram.setUniformMat3("vw_matrix", viewMatrix);
        shaderProgram.setUniformMat3("md_matrix", md_matrix);
        shaderProgram.setUniform1f("hue", hue);
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        texture.unbind();

    }

}
