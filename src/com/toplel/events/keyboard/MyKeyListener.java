package com.toplel.events.keyboard;

import org.lwjgl.util.vector.Vector2f;

interface MyKeyListener {

    default public void onKeyDown(){}
    default public void onKeyUp(){}
    default public void onKeyHold(){}

}
