package com.toplel.state;

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

    public static void stateResize(){
        currentState.onResize();
    }

    public abstract void init();
    public abstract void event();
    public abstract void close();
    public void onResize(){}

}
