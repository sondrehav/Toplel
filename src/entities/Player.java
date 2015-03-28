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
            Vector2f.add(front, position, position);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            Vector2f.sub(front, position, position);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            Vector2f.sub(right, position, position);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            Vector2f.add(right, position, position);
        }
    }

}
