package ui;

import main.MyMainClass;
import math.MyMat3;
import math.MyVec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderer.MyRenderer;
import renderer.objects.MyShaderProgram;

import java.io.IOException;

public class Old_MyButton extends MyRenderer {

    float x0, y0, xs, ys;
    private ButtonState state = ButtonState.IDLE;

    private MyShaderProgram myShaderProgram = null;
    public final String fragmentShaderPath = "res/shader/button.fs";
    public final String vertexShaderPath = "res/shader/button.vs";

    public float alpha = 1f;
    public MyVec3 col = new MyVec3(1f, 1f, 1f);

    MyLabel labelRenderer = new MyLabel("res/img/text/bmpfont1.bmp", 5, 11);
    String label = "";

    public Old_MyButton(String label, float x0, float y0, float xs, float ys){
        super();
        this.label = label;
        labelRenderer.setCentered(true);
        labelRenderer.setVerticalCentered(true);
        labelRenderer.setSize(Math.min(ys, xs) * .2f);
        labelRenderer.setPos((x0 + xs * .5f), (y0 - 0.14f + ys*.5f));
        labelRenderer.setSpacing(0.1f);

        this.x0 = x0; this.y0 = y0; this.xs = xs; this.ys = ys;
        try{
            myShaderProgram = MyShaderProgram.addShader(vertexShaderPath, fragmentShaderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void event(){
        float mx = 2f * (float) Mouse.getX() / MyMainClass.getWidth() - 1f;
        float my = 2f * (float) Mouse.getY() / MyMainClass.getHeight() - 1f;
        if((mx >= x0 && mx <= (x0 + xs))&&(my >= y0 && my <= (y0 + ys))){
            if(Mouse.isButtonDown(0)){
                setState(ButtonState.MOUSE_DOWN);
            } else {
                setState(ButtonState.MOUSE_OVER);
            }
        } else {
            setState(ButtonState.IDLE);
        }
    }

    public void render(){
        MyMat3 projection = MyMat3.getIdentity();
        projection = projection.translate(new MyVec3(x0, y0, 1f));
        projection = projection.scale(new MyVec3(xs, ys, 1f));
        GL30.glBindVertexArray(vaoid);
        GL20.glEnableVertexAttribArray(0);
        myShaderProgram.bind();
        myShaderProgram.setUniformMat3("projection", projection);
        myShaderProgram.setUniform3f("in_color", col.vector[0], col.vector[1], col.vector[2]);
        myShaderProgram.setUniform1f("alpha", alpha);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
        labelRenderer.render(label);
    }

    private void setState(ButtonState newState){
        ButtonState oldState = state;
        state = newState;
        if(listener==null) return;
        if(oldState == ButtonState.IDLE && newState == ButtonState.MOUSE_OVER) listener.onMouseOver();
        else if(oldState == ButtonState.MOUSE_OVER && newState == ButtonState.IDLE) listener.onMouseOut();
        else if(oldState == ButtonState.MOUSE_OVER && newState == ButtonState.MOUSE_DOWN) listener.onMouseDown();
        else if(oldState == ButtonState.MOUSE_DOWN && newState == ButtonState.MOUSE_OVER) listener.onMouseUp();
        else if(oldState == ButtonState.MOUSE_DOWN && newState == ButtonState.IDLE) listener.onMouseOut();
    }

    OnAction listener = null;
    public void addListener(OnAction listener){
        this.listener = listener;
    }

    public enum ButtonState{
        IDLE, MOUSE_OVER, MOUSE_DOWN;
    }

}
