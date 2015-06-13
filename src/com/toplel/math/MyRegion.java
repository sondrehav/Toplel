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

    public MyRegion clone(){
        return new MyRegion(this.vec_a.x, this.vec_a.y, this.vec_b.x, this.vec_b.y);
    }

    public MyRegion mult(MyVec2 vec){
        MyRegion region = this.clone();
        region.vec_a.x *= vec.x;
        region.vec_a.y *= vec.y;
        region.vec_b.x *= vec.x;
        region.vec_b.y *= vec.y;
        return region;
    }

    @Override
    public String toString(){
        return "MyRegion="+vec_a.toString()+", " + vec_b.toString();
    }

}
