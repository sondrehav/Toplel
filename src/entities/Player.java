package entities;

import org.lwjgl.input.Keyboard;
import utils.Vector2f;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Player extends Renderable {

    public Player(){
        super();

        // TODO: Must be replaced by scripting
        Camera.setEntity(this);
    }

    @Override
    public void event(){
        super.event();
        if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){
            Camera.height*=1.01;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){
            Camera.height*=.99;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_O)){
            rotation++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            rotation--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.add(front);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.sub(front);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.sub(right);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.add(right);
        }
    }

}
