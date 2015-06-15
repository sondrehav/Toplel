package com.toplel.ui.elements;

import com.toplel.events.mouse.MyListenerInstance;
import com.toplel.main.MyContext;
import com.toplel.math.MyMatrix4f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class MyElement implements MyListenerInstance {

    MyAnchor anchor = MyAnchor.BOTTOM_LEFT;
    private Vector2f size;
    private Vector2f position;
    protected MyElement parent = null;

    protected final Matrix4f md_matrix;
    protected final Matrix4f inv_md_matrix;

    public Vector2f getPosition() {
        return position;
    }
    public Vector2f getSize() {
        return size;
    }

    protected ArrayList<MyElement> children = new ArrayList<>();

    public MyElement(Vector2f position, Vector2f size){
        md_matrix = Matrix4f.setIdentity(new Matrix4f());
        Matrix4f.translate(position, md_matrix, md_matrix);
        MyMatrix4f.scaleXY(size, md_matrix, md_matrix);
        inv_md_matrix = Matrix4f.invert(md_matrix, null);
        this.size = size;
        this.position = position;
    }

    public void addChild(MyElement child){
        child.parent = this;
        children.add(child);
    }

    public void removeChild(MyElement child){
        child.parent = null;
        children.remove(child);
    }

    public void render(){}

    public void event(){}

    private MyContext context;
    public void setContext(MyContext context){ this.context = context; }
    public MyContext getContext(){ return this.context; }

    public void setPosition(Vector2f position){
        Matrix4f.setIdentity(md_matrix);
        Matrix4f.translate(position, md_matrix, md_matrix);
        Matrix4f.scale(new Vector3f(size.x, size.y, 1f), md_matrix, md_matrix);
        Matrix4f.invert(md_matrix, inv_md_matrix);
    }

    @Override
    public Matrix4f getInvertedModelMatrix() {
        return inv_md_matrix;
    }

}
