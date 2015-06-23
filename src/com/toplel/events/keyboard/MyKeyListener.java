package com.toplel.events.keyboard;

public interface MyKeyListener {

    default public void onKeyDown(){}
    default public void onKeyUp(){}
    default public void onKeyHold(){}

}
