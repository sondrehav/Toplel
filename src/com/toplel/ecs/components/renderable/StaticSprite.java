package com.toplel.ecs.components.renderable;

import com.toplel.ecs.components.Component;
import com.toplel.ecs.entity.GameObject;
import com.toplel.events.inloop.DrawInterface;
import com.toplel.events.inloop.OnRender;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Matrix4f;

public class StaticSprite extends Component implements DrawInterface {

    private MyTexture texture;
    private static final MyVertexObject vertexObject = MyVertexObject.SQUARE;
    private static final MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");

    public StaticSprite(GameObject parent) {
        super(parent);
    }

    @Override
    public void start() {
        OnRender.addListener(this);
    }

    @Override
    public void destroy() {
        OnRender.removeListener(this);
    }

    @Override
    public String key() {
        return "staticSprite";
    }

    @Override
    public void render() {
        Matrix4f md_matrix = parent.getTransform().getMd_matrix();
        Matrix4f prvw_matrix = parent.getTransform().getContext().getViewProjection();
        texture.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", prvw_matrix);
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        texture.unbind();
    }
}
