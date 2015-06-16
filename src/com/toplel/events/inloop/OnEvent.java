package com.toplel.events.inloop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class OnEvent {

    public OnEvent(){
        addListener(this);
    }

    public abstract void event();

    public void disable(){
        removeListener(this);
    }

    public void enable(){
        addListener(this);
    }

    private static ArrayList<OnEvent> listeners = new ArrayList<>();

    public static void poll(){

        for (OnEvent listener : listeners){
            listener.event();
        }
        while(!toAdd.isEmpty()){
            listeners.add(toAdd.poll());
        }
        while(!toRemove.isEmpty()){
            listeners.remove(toRemove.poll());
        }
    }

    private static Queue<OnEvent> toAdd = new LinkedList<>();
    public static void addListener(OnEvent listener){
        toAdd.add(listener);
    }

    private static Queue<OnEvent> toRemove = new LinkedList<>();
    public static void removeListener(OnEvent listener){
        toRemove.add(listener);
    }

}
