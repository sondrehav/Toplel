package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class SimpleFileReader {

    public static String read(String path) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;
        String file = "";
        while((line = br.readLine()) != null){
            file+=line+"\n";
        }
        return file;
    }

}
