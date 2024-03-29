package com.toplel.util.objects;

import com.toplel.util.Console;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MyVertexObject {

    public static final MyVertexObject SQUARE = createSquare(0f,0f,1f,1f);

    public static MyVertexObject createSquare(float x0, float y0, float x1, float y1){
        return new MyVertexObject(new float[][]{
                {x0,y0,x1,y0,x1,y1,x0,y1},
                {1f,1f,0f,1f,0f,0f,1f,0f}
        }, new int[]{
                0, 1, 2, 2, 3, 0
        });
    }

    public static MyVertexObject createSquare(Vector2f a, Vector2f b){
        float x0 = a.x; float y0 = a.y;
        float x1 = b.x; float y1 = b.y;
        return new MyVertexObject(new float[][]{
                {x0,y0,x1,y0,x1,y1,x0,y1},
                {0f,1f,1f,1f,1f,0f,0f,0f}
        }, new int[]{
                0, 1, 2, 2, 3, 0
        });
    }

    public static final int VERTEX_ATTRIB = 0, TEXCOORD_ATTRIB = 1;
    private static MyVertexObject bound = null;

    public final int vaoid;
    public final int vboid;
    public final int count;
    public final int drawType;

    public final float leftBound;
    public final float rightBound;
    public final float topBound;
    public final float bottomBound;

    private IntBuffer indicesBuffer = null;

    public MyVertexObject(float[][] vertexAndTC){
        this(vertexAndTC, null, GL11.GL_TRIANGLES);
    }

    public MyVertexObject(float[][] vertexAndTC, int[] indices, int drawType){
        this.drawType = drawType;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertexAndTC[0].length * 2);
        float l = Float.MAX_VALUE, r = Float.MIN_VALUE, t = Float.MIN_VALUE, b = Float.MAX_VALUE;
        for (int i = 0; i < vertexAndTC[0].length; i+=2) {
            buffer.put(vertexAndTC[0][i]);      // x
            buffer.put(vertexAndTC[0][i+1]);    // y
            buffer.put(vertexAndTC[1][i]);      // u
            buffer.put(vertexAndTC[1][i+1]);    // v
            if(vertexAndTC[0][i]<l) l = vertexAndTC[0][i];
            if(vertexAndTC[0][i]>r) r = vertexAndTC[0][i];
            if(vertexAndTC[0][i+1]<b) b = vertexAndTC[0][i+1];
            if(vertexAndTC[0][i+1]>t) t = vertexAndTC[0][i+1];
        }
        buffer.flip();
        this.topBound = t; this.bottomBound = b;
        this.leftBound = l; this.rightBound = r;

        vaoid = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoid);

        vboid = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(VERTEX_ATTRIB, 2, GL11.GL_FLOAT, false, 4*4, 0);
        GL20.glEnableVertexAttribArray(VERTEX_ATTRIB);
        GL20.glVertexAttribPointer(TEXCOORD_ATTRIB, 2, GL11.GL_FLOAT, false, 4*4, 4*2);
        GL20.glEnableVertexAttribArray(TEXCOORD_ATTRIB);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        if(indices==null){
            switch (drawType){
                case GL11.GL_TRIANGLE_STRIP:
                    count = (int)((float)vertexAndTC[0].length/2f);
                    break;
                default:
                    count = 6*(int)((float)vertexAndTC[0].length/2f);
                    break;
            }
            return;
        } else {
            count = indices.length;
            indicesBuffer = BufferUtils.createIntBuffer(indices.length);
            indicesBuffer.put(indices);
            indicesBuffer.flip();
        }

        GL30.glBindVertexArray(0);

    }

    public MyVertexObject(float[][] vertexAndTC, int[] indices){
        this(vertexAndTC, indices, GL11.GL_TRIANGLES);
    }

    public void bind(){
        if(bound != null){
            Console.printErr("VertexObject not unbound correctly: " + bound.toString());
            bound.unbind();
        }
        GL30.glBindVertexArray(vaoid);
        bound = this;
    }

    public void unbind(){
        if(bound != this){
            Console.printErr("VertexObject was not bound to begin with: " + this.toString());
            return;
        }
        GL30.glBindVertexArray(0);
        bound = null;
    }

    private static int drawCalls = 0;
    private static int drawCallsCount = 0;

    public static void resetDrawCallCounter(){
        drawCalls = drawCallsCount;
        drawCallsCount = 0;
    }

    public static int getDrawCallCount(){
        return drawCalls;
    }

    public void draw(){
        if(bound != this){
            Console.printErr("VertexObject not bound: " + this.toString());
            return;
        }
        if(indicesBuffer!=null) GL11.glDrawElements(drawType, indicesBuffer);
        else GL11.glDrawArrays(drawType, 0, count);
        drawCallsCount++;
    }

}
