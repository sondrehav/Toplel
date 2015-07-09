package com.toplel.ecs.entity;

import com.toplel.ecs.ComponentManager;
import com.toplel.ecs.components.Component;
import com.toplel.ecs.components.Transform;
import com.toplel.util.Console;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameObject {

    private HashMap<String, Component> components = new HashMap<>();
    private Transform transform = null;

    private ArrayList<GameObject> children = new ArrayList<>();
    private GameObject parent;

    public final int id;
    private static int count;

    public GameObject(GameObject parent){
        this.parent = parent;
        id = count++;

    }

    public Transform getTransform() {
        return transform;
    }

    public GameObject getParent() {
        return parent;
    }

    public Component getComponent(String component){
//        for(Map.Entry<String, Component> comp : components.entrySet()){
//            System.out.println(comp.getKey());
//        }
        if(components.containsKey(component)){
            return components.get(component);
        }
        if(component.contentEquals("transform")){
            return transform;
        }
        Console.printErr("No component named '" + component + "'.");
        return null;
    }

    public void addComponent(Component component){
        if(component.key().contentEquals("transform")){
            transform = (Transform) component;
            ComponentManager.addComponent(component);
            return;
        }
        components.put(component.key(), component);
        ComponentManager.addComponent(component);
    }

    public void removeComponent(String component){
        if(component.contentEquals("transform")){
            transform.destroy();
            ComponentManager.removeComponent(transform);
            transform = null;
            return;
        }
        ComponentManager.removeComponent(components.get(component));
        components.remove(component);
    }

    public void removeAllComponents(){
        for (Map.Entry<String, Component> c : components.entrySet()) {
            c.getValue().destroy();
            components.remove(c.getValue());
        }
        components.clear();
        transform.destroy();
        transform = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void moveTo(int x, int y){
        Vector2f moveTo = new Vector2f(x, y);
        Vector2f dist = Vector2f.sub(transform.getPosition(), moveTo, null);
        float rotation = (float) Math.atan2(dist.y, dist.x);
        Vector2f movementVector = new Vector2f((float)Math.cos(rotation), (float)Math.sin(rotation));
        this.transform.setRotation((float)Math.toDegrees(rotation));
        System.out.println(movementVector);
        this.transform.addPosition(movementVector);
    }

}
