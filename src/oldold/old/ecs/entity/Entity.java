package oldold.old.ecs.entity;

import oldold.old.ecs.component.Component;

import java.util.ArrayList;

public class Entity {

    public final int id;
    private static int counter = 0;

    private ArrayList<Component> components = new ArrayList<>();

    public Entity(){
        id = ++counter;
    }

    public void addComponent(Component component){
        components.add(component);
    }

    public void removeComponent(Component component){
        components.remove(component);
    }

}
