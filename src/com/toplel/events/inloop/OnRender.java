package com.toplel.events.inloop;

import java.util.*;

public abstract class OnRender {

    private static ArrayList<DrawInterface> listeners = new ArrayList<>();

    public static void poll(){

        for (DrawInterface renderer : listeners){
            renderer.render();
        }
        while(!toRemove.isEmpty()){
            listeners.remove(toRemove.poll());
        }
        while(!toAdd.isEmpty()){
            DrawInterface r = toAdd.poll();
            if(!listeners.contains(r)){
                listeners.add(r);
            }
        }
    }

    private static Queue<DrawInterface> toAdd = new LinkedList<>();
    public static void addListener(DrawInterface listener){
        toAdd.add(listener);
    }

    private static Queue<DrawInterface> toRemove = new LinkedList<>();
    public static void removeListener(DrawInterface listener){
        toRemove.add(listener);
    }

}
