package loaders;

import java.io.IOException;

public class MyTextureLoader {

    private static String getFileExtension(String s){
        return s.split("\\.")[1].toLowerCase().trim();
    }

    public static int load(String path) throws IOException{
        return 0; //TODO: Make!
    }

}
