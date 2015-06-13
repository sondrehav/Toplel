package com.toplel.state;

import com.toplel.math.MyMat3;

public abstract class MyMasterState {

    private static MyMasterState currentState = null;

    public static void switchState(MyMasterState newState){
        if(currentState!=null) currentState.close();
        currentState = newState;
        currentState.init();
    }

    public static void stateEvent(){
        currentState.event();
    }

    public static void stateRender(){
        currentState.render();
    }

    public abstract void init();
    public abstract void event();
    public abstract void render();
    public abstract void close();

}
