package com.toplel.events.keyboard;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class MyKeyboardEventHandler {

    private static ArrayList<OnKeyEvent> listeners = new ArrayList<>();

    private static final boolean[] keys = new boolean[256];

    // TODO: This will throw ConcurrentModificationException!!! Add "toAdd" and "toRemove".

    public static void poll(){
        while(Keyboard.next()){
            int eventKey = Keyboard.getEventKey();
            boolean state = Keyboard.getEventKeyState();
            keys[eventKey] = state;
            for(OnKeyEvent listener : listeners){
                if(listener.key==eventKey&&state) listener.onKeyDown();
                else if(listener.key==eventKey&&!state) listener.onKeyUp();
            }
        }
        for(OnKeyEvent listener : listeners){
            if(keys[listener.key]) listener.onKeyHold();
        }
    }

    public static void addListener(OnKeyEvent listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public static void removeListener(OnKeyEvent listener){
        if(listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

}
