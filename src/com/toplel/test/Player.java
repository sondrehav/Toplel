package com.toplel.test;

import com.toplel.context.MyContext;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Player {

    float speed = 10f;

    public Vector2f getPosition() {
        return position;
    }

    Matrix4f md_matrix = new Matrix4f();
    Vector2f position = new Vector2f();

    private OnKeyEvent[] keyEvents = new OnKeyEvent[4];

    private MyTexture texture = MyTexture.addTexture("res/img/player/playerBase.png");
    private MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");
    private MyVertexObject vertexObject = MyVertexObject.createSquare(0f,0f,1f,1f);

    public Player(){
        keyEvents[0]=new OnKeyEvent(Keyboard.KEY_W) {@Override public void onKeyHold() {position.y+=speed;}};
        keyEvents[1]=new OnKeyEvent(Keyboard.KEY_A) {@Override public void onKeyHold() {position.x-=speed;}};
        keyEvents[2]=new OnKeyEvent(Keyboard.KEY_S) {@Override public void onKeyHold() {position.y-=speed;}};
        keyEvents[3]=new OnKeyEvent(Keyboard.KEY_D) {@Override public void onKeyHold() {position.x+=speed;}};
    }

    public void render(){

        Matrix4f.setIdentity(md_matrix);
        Matrix4f.translate(position, md_matrix, md_matrix);
        Matrix4f.scale(new Vector3f(100f,100f,1f), md_matrix, md_matrix);

        texture.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", MyContext.get("world").getViewProjection());
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
//        System.out.println(Matrix4f.mul(MyContext.get("world").getViewProjection(), md_matrix, null));
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        texture.unbind();
    }

}
