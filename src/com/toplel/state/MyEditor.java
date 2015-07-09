package com.toplel.state;

import com.toplel.context.MyHUD;
import com.toplel.context.MyWorld;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.main.MyMain;
import com.toplel.test.Player;
import com.toplel.test.Tileset;
import com.toplel.test.WorldMap;
import com.toplel.ui.elements.MyFont;
import com.toplel.util.Console;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
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

    MyFont font = new MyFont(new Tileset(MyTexture.addTexture("res/img/text/bmpfont1.bmp"), 5, 11), 32);
    MyFont.TextObject drawCalls = font.getCongestedText("Draw calls:");
    MyFont.TextObject fps = font.getCongestedText("FPS:");
    MyFont.TextObject renderTime = font.getCongestedText("Errors:");

    @Override
    public void init() {
        worldMap = new WorldMap("res/scene/map/test.json");
        world.setPosition(new Vector2f());
        player.setPosition(3600, 6500);
        world.setPosition(new Vector2f(-player.getPosition().x, -player.getPosition().y));
        drawCalls.setSize(20f);
        fps.setSize(20f);
        renderTime.setSize(20f);
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
        worldMap.render(player.getPosition(), light);

        player.render();

        lastFramePosition.x = player.getPosition().x;
        lastFramePosition.y = player.getPosition().y;

        drawCalls.render(40f, 40f);
        fps.render(40f, 60f);
        renderTime.render(40f, 80f);
        font.renderText(170f, 60f, 20f, String.valueOf((int)(MyMain.getAvgFPS()+0.01f)));
        font.renderText(170f,80f,20f, Console.lastErr());
        font.renderText(170f, 40f, 20f, String.valueOf(MyVertexObject.getDrawCallCount()));

    }

    @Override
    public void close() {

    }

}
