package entities;

import loaders.SceneLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class Scene {

    static HashMap<String, Scene> scenes = new HashMap<String, Scene>();

    private ArrayList<Entity> entities = new ArrayList<Entity>();

    String name = null;
    String path = null;

    public void addEntity(Entity e){
        entities.add(e);
    }

    public void setName(String name){
        this.name = name;
    }

    public static Scene addScene(String path) throws Exception{
        if(scenes.containsKey(path)){
            return scenes.get(path);
        }
        Scene s = SceneLoader.load(path);
        scenes.put(path, s);
        return s;
    }

    public Scene(String path){
        this.path = path;
    }

    public void render(){
        for(Entity e : entities){
            if(e instanceof Renderable){
                Renderable r = (Renderable) e;
                r.render();
            }
        }
    }

    public void event(){
        for(Entity e : entities){
            e.event();
        }
    }

    @Override
    public boolean equals(Object o){
        return this.path.equals(o);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
