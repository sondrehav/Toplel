package ecs;

import ecs.component.Physics;
import ecs.component.Sprite;
import ecs.component.Transform;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Entity {

    public Transform transform;
    protected HashMap<String, Component> components = new HashMap<>();

    public Entity(JSONObject object){
        Iterator<?> keys = object.keys();
        while(keys.hasNext()){
            String key = (String)keys.next();
            if(object.get(key) instanceof JSONObject){
                switch(key){
                    case "transform":
                        this.transform = new Transform((JSONObject)object.get(key), this);
                        break;
                    case "physics":
                        this.addComponent(new Physics((JSONObject)object.get(key), this));
                        break;
                    case "sprite":
                        this.addComponent(new Sprite((JSONObject)object.get(key), this));
                }
            }
        }

    }

    public void addComponent(Component component){
        components.put(component.jsonIdentifier, component);
    }

    public boolean removeComponent(Component component){
        if(components.containsKey(component.jsonIdentifier)){
            components.remove(component.jsonIdentifier);
            return true;
        }
        return false;
    }

    public boolean removeComponent(String jsonIdentifier){
        if(components.containsKey(jsonIdentifier)){
            components.remove(jsonIdentifier);
            return true;
        }
        return false;
    }

    public void event(){
        for(Map.Entry<String, Component> c : components.entrySet()){
            c.getValue().event();
        }
    }

}
