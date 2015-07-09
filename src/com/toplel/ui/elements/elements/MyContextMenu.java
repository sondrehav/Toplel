package com.toplel.ui.elements.elements;

import com.toplel.ui.elements.MyElement;
import com.toplel.util.Console;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class MyContextMenu extends MyElement {

    private HashMap<Integer, MySubElement> elements = new HashMap<>();

    private int index = 0;

    public MyContextMenu(Vector2f position, Vector2f size){
        super(position, size);
    }

    public MySubElement addElement(String label){
        MySubElement e = new MySubElement(label, index, this);
        e.setContext(this.getContext());
        elements.put(index, e);
        /*MyMouseEventHandler.addListener(new MyMouseListener(e, 0) {
            @Override public void onMouseIn(float mx, float my) { e.alpha=.7f; }
            @Override public void onMouseOut(float mx, float my) { e.alpha=.5f; }
            @Override public void onMouseDown(float mx, float my) {
                e.alpha=.8f;
                if(e.listener!=null) e.listener.event();
            }
            @Override public void onMouseUp(float mx, float my) { e.alpha=.7f; }
        });*/
        index++;
        return e;
    }

    public void render(){
        if(!isActive()) return;
        for (Map.Entry<Integer, MySubElement> e : elements.entrySet()){
            e.getValue().render();
        }
    }

    public void addListener(int element, OnAction action){
        if(elements.containsKey(element)) elements.get(element).listener=action;
        else Console.printErr("No element at index " + element + ".");
    }

    public void setPosition(Vector2f position){
        super.setPosition(position);
        // TODO: Subelements
    }

    public static class MySubElement extends MyElement {

        OnAction listener = null;

        MyTexture texture;
        MyShaderProgram shaderProgram;
        MyVertexObject vertexObject;

        public float alpha = .5f;

        String label;
        private MySubElement(String label, int index, MyContextMenu parent){
            super(new Vector2f(parent.getPosition().x, parent.getPosition().y + parent.getSize().y * index++), parent.getSize());
            this.label = label;
            shaderProgram = MyShaderProgram.addShaderProgram("res/shader/def_test.vs", "res/shader/def_test.fs");
            vertexObject = MyVertexObject.createSquare(0f,0f,1f,1f);
        }

        public void render(){
            shaderProgram.bind();
            shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
            shaderProgram.setUniformMat4("md_matrix", md_matrix);
            shaderProgram.setUniform1f("alpha", this.alpha);
            vertexObject.bind();
            vertexObject.draw();
            vertexObject.unbind();
            shaderProgram.unbind();
        }

    }

}
