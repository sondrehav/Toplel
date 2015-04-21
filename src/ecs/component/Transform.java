package ecs.component;

import ecs.Component;
import ecs.ComponentMessage;
import ecs.Entity;
import org.json.JSONObject;
import utils.fields.FloatField;
import utils.fields.Vector2Field;

public class Transform extends Component{

    Vector2Field position = new Vector2Field("position");
    FloatField rotation = new FloatField("rotation"){};
    FloatField rotation_offset = new FloatField("rotation_offset");

    public Transform(JSONObject object, Entity owner){
        super("transform", owner);
        this.load(object);
    }

    @Override
    public void load(JSONObject object) {
        position.setValue(object);
        rotation.setValue(object);
        rotation_offset.setValue(object);
    }

    @Override
    public boolean recieve(ComponentMessage componentMessage) {
        return false;
    }

}
