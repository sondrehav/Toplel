package state;

import main.MyMainClass;
import math.MyVec3;
import ui.MyButton;
import ui.MyButtonListener;
import ui.OnAction;

public class MyMainMenu extends MyState {

    MyButton play = new MyButton("PLAY", -.5f, .4f-.1f, 1f, .2f);
    MyButton button2 = new MyButton("LOAD", -.5f, .15f-.1f, 1f, .2f);
    MyButton options = new MyButton("OPTIONS", -.5f, -.1f-.1f, 1f, .2f);
    MyButton exit = new MyButton("EXIT", -.5f, -.35f-.1f, 1f, .2f);

    @Override
    public void init() {
        play.col = new MyVec3(.5f, .5f, 1f);
        play.addListener(new OnAction() {
            @Override
            public void onMouseOver() {
                play.col = new MyVec3(.8f, .8f, 1f);
            }

            @Override
            public void onMouseOut() {
                play.col = new MyVec3(.5f, .5f, 1f);
            }

            @Override
            public void onMouseDown() {
                play.col = new MyVec3(1f, 1f, 1f);
            }

            @Override
            public void onMouseUp() {
                play.col = new MyVec3(.8f, .8f, 1f);
                MyMainClass.newState(new MyMainGame());
            }
        });
        button2.col = new MyVec3(.5f, .5f, 1f);
        button2.addListener(new OnAction() {
            @Override
            public void onMouseOver() {
                button2.col = new MyVec3(.8f, .8f, 1f);
            }

            @Override
            public void onMouseOut() {
                button2.col = new MyVec3(.5f, .5f, 1f);
            }

            @Override
            public void onMouseDown() {
                button2.col = new MyVec3(1f, 1f, 1f);
            }

            @Override
            public void onMouseUp() {
                button2.col = new MyVec3(.8f, .8f, 1f);
            }
        });
        options.col = new MyVec3(.5f, .5f, 1f);
        options.addListener(new OnAction() {
            @Override
            public void onMouseOver() {
                options.col = new MyVec3(.8f, .8f, 1f);
            }

            @Override
            public void onMouseOut() {
                options.col = new MyVec3(.5f, .5f, 1f);
            }

            @Override
            public void onMouseDown() {
                options.col = new MyVec3(1f, 1f, 1f);
            }

            @Override
            public void onMouseUp() {
                options.col = new MyVec3(.8f, .8f, 1f);
            }
        });
        exit.col = new MyVec3(.5f, .5f, 1f);
        exit.addListener(new MyButtonListener<MyMainMenu>(this) {
            @Override
            public void onMouseOver() {
                exit.col = new MyVec3(.8f, .8f, 1f);
            }

            @Override
            public void onMouseOut() {
                exit.col = new MyVec3(.5f, .5f, 1f);
            }

            @Override
            public void onMouseDown() {
                exit.col = new MyVec3(1f, 1f, 1f);
            }

            @Override
            public void onMouseUp() {
                exit.col = new MyVec3(.8f, .8f, 1f);
                MyMainClass.stop();
            }
        });
    }

    @Override
    public void event() {
        play.event();
        button2.event();
        options.event();
        exit.event();
    }

    @Override
    public void render() {
        play.render();
        button2.render();
        options.render();
        exit.render();
    }

    @Override
    public void close() {

    }
}
