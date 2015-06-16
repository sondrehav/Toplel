package com.toplel.ui.elements.elements;

import com.toplel.components.MyPosition;
import com.toplel.components.MySprite;
import com.toplel.context.MyContext;
import com.toplel.events.inloop.OnRender;
import com.toplel.events.mouse.OnMouseEvent;
import com.toplel.main.OnResize;
import com.toplel.ui.elements.MyAnchor;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Vector2f;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyPane extends MyPosition {

    MyVertexObject vertexObject;
    MyShaderProgram shaderProgram;
    MyContext context;
    float alpha = .1f;

    public MyAnchor top = MyAnchor.TOP;
    public MyAnchor bottom = MyAnchor.TOP;
    public MyAnchor left = MyAnchor.RIGHT;
    public MyAnchor right = MyAnchor.RIGHT;

    OnResize r = new OnResize() {
        @Override
        public void onResize(int width, int height, int old_width, int old_height) {
            float old_x0 = getPosition().x;
            float old_y0 = getPosition().y;
            float old_xs = getSize().x;
            float old_ys = getSize().y;
            float old_x1 = old_xs + old_x0;
            float old_y1 = old_ys + old_y0;
            int heightAddition = height - old_height;
            int widthAddition = width - old_width;
            switch (top) {
                case TOP:
                    setSize(new Vector2f(old_xs, old_ys + heightAddition));
                    break;
            }
            switch (left) {
                case RIGHT:
                    setPosition(new Vector2f(old_x0 + widthAddition, old_y0));
                    break;
            }
        }
    };

    OnMouseEvent mouseEventLeft = new OnMouseEvent(0, this) {

        @Override
        public void onMouseIn(Vector2f mouse) {
            alpha = .15f;
        }

        @Override
        public void onMouseOut(Vector2f mouse) {
            alpha = .1f;
        }

//        float startDragX;
//        float startDragY;
//
//        @Override
//        public void onMouseDown(Vector2f mouse) {
//            Vector2f newPosition = context.fromContext(mouse);
//            startDragX = newPosition.x - getPosition().x;
//            startDragY = newPosition.y - getPosition().y;
//        }
//
//        @Override
//        public void onMouseDrag(Vector2f mouse) {
////            System.out.println(context.fromContext(mouse));
//            Vector2f newPosition = context.fromContext(mouse);
//            Vector2f.sub(newPosition, new Vector2f(startDragX, startDragY), newPosition);
//            setPosition(newPosition);
//        }
    };

    public MyPane(Vector2f position, Vector2f size, MyContext context){
        super(position, 0f, size);
        vertexObject = MyVertexObject.createSquare(
                0f, 0f,
                1f, 1f
        );
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/def_test.vs", "res/shader/def_test.fs");
        this.context = context;
    }

    OnRender renderer = new OnRender(5){
        @Override
        public void render() {
            shaderProgram.bind();
            shaderProgram.setUniformMat4("prvw_matrix", context.getViewProjection());
            shaderProgram.setUniformMat4("md_matrix", getModelMatrix());
            shaderProgram.setUniform1f("alpha", alpha);
            vertexObject.bind();
            vertexObject.draw();
            vertexObject.unbind();
            shaderProgram.unbind();
        }
    };

    LinkedList<MySprite> subElements = new LinkedList<>();
    public void add(MySprite position){
        position.setLayer(this.renderer.getLayer()-1);
        position.setContext(this.context);
        float space = 10f;
        Vector2f p = this.getPosition();
        for(MySprite e : subElements){
            p.y += e.getSize().y + space;
        }
        position.setPosition(p);
        subElements.add(position);
    }

}
