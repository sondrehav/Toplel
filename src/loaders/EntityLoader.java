package loaders;

import entities.Entity;
import entities.Player;
import entities.Renderable;
import entities.Rotatable;
import org.json.JSONException;
import org.json.JSONObject;
import utils.SimpleFileReader;
import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class EntityLoader {

    public static Entity load(JSONObject object) throws Exception{
        String type = null;
        try{
            type = object.getString("type");
        } catch (JSONException e) {
            JSONObject file = new JSONObject(SimpleFileReader.read(object.getString("path")));
            return load(file);
        }
        switch(type){
            case "Entity":
                return new Entity(getPosition(object));
            case "Rotatable":
                return new Rotatable(getPosition(object),getRotation(object));
            case "Renderable":
                return new Renderable(
                        getPosition(object),
                        object.getJSONObject("sprite").getString("spritePath"),
                        object.getJSONObject("shader").getString("vertexShader"),
                        object.getJSONObject("shader").getString("fragmentShader"),
                        getRotation(object)
                );
            case "Player":
                return new Player(
                        getPosition(object),
                        object.getJSONObject("sprite").getString("spritePath"),
                        object.getJSONObject("shader").getString("vertexShader"),
                        object.getJSONObject("shader").getString("fragmentShader"),
                        getRotation(object)
                );
            default:
                throw new Exception("Unknown type: " + object.getString("type"));
        }
    }

    private static Vector2f getPosition(JSONObject object){
        Vector2f pos = new Vector2f((float)object.getDouble("xpos"),
                (float)object.getDouble("ypos"));
        return pos;
    }

    private static float getRotation(JSONObject object){
        return (float) object.getDouble("rot");
    }

}
