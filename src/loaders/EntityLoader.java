package loaders;

import org.json.JSONObject;
import utils.SimpleFileReader;

public class EntityLoader {

    public static String load(String path, String e) throws Exception{
        System.out.println("EntityLoader.load");
        String file = SimpleFileReader.read(path);
        JSONObject object = new JSONObject(file);
        for(String key : object.keySet()){
//            switch(key){
//                case "transform":
//
//            }
        }
        return null;
    }

//    public static Transform loadTransform(JSONObject object){
//        Transform t = new Transform();
//        if(object.has("position")){
//            if(object.getJSONObject("position").has("x"))
//                t.position.x = (float) object.getJSONObject("position").getDouble("x");
//            if(object.getJSONObject("position").has("y"))
//                t.position.x = (float) object.getJSONObject("position").getDouble("y");}
//        if(object.has("size")){
//            if(object.getJSONObject("size").has("x"))
//                t.size.x = (float) object.getJSONObject("size").getDouble("x");
//            if(object.getJSONObject("size").has("y"))
//                t.size.y = (float) object.getJSONObject("size").getDouble("y");}
//        if(object.has("rotation"))
//            t.rotation = (float) object.getDouble("rotation");
//        return t;
//    }
//
//    public Component loadSprite(JSONObject object) throws IOException {
//        String sprite = object.getString("path");
//        String vs = "res/shader/default.vs";
//        String fs = "res/shader/default.fs";
//        if(object.has("shader")){
//            if(object.getJSONObject("shader").has("vertexShader"))
//                vs = object.getJSONObject("shader").getString("vertexShader");
//            if(object.getJSONObject("shader").has("fragmentShader"))
//                fs = object.getJSONObject("shader").getString("fragmentShader");
//        }
//        return new Sprite(this.owner, this.path, vs, fs);
//    }
//
//    public static void main(String[] args) throws Exception{
//        Entity e = load("res/scene/entity/ecs_ent1.ent", new PriorityQueue<Component>());
//    }

}
