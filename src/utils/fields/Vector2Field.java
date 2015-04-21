package utils.fields;

import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;

public class Vector2Field extends Field<Vector2f> {

    public Vector2Field(String name){
        super(name);
    }

    @Override
    public void setValue(JSONObject object){
        float x = 0f, y = 0f;
        System.out.println();
        System.out.println(object);
        System.out.println(this.name());
        System.out.println();
        if(object.has(this.name())){
            if(object.getJSONObject(this.name()).has("x")){
                x = (float)object.getJSONObject(this.name()).getDouble("x");
            }
            if(object.getJSONObject(this.name()).has("y")){
                y = (float)object.getJSONObject(this.name()).getDouble("y");
            }
        }
        this.v = new Vector2f(x, y);
    }
}
