package com.toplel.main;

import java.util.ArrayList;

public abstract class OnResize {

    static ArrayList<OnResize> listeners = new ArrayList<>();

    public abstract void onResize(int width, int height, int old_width, int old_height);

    public OnResize(){
        listeners.add(this);
    }

    public void removeThis(){
        listeners.remove(this);
    }

}
