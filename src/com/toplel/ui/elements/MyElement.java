package com.toplel.ui.elements;

import com.toplel.math.MyMat3;
import com.toplel.math.MyRegion;
import com.toplel.math.MyVec2;

import java.util.ArrayList;

public class MyElement {

    MyAnchor anchor = MyAnchor.BOTTOM_LEFT;
    public final MyVec2 size;
    public final MyVec2 position;
    protected MyElement parent = null;

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

    public MyRegion getRegion(){
        return new MyRegion(this.position.x, this.position.y,
                this.position.x + this.size.x, this.position.y + this.size.y);
    }

}
