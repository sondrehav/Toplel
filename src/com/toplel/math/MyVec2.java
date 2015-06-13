package com.toplel.math;

import java.nio.FloatBuffer;

public class MyVec2 {

    /**
     * This class represent a 2-dimensional vector. The vector is stored in a
     * array of 2 floats.
     */

    public float x, y;

    public MyVec2(){}
    public MyVec2(float x, float y){
        this.x = x; this.y = y;
    }

    public MyVec2(MyVec3 in){
        this.x = in.x;
        this.y = in.y;
    }

    /**
     * This method clones the current vector into a new one.
     * @return a new identical vector.
     */

    public MyVec2 clone(){
        MyVec2 v = new MyVec2();
        v.x = this.x;
        v.y = this.y;
        return v;
    }

    /**
     * This method adds one vector with another.
     * @param input is the vector to add to the current.
     * @return the new added vector.
     */

    public MyVec2 add(MyVec2 input){
        return new MyVec2(input.x+this.x,input.y+this.y);
    }

    /**
     * This method subtracts one vector from another.
     * @param input is the vector to subtracts from the current.
     * @return the new subtracted vector.
     */

    public MyVec2 subtract(MyVec2 input){
        return new MyVec2(this.x-input.x,this.y-input.y);
    }

    /**
     * This method scales this vector.
     * @param input is the scale factor.
     * @return the new scaled vector.
     */

    public MyVec2 scale(float input){
        return new MyVec2(this.x*input,this.y*input);
    }

    /**
     * This method calculates the dot product between two vectors.
     * @param input is the other vector.
     * @return the dot product.
     */

    public float dot(MyVec2 input){
        return this.x*input.x+this.y*input.y;
    }

    /**
     * This method calculates the length, but does not square the result.
     * @return the length to the power of two.
     */

    public float length(){
        return this.x*this.x+this.y*this.y;
    }

    /**
     * This method calculates the actual length.
     * @return the length.
     */

    public float lengthSqrt(){
        return (float)Math.sqrt(this.length());
    }

    /**
     * Creates a buffer from the vector.
     * @param vector is the input vector.
     * @return the float buffer.
     */
    public static FloatBuffer store(MyVec2 vector){
        FloatBuffer buf = FloatBuffer.allocate(2);
        buf.put(vector.x).put(vector.y);
        return buf;
    }

    @Override
    public String toString() {
        return "MyVec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
