package oldold.state;

import oldold.main.MyMainClass;
import oldold.math.MyMat3;
import oldold.math.vector.MyVec2;
import org.lwjgl.input.Keyboard;
import oldold.ui.MyButton;
import oldold.ui.MyButtonListener;
import oldold.ui.MyElement;
import oldold.ui.MyLabel;
import oldold.util.input.MyEventType;
import oldold.util.input.MyKeyboardHandler;
import oldold.util.input.MyListener;

public class MySplashScreen extends MyState {

    MyKeyboardHandler input = new MyKeyboardHandler();

    MyLabel titleText = new MyLabel("res/font1.json");
    MyLabel text2 = new MyLabel("res/gothica.json");

    String text_a = "abcd".toUpperCase();
    String text_b = "efgh".toUpperCase();

    MyButton button = new MyButton();

    @Override
    public void init() {
        System.out.println(this);
        input.addListener(new MyListener(Keyboard.KEY_RETURN, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                MyMainClass.newState(new MyMainMenu());
            }
        });
        input.addListener(new MyListener(Keyboard.KEY_E, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                titleText.verticalCentered = titleText.verticalCentered ? false : true;
                text2.verticalCentered = text2.verticalCentered ? false : true;
                System.out.println("attached = " + titleText.attached);
            }
        });
        input.addListener(new MyListener(Keyboard.KEY_W, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                titleText.horizontalCentered = titleText.horizontalCentered ? false : true;
                text2.horizontalCentered = text2.horizontalCentered ? false : true;
            }
        });
        input.addListener(new MyListener(Keyboard.KEY_S, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                switch (titleText.attached){
                    case BOTTOM_LEFT:
                        titleText.attached = MyElement.Attached.TOP_LEFT;
                        break;
                    case TOP_LEFT:
                        titleText.attached = MyElement.Attached.TOP_RIGHT;
                        break;
                    case TOP_RIGHT:
                        titleText.attached = MyElement.Attached.BOTTOM_RIGHT;
                        break;
                    case BOTTOM_RIGHT:
                        titleText.attached = MyElement.Attached.BOTTOM_LEFT;
                        break;
                }
            }
        });
        input.addListener(new MyListener(Keyboard.KEY_D, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                switch (text2.attached){
                    case BOTTOM_LEFT:
                        text2.attached = MyElement.Attached.TOP_LEFT;
                        break;
                    case TOP_LEFT:
                        text2.attached = MyElement.Attached.TOP_RIGHT;
                        break;
                    case TOP_RIGHT:
                        text2.attached = MyElement.Attached.BOTTOM_RIGHT;
                        break;
                    case BOTTOM_RIGHT:
                        text2.attached = MyElement.Attached.BOTTOM_LEFT;
                        break;
                }
            }
        });
        titleText.position = new MyVec2(100f, 100f);
        text2.position = new MyVec2(100f, 160f);
        button.addListener(new MyButtonListener() {
            @Override
            public void onMouseOver() {
                button.alpha = .7f;
            }

            @Override
            public void onMouseOut() {
                button.alpha = .6f;
            }

            @Override
            public void onMouseDown() {
                System.out.println("HELLO WORLD!");
                button.alpha = .8f;
            }

            @Override
            public void onMouseUp() {
                button.alpha = .7f;
            }
        });
        button.position = new MyVec2(100f,100f);
        button.size = new MyVec2(100f,100f);
        MyLabel buttonLabel = new MyLabel("res/font1.json");
        buttonLabel.horizontalCentered = true;
        buttonLabel.verticalCentered = true;
        buttonLabel.text = "hello world;";
        button.addChild(buttonLabel);
    }

    int frame = 0;
    @Override
    public void event() {
        input.update();
        button.event();
    }

    @Override
    public void render(MyMat3 projectionMatrix) {
        titleText.text = "frame: " + (frame++);
        text2.text = text_b;
        titleText.render(projectionMatrix);
        text2.render(projectionMatrix);
        button.render(projectionMatrix);
    }

    @Override
    public void close() {

    }
}
