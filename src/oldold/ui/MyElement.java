package oldold.ui;

import oldold.main.MyMainClass;
import oldold.math.MyMat3;
import oldold.math.vector.MyVec2;

import java.util.ArrayList;

public abstract class MyElement {

    public MyVec2 position = new MyVec2();
    protected ArrayList<MyElement> children = null;
    protected MyElement parent = null;

    public Attached attached = Attached.BOTTOM_LEFT;

    public MyElement() {}

    public final void addChild(MyElement element){
        if(children==null) children = new ArrayList<>();
        children.add(element);
    }

    public final void removeChild(MyElement element){
        children.remove(element);
    }

    protected MyVec2 internalPos(){
        switch (this.attached){
            case TOP_LEFT:
                return new MyVec2(position.vector[0], MyMainClass.getHeight()-position.vector[1]);
            case TOP_RIGHT:
                return new MyVec2(MyMainClass.getWidth()-position.vector[0], MyMainClass.getHeight()-position.vector[1]);
            case BOTTOM_LEFT:
                return this.position.clone();
            case BOTTOM_RIGHT:
                return new MyVec2(MyMainClass.getWidth()-position.vector[0], position.vector[1]);
        }
        return null;
    }

    public static enum Attached{
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
    }

    public abstract void render(MyMat3 projectionMatrix);

}
