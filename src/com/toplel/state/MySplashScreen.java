package com.toplel.state;

import com.toplel.event.mouse.MyMouseEventHandler;
import com.toplel.event.mouse.MyMouseListener;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.elements.MyImage;
import com.toplel.ui.elements.elements.MyLineElement;
import com.toplel.ui.elements.elements.MyPane;

import java.util.ArrayList;

public class MySplashScreen extends MyMasterState {

    MyPane pane = new MyPane(new MyVec2(100f, 100f), new MyVec2(600f, 400f));
    MyLineElement lineElement;
    MyImage image = new MyImage("res/img/game/objects/tree.png", new MyVec2(100f,100f));

    ArrayList<MyImage> images = new ArrayList<>();

    MyImage isdragging = null;

    @Override
    public void init() {

        MyMouseEventHandler.addListener(new MyMouseListener(pane, 0) {
            @Override public void onMouseUp(float mx, float my) { pane.alpha = .8f; }
            @Override public void onMouseDown(float mx, float my) { pane.alpha = .9f; }
            @Override public void onMouseOut(float mx, float my) { pane.alpha = .7f; }
            @Override public void onMouseIn(float mx, float my) { pane.alpha = .8f;}
        });
        MyMouseEventHandler.addListener(new MyMouseListener(image, 0) {
            @Override public void onMouseOut(float mx, float my) { image.alpha = .7f; }
            @Override public void onMouseIn(float mx, float my) { image.alpha = .8f;}
            @Override
            public void onMouseDown(float mx, float my){
                MyImage img = new MyImage("res/img/game/objects/tree.png", new MyVec2(mx, my));
                MyMouseEventHandler.addListener(new MyMouseListener(img, 0) {
                    @Override
                    public void onMouseDrag(float mx, float my) {
                        img.setPosition(new MyVec2(mx, my));
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
                isdragging.setPosition(new MyVec2(mx, my));
            }
            @Override
            public void onMouseDragRelease(float mx, float my){
                isdragging = null;
            }
        });
    }

    int frame = 0;
    @Override
    public void event() {
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
