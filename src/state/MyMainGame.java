package state;

import main.MyMainClass;
import math.MyMat3;
import org.lwjgl.input.Keyboard;
import util.input.MyEventType;
import util.input.MyKeyboardHandler;
import util.input.MyListener;

public class MyMainGame extends MyState {

    MyKeyboardHandler keyinput = new MyKeyboardHandler();

    @Override
    public void init() {
        keyinput.addListener(new MyListener(Keyboard.KEY_ESCAPE, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                MyMainClass.newState(new MyMainMenu());
            }
        });
    }

    @Override
    public void event() {
        keyinput.update();
    }

    @Override
    public void render(MyMat3 projectionMatrix) {

    }

    @Override
    public void close() {

    }
}
