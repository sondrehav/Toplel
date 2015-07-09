package com.toplel.util;

public class Console {

    private static String lastLine = null;
    private static String lastErr = null;

    public static void printLn(String line){
        System.out.println(line);
        lastLine = line;
    }

    public static void printErr(String line){
        System.err.println(line);
        lastErr = line;
    }

    public static String lastLine(){
        return lastLine;
    }

    public static String lastErr(){
        return lastErr;
    }

}
