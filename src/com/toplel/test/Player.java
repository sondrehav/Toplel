package com.toplel.test;

import com.toplel.context.MyContext;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.events.mouse.MyMouseEventHandler;
import com.toplel.events.mouse.OnMouseEvent;
import com.toplel.main.MyMain;
import com.toplel.state.AnimatedSprite;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Player {

    enum State {
        RUNNING, WALKING, SNEAKING
    }

    State state;

    float stamina = 100f;

    float speed = 3f;
    float rotation = 0f;

    public Vector2f getPosition() {
        return position;
    }

    public void setRotation(float rotation){
        this.rotation = rotation + 90f;
    }

    Matrix4f md_matrix = new Matrix4f();
    Vector2f position = new Vector2f();

    private OnKeyEvent[] keyEvents = new OnKeyEvent[6];

    private AnimatedSprite bodySprite = new AnimatedSprite(new Tileset(MyTexture.addTexture("res/img/opengamedev/skeleton.png"), 32, 32));

//    private Tileset image = new Tileset(MyTexture.addTexture("res/img/player/playerBase.png"), 0, 0, 0, "");
    private MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");
    private MyVertexObject vertexObject = MyVertexObject.createSquare(0f,0f,1f,1f);

    public Player(){
        keyEvents[0]=new OnKeyEvent(Keyboard.KEY_W) {@Override public void onKeyHold() {position.y+=speed;}};
        keyEvents[1]=new OnKeyEvent(Keyboard.KEY_A) {@Override public void onKeyHold() {position.x-=speed;}};
        keyEvents[2]=new OnKeyEvent(Keyboard.KEY_S) {@Override public void onKeyHold() {position.y-=speed;}};
        keyEvents[3]=new OnKeyEvent(Keyboard.KEY_D) {@Override public void onKeyHold() {position.x+=speed;}};
        keyEvents[5]=new OnKeyEvent(Keyboard.KEY_P) {@Override public void onKeyDown() {bodySprite.useSet("DEATH");}};
        keyEvents[4]=new OnKeyEvent(Keyboard.KEY_LSHIFT) {
            @Override public void onKeyDown() {speed+=3f;}
            @Override public void onKeyUp() {speed-=3f;}};
        bodySprite.createSet("IDLE", 100L, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, AnimatedSprite.Type.SAW);
        bodySprite.createSet("WALKING", 100L, new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, AnimatedSprite.Type.SAW);
        bodySprite.createSet("RUNNING", 50L, new int[]{20, 21, 22, 23, 24, 25, 26, 27, 28, 29}, AnimatedSprite.Type.SAW);

        AnimationDoneCallback attack = new AnimationDoneCallback() {
            @Override
            public void onAnimationDone() {
                bodySprite.useSet("IDLE");
                isIdle = true;
            }
        };

        AnimationDoneCallback death = new AnimationDoneCallback() {
            @Override
            public void onAnimationDone() {
                MyMain.stop();
            }
        };

        bodySprite.createSet("ATTACK", 100L, new int[]{30, 31, 32, 33, 34, 35, 36, 37, 38, 39}, AnimatedSprite.Type.ONESHOT, false, attack);
        bodySprite.createSet("DEATH", 100L, new int[]{40, 41, 42, 43, 44, 45, 46, 47, 48, 49}, AnimatedSprite.Type.ONESHOT, false, death);
        bodySprite.useSet("IDLE");
//        headSprite.createSet("IDLE", 1000L, new int[]{3}, AnimatedSprite.Type.SAW);
//        headSprite.useSet("IDLE");
    }

    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }

    Vector2f lastPos = new Vector2f();
    float headRotation = 0f;

    boolean t = false;
    boolean isIdle = false;
    public void render(){

        if(Mouse.isButtonDown(0)){
            if(!t){
                t = true;
                bodySprite.useSet("ATTACK");
                System.out.println("ATTACK");
            }
        } else {
            t = false;
        }

        float velocity = Vector2f.sub(position, lastPos, null).length();
        if(velocity==0f){
            if(!isIdle){
                bodySprite.useSet("IDLE");
                isIdle = true;
            }
        } else if(velocity>=3f&&velocity<6f){
            bodySprite.useSetNonReset("WALKING");
            isIdle = false;
        } else {
            bodySprite.useSetNonReset("RUNNING");
            isIdle = false;
        }
        
        lastPos = new Vector2f(position);

        Matrix4f.setIdentity(md_matrix);
        Matrix4f.translate(position, md_matrix, md_matrix);
        Matrix4f head = new Matrix4f(md_matrix);

//        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 0f, 1f), md_matrix, md_matrix);
        Matrix4f.translate(new Vector2f(-50f, -50f), md_matrix, md_matrix);
        Matrix4f.scale(new Vector3f(100f, 100f, 1f), md_matrix, md_matrix);

        float mx = 2f * (float) Mouse.getX() / MyMain.getWidth() - 1f;
        float my = 2f * (float) Mouse.getY() / MyMain.getHeight() - 1f;
        Vector2f mousePos = MyContext.get("world").fromContext(new Vector2f(mx, my));
        Vector2f.sub(position, mousePos, mousePos);

        float headRotation = (float) Math.atan2(mousePos.y, mousePos.x) - (float) Math.PI / 2f;

        Matrix4f.rotate(headRotation, new Vector3f(0f, 0f, 1f), head, head);
        Matrix4f.translate(new Vector3f(-50f, -50f, -0.01f), head, head);
        Matrix4f.scale(new Vector3f(100f, 100f, 1f), head, head);

        bodySprite.render(md_matrix);
//        headSprite.render(head);
    }

}
