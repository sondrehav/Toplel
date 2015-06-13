package com.toplel.math;

import java.util.Random;

public abstract class MyMath {

    /**
     * This class contains some common oldold.math function not preset in the java oldold.math library.
     */

    /*

    BASIC FUNCTIONS

     */

    /**
     * This method clamps the input value.
     * @param c0 The first clamp limit.
     * @param c1 The second clamp limit.
     * @param val Value to be clamped.
     * @return Clamped value.
     */
    public static float clamp(float c0, float c1, float val){
        float min = min(c0, c1);
        float max = max(c0, c1);
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }

    /**
     * Outputs the minimum value of the two inputs.
     * @param a Input a.
     * @param b Input b.
     * @return The minimum value.
     */
    public static float min(float a, float b){
        if(a<b) return a;
        return b;
    }

    /**
     * Outputs the maximum value of the two inputs.
     * @param a Input a.
     * @param b Input b.
     * @return The maximum value.
     */
    public static float max(float a, float b){
        if(a>b) return a;
        return b;
    }

    /**
     * Linear interpolates between two values.
     * @param a Input a.
     * @param b Input b.
     * @param f Lerp factor. Assumes this is between 0 and 1.
     * @return The linear interpolated value.
     */
    public static float lerp(float a, float b, float f) {
        return a * f + b * (1f - f);
    }

    public static MyVec2 quadraticBezier(MyVec2 p0, MyVec2 p1, MyVec2 p2, float t){
        MyVec2 finalVec = new MyVec2();
        finalVec.x = (float) Math.pow(1f - t, 2) * p0.x +
                (1f - t) * 2f * t * p1.x +
                t * t * p2.x;
        finalVec.y = (float) Math.pow(1f - t, 2) * p0.y +
                (1f - t) * 2f * t * p1.y +
                t * t * p2.y;
        return finalVec;
    }

    public static MyVec2 quadraticBezier2(MyVec2 p0, MyVec2 p1, MyVec2 p2, float t){
        MyVec2 finalVec = new MyVec2();
        MyVec2 controlPoint = new MyVec2();
        controlPoint.x = p1.x * 2f - (p0.x + p2.x) / 2f;
        controlPoint.y = p1.y * 2f - (p0.y + p2.y) / 2f;
        finalVec.x = (float) Math.pow(1f - t, 2) * p0.x +
                (1f - t) * 2f * t * controlPoint.x +
                t * t * p2.x;
        finalVec.y = (float) Math.pow(1f - t, 2) * p0.y +
                (1f - t) * 2f * t * controlPoint.y +
                t * t * p2.y;
        return finalVec;
    }

    public static MyVec2 lerp(MyVec2 a, MyVec2 b, float f){
        MyVec2 finalVec = new MyVec2();
        finalVec.x = a.x * f + b.x * (1f - f);
        finalVec.y = a.y * f + b.y * (1f - f);
        return finalVec;
    }

    /*

    RANDOM FUNCTIONS

     */

    private static final Random rand = new Random();

    /**
     * This method returns a normally distributed random value.
     * @return Normally distributed value.
     */
    public static float randomNormalDist(float my, float std){
        return ((float)rand.nextGaussian()*std + my);
    }

    /**
     * This method returns a uniformly distributed random value.
     * @return Uniformly distributed value.
     */
    public static float randomUniformDist(float from, float to){
        return rand.nextFloat() * (to - from) + from;
    }

    /*

    LINEAR MATH

     */




    // TODO: Make
//    public static float[][] gaussianElimination(float[][] input){
//        float[][] output = input.clone();
//        int col = 0;
//        int row = 0;
//        while(row<input.length&&col<input[0].length) {
//
//            // locate pivot
//            for (int i = col; i < output[0].length; i++) {
//                for (int j = 0; j < output.length; j++) {
//                    if (output[j][i] != 0f) {
//                        // pivot found
//                        col = i;
//                        row = j;
//                        for (int k = 0; k < output.length; k++) {
//                            if (output[k][col] != 0 && k != col) {
//                                for (int l = col; l < output[0].length; l++) {
//                                    output[k][l] = output[col][row] / (-output[i][col] / output[j][col]) + output[k][l];
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return output;
//    }

}
