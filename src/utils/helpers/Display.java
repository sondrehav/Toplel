package utils.helpers;

import java.time.LocalTime;

public class Display {

    public static void display(String input){
        System.out.println(LocalTime.now().toString() + " -> " + input);
    }

}
