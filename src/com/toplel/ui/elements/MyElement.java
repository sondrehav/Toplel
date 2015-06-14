package com.toplel.ui.elements;

import com.toplel.event.mouse.MyListenerInstance;
import com.toplel.main.MyContext;
import com.toplel.math.MyMat3;
import com.toplel.math.MyRegion;
import com.toplel.math.MyVec2;

import java.util.ArrayList;

public class MyElement implements MyListenerInstance {

    MyAnchor anchor = MyAnchor.BOTTOM_LEFT;
    private MyVec2 size;
    private MyVec2 position;
    protected MyElement parent = null;

    public MyVec2 getPosition() {
        return position;
    }

    public void setPosition(MyVec2 position) {
        this.position = position;
    }

    public MyVec2 getSize() {
        return size;
    }

    public void setSize(MyVec2 size) {
        this.size = size;
    }


    protected ArrayList<MyElement> children = new ArrayList<>();

    public MyElement(MyVec2 position, MyVec2 size){
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

    public final void render(){
        render(MyMat3.getIdentity());
    }

    public void render(MyMat3 viewMatrix){}
    public void event(){}

    protected MyContext context = MyContext.HUD;
    public void setContext(MyContext context){
        this.context = context;
    }

    public MyContext getContext(){
        return this.context;
    }

    @Override
    public MyRegion getRegion(){
        return new MyRegion(this.position.x, this.position.y,
                this.position.x + this.size.x, this.position.y + this.size.y);
    }

}
