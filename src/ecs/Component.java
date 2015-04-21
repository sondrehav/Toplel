package ecs;

import org.json.JSONObject;

public abstract class Component {

    public abstract void load(JSONObject object);

    protected Entity owner;
    public final String jsonIdentifier;

    protected Component(String jsonIdentifier, Entity owner){
        this.jsonIdentifier = jsonIdentifier;
        this.owner = owner;
    }

    protected void event(){};

    public abstract boolean recieve(ComponentMessage componentMessage);

}
