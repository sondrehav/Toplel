package utils;

/**
 * Created by Sondre_ on 25.12.2014.
 */
public class Vector2f {

    public float x, y;

    public Vector2f(){x = 0; y = 0;}

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public static Vector2f add(Vector2f a, Vector2f b, Vector2f dest){
        if(dest == null){
            dest = new Vector2f();
        }
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
        return dest;
    }

    public static Vector2f sub(Vector2f a, Vector2f b, Vector2f dest){
        if(dest == null){
            dest = new Vector2f();
        }
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
        return dest;
    }

    public static Vector2f scale(Vector2f a, float f, Vector2f dest){
        if(dest == null){
            dest = new Vector2f();
        }
        dest.x = a.x * f;
        dest.y = a.y * f;
        return dest;
    }

    public static Vector2f normalize(Vector2f a, Vector2f dest){
        if(dest == null){
            dest = new Vector2f();
        }
        Vector2f.scale(a, 1 / a.lengthSqrt(), dest);
        return dest;
    }

    public float length(){
        return x*x+y*y;
    }

    public static Vector2f average(Vector2f dest, Vector2f... vec){
        if(dest == null){
            dest = new Vector2f();
        }
        int count = 0;
        for(Vector2f v : vec){
            count++;
            Vector2f.add(dest, v, dest);
        }
        Vector2f.scale(dest, 1f / (float) count, dest);
        return dest;
    }


    public static float dot(Vector2f a, Vector2f b){
        return a.x * b.x + a.y * b.y;
    }

    public static boolean isZero(Vector2f vec){
        if(vec.x == 0 && vec.y == 0){
            return true;
        } return false;
    }

    public static float[] toArray(Vector2f vec){
        return new float[]{vec.x, vec.y};
    }

    // Non-static

    public void scale(float scale){
        Vector2f.scale(this, scale, this);
    }
    public void normalize(){
        Vector2f.normalize(this, this);
    }
    public float dot(Vector2f a){
        return Vector2f.dot(this, a);
    }
    public float  lengthSqrt(){
        return (float) Math.sqrt(x*x+y*y);
    }
    public float[] toArray(){
        return Vector2f.toArray(this);
    }

    @Override
    public String toString(){
        return "x= " + x + ";\ty=" + y + ";";
    }

}
