package state;

import loaders.MySimpleFileReader;
import main.MyMainClass;
import math.MyMat3;
import math.MyRegion;
import math.MyVec2;
import math.MyVec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderer.objects.MyShaderProgram;
import renderer.objects.MyVertexObject;
import ui.MyLabel;
import util.input.MyEventType;
import util.input.MyKeyboardHandler;
import util.input.MyListener;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MySplashScreen extends MyState {

    MyKeyboardHandler input = new MyKeyboardHandler();

    MyLabel titleText = new MyLabel("res/font1.json");

    String text = "Hello, world!";

    @Override
    public void init() {
        input.addListener(new MyListener(Keyboard.KEY_RETURN, MyEventType.BUTTON_UP) {
            @Override
            public void event() {
                MyMainClass.newState(new MyMainMenu());
            }
        });
        titleText.position = new MyVec2(100f, 100f);
    }

    @Override
    public void event() {

    }

    @Override
    public void render(MyMat3 projectionMatrix) {
        titleText.text = text;
        titleText.render(projectionMatrix);
    }

    @Override
    public void close() {

    }
}
