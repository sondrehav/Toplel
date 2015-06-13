package com.toplel.util;

import com.toplel.math.MyMat3;

public abstract class MyMatrixStack {

    private static MyMat3 current = null;
    private static MyMat3[] stack = new MyMat3[32];
    private static int currentPointer = 0;

    public static void pushMatrix(MyMat3 matrix){
        if(currentPointer>=stack.length){
            System.err.println("Matrix stack overflow!");
            System.exit(5);
        }
        stack[currentPointer++] = matrix;
    }

    public static void popMatrix(){
        currentPointer--;
    }

}
