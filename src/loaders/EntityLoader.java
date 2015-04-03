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
        Entity entity = null;
        if(object.has("path")){
            JSONObject file = new JSONObject(SimpleFileReader.read(object.getString("path")));
            entity = load(file);
        }
        String type = null;
        if(entity != null){
            if(entity instanceof Player)
                type = "Player";
            else if(entity instanceof Renderable)
                type = "Renderable";
            else if(entity instanceof Rotatable)
                type = "Rotatable";
            else if(entity instanceof Entity)
                type = "Entity";
        }
        if(object.has("type"))
            type = object.getString("type");
        switch(type) {
            case "Player":
                if (entity == null)
                    entity = new Player();
            case "Renderable":
                if (entity == null)
                    entity = new Renderable();
                if(object.has("sprite"))
                    if(object.getJSONObject("sprite").has("spritePath"))
                        ((Renderable) entity).setSprite(object.getJSONObject("sprite").getString("spritePath"));
                if(object.has("shader")) {
                    String vs = "res/shader/vertTest.vs";
                    String fs = "res/shader/fragTest.fs";
                    if (object.getJSONObject("shader").has("vertexShader"))
                        vs = object.getJSONObject("shader").getString("vertexShader");
                    if (object.getJSONObject("shader").has("fragmentShader"))
                        fs = object.getJSONObject("shader").getString("fragmentShader");
                    ((Renderable) entity).setShaderProgram(vs, fs);
                }
            case "Rotatable":
                if (entity == null)
                    entity = new Rotatable();
                if(object.has("rot"))
                    ((Rotatable) entity).rotation = (float)object.getDouble("rot");
                if(object.has("x_size"))
                    ((Rotatable) entity).size.x = (float)object.getDouble("x_size");
                if(object.has("y_size"))
                    ((Rotatable) entity).size.y = (float)object.getDouble("y_size");
            case "Entity":
                if (entity == null)
                    entity = new Entity();
                if(object.has("x_pos"))
                    entity.position.x = (float) object.getDouble("x_pos");
                if(object.has("y_pos"))
                    entity.position.y = (float) object.getDouble("y_pos");
                break;
            default:
                throw new Exception("Unknown type: " + object.getString("type"));
        }
        return entity;
    }

}
