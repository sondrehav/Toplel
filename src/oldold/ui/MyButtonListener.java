package oldold.ui;

public abstract class MyButtonListener<T> implements OnAction {
    T thisObject = null;
    public MyButtonListener(T input){
        thisObject = input;
    }
    public MyButtonListener(){
    }
}
