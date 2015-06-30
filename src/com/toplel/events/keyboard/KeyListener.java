package com.toplel.events.keyboard;

public interface KeyListener {

    default public void onKeyDown(){}
    default public void onKeyUp(){}
    default public void onKeyHold(){}

}
