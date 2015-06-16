package com.toplel.components;

import com.toplel.context.MyContext;
import com.toplel.events.inloop.OnRender;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.events.mouse.OnMouseEvent;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MySimpleFileReader;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.util.ArrayList;

public class MySprite extends MyPosition {

    private MyTexture texture;
    private MyShaderProgram shaderProgram;
    private MyVertexObject vertexObject;

    public static MySprite addSprite(String jsonPath){

        JSONObject object;
        try{
            object = new JSONObject(MySimpleFileReader.read(jsonPath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String imagePath = object.has("imagePath") ? object.getString("imagePath") : MyTexture.DEFAULT_IMAGE;
        float position_x = object.has("positionX") ? (float)object.getDouble("positionX") : 0f;
        float position_y = object.has("positionY") ? (float)object.getDouble("positionY") : 0f;
        float rotation = object.has("rotation") ? (float)object.getDouble("rotation") : 0f;
        float size_x = object.has("sizeX") ? (float)object.getDouble("sizeX") : 1f;
        float size_y = object.has("sizeY") ? (float)object.getDouble("sizeY") : 1f;
        String context = object.has("context") ? object.getString("context") : "world";
        String vertexShader = object.has("vertexShader") ? object.getString("vertexShader") : MyShaderProgram.DEFAULT_VERTEX_SHADER;
        String fragmentShader = object.has("fragmentShader") ? object.getString("fragmentShader") : MyShaderProgram.DEFAULT_FRAGMENT_SHADER;
        int layer = object.has("layer") ? object.getInt("layer") : 0;

        Vector2f _position = new Vector2f(position_x, position_y);
        Vector2f _size = new Vector2f(size_x, size_y);

        MySprite sprite = new MySprite(_position, rotation, _size);

        sprite.setContext(MyContext.get(context));
        sprite.texture = MyTexture.addTexture(imagePath);
        sprite.shaderProgram = MyShaderProgram.addShaderProgram(vertexShader, fragmentShader);
        sprite.vertexObject = MyVertexObject.createSquare(0f, 0f, 1f, 1f);
        sprite.getRenderer().setLayer(layer);
/*
        JSONObject uniforms = object.has("uniforms") ? object.getJSONObject("uniforms") : null;
        for(String uniformKeys : uniforms.keySet()){
            JSONObject uniform = uniforms.getJSONObject(uniformKeys);
        }
*/
        return sprite;

    }

    private MySprite(Vector2f position, float rotation, Vector2f size){
        super(position, rotation, size);
    }

    OnRender renderer = new OnRender(0){
        @Override
        public void render() {
            texture.bind();
            shaderProgram.bind();
            shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
            shaderProgram.setUniformMat4("md_matrix", getModelMatrix());
            shaderProgram.setUniform1f("addColor", addColor);
            vertexObject.bind();
            vertexObject.draw();
            vertexObject.unbind();
            shaderProgram.unbind();
            texture.unbind();
        }
    };

    public void setAddColor(float addColor) {
        this.addColor = addColor;
    }

    float addColor = 0f;

    public float getAddColor() {
        return addColor;
    }

    public OnRender getRenderer() {
        return renderer;
    }

    public void setOnMouseEvent(OnMouseEvent onMouseEvent){
        if(mouseEvent!=null) mouseEvent.disable();
        mouseEvent = onMouseEvent;
    }

    public void addOnKeyEvent(OnKeyEvent onKeyEvent){
        keyEvents.add(onKeyEvent);
    }
    public void removeOnKeyEvent(OnKeyEvent onKeyEvent){
        keyEvents.remove(onKeyEvent);
    }
    public void setKeyListeners(boolean active){
        for(OnKeyEvent e : keyEvents){
            if(active) e.enable();
        }
    }

    ArrayList<OnKeyEvent> keyEvents = new ArrayList<>();;
    OnMouseEvent mouseEvent = null;/* {

        MySprite dragging = null;

        @Override
        public boolean isInside(Vector2f mouse) {
            return isCoordsInside(mouse, context);
        }

        @Override
        public void onMouseDown(Vector2f mouse) {
            dragOffset = Vector2f.sub(context.fromContext(mouse), getPosition(), null);
            addColor = .5f;
            MySprite newSprite = duplicate();
            dragging = newSprite;
            newSprite.setLayer(renderer.getLayer() + 3);
            newSprite.setContext(MyContext.get("world"));

        }

        @Override
        public void onMouseUp(Vector2f mouse) {
            addColor = .2f;
            dragging = null;
        }

        @Override
        public void onMouseOut(Vector2f mouse) {
            addColor = 0f;
        }

        @Override
        public void onMouseIn(Vector2f mouse) {
            addColor = .2f;
        }

        Vector2f dragOffset;
        @Override
        public void onMouseDrag(Vector2f mouse) {
            mouse = dragging.context.fromContext(mouse);
            dragging.setPosition(Vector2f.sub(mouse, dragOffset, mouse));
        }
    };

    OnMouseEvent rightMouseEvent = new OnMouseEvent(1) {
        @Override
        public boolean isInside(Vector2f mouse) {
            return isCoordsInside(mouse, context);
        }

        @Override
        public void onMouseDown(Vector2f mouse) {
            rightMouseEvent.disable();
            mouseEvent.disable();
            renderer.disable();
        }
    };*/

    public MySprite duplicate(){
        MySprite sprite = new MySprite(new Vector2f(getPosition()),
                getRotation(),
                new Vector2f(getSize()));
        sprite.shaderProgram = shaderProgram;
        sprite.texture = texture;
        sprite.vertexObject = vertexObject;
        sprite.setContext(this.getContext());
        sprite.setPosition(Vector2f.add(sprite.getPosition(), new Vector2f(100f, 0f), null));
        return sprite;
    }

    public void setLayer(int layer){
        this.renderer.setLayer(layer);
    }

}
