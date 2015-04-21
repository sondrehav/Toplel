package ecs.component;

import ecs.Component;
import ecs.ComponentMessage;
import ecs.Entity;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;
import utils.fields.FloatField;
import utils.fields.Vector2Field;

public class Physics extends Component {

    public final float gravity = -9.81f;

    FloatField mass = new FloatField("mass");
    FloatField friction = new FloatField("friction");
    Vector2Field velocity = new Vector2Field("velocity");
    private Vector2f acceleration = new Vector2f();
    Vector2Field force = new Vector2Field("force");
    FloatField max_velocity = new FloatField("max_velocity");
    FloatField max_force = new FloatField("max_force");
    private float ang_acceleration;
    FloatField torque = new FloatField("torque");
    FloatField ang_velocity = new FloatField("ang_velocity");
    FloatField max_ang_velocity = new FloatField("max_ang_velocity");
    FloatField max_torque = new FloatField("max_torque");

    public Physics(JSONObject object, Entity owner){
        super("physics", owner);
        this.load(object);
    }

    @Override
    public void load(JSONObject object) {
        mass.setValue(object);
        friction.setValue(object);
        velocity.setValue(object);
        force.setValue(object);
        max_velocity.setValue(object);
        max_force.setValue(object);
        torque.setValue(object);
        ang_velocity.setValue(object);
        max_ang_velocity.setValue(object);
        max_torque.setValue(object);
    }

    @Override
    public boolean recieve(ComponentMessage componentMessage) {
        return false;
    }

    @Override
    public void event() {

        // Step 1: Calculate sum of force

        Vector2f sumForce = new Vector2f(0f,0f);

        Vector2f.add(sumForce, force.v, sumForce);

        Vector2f friction_force = new Vector2f(velocity.v.x, velocity.v.y);
        friction_force.scale(friction.v / gravity);

        Vector2f.add(sumForce, friction_force, sumForce);

        // Step 2: Compute position

        acceleration.x = sumForce.x / mass.v;
        acceleration.y = sumForce.y / mass.v;
        Vector2f.add(velocity.v, acceleration, velocity.v);

        Vector2f.add(owner.transform.position.v, velocity.v, owner.transform.position.v);

//        System.out.println("position = " + owner.transform.position.v);

    }
}
