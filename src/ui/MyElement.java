package ui;

import math.MyRegion;
import renderer.MyRenderer;

import java.util.ArrayList;

public abstract class MyElement {

    protected MyRegion region = new MyRegion(0f, 0f, 1f, 1f);
    ArrayList<MyElement> children = null;
    private MyElement parent = null;

    public MyElement() {}

    public void setRegion(MyRegion region){
        this.region = region;
    }

    public MyRegion getRegion(){
        return this.region.clone();
    }

    public abstract void render();

    public final void addChild(MyElement element){
        if(children==null) children = new ArrayList<>();
        children.add(element);
    }

    public final void removeChild(MyElement element){
        children.remove(element);
    }

    public void attachTo(MyElement element){
        element.addChild(this);
    }

}
