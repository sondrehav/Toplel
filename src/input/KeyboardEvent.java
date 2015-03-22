package input;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class KeyboardEvent implements Observer {
    @Override
    public void update(Observable obj, Object arg){
        System.out.println("KeyboardEvent.update");
    }
}
