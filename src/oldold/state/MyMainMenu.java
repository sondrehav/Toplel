package oldold.state;

import oldold.math.MyMat3;

public class MyMainMenu extends MyState {
/*
    Old_MyButton play = new Old_MyButton("PLAY", -.5f, .4f-.1f, 1f, .2f);
    Old_MyButton button2 = new Old_MyButton("LOAD", -.5f, .15f-.1f, 1f, .2f);
    Old_MyButton options = new Old_MyButton("OPTIONS", -.5f, -.1f-.1f, 1f, .2f);
    Old_MyButton exit = new Old_MyButton("EXIT", -.5f, -.35f-.1f, 1f, .2f);

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
    public void render(MyMat3 projectionMatrix) {
        play.render();
        button2.render();
        options.render();
        exit.render();
    }

    @Override
    public void close() {

    }*/

    @Override
    public void init() {

    }

    @Override
    public void event() {

    }

    @Override
    public void render(MyMat3 projectionMatrix) {

    }

    @Override
    public void close() {

    }
}
