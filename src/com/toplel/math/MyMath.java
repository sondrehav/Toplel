package com.toplel.math;

import org.lwjgl.util.vector.Vector2f;

import java.util.Arrays;
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

    public static Vector2f quadraticBezier(Vector2f p0, Vector2f p1, Vector2f p2, float t){
        Vector2f finalVec = new Vector2f();
        finalVec.x = (float) Math.pow(1f - t, 2) * p0.x +
                (1f - t) * 2f * t * p1.x +
                t * t * p2.x;
        finalVec.y = (float) Math.pow(1f - t, 2) * p0.y +
                (1f - t) * 2f * t * p1.y +
                t * t * p2.y;
        return finalVec;
    }

    public static Vector2f quadraticBezier2(Vector2f p0, Vector2f p1, Vector2f p2, float t){
        Vector2f finalVec = new Vector2f();
        Vector2f controlPoint = new Vector2f();
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

    public static Vector2f lerp(Vector2f a, Vector2f b, float f){
        Vector2f finalVec = new Vector2f();
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


    public static void main(String[] args) {

        float[][] f = new float[4][4];
        f[0][0] = -8f;
        f[0][1] = -6f;
        f[0][2] = 2f;
        f[0][3] = 3f;
        f[1][0] = 1f;
        f[1][1] = 3f;
        f[1][2] = -1f;
        f[1][3] = 3f;
        f[2][0] = 2f;
        f[2][1] = 4f;
        f[2][2] = -2f;
        f[2][3] = 0f;
        f[3][0] = 0f;
        f[3][1] = -3f;
        f[3][2] = -2f;
        f[3][3] = -1f;

        for (int i = 0; i < f.length; i++) {
            System.out.print("\n{" + Arrays.toString(f[i]) + "}");
            if(i!=f.length-1) System.out.print(",");
        }
        System.out.println();

        f = inverse(f);

        for (int i = 0; i < f.length; i++) {
            System.out.print("\n{" + Arrays.toString(f[i]) + "}");
            if(i!=f.length-1) System.out.print(",");
        }
        System.out.println();

    }

    public static float[][] identity(int dim){
        float[][] f = new float[dim][dim];
        for (int i = 0; i < dim; i++) {
            f[i][i] = 1f;
        }
        return f;
    }

    public static float[][] inverse(float[][] input){
        if(input.length!=input[0].length){
            System.err.println("Not square.");
            return null;
        }
        int dim = input.length;
        float[][] f = append(input, identity(dim));
        f = gaussianElimination(f);
        return splitRight(f, dim);
    }

    public static float[][] append(float[][] left, float[][] right){
        if(left.length!=right.length){
            System.err.println("Not equal row dimension.");
            return null;
        }
        float[][] out = new float[left.length][left[0].length+right[0].length];
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < out[0].length; j++) {
                if(j < left[0].length) {
                    out[i][j] = left[i][j];
                } else {
                    out[i][j] = right[i][j - left[0].length];
                }
            }
        }
        return out;
    }

    public static float[][] splitLeft(float[][] input, int col){
        float[][] out = new float[input.length][col];
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < col; j++) {
                out[i][j] = input[i][j];
            }
        }
        return out;
    }

    public static float[][] splitRight(float[][] input, int col){
        float[][] out = new float[input.length][input[0].length - col];
        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < out[0].length; j++) {
                out[i][j] = input[i][j + col];
            }
        }
        return out;
    }

    public static final int MAX_ITER = 16; // Keep it from running indefinitely.
    public static float[][] gaussianElimination(float[][] input){
        float[][] f = input.clone();
        int p;
        int c = 0;
        int lookFromCol = 0;
        int lookFromRow = 0;
        while(true){
            if((c++)>=MAX_ITER){
                System.err.println("Gaussian elimination terminated.");
                return null;
            }
            p = getPivot(f, lookFromRow, lookFromCol);
            if(p<0||p>=input.length*input[0].length){
                break;
            }
            int row = Math.floorDiv(p, f[0].length);
            int col = p % f[0].length;
            lookFromCol = col + 1;
            lookFromRow = row + 1;

            swapRow(f, row, 0);
            if(f[0][col] != 1f) scaleRow(f, 0, 1f / f[0][col]);
            for (int i = 1; i < f.length; i++) { // DOWN
                if(f[i][col]!=0f) addRows(f, i, 0, col);
            }
        }
        // Sorting
        lookFromCol = 0;
        lookFromRow = 0;
        while(true){
            int pivot = getPivot(f, lookFromRow, lookFromCol);
            if(pivot<0||pivot>=f.length*f[0].length){
                break;
            }
            int row = Math.floorDiv(pivot, f[0].length);
            int col = pivot % f[0].length;
            if(lookFromCol>=f[0].length||lookFromRow>=f.length) break;
            swapRow(f, lookFromRow, row);
            lookFromCol = col + 1;
            lookFromRow++;
        }
        return f;
    }

    private static void swapRow(float[][] input, int row_1, int row_2){
        float[] temp = input[row_1];
        input[row_1] = input[row_2];
        input[row_2] = temp;
    }

    private static void scaleRow(float[][] input, int row_1, float scale){
        for (int i = 0; i < input[row_1].length; i++) {
            input[row_1][i] *= scale;
        }
    }

    private static void addRows(float[][] input, int toRow, int fromRow, int startCol){
        float sc = - input[toRow][startCol] / input[fromRow][startCol];
        for (int i = 0; i < input[toRow].length; i++) {
            input[toRow][i] = input[fromRow][i] * sc + input[toRow][i];
        }
    }

    private static int getPivot(float[][] input, int startRow, int startCol){
        int c_row = -1;
        int c_col = Integer.MAX_VALUE;
        for (int i = startRow; i < input.length; i++) {
            for (int j = startCol; j < input[0].length; j++) {
                if(input[i][j]!=0f&&j<c_col){
                    c_col = j;
                    c_row = i;
                }
            }
        }
        return c_col + c_row * input[0].length;
    }

}
