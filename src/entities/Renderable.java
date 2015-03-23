package entities;

import utils.ShaderProgram;
import utils.Sprite;
import utils.Vector2f;

import java.io.IOException;

public class Renderable extends Rotatable {

    Sprite sprite = null;
    ShaderProgram shaderProgram = null;
    public float depth;

    public Renderable(){
        super();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        try{
            this.sprite = Sprite.addSprite(sprite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(String vs, String fs) {
        try{
            this.shaderProgram = ShaderProgram.addShader(vs, fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public void render(){
        sprite.renderAt(position, size, rotation, depth, shaderProgram);
    }

}
