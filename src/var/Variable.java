package var;

public interface Variable<T> {
    public void setVariable(byte[] bytes) throws IllegalArgumentException;
    public String getName();
    public T get();
}
