package util;

public abstract class MyHelpers {
    public static String getFileExtension(String s){
        return s.split("\\.")[1].toLowerCase().trim();
    }
}
