package util.input;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MyKeyboardHandler {

    private static class Event{
        int key;
        MyEventType type;

        public Event(int key, MyEventType type) {
            this.key = key;
            this.type = type;
        }
    }

    private static ArrayList<Event> events = new ArrayList<>();
    public static void poll(){
        events.clear();
        while(Keyboard.next()) {
            Keyboard.poll();
            int key = Keyboard.getEventKey();
            MyEventType type = Keyboard.getEventKeyState() ? MyEventType.BUTTON_DOWN : MyEventType.BUTTON_UP;
            events.add(new Event(key, type));
        }
    }

    //---------

    HashMap<Integer, ArrayList<MyListener>> list = new HashMap<>();
    public void addListener(MyListener input){
        if(!list.containsKey(input.key)){
            ArrayList<MyListener> ls = new ArrayList<>();
            ls.add(input);
            list.put(input.key, ls);
        } else {
            list.get(input.key).add(input);
        }
    }

    public void removeListener(MyListener input){ //TODO: Remove
//        for(Map.Entry<Integer, ArrayList<MyListener>> e : list.entrySet()){
//            for(MyListener registered : e.getValue()){
//                if(registered.equals(input)){
//
//                }
//            }
//        }
    }

    private boolean enabled = true;
    public void disable(){
        enabled = false;
    }

    public void enable(){
        enabled = true;
    }

    public void update(){
        if(!enabled) return;
        Queue<Event> queue = new LinkedList<>();
        queue.addAll(events);
        while(!queue.isEmpty()){
            Event event = queue.poll();
            int key = event.key;
            MyEventType type = event.type;
            if(list.containsKey(key)){
                for(MyListener registered : list.get(key)){
                    if(registered.type == type) registered.event();
                }
            }
        }
    }

}
