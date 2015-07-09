package com.toplel.ecs;

import com.toplel.ecs.components.Component;
import com.toplel.ecs.components.Script;
import com.toplel.ecs.components.Transform;
import com.toplel.ecs.components.renderable.AnimatedSprite;
import com.toplel.ecs.components.renderable.StaticSprite;
import com.toplel.ecs.entity.GameObject;
import com.toplel.util.Console;
import com.toplel.util.objects.MySimpleFileReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public abstract class ComponentManager {

    private static ArrayList<Component> components = new ArrayList<>();

    public static GameObject loadGameObject(String path){
        String file = null;
        try{
            file = MySimpleFileReader.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject(file);
        GameObject gameObject = new GameObject(null);
        for(String s : object.keySet()){
            Component component = null;
            switch (s){
                case "transform":
                    component = new Transform(gameObject);
                    break;
                case "script":
                    component = new Script(gameObject);
                    break;
                case "animatedSprite":
                    component = new AnimatedSprite(gameObject);
                    break;
                case "staticSprite":
                    component = new StaticSprite(gameObject);
                    break;
                default:
                    Console.printErr("No component named '" + s + "'.");
                    continue;
            }
            component.setValues(object.getJSONObject(s));
            gameObject.addComponent(component);
            addComponent(component);
        }
        return gameObject;
    }

    public static void start(){
        for (Component component : components){
            component.start();
        }
    }

    static int frame = 0;
    public static int FIXED_UPDATE = 60;
    public static void event(){
        frame++;
        for (Component component : components){
            component.update();
        }
        for (Component component : components){
            component.updateLate();
        }
        if(frame++>=FIXED_UPDATE){ // TODO: Fixed update on different frames
            for (Component component : components){
                component.fixedUpdate();
            }
            frame = 0;
        }
    }

    public static void destroy(){
        for (Component component : components){
            component.destroy();
        }
    }

    public static void removeComponent(Component component){
        components.remove(component);
    }

    public static void addComponent(Component component){
        components.add(component);
    }

}
