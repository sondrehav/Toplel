package com.toplel.math;

public class MyRegion {

    public MyVec2 vec_a = new MyVec2();
    public MyVec2 vec_b = new MyVec2();

    public MyRegion(float x0, float y0, float x1, float y1){
        vec_a.x = x0;
        vec_a.y = y0;
        vec_b.x = x1;
        vec_b.y = y1;
    }

    public boolean isInside(MyVec2 point){
        if(point.x >= vec_a.x && point.x <= vec_b.x &&
                point.y >= vec_a.y && point.y <= vec_b.y) return true;
        return false;
    }

    @Override
    public String toString(){
        return "MyRegion="+vec_a.toString()+", " + vec_b.toString();
    }

}
