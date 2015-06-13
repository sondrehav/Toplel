package com.toplel.events.keyboard;

public abstract class MyKeyListener {

    int key;
    public MyKeyListener(int keycode){
        this.key = keycode;
    }

    public void onKeyDown(){}
    public void onKeyUp(){}
    public void onKeyHold(){}

}
