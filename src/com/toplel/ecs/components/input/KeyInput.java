package com.toplel.ecs.components.input;

import com.toplel.ecs.components.Component;
import com.toplel.ecs.entity.GameObject;
import com.toplel.events.keyboard.MyKeyListener;
import com.toplel.events.keyboard.MyKeyboardEventHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class KeyInput extends Component {

    HashMap<Integer, MyKeyListener> h_keys = new HashMap<>();

    public KeyInput(GameObject parent) {
        super(parent);
    }

    @Override
    public void setValues(JSONObject object) {

        JSONArray jsonkeys = object.has("keys") ? object.getJSONArray("keys") : null;
        for (int i = 0; i < jsonkeys.length(); i++) {
            h_keys.put(Keyboard.getKeyIndex("KEY_"+jsonkeys.getString(i)), null);
        }

    }

    public void registerListener(MyKeyListener keyListener, int key){
        if(h_keys.containsKey(key)) h_keys.put(key, keyListener);
        else System.err.println("No available listener on key '" + key + "'.");
    }

    public void unregisterListener(MyKeyListener keyListener){
        MyKeyboardEventHandler.removeListener(keyListener);
    }


    @Override
    public void start() {
        for (Map.Entry<Integer, MyKeyListener> e : h_keys.entrySet()) {
            MyKeyboardEventHandler.addListener(e.getValue(), e.getKey());
        }
    }

    @Override
    public void destroy() {
        for (Map.Entry<Integer, MyKeyListener> e : h_keys.entrySet()) {
            MyKeyboardEventHandler.removeListener(e.getValue());
        }
    }

    @Override
    public String key() {
        return "keyInput";
    }
}
