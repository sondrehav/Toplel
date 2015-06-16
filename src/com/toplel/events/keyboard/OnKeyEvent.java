package com.toplel.events.keyboard;

public abstract class OnKeyEvent implements MyKeyListener {

    int key;
    public OnKeyEvent(int keycode){
        this.key = keycode;
        enable();
    }

    public final void enable(){
        MyKeyboardEventHandler.addListener(this);
    }

    public final void disable(){
        MyKeyboardEventHandler.removeListener(this);
    }

}
