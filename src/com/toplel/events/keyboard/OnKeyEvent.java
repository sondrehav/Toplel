package com.toplel.events.keyboard;

public abstract class OnKeyEvent implements KeyListener {

    public final int key;

    public OnKeyEvent(int key){
        this.key = key;
        enable();
    }

    public final void enable(){
        KeyboardEventHandler.addListener(this);
    }

    public final void disable(){
        KeyboardEventHandler.removeListener(this);
    }

}
