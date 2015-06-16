package com.toplel.state;

import com.toplel.components.MySprite;
import com.toplel.context.MyContext;
import com.toplel.context.MyHUD;
import com.toplel.context.MyWorld;
import com.toplel.events.keyboard.OnKeyEvent;
import com.toplel.events.mouse.OnMouseEvent;
import com.toplel.main.MyMain;
import com.toplel.math.MyMath;
import com.toplel.ui.elements.MyAnchor;
import com.toplel.ui.elements.elements.MyPane;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class MyEditor extends MyMasterState {

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), new Matrix4f());
    MyPane pane1 = new MyPane(new Vector2f(20f,20f), new Vector2f(Display.getWidth() - 1000f, Display.getHeight() - 40f), hud);
    MyPane pane2 = new MyPane(new Vector2f(Display.getWidth() - 20f - Display.getWidth() + 1000f,20f), new Vector2f(Display.getWidth() - 1000f, Display.getHeight() - 40f), hud);

    @Override
    public void init() {
        new OnKeyEvent(Keyboard.KEY_W){ @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f,-10f), null)); } };
        new OnKeyEvent(Keyboard.KEY_A){ @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(10f,0f), null)); } };
        new OnKeyEvent(Keyboard.KEY_S){ @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f,10f), null)); } };
        new OnKeyEvent(Keyboard.KEY_D){ @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(-10f,0f), null)); } };
        pane1.left = MyAnchor.LEFT;
        pane2.right = MyAnchor.RIGHT;
        MySprite tree = MySprite.addSprite("res/scene/tree.json");
        tree.setContext(world);
        pane1.add(tree);
        tree.setOnMouseEvent(new OnMouseEvent(tree) {
            @Override
            public void onLeftMouseDrag(Vector2f mouse) {
                if(currentlyDragging!=null){
                    currentlyDragging.setPosition(currentlyDragging.getContext().fromContext(mouse));
                }
            }

            @Override
            public void onMouseOut(Vector2f mouse) {
                tree.setAddColor(0f);
            }
            @Override
            public void onMouseIn(Vector2f mouse) {
                tree.setAddColor(.5f);
            }
            private MySprite currentlyDragging = null;
            @Override
            public void onLeftMouseDown(Vector2f mouse) {
                MySprite sprite = tree.duplicate();
                sprite.setLayer(tree.getRenderer().getLayer() - 2);
                sprite.setContext(MyContext.get("world"));
                currentlyDragging = sprite;
                sprite.setOnMouseEvent(new OnMouseEvent(0, sprite) {
                    @Override
                    public void onMouseIn(Vector2f mouse) {
                        sprite.setAddColor(.5f);
                        sprite.setKeyListeners(true);
                    }

                    @Override
                    public void onMouseOut(Vector2f mouse) {
                        sprite.setAddColor(0f);
                        sprite.setKeyListeners(false);
                    }

                    @Override
                    public void onLeftMouseDown(Vector2f mouse) {
                        dragOffset = Vector2f.sub(sprite.getContext().fromContext(mouse), sprite.getPosition(), null);
                    }

                    Vector2f dragOffset;

                    @Override
                    public void onLeftMouseDrag(Vector2f mouse) {
                        mouse = sprite.getContext().fromContext(mouse);
                        sprite.setPosition(Vector2f.sub(mouse, dragOffset, mouse));
                    }

                    @Override
                    public void onRightMouseDrag(Vector2f mouse) {
                        float sizeMult = 1f / (float) Math.pow(2.0, Mouse.getDY() * 0.001f);
                        float rotAdd = Mouse.getDX() * 0.1f;
                        sprite.setSize(new Vector2f(sprite.getSize().x * sizeMult, sprite.getSize().y * sizeMult));
                        sprite.setRotation(sprite.getRotation() + rotAdd);
                    }
                });
                currentlyDragging = sprite;
            }

            @Override
            public void onLeftMouseDragRelease(Vector2f mouse) {
                currentlyDragging.setRotation(MyMath.randomUniformDist(0f, 360f));
                currentlyDragging = null;
            }

        });
    }

    @Override
    public void event() {

    }

    @Override
    public void render() {

    }

    @Override
    public void close() {

    }

}
