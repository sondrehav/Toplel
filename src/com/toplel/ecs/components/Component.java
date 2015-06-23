package com.toplel.ecs.components;

import com.toplel.ecs.entity.GameObject;
import org.json.JSONObject;

public abstract class Component {

    public final GameObject parent;

    public Component(GameObject parent){
        this.parent = parent;
    }

    public void setValues(JSONObject object){} // For initializing variables
    public void start(){} // For initializing variables in gamestate
    public void update(){} // Update in game loop
    public void updateLate(){} // For updating after the game loop
    public void fixedUpdate(){} // For updating at a fixed rate
    public void destroy(){} // For destroying the component

    public abstract String key();

}
