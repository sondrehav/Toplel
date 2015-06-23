package com.toplel.ecs.components.renderable;

import com.toplel.ecs.components.Component;
import com.toplel.ecs.entity.GameObject;
import com.toplel.events.inloop.DrawInterface;
import com.toplel.events.inloop.OnRender;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.json.JSONObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;

public class AnimatedSprite extends Component implements DrawInterface{

    private MyTexture texture = null;
    private static final MyVertexObject vertexObject = MyVertexObject.SQUARE;
    private static final MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/regionShader.vs", "res/shader/regionShader.fs");

    private Region currentRegion = new Region(new Vector2f(0f, 0f), new Vector2f(1f, 1f));

    private static class Region{
        Vector2f regionFrom;
        Vector2f regionTo;
        public Region(Vector2f regionFrom, Vector2f regionTo) {
            this.regionFrom = regionFrom;
            this.regionTo = regionTo;
        }

        @Override
        public String toString() {
            return "Region{" +
                    "regionFrom=" + regionFrom +
                    ", regionTo=" + regionTo +
                    '}';
        }
    }

    HashMap<String, Region> textureRegions = new HashMap<>();

    public AnimatedSprite(GameObject parent){
        super(parent);
    }

    @Override
    public void setValues(JSONObject object) {
        texture = object.has("imagePath") ? MyTexture.addTexture(object.getString("imagePath")) : MyTexture.addTexture("res/img/defaultImage.png");
        JSONObject animation = object.has("animation") ? object.getJSONObject("animation") : null;
        JSONObject states = animation.has("states") ? animation.getJSONObject("states") : null;
        for(String obj : states.keySet()){
            float fromu = (float) states.getJSONObject(obj).getDouble("from.u") / texture.WIDTH;
            float fromv = (float) states.getJSONObject(obj).getDouble("from.v") / texture.HEIGHT;
            float tou = (float) states.getJSONObject(obj).getDouble("to.u") / texture.WIDTH;
            float tov = (float) states.getJSONObject(obj).getDouble("to.v") / texture.HEIGHT;
            textureRegions.put(obj, new Region(
                    new Vector2f(fromu, fromv),
                    new Vector2f(tou, tov)
            ));
        }
    }

    public void setState(String state){
        if(!textureRegions.containsKey(state)){
            System.err.println("No state named '" + state + "'.");
            return;
        }
        currentRegion = textureRegions.get(state);
    }

    @Override
    public void render() {
        if(parent.getTransform()==null) return;
        Matrix4f md_matrix = parent.getTransform().getMd_matrix();
        Matrix4f prvw_matrix = parent.getTransform().getContext().getViewProjection();
        texture.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", prvw_matrix);
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        shaderProgram.setUniform2f("uv_from", currentRegion.regionFrom.x, currentRegion.regionFrom.y);
        shaderProgram.setUniform2f("uv_to", currentRegion.regionTo.x, currentRegion.regionTo.y);
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        texture.unbind();
    }

    @Override
    public void start(){
        OnRender.addListener(this);
    }

    @Override
    public void destroy(){
        OnRender.removeListener(this);
    }

    @Override
    public String key() {
        return "animatedSprite";
    }

}
