package com.toplel.state;

import com.toplel.context.MyHUD;
import com.toplel.context.MyWorld;
import com.toplel.ecs.ComponentManager;
import com.toplel.ecs.components.renderable.AnimatedSprite;
import com.toplel.ecs.entity.GameObject;
import com.toplel.main.MyMain;
import org.lwjgl.util.vector.Matrix4f;

public class MyEditor extends MyMasterState {

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), new Matrix4f());

    GameObject player;

    @Override
    public void init() {

        player = ComponentManager.loadGameObject("res/scene/ghast.json");
        ComponentManager.start();
        AnimatedSprite c = (AnimatedSprite) player.getComponent("animatedSprite");
        c.setState("idle1");
    }

    int k = 0;
    boolean t = false;

    @Override
    public void event() {
        ComponentManager.event();
    }

    @Override
    public void close() {
        ComponentManager.destroy();
    }

}
