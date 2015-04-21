package utils;

public class QuadTree<Value extends Coordinate> {

    public static void main(String[] args){
//        new QuadTree<>();
    }

    private final Rect bounds;
    private Value[] elements;

    private QuadTree<Value> nw, ne, se, sw;

    public QuadTree(float size, int elements) {
        this(0f, 0f, size, elements);
    }

    @SuppressWarnings("unchecked")
    protected QuadTree(float x, float y, float size, int elements) {
        bounds = new Rect(x, y, size);
        this.elements = (Value[])(new Coordinate[elements]);
    }

    private static class Rect{
        float x, y, size;
        public Rect(float x, float y, float size){
            this.x = x; this.y = y; this.size = size;
        }
    }

}
