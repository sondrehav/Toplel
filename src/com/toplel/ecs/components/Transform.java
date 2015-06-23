package com.toplel.ecs.components;

import com.toplel.context.MyContext;
import com.toplel.ecs.entity.GameObject;
import org.json.JSONObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Transform extends Component {

    @Override
    public void setValues(JSONObject object) {

        position.x = object.has("position.x") ? (float) object.getDouble("position.x") : 0f;
        position.y = object.has("position.y") ? (float) object.getDouble("position.y") : 0f;
        depth = object.has("depth") ? (float) object.getDouble("depth") : 0f;
        rotation = object.has("rotation") ? (float) object.getDouble("rotation") : 0f;
        size.x = object.has("size.x") ? (float) object.getDouble("size.x") : 1f;
        size.y = object.has("size.y") ? (float) object.getDouble("size.y") : 1f;
        context = object.has("context") ? MyContext.get(object.getString("context")) : null;
        recalcMat();

    }

    public static final Vector3f UP = new Vector3f(0f,0f,1f);

    public MyContext context = MyContext.get("world");
    private final Vector2f position = new Vector2f();
    private float rotation = 0f;
    private final Vector2f size = new Vector2f(1f, 1f);
    private float depth = 0f;

    public MyContext getContext() {
        return context;
    }

    public Matrix4f getMd_matrix() {
        return md_matrix;
    }

    public Matrix4f getInv_md_matrix() {
        return inv_md_matrix;
    }

    private final Matrix4f md_matrix = new Matrix4f();
    private final Matrix4f inv_md_matrix = new Matrix4f();

    public Transform(GameObject parent){
        super(parent);
    }

    @Override
    public String key() {
        return "transform";
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public void setPosition(Vector2f position) {
        this.position.x = position.x;
        this.position.y = position.y;
        recalcMat();
    }

    public void addPosition(Vector2f position) {
        this.position.x += position.x;
        this.position.y += position.y;
        recalcMat();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        recalcMat();
    }

    public void addRotation(float rotation) {
        this.rotation += rotation;
        recalcMat();
    }

    public Vector2f getSize() {
        return new Vector2f(size);
    }

    public void setSize(Vector2f size) {
        this.size.x = size.y;
        this.size.y = size.y;
        recalcMat();
    }

    private void recalcMat(){
        Matrix4f.setIdentity(md_matrix);
        Matrix4f.translate(new Vector3f(this.position.x, this.position.y, depth), md_matrix, md_matrix);
        Matrix4f.rotate(this.rotation, UP, md_matrix, md_matrix);
        Matrix4f.scale(new Vector3f(size.x, size.y, 1f), md_matrix, md_matrix);
        Matrix4f.invert(md_matrix, inv_md_matrix);
    }

}
