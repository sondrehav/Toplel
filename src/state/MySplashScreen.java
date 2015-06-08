package state;

import loaders.MySimpleFileReader;
import main.MyMainClass;
import math.MyVec3;
import org.lwjgl.input.Keyboard;
import renderer.MyTextRenderer;
import util.input.MyEventType;
import util.input.MyKeyboardHandler;
import util.input.MyListener;

import java.io.IOException;

public class MySplashScreen extends MyState {

    MyKeyboardHandler input = new MyKeyboardHandler();

    MyTextRenderer titleText = new MyTextRenderer("res/img/text/czechgotika.bmp", 24, 24, 65);
    MyTextRenderer advanceText = new MyTextRenderer("res/img/text/bmpfont1.bmp", 5, 11);
    MyTextRenderer madeBy = new MyTextRenderer("res/img/text/bmpfont1.bmp", 5, 11);

    String text = null;

    @Override
    public void init() {
        input.addListener(new MyListener(Keyboard.KEY_RETURN, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                MyMainClass.newState(new MyMainMenu());
            }
        });

        try{
            text = MySimpleFileReader.read("res/lorumipsum.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        madeBy.setCentered(true);
        madeBy.setPos(0f, 0f);
        madeBy.setSize(0.02f);
        madeBy.setColor(new MyVec3(.88f, .875f, .8f));
        madeBy.setMaxLineWidth(70);
        madeBy.setVerticalCentered(true);

        titleText.setCentered(true);
        titleText.setPos(0f,.6f);
        titleText.setSize(0.13f);
        titleText.setColor(new MyVec3(.88f,.875f,.8f));

        advanceText.setCentered(true);
        advanceText.setPos(0f,-.75f);
        advanceText.setSize(0.02f);
        advanceText.setColor(new MyVec3(.88f,.875f,.8f));

    }

    int frame = 0;
    @Override
    public void event() {
        input.update();
        advanceText.setRotation(5.0f * (float) Math.sin(Math.toRadians(frame++)));
    }

    @Override
    public void render() {
        titleText.render("radicals".toUpperCase());
        madeBy.render(text);
        advanceText.render("Press 'ENTER' to continue!");
    }

    @Override
    public void close() {

    }
}
