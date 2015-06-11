package ui;

import main.MyMainClass;
import math.MyMat3;
import math.MyVec2;
import math.MyVec3;
import org.lwjgl.input.Mouse;
import renderer.MyRenderer;
import renderer.objects.MyShaderProgram;
import renderer.objects.MyVertexObject;

import java.io.IOException;

public class MyButton extends MyElement {

    private ButtonState state = ButtonState.IDLE;

    private MyShaderProgram myShaderProgram = null;
    public final String fragmentShaderPath = "res/shader/button.fs";
    public final String vertexShaderPath = "res/shader/button.vs";

    public MyVec2 size = new MyVec2(5f, 5f);
    public float alpha = 1f;
    public MyVec3 color = new MyVec3(1f, 1f, 1f);
    private MyVertexObject vertexObject;

    public MyButton(){
        super();
        try{
            myShaderProgram = MyShaderProgram.addShaderProgram(vertexShaderPath, fragmentShaderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        vertexObject = MyVertexObject.createSquare(0f,0f,1f,1f);
    }

    public void event(){
        float mx = (float)Mouse.getX();
        float my = (float)Mouse.getY();
        float x0 = this.position.vector[0];
        float y0 = this.position.vector[1];
        float x1 = this.position.vector[0] + this.size.vector[0];
        float y1 = this.position.vector[1] + this.size.vector[1];
//        System.out.println("x0 = " + x0);
//        System.out.println("x1 = " + x1);
//        System.out.println("y0 = " + y0);
//        System.out.println("y1 = " + y1);
//        System.out.println("mx = " + mx);
//        System.out.println("my = " + my);
//        System.out.println();
        if((mx >= x0 && mx <= x1)&&(my >= y0 && my <= y1)){
            if(Mouse.isButtonDown(0)){
                setState(ButtonState.MOUSE_DOWN);
            } else {
                setState(ButtonState.MOUSE_OVER);
            }
        } else {
            setState(ButtonState.IDLE);
        }
    }

    public void render(MyMat3 projection){
//        this.texture.bind();
        MyMat3 modelMatrix = MyMat3.getIdentity();
        modelMatrix = modelMatrix.translate(new MyVec3(this.position.vector[0], this.position.vector[1], 0f));
        modelMatrix = modelMatrix.scale(new MyVec3(this.size.vector[0],this.size.vector[1],1f));
        this.myShaderProgram.bind();
        this.myShaderProgram.setUniform1f("in_alpha", alpha);
        this.myShaderProgram.setUniformMat3("pr_matrix", projection);
        this.myShaderProgram.setUniformMat3("md_matrix", modelMatrix);
        this.vertexObject.bind();
        this.vertexObject.draw();
        this.vertexObject.unbind();
        this.myShaderProgram.unbind();
//        this.texture.unbind();

        MyVec3 pos = new MyVec3(this.position.vector[0], this.position.vector[1], 2f).scale(.5f);
        MyMat3 pr = projection.translate(pos);
        if(this.children!=null){
            for (int i = 0; i < children.size(); i++) {
                this.children.get(i).render(pr);
            }
        }
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
