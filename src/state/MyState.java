package state;

import math.MyMat3;

public abstract class MyState {

    public abstract void init();
    public abstract void event();
    public abstract void render(MyMat3 projectionMatrix);
    public abstract void close();

    @Override
    public String toString(){
        return this.getClass().toString();
    }

}
