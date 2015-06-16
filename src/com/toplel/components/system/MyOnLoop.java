package com.toplel.components.system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MyOnLoop {

    private ArrayList<OnAction> events = new ArrayList<>();
    private ArrayList<OnAction> renders = new ArrayList<>();

    private Queue<OnAction> r_toAdd = new LinkedList<>();
    private Queue<OnAction> e_toAdd = new LinkedList<>();
    private Queue<OnAction> r_toRemove = new LinkedList<>();
    private Queue<OnAction> e_toRemove = new LinkedList<>();

    private boolean renderActive = true;
    private boolean eventActive = true;

    public boolean isEventActive() {
        return eventActive;
    }

    /**
     * Used for enabling / disabling events in loop.
     * @param eventActive
     */
    public void setEventActive(boolean eventActive) {
        this.eventActive = eventActive;
    }

    public boolean isRenderActive() {

        return renderActive;
    }

    /**
     * Used for enabling / disabling renders in loop.
     * @param renderActive
     */
    public void setRenderActive(boolean renderActive) {
        this.renderActive = renderActive;
    }

    public void addOnEvent(OnAction event){
        e_toAdd.add(event);
    }

    public void removeOnEvent(OnAction event){
        e_toRemove.add(event);
    }

    public void addOnRender(OnAction render){
        r_toAdd.add(render);
    }

    public void removeOnRender(OnAction render){
        r_toRemove.add(render);
    }

    public void event(){
        if(eventActive){
            for(OnAction e : events){
                e.onAction();
            }
        }
        while(!e_toRemove.isEmpty()){
            OnAction e = e_toRemove.poll();
            if(events.contains(e)) events.remove(e);
            else System.err.println("Cant find element to remove from events in MyOnLoop: event().");
        }
        while(!e_toAdd.isEmpty()){
            OnAction e = e_toAdd.poll();
            if(!events.contains(e)) events.add(e);
            else System.err.println("Event already added in MyOnLoop: event().");
        }
    }

    public void render(){
        if(renderActive){
            for(OnAction r : renders){
                r.onAction();
            }
        }
        while(!r_toRemove.isEmpty()){
            OnAction r = r_toRemove.poll();
            if (renders.contains(r)) renders.remove(r);
            else System.err.println("Cant find element to remove from renders in MyOnLoop: render().");
        }
        while(!r_toAdd.isEmpty()){
            OnAction r = r_toAdd.poll();
            if(!renders.contains(r)) renders.add(r);
            else System.err.println("Event already added in MyOnLoop: render().");
        }
    }

}
