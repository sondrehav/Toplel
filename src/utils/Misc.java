package utils;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class Misc {
    public static String getFileExtension(String path) throws IllegalArgumentException{
        try{
            String[] s = path.split("\\.");

            return s[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Path is not formatted correctly: \'" + path + "\'.");
        }
    }
}
