package com.toplel.events.keyboard;

import org.lwjgl.input.Keyboard;

import java.util.*;

public abstract class KeyboardEventHandler {

    private static final boolean[] keys = new boolean[256];
    private static ArrayList<OnKeyEvent> listeners = new ArrayList<>();

    // TODO: Switch to HashMap for faster lookup.

    public static void poll(){
        while(Keyboard.next()){
            int eventKey = Keyboard.getEventKey();
            boolean state = Keyboard.getEventKeyState();
            keys[eventKey] = state;
            for(OnKeyEvent listener : listeners){
                if(listener.key==eventKey){
                    if(state) listener.onKeyDown();
                    else listener.onKeyUp();
                }
            }
        }
        for (OnKeyEvent listener : listeners){
            if(keys[listener.key]) listener.onKeyHold();
        }
        while(!toAdd.isEmpty()){
            listeners.add(toAdd.poll());
        }
        while(!toRemove.isEmpty()){
            listeners.remove(toRemove.poll());
        }
    }

    private static Queue<OnKeyEvent> toAdd = new LinkedList<>();
    private static Queue<OnKeyEvent> toRemove = new LinkedList<>();

    public static void addListener(OnKeyEvent listener){
        if(listeners.contains(listener)) return;
        toAdd.add(listener);
    }

    public static void removeListener(OnKeyEvent listener){
        if(!listeners.contains(listener)) return;
        toRemove.add(listener);
    }

}
