package com.toplel.state;

import com.toplel.context.MyHUD;
import com.toplel.context.MyWorld;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.main.MyMain;
import com.toplel.test.Player;
import com.toplel.test.WorldMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class MyEditor extends MyMasterState {

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), new Matrix4f());
    OnKeyEvent exit = new OnKeyEvent(Keyboard.KEY_ESCAPE) {
        @Override
        public void onKeyDown() {
            MyMain.stop();
        }
    };
    OnKeyEvent fc = new OnKeyEvent(Keyboard.KEY_F11) {
        @Override
        public void onKeyDown() {
            MyMain.toggleFullscreen();
        }
    };

    Player player = new Player();

    WorldMap worldMap;

    @Override
    public void init() {
        worldMap = new WorldMap("res/scene/map/test.json");
//        world.setPosition(new Vector2f());
        player.setPosition(3600, 6500);
//        world.setPosition(new Vector2f(-player.getPosition().x, -player.getPosition().y));
    }


    Vector2f speed = new Vector2f(0f,0f);

    Vector2f lastFramePosition = new Vector2f();

    @Override
    public void event() {

        Vector2f vector = player.getPosition();
        Vector2f integration = Vector2f.sub(vector, lastFramePosition, null);

        if(integration.x!=0f||integration.y!=0f){
            float rot = (float) Math.toDegrees(Math.atan2(integration.y, integration.x));
            player.setRotation(rot);
        }

        speed = Vector2f.sub(vector, world.getPosition(), speed);
        world.addPosition(new Vector2f(speed.x * 0.05f, speed.y * 0.05f));
        integration.scale(1.5f);
        world.addPosition(integration);

        if(worldMap.collidesAt(player.getPosition().x, player.getPosition().y)){
            if(!worldMap.collidesAt(player.getPosition().x, lastFramePosition.y)){
                player.setPosition(player.getPosition().x, lastFramePosition.y);
            } else if(!worldMap.collidesAt(lastFramePosition.x, player.getPosition().y)){
                player.setPosition(lastFramePosition.x, player.getPosition().y);
            } else {
                player.setPosition(lastFramePosition.x, lastFramePosition.y);
            }
        }


        float light = 1f;
        long time = System.nanoTime();
        worldMap.render(player.getPosition(), light);
        long timeElapsed = System.nanoTime() - time;
        average = (0.99f * average + 0.01f * (float)timeElapsed);
//        System.out.println("Average render time: " + average / 1000000f + " ms.");

        player.render();

        lastFramePosition.x = player.getPosition().x;
        lastFramePosition.y = player.getPosition().y;

    }
    private float average = 0;

    @Override
    public void close() {

    }

}
