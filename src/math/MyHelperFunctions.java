package math;

import java.util.Random;

public abstract class MyHelperFunctions {

    /**
     * This class contains some common math function not preset in the java math library.
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

    /*

    RANDOM FUNCTIONS

     */

    private static final Random rand = new Random();

    /**
     * This method returns a normally distributed random value.
     * @return Normally distributed value.
     */
    public static float randomNormalDist(){
        return (float)rand.nextGaussian();
    }

    /**
     * This method returns a uniformly distributed random value.
     * @return Uniformly distributed value.
     */
    public static float randomUniformDist(){
        return rand.nextFloat();
    }

    /*

    LINEAR MATH

     */



//    public static void

    private static int argmax(float[][] input, int col){
        int index = -1;
        float val = 0f;
        for (int i = 0; i < input.length; i++) {
            if(Math.abs(input[i][col]) > val) index = i;
        }
        return index;
    }

    private static void swap(float[][] input, int a, int b){
        float[] temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }

    public static void main(String[] args) {
        float[][] mat = new float[][]{{0, 3, 4},{5, 6, 8},{1, 2, 3}};
        disp(mat);
        mat = gaussianElimination(mat);
        disp(mat);
    }

    private static void disp(float[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[i][j]+"\t");
            }
            System.out.print("\n");
        }
    }

    public static float[][] gaussianElimination(float[][] input){
        float[][] output = input.clone();
        int col = 0;
        int row = 0;
        while(row<input.length&&col<input[0].length) {

            // locate pivot
            for (int i = col; i < output[0].length; i++) {
                for (int j = 0; j < output.length; j++) {
                    if (output[j][i] != 0f) {
                        // pivot found
                        col = i;
                        row = j;
                        for (int k = 0; k < output.length; k++) {
                            if (output[k][col] != 0 && k != col) {
                                for (int l = col; l < output[0].length; l++) {
                                    output[k][l] = output[col][row] / (-output[i][col] / output[j][col]) + output[k][l];
                                }
                            }
                        }
                    }
                }
            }
        }
        return output;
    }

}
