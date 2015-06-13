package oldold.old.ecs.component;

import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;
import oldold.loaders.MySimpleFileReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

public class Component {

    static HashMap<String, Component> components = new HashMap<>();

    String name;
    ByteBuffer variables;
    HashMap<String, Var> locations = new HashMap<>();

    public static Component load(String path) throws IOException{
        if(!components.containsKey(path))
            components.put(path, new Component(new JSONObject(MySimpleFileReader.read(path))));
        return components.get(path);
    }

    private Component(JSONObject object){
        name = object.getString("title");
        JSONObject vars = object.getJSONObject("oldold/old/var");
        Iterator<?> it = vars.keys();
        while(it.hasNext()){
            String type = (String)it.next();
            String value = vars.getString(type);
            switch (type){
                case "float":{
                    locations.put(value, new Var<>(value, 0f));
                    break;
                }
                case "vec2":{
                    locations.put(value, new Var<>(value, new Vector2f()));
                    break;
                }
                case "int":{
                    locations.put(value, new Var<>(value, 0));
                    break;
                }
                case "str":{
                    locations.put(value, new Var<>(value, ""));
                    break;
                }
            }
        }
    }

    public <T> void setVar(String name, T input) throws IllegalArgumentException {
        if(!locations.containsKey(name)){
            throw new IllegalArgumentException("No variable named '" + name + "'.");
        }
        if(input.getClass()!=locations.get(name).getClass()){
            throw new IllegalArgumentException("Input type invalid. Current is " + locations.get(name).getClass() + ". Provided is " + input.getClass()+".");
        }
        locations.get(name).obj = input;
    }

    @SuppressWarnings("unchecked") // ClassCastException is catched.
    public <T> T getVar(String name) throws IllegalArgumentException {
        if(!locations.containsKey(name)){
            throw new IllegalArgumentException("No variable named '" + name + "'.");
        }
        try{
            T obj = (T)locations.get(name).obj;
            return obj;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }



    private static class Var<T>{
        String name;
        T obj;
        public Var(String name, T obj){
            this.name = name;
            this.obj = obj;
        }
    }

}
