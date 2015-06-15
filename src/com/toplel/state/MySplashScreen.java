package com.toplel.state;

import com.toplel.events.keyboard.MyKeyListener;
import com.toplel.events.keyboard.MyKeyboardEventHandler;
import com.toplel.events.mouse.MyMouseEventHandler;
import com.toplel.events.mouse.MyMouseListener;
import com.toplel.main.MyHUD;
import com.toplel.main.MyMain;
import com.toplel.main.MyWorld;
import com.toplel.ui.elements.elements.MyImage;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import java.util.LinkedList;

public class MySplashScreen extends MyMasterState {

    MyImage tree = new MyImage("res/img/game/objects/tree.png", new Vector2f(100f,100f), new Vector2f(100f,100f));
    MyImage rock = new MyImage("res/img/game/objects/rock.png", new Vector2f(100f,250f), new Vector2f(100f,100f));
//    MyImage colorTest = new MyImage("res/img/colorLoadTest.png", new Vector2f(0f,0f), new Vector2f(1024f,1024f));

    final Matrix4f viewMatrix = Matrix4f.setIdentity(new Matrix4f());

    MyHUD hud = new MyHUD(MyMain.getProjectionMatrix());
    MyWorld world = new MyWorld(MyMain.getProjectionMatrix(), viewMatrix);

    LinkedList<MyImage> images = new LinkedList<>();

    @Override
    public void init() {
        viewMatrix.m32 = .1f;
        tree.setContext(hud);
        rock.setContext(hud);
//        colorTest.setContext(hud);
        MyMouseEventHandler.addListener(new MyMouseListener(tree, 0) {

            MyImage dragging = null;
            @Override
            public void onMouseIn(float mx, float my) {
                tree.alpha = .9f;
            }

            @Override
            public void onMouseOut(float mx, float my) {
                tree.alpha = .6f;
            }

            @Override
            public void onMouseDown(float mx, float my) {
                Vector2f mousePos = tree.getContext().toContext(new Vector2f(mx, my));
                mousePos = world.fromContext(mousePos);
                System.out.println("m = " + mousePos);
                MyImage newImg = new MyImage("res/img/game/objects/tree.png", mousePos, new Vector2f(100f,100f));
                newImg.setContext(world);
                images.addFirst(newImg);
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        newImg.setPosition(new Vector2f(mx-newImg.getSize().x*.5f, my-newImg.getSize().y*.5f));
                    }
                    @Override
                    public void onMouseDown(float mx, float my){
                        images.remove(newImg);
                        images.addFirst(newImg);
                    }
                });
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 1) {
                    @Override
                    public void onMouseUp(float mx, float my) {
                        images.remove(newImg);
                    }
                });
                dragging = newImg;
            }

            @Override
            public void onMouseDrag(float mx, float my) {
                Vector2f mousePos = tree.getContext().toContext(new Vector2f(mx, my));
                mousePos = dragging.getContext().fromContext(mousePos);
                dragging.setPosition(mousePos);
            }

            @Override
            public void onMouseDragRelease(float mx, float my){
                dragging = null;
            }

        });
        MyMouseEventHandler.addListener(new MyMouseListener(rock, 0) {

            MyImage dragging = null;
            @Override
            public void onMouseIn(float mx, float my) {
                rock.alpha = .9f;
            }

            @Override
            public void onMouseOut(float mx, float my) {
                rock.alpha = .6f;
            }

            @Override
            public void onMouseDown(float mx, float my) {
                Vector2f mousePos = rock.getContext().toContext(new Vector2f(mx, my));
                mousePos = world.fromContext(mousePos);
                MyImage newImg = new MyImage("res/img/game/objects/rock.png", mousePos, new Vector2f(50f,50f));
                newImg.setContext(world);
                images.addFirst(newImg);
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        newImg.setPosition(new Vector2f(mx-newImg.getSize().x*.5f, my-newImg.getSize().y*.5f));
                    }
                    @Override
                    public void onMouseDown(float mx, float my){
                        images.remove(newImg);
                        images.addFirst(newImg);
                    }
                });
                MyMouseEventHandler.addListener(new MyMouseListener(newImg, 1) {
                    @Override
                    public void onMouseUp(float mx, float my) {
                        images.remove(newImg);
                    }
                });
                dragging = newImg;
            }

            @Override
            public void onMouseDrag(float mx, float my) {
                Vector2f mousePos = rock.getContext().toContext(new Vector2f(mx, my));
                mousePos = dragging.getContext().fromContext(mousePos);
                dragging.setPosition(mousePos);
            }

            @Override
            public void onMouseDragRelease(float mx, float my){
                dragging = null;
            }

        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_S) {
            @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f,3f), null)); }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_D) {
            @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(-3f,0f), null)); }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_W) {
            @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(0f,-3f), null)); }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_A) {
            @Override public void onKeyHold() { world.setPosition(Vector2f.add(world.getPosition(), new Vector2f(3f,0f), null)); }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_Q) {
            @Override public void onKeyHold() {
                Vector2f v = new Vector2f(world.getSize());
                v.x *= 1.01f;
                v.y *= 1.01f;
                world.setSize(v);
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_E) {
            @Override public void onKeyHold() {
                Vector2f v = new Vector2f(world.getSize());
                v.x *= .99f;
                v.y *= .99f;
                world.setSize(v);
            }
        });
    }

    @Override
    public void event() {
//        world.setRotation(world.getRotation()+1f);
    }

    @Override
    public void render() {
        tree.render();
        rock.render();
        for(MyImage image : images){
            image.render();
        }
//        colorTest.render();
    }

    @Override
    public void close() {

    }

    @Override
    public void onResize(){
        world.recalculate();
        hud.recalculate();
    }
}
