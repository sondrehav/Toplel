package ui;

import math.MyRegion;
import math.MyVec2;
import renderer.MyRenderer;

import java.util.ArrayList;

public abstract class MyElement {

    public MyVec2 position = new MyVec2();
    ArrayList<MyElement> children = null;
    private MyElement parent = null;

    public Attached attached = Attached.BOTTOM_LEFT;

    public MyElement() {}

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

    public static enum Attached{
        TOP, TOP_LEFT, TOP_RIGHT, BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, RIGHT, CENTER
    }

}
