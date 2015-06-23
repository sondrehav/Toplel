package com.toplel.events.keyboard;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MyKeyboardEventHandler {

    private static final boolean[] keys = new boolean[256];
    private static HashMap<Integer, ArrayList<MyKeyListener>> listeners = new HashMap<>();

    // TODO: This will throw ConcurrentModificationException!!! Add "toAdd" and "toRemove".

    public static void poll(){
        while(Keyboard.next()){
            int eventKey = Keyboard.getEventKey();
            boolean state = Keyboard.getEventKeyState();
            keys[eventKey] = state;
            for(MyKeyListener listener : listeners.get(eventKey)){
                if(state) listener.onKeyDown();
                else listener.onKeyUp();
            }
        }
        for(int i=0;i<keys.length;i++){ // TODO: Replace with something more efficient.
            if(keys[i]){
                for(MyKeyListener k : listeners.get(i)){
                    k.onKeyHold();
                }
            }
        }
    }

    public static void addListener(MyKeyListener listener, int key){
        if(!listeners.containsKey(key)) listeners.put(key, new ArrayList<MyKeyListener>());
        listeners.get(key).add(listener);
    }

    public static void removeListener(MyKeyListener listener){
        for(Map.Entry<Integer, ArrayList<MyKeyListener>> listenerEntry : listeners.entrySet()){
            if(listenerEntry.getValue().contains(listener)){
                listenerEntry.getValue().remove(listener);
            }
        }
    }

}
