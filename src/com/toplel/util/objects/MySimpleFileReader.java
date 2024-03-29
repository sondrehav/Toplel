package com.toplel.util.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class MySimpleFileReader {

    public static String read(String path) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;
        StringBuilder file = new StringBuilder();
        while((line = br.readLine()) != null){
            file.append(line+"\n");
        }
        br.close();
        return file.toString();
    }

}
