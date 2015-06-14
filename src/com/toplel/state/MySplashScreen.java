package com.toplel.state;

import com.toplel.event.mouse.MyMouseEventHandler;
import com.toplel.event.mouse.MyMouseListener;
import com.toplel.events.keyboard.MyKeyListener;
import com.toplel.events.keyboard.MyKeyboardEventHandler;
import com.toplel.main.MyContext;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.elements.MyImage;
import com.toplel.ui.elements.elements.MyLineElement;
import com.toplel.ui.elements.elements.MyPane;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class MySplashScreen extends MyMasterState {

    MyPane pane = new MyPane(new MyVec2(100f, 100f), new MyVec2(600f, 400f));
    MyLineElement lineElement;
    MyImage image = new MyImage("res/img/defaultImage.png", new MyVec2(100f,100f), new MyVec2(100f, 100f));

    ArrayList<MyImage> images = new ArrayList<>();

    MyImage isdragging = null;

    MyVec2 position = new MyVec2();

    @Override
    public void init() {

//        MyMouseEventHandler.addListener(new MyMouseListener(pane, 0) {
//            @Override public void onMouseUp(float mx, float my) { pane.alpha = .8f; }
//            @Override public void onMouseDown(float mx, float my) { pane.alpha = .9f; }
//            @Override public void onMouseOut(float mx, float my) { pane.alpha = .7f; }
//            @Override public void onMouseIn(float mx, float my) { pane.alpha = .8f;}
//        });
        MyMouseEventHandler.addListener(new MyMouseListener(image, 0) {
            float initialMX;
            float initialMY;
            @Override public void onMouseOut(float mx, float my) { image.alpha = .7f; }
            @Override public void onMouseIn(float mx, float my) { image.alpha = .8f;}
            @Override
            public void onMouseDown(float mx, float my){
                initialMX = mx;
                initialMY = my;
                MyImage img = new MyImage("res/img/defaultImage.png", new MyVec2(mx, my), new MyVec2(100f, 100f));
                img.setContext(MyContext.WORLD);
                MyMouseEventHandler.addListener(new MyMouseListener(img, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        img.setPosition(new MyVec2(mx-img.getSize().x*.5f, my-img.getSize().y*.5f));
                    }
                });
                MyMouseEventHandler.addListener(new MyMouseListener(img, 1) {
                    @Override
                    public void onMouseDown(float mx, float my) {
                        images.remove(img);
                        //MyMouseEventHandler.removeListener(); TODO: Make
                    }
                });
                images.add(img);
                isdragging = img;
            }
            @Override
            public void onMouseDrag(float mx, float my) {
                isdragging.setPosition(new MyVec2(mx-isdragging.getSize().x*.5f, my-isdragging.getSize().y*.5f));
            }
            @Override
            public void onMouseDragRelease(float mx, float my){
                isdragging = null;
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_W) {
            @Override public void onKeyHold() { position.y+=5f; }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_A) {
            @Override public void onKeyHold() { position.x-=5f; }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_S) {
            @Override
            public void onKeyHold() {
                position.y -= 5f;
            }
        });
        MyKeyboardEventHandler.addListener(new MyKeyListener(Keyboard.KEY_D) {
            @Override
            public void onKeyHold() {
                position.x += 5f;
            }
        });
    }

    int frame = 0;
    @Override
    public void event() {
        MyContext.WORLD.reset();
        MyContext.HUD.reset();
        MyContext.WORLD.setView(MyContext.WORLD.getView().translate(position.scale(-1f)));
    }

    @Override
    public void render() {
//        pane.render();
        image.render();
        for(MyImage i : images){
            i.render();
        }
    }

    @Override
    public void close() {

    }
}
