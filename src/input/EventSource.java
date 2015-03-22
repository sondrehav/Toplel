package input;

import org.lwjgl.input.Keyboard;

import java.util.Observable;

public class EventSource extends Observable implements Runnable {

    public final int key;

    public EventSource(int key){
        this.key = key;
    }

    @Override
    public void run(){
        boolean last = false;
        while(true){
            if(Keyboard.isKeyDown(key)){
                if(!last){
                    notifyObservers();
                    last = true;
                }
            }
            last = false;
        }
    }

}
