package com.toplel.events.keyboard;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class MyKeyboardEventHandler {

    private static ArrayList<MyKeyListener> listeners = new ArrayList<>();

    private static final boolean[] keys = new boolean[256];

    public static void poll(){
        while(Keyboard.next()){
            int eventKey = Keyboard.getEventKey();
            boolean state = Keyboard.getEventKeyState();
            keys[eventKey] = state;
            for(MyKeyListener listener : listeners){
                if(listener.key==eventKey&&state) listener.onKeyDown();
                else if(listener.key==eventKey&&!state) listener.onKeyUp();
            }
        }
        for(MyKeyListener listener : listeners){
            if(keys[listener.key]) listener.onKeyHold();
        }
    }

    public static void addListener(MyKeyListener listener){
        listeners.add(listener);
    }

    public static void removeListener(MyKeyListener listener){
        listeners.remove(listener);
    }

}
