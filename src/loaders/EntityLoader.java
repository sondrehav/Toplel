package loaders;

import entities.Entity;
import entities.Player;
import entities.Renderable;
import entities.Rotatable;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;
import utils.SimpleFileReader;

public class EntityLoader {

    public static Entity load(JSONObject object) throws Exception{
        String type = null;
        try{
            type = object.getString("type");
        } catch (JSONException e) {
            JSONObject file = new JSONObject(SimpleFileReader.read(object.getString("path")));
            return load(file);
        }
        Entity entity = null;
        switch(type) {
            case "Player":
                entity = new Player();
            case "Renderable":
                if (entity == null)
                    entity = new Renderable();
                ((Renderable) entity).setSprite(object.getJSONObject("sprite").getString("spritePath"));
                ((Renderable) entity).setShaderProgram(
                        object.getJSONObject("shader").getString("vertexShader"),
                        object.getJSONObject("shader").getString("fragmentShader")
                );
            case "Rotatable":
                if (entity == null)
                    entity = new Rotatable();
                ((Rotatable) entity).setRotation(getRotation(object));
                ((Rotatable) entity).setSize(getSize(object));
            case "Entity":
                if (entity == null)
                    entity = new Entity();
                entity.setPosition(getPosition(object));
                break;
            default:
                throw new Exception("Unknown type: " + object.getString("type"));
        }
        return entity;
    }

    private static Vector2f getPosition(JSONObject object){
        Vector2f pos = new Vector2f((float)object.getDouble("x_pos"),
                (float)object.getDouble("y_pos"));
        return pos;
    }

    private static Vector2f getSize(JSONObject object){
        Vector2f size = new Vector2f((float)object.getDouble("x_size"),
                (float)object.getDouble("y_size"));
        return size;
    }

    private static float getRotation(JSONObject object){
        return (float) object.getDouble("rot");
    }

}
