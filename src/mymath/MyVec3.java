package mymath;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;

public class MyVec3 {

    /**
     * This class represent a 3-dimensional vector. The vector is stored in a
     * array of 3 floats.
     */

    public float[] vector = new float[3];

    public MyVec3(){}
    public MyVec3(float x, float y, float z){this.vector[0]=x; this.vector[1]=y; this.vector[2]=z;}

    /**
     * This method clones the current vector into a new one.
     * @return a new identical vector.
     */

    public MyVec3 clone(){
        MyVec3 v = new MyVec3();
        for (int i = 0; i < 3; i++) {
            v.vector[i] = this.vector[i];
        }
        return v;
    }

    /**
     * This method adds one vector with another.
     * @param input is the vector to add to the current.
     * @return the new added vector.
     */

    public MyVec3 add(MyVec3 input){
        return new MyVec3(input.vector[0]+this.vector[0],input.vector[1]+this.vector[1],input.vector[2]+this.vector[2]);
    }

    /**
     * This method subtracts one vector from another.
     * @param input is the vector to subtracts from the current.
     * @return the new subtracted vector.
     */

    public MyVec3 subtract(MyVec3 input){
        return new MyVec3(this.vector[0]-input.vector[0],this.vector[1]-input.vector[1],this.vector[2]-input.vector[2]);
    }

    /**
     * This method scales this vector.
     * @param input is the scale factor.
     * @return the new scaled vector.
     */

    public MyVec3 scale(float input){
        return new MyVec3(this.vector[0]*input,this.vector[1]*input,this.vector[2]*input);
    }

    /**
     * This method calculates the dot product between two vectors.
     * @param input is the other vector.
     * @return the dot product.
     */

    public float dot(MyVec3 input){
        return this.vector[0] * input.vector[0] + this.vector[1] * input.vector[1] + this.vector[2] * input.vector[2];
    }

    /**
     * This method calculates the cross product between two vectors.
     * @param input is the other vector.
     * @return the cross product.
     */

    public MyVec3 cross(MyVec3 input){
        MyVec3 v = new MyVec3();
        v.vector[0] = this.vector[1] * input.vector[2] - this.vector[2] * input.vector[1];
        v.vector[1] = this.vector[2] * input.vector[0] - this.vector[0] * input.vector[2];
        v.vector[2] = this.vector[0] * input.vector[1] - this.vector[1] * input.vector[0];
        return v;
    }

    /**
     * This method calculates the length, but does not square the result.
     * @return the length to the power of two.
     */

    public float length(){
        return this.vector[0]*this.vector[0]+this.vector[1]*this.vector[1]+this.vector[2]*this.vector[2];
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
        buf.put(vector.vector[0]);
        return buf;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("##.##");
        String s = "[ \t" + df.format(this.vector[0]) + " \t" + df.format(this.vector[1]) + " \t" + df.format(this.vector[2]) + " \t]";
        return s;
    }

}
