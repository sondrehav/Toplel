package com.toplel.main;

import com.toplel.math.MyMat3;
import com.toplel.math.MyVec2;
import com.toplel.math.MyVec3;

public class MyContext {

    public static MyContext WORLD = new MyContext(MyMain.getProjection(), MyMat3.getIdentity());
    public static MyContext HUD = new MyContext(MyMain.getProjection(), MyMat3.getIdentity());

    private MyMat3 projection, view;

    public MyContext(MyMat3 projection, MyMat3 view){
        this.projection = projection;
        this.view = view;
        orig_projection = projection.clone();
        orig_view = view.clone();
    }

    private final MyMat3 orig_projection, orig_view;
    public void reset(){
        this.projection = orig_projection.clone();
        this.view = orig_view.clone();
    }

    public MyMat3 getView() {
        return view;
    }

    public void setView(MyMat3 view) {
        this.view = view;
    }

    public MyMat3 getProjection() {
        return projection;
    }

    public void setProjection(MyMat3 projection) {
        this.projection = projection;
    }

    public MyMat3 getCombined(){
        return projection.mult(view);
    }

    public MyVec2 getTransformed(MyVec2 input){
        MyVec3 vec = new MyVec3(input, 1f);
        vec = view.mult(vec);
        return new MyVec2(vec);
    }

}
