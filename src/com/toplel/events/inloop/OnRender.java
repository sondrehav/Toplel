package com.toplel.events.inloop;

import com.toplel.util.objects.MySimpleFileReader;

import java.io.IOException;
import java.util.*;

public abstract class OnRender implements Comparable<OnRender> {

    public OnRender(){
        this(0);
    }

    public OnRender(int layer){
        this.layer = layer;
        addListener(this);
    }

    int layer;

    @Override
    public int compareTo(OnRender o) {
        int change1 = o.layer;
        int change2 = this.layer;
        if (change1 < change2) return -1;
        if (change1 > change2) return 1;
        return 0;
    }

    public abstract void render();

    public void disable(){
        removeListener(this);
    }

    public void enable(){
        addListener(this);
    }

    private static class SetLayer{
        int newLayer;
        OnRender renderer;
        public SetLayer(int newLayer, OnRender renderer) {
            this.newLayer = newLayer;
            this.renderer = renderer;
        }
    }

    private static Queue<SetLayer> setLayer = new LinkedList<>();
    public void setLayer(int layer){
        setLayer.add(new SetLayer(layer, this));
    }

    public int getLayer(){
        return this.layer;
    }

    private static TreeMap<Integer, ArrayList<OnRender>> listeners = new TreeMap<>();

    public static void poll(){

        for (Map.Entry<Integer, ArrayList<OnRender>> listener : listeners.entrySet()){
            for(OnRender l : listener.getValue()){
                l.render();
            }
        }
        while(!toRemove.isEmpty()){
            OnRender r = toRemove.poll();
            if(listeners.containsKey(r.layer)){
                if(listeners.get(r.layer).contains(r)){
                    listeners.get(r.layer).remove(r);
                } else {
                    System.err.println("No such renderer in");
                }
            }
        }
        while(!toAdd.isEmpty()){
            OnRender r = toAdd.poll();
            if(listeners.containsKey(r.layer)){
                if(!listeners.get(r.layer).contains(r)){
                    listeners.get(r.layer).add(r);
                }
            }
            else{
                listeners.put(r.layer, new ArrayList<OnRender>());
                listeners.get(r.layer).add(r);
            }
        }
        while(!setLayer.isEmpty()){
            SetLayer s = setLayer.poll();
            int oldLayer = s.renderer.layer;
            int newLayer = s.newLayer;
            listeners.get(oldLayer).remove(s.renderer);
            if(!listeners.containsKey(newLayer)){
                listeners.put(newLayer, new ArrayList<OnRender>());
            }
            listeners.get(newLayer).add(s.renderer);
        }
    }

    private static Queue<OnRender> toAdd = new LinkedList<>();
    public static void addListener(OnRender listener){
        toAdd.add(listener);
    }

    private static Queue<OnRender> toRemove = new LinkedList<>();
    public static void removeListener(OnRender listener){
        toRemove.add(listener);
    }

}
