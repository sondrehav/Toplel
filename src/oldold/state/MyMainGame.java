package oldold.state;

import oldold.main.MyMainClass;
import oldold.math.MyMat3;
import org.lwjgl.input.Keyboard;
import oldold.util.input.MyEventType;
import oldold.util.input.MyKeyboardHandler;
import oldold.util.input.MyListener;

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
