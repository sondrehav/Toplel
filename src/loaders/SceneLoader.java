package loaders;

import entities.Entity;
import entities.Scene;
import main.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.SimpleFileReader;

import java.util.HashMap;


public abstract class SceneLoader {

    static HashMap<String, Scene> loaded = new HashMap<String, Scene>();

    public static Scene load(String path) throws Exception {
        if(loaded.containsKey(path)){
            return loaded.get(path);
        }
        JSONObject file = new JSONObject(SimpleFileReader.read(path));
        Scene scene = new Scene(path);

        scene.setName(file.getString("title"));
        JSONArray entities = file.getJSONArray("entities");
        for(int i=0;i<entities.length();i++){
            JSONObject obj = entities.getJSONObject(i);
            Entity e = EntityLoader.load(obj);
            System.out.println("e = " + e);
            scene.addEntity(e);
        }
        Main.setTitle(scene.getName());
        return scene;
    }

}
