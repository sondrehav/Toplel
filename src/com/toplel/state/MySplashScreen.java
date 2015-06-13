package com.toplel.state;

import com.toplel.event.mouse.MyMouseEventHandler;
import com.toplel.event.mouse.MyMouseListener;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.elements.MyLineElement;
import com.toplel.ui.elements.elements.MyPane;

public class MySplashScreen extends MyMasterState {

    MyPane pane = new MyPane(new MyVec2(100f, 100f), new MyVec2(100f, 100f));
    MyLineElement line;

    @Override
    public void init() {
        MyMouseEventHandler.addListener(new MyMouseListener(pane.getRegion(),0) {
            @Override
            public void onMouseDragRelease(float mx, float my) {
            }

            @Override
            public void onMouseDrag(float mx, float my) {
            }

            @Override
            public void onMouseUp(float mx, float my) {
                pane.alpha = .8f;
            }

            @Override
            public void onMouseDown(float mx, float my) {
                pane.alpha = .9f;
                System.out.println("MySplashScreen.onMouseDown");
            }

            @Override
            public void onMouseOut(float mx, float my) {
                pane.alpha = .7f;
                System.out.println("MySplashScreen.onMouseOut");
            }

            @Override
            public void onMouseOver(float mx, float my) {
//                System.out.println("MySplashScreen.onMouseOver");
            }

            @Override
            public void onMouseIn(float mx, float my) {
                pane.alpha = .8f;
                System.out.println("MySplashScreen.onMouseIn");
            }
        });
        MyVec2[] points = new MyVec2[4];
        points[0] = new MyVec2(100f,100f);
        points[1] = new MyVec2(300f,100f);
        points[2] = new MyVec2(500f,400f);
        points[3] = new MyVec2(200f,500f);
        line = new MyLineElement(points, new MyVec2(0f, 0f), new MyVec2(800f, 800f));
    }

    @Override
    public void event() {

    }

    @Override
    public void render() {
        line.render();
    }

    @Override
    public void close() {

    }
}
