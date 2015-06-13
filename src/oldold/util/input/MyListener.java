package oldold.util.input;

public abstract class MyListener<T> implements OnAction{

    protected T thisObject = null;
    MyEventType type;
    int key;

    public MyListener(int key, MyEventType type){
        this(key, type, null);
    }

    public MyListener(int key, MyEventType type, T reference){
        this.key = key;
        this.type = type;
        this.thisObject = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyListener that = (MyListener) o;

        if (key != that.key) return false;
        if (thisObject != null ? !thisObject.equals(that.thisObject) : that.thisObject != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = thisObject != null ? thisObject.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + key;
        return result;
    }
}