package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Player extends Renderable {

    public Player(){
        super();

        // TODO: Must be replaced by scripting
//        Camera.setEntity(this);
    }

    @Override
    public void event(){
        super.event();
        float s = 0.01f;
        if(Keyboard.isKeyDown(Keyboard.KEY_ADD)){
            Camera.zoom*=1.01;
//            this.size.x*=1.01;
//            this.size.y*=1.01;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){
            Camera.zoom*=.99;
//            this.size.x*=0.99;
//            this.size.y*=0.99;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_O)){
            rotation++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            rotation--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            Vector2f.add(position, new Vector2f(front.x * s, front.y * s), position);
//            position.y+=s;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            Vector2f.sub(position, new Vector2f(front.x * s, front.y * s), position);
//            position.y-=s;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            Vector2f.sub(position, new Vector2f(right.x * s, right.y * s), position);
//            position.x-=s;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            Vector2f.add(position, new Vector2f(right.x * s, right.y * s), position);
//            position.x+=s;
        }
    }

}
