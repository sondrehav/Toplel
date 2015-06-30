package com.toplel.state;

import com.toplel.context.MyHUD;
import com.toplel.context.MyWorld;
import com.toplel.ecs.entity.GameObject;
import com.toplel.main.MyMain;
import com.toplel.test.Player;
import com.toplel.test.WorldMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class MyEditor extends MyMasterState {

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), new Matrix4f());

    Player player = new Player();

    WorldMap worldMap;

    @Override
    public void init() {
        worldMap = new WorldMap("res/scene/map/test.json");
        world.setPosition(new Vector2f(-0,-0));
    }


    Vector2f speed = new Vector2f(0f,0f);

    @Override
    public void event() {
//        System.out.println("p:" + player.getPosition());
//        System.out.println("w:" + world.getPosition());
//        System.out.println();
        speed = Vector2f.sub(player.getPosition(), world.getPosition(), speed);
        speed.scale(0.1f);
        world.addPosition(speed);
        worldMap.render();
        player.render();
    }

    @Override
    public void close() {

    }

}
