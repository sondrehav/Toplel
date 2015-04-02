package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

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
            position.y-=.01;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.y+=.01;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x+=.01;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x-=.01f;
        }
    }

}
