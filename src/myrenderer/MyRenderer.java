package myrenderer;

import mymath.MyVec3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public abstract class MyRenderer {

    private static ArrayList<MyVec3> line = new ArrayList<>();

    public static void lineBegin(MyVec3 vec3){
        line.clear();
        line.add(vec3);
    }

    public static void lineMoveTo(MyVec3 vec3){
        line.add(vec3);
    }

    public static void lineDraw() {
        FloatBuffer buf = BufferUtils.createFloatBuffer(2*line.size());
        for (int i = 0; i < line.size(); i++) buf.put(new float[]{line.get(i).vector[0],line.get(i).vector[1]});
        buf.flip();
//        d_setBuffer(buf);
    }

    private void d_draw(){
        // State information etc...
    }

//    private static FloatBuffer buffer = null;
//    private static void d_setBuffer(FloatBuffer buf){
//        buffer = buf;
//    }
//
//
//    private static FloatBuffer buffer = null;
//    private static void d_setBuffer(FloatBuffer buf){
//        buffer = buf;
//    }

}
