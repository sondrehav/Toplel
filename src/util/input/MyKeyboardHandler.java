package util.input;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;

public class MyKeyboardHandler {

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
        while(Keyboard.next()){
            Keyboard.poll();
            int key = Keyboard.getEventKey();
            EventType type = Keyboard.getEventKeyState() ? EventType.BUTTON_DOWN : EventType.BUTTON_UP;
            if(list.containsKey(Keyboard.getEventKey())){
                for(MyListener registered : list.get(key)){
                    if(registered.type == type) registered.event();
                }
            }
        }
    }

    public static enum EventType{
        BUTTON_DOWN, BUTTON_UP, BUTTON_HOLD
    }

}
