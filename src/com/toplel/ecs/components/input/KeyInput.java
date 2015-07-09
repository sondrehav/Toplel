package com.toplel.ecs.components.input;

import com.toplel.ecs.components.Component;
import com.toplel.ecs.entity.GameObject;
import com.toplel.events.keyboard.KeyListener;
import com.toplel.events.keyboard.KeyboardEventHandler;
import com.toplel.util.Console;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class KeyInput extends Component {

    HashMap<Integer, KeyListener> h_keys = new HashMap<>();

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

    public void registerListener(KeyListener keyListener, int key){
        if(h_keys.containsKey(key)) h_keys.put(key, keyListener);
        else Console.printErr("No available listener on key '" + key + "'.");
    }

    public void unregisterListener(KeyListener keyListener){
//        KeyboardEventHandler.removeListener(keyListener);
    }


    @Override
    public void start() {
//        for (Map.Entry<Integer, KeyListener> e : h_keys.entrySet()) {
//            KeyboardEventHandler.addListener(e.getValue(), e.getKey());
//        }
    }

    @Override
    public void destroy() {
//        for (Map.Entry<Integer, KeyListener> e : h_keys.entrySet()) {
//            KeyboardEventHandler.removeListener(e.getValue());
//        }
    }

    @Override
    public String key() {
        return "keyInput";
    }
}
