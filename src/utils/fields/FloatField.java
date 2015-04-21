package utils.fields;

import main.Main;
import org.json.JSONObject;

public class FloatField extends Field<Float> {

    public FloatField(String jsonIdentifier) {
        super(jsonIdentifier);
    }

    @Override
    public void setValue(JSONObject object){
        if(object.has(this.name())){
            this.v = (float)object.getDouble(this.name());
        }
    }

}
