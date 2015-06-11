package game;

import main.MyMainClass;
import math.MyVec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MyPlayer {

    public enum PlayerState {
        WALKING, IDLE
    }

    public float rotation;
    public MyVec3 position = new MyVec3(0f, 0f, 1f);
    float speed = 0.1f;
    Head head = new Head();
    final String image = "res/img/player/player.png";
    private PlayerState playerState = PlayerState.IDLE;

    int animTick = 60;

    public MyPlayer(){}

    public void event(){
        boolean walking = false;
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.position.vector[1]+=speed;
            walking = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.position.vector[1]-=speed;
            walking = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            this.position.vector[0]-=speed;
            walking = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.position.vector[0]+=speed;
            walking = true;
        }
        if(walking){
            playerState = PlayerState.WALKING;
        } else {
            playerState = PlayerState.IDLE;
        }
        float mx = (float)Mouse.getX()/(float) MyMainClass.getWidth() - 0.5f;
        float my = (float)Mouse.getY()/(float) MyMainClass.getHeight() - 0.5f;
        head.rotation = (float) Math.toDegrees(Math.atan2(my, mx));
    }

    private int frame = 0;
    public void render(){

        int img_x = 0;
        int img_y = 0;
        switch (playerState){
            case WALKING:
                int walkingAnim = frame %  animTick / 2;
                img_x = 32 + walkingAnim * 32;
                img_y = 0;
                break;
            case IDLE:
                img_x = 0;
                img_y = 0;
                break;
        }
        if(frame++ >= animTick){
            frame=0;
        }
//
//        draw()
//
    }

    private static class Head {
        float rotation;
        int x_offset, y_offset;
    }

}
