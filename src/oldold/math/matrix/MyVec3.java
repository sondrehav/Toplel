package com.toplel.math;

import java.nio.FloatBuffer;

public class MyVec3 {

    /**
     * This class represent a 3-dimensional vector. The vector is stored in a
     * array of 3 floats.
     */

    public float x, y, z;

    public MyVec3(){}
    public MyVec3(MyVec2 in, float z){
        x = in.x;
        y = in.y;
        this.z = z;
    }
    public MyVec3(MyVec2 in){
        x = in.x;
        y = in.y;
    }
    public MyVec3(float x, float y, float z){this.x=x; this.y=y; this.z=z;}

    /**
     * This method clones the current vector into a new one.
     * @return a new identical vector.
     */

    public MyVec3 clone(){
        MyVec3 v = new MyVec3();
        v.x = x;
        v.y = y;
        v.z = z;
        return v;
    }

    /**
     * This method adds one vector with another.
     * @param input is the vector to add to the current.
     * @return the new added vector.
     */

    public MyVec3 add(MyVec3 input){
        return new MyVec3(input.x+this.x,input.y+this.y,input.z+this.z);
    }

    /**
     * This method subtracts one vector from another.
     * @param input is the vector to subtracts from the current.
     * @return the new subtracted vector.
     */

    public MyVec3 subtract(MyVec3 input){
        return new MyVec3(this.x-input.x,this.y-input.y,this.z-input.z);
    }

    /**
     * This method scales this vector.
     * @param input is the scale factor.
     * @return the new scaled vector.
     */

    public MyVec3 scale(float input){
        return new MyVec3(this.x*input,this.y*input,this.z*input);
    }

    /**
     * This method calculates the dot product between two vectors.
     * @param input is the other vector.
     * @return the dot product.
     */

    public float dot(MyVec3 input){
        return this.x * input.x + this.y * input.y + this.z * input.z;
    }

    /**
     * This method calculates the cross product between two vectors.
     * @param input is the other vector.
     * @return the cross product.
     */

    public MyVec3 cross(MyVec3 input){
        MyVec3 v = new MyVec3();
        v.x = this.y * input.z - this.z * input.y;
        v.y = this.z * input.x - this.x * input.z;
        v.z = this.x * input.y - this.y * input.x;
        return v;
    }

    /**
     * This method calculates the length, but does not square the result.
     * @return the length to the power of two.
     */

    public float length(){
        return this.x*this.x+this.y*this.y+this.z*this.z;
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
    public static FloatBuffer store(MyVec3 vector){
        FloatBuffer buf = FloatBuffer.allocate(3);
        buf.put(vector.x);
        return buf;
    }

    @Override
    public String toString() {
        return "MyVec3{" +
                "\n\t"+x+", "+y+", "+z+
                "\n}";
    }
}
