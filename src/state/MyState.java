package state;

public abstract class MyState {

    public abstract void init();
    public abstract void event();
    public abstract void render();
    public abstract void close();

}
