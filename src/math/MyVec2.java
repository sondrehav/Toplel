package math;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class MyVec2 {

    /**
     * This class represent a 2-dimensional vector. The vector is stored in a
     * array of 2 floats.
     */

    public float[] vector = new float[2];

    public MyVec2(){}
    public MyVec2(float x, float y){this.vector[0]=x; this.vector[1]=y;}

    /**
     * This method clones the current vector into a new one.
     * @return a new identical vector.
     */

    public MyVec2 clone(){
        MyVec2 v = new MyVec2();
        for (int i = 0; i < 2; i++) {
            v.vector[i] = this.vector[i];
        }
        return v;
    }

    /**
     * This method adds one vector with another.
     * @param input is the vector to add to the current.
     * @return the new added vector.
     */

    public MyVec2 add(MyVec2 input){
        return new MyVec2(input.vector[0]+this.vector[0],input.vector[1]+this.vector[1]);
    }

    /**
     * This method subtracts one vector from another.
     * @param input is the vector to subtracts from the current.
     * @return the new subtracted vector.
     */

    public MyVec2 subtract(MyVec2 input){
        return new MyVec2(this.vector[0]-input.vector[0],this.vector[1]-input.vector[1]);
    }

    /**
     * This method scales this vector.
     * @param input is the scale factor.
     * @return the new scaled vector.
     */

    public MyVec2 scale(float input){
        return new MyVec2(this.vector[0]*input,this.vector[1]*input);
    }

    /**
     * This method calculates the dot product between two vectors.
     * @param input is the other vector.
     * @return the dot product.
     */

    public float dot(MyVec2 input){
        return this.vector[0] * input.vector[0] + this.vector[1] * input.vector[1];
    }

    /**
     * This method calculates the length, but does not square the result.
     * @return the length to the power of two.
     */

    public float length(){
        return this.vector[0]*this.vector[0]+this.vector[1]*this.vector[1];
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
        buf.put(vector.vector[0]);
        return buf;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("##.##");
        String s = "[ \t" + df.format(this.vector[0]) + " \t" + df.format(this.vector[1]) + "\t]";
        return s;
    }

}
