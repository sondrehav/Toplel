package entities;

import utils.ShaderProgram;
import utils.Sprite;
import utils.Vector2f;

import java.io.IOException;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Renderable extends Rotatable {

    Sprite sprite = null;
    ShaderProgram shaderProgram = null;
    public float depth;
    public Vector2f size = new Vector2f(1f, 1f);

    public Renderable(Vector2f pos, String imageFilePath, String vertexShader, String fragnemtShader, float rot){
        super(pos, rot);
        try{
            sprite = Sprite.addSprite(imageFilePath);
            shaderProgram = ShaderProgram.addShader(vertexShader, fragnemtShader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(){
        sprite.renderAt(position, size, rotation, depth, shaderProgram);
    }

}
