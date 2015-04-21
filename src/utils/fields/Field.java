package utils.fields;

import main.Main;
import org.json.JSONObject;
import utils.GetJSONVal;

public abstract class Field<Value> implements GetJSONVal{

    public Value v;
    private String name;

    public Field(String jsonIdentifier){
        name = jsonIdentifier;
    }

    public String name(){return name;}

    @Override
    public String toString(){
        return "["+name+"; "+v.toString()+"]";
    }

    @SuppressWarnings("unchecked") // Catches ClassCastException. TODO: Find better solution.
    public void setValue(JSONObject object){
        if(object.has(this.name())){
            Object o = object.get(this.name());
            try{
                v = (Value) o;
            } catch (ClassCastException e) {
                e.printStackTrace();
                Main.stop();
            }
        }
    }

}
