package entities;

import org.lwjgl.input.Keyboard;
import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Player extends Renderable {

    public Player(Vector2f pos, String imageFilePath, String vertexShader, String fragnemtShader, float rot){
        super(pos, imageFilePath, vertexShader, fragnemtShader, rot);

        // TODO: Must be replaced by scripting
        Camera.setEntity(this);
    }

    @Override
    public void event(){
        super.event();
        if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){
            Camera.height++;
            System.out.println("Camera.height = " + Camera.height);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){
            Camera.height--;
            System.out.println("Camera.height = " + Camera.height);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.y+=1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.y-=1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=1f;
        }
    }

}
