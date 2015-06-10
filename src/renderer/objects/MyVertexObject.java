package renderer.objects;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MyVertexObject {
    
    public static final MyVertexObject SQUARE = new MyVertexObject(new float[][]{
            {0f,0f,1f,0f,1f,1f,0f,1f},
            {0f,0f,1f,0f,1f,1f,0f,1f}
    }, new int[]{
            0, 1, 2, 2, 1, 3
    });
    
    public static MyVertexObject createSquare(float x0, float y0, float x1, float y1){
        return new MyVertexObject(new float[][]{
                {x0,y0,x1,y0,x1,y1,x0,y1},
                {0f,0f,1f,0f,1f,1f,0f,1f}
        }, new int[]{
                0, 1, 2, 2, 1, 3
        });
    }

    public static final int VERTEX_ATTRIB = 0, TEXCOORD_ATTRIB = 1;
    private static MyVertexObject bound = null;

    public final int vaoid;
    public final int vboid;
    public final int ibo;
    public final int count;

    public MyVertexObject(float[][] vertexAndTC, int[] indices){

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertexAndTC[0].length * 2);
        for (int i = 0; i < vertexAndTC.length; i+=2) {
            buffer.put(vertexAndTC[0][i]);
            buffer.put(vertexAndTC[0][i+1]);
            buffer.put(vertexAndTC[1][i]);
            buffer.put(vertexAndTC[1][i+1]);
        }
        buffer.flip();

        vaoid = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoid);

        vboid = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboid);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(VERTEX_ATTRIB, 2, GL11.GL_FLOAT, false, 2, 0);
        GL20.glVertexAttribPointer(TEXCOORD_ATTRIB, 2, GL11.GL_FLOAT, false, 2, 2);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        ibo = GL15.glGenBuffers();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        count = indicesBuffer.capacity();

    }

    public void bind(){
        if(bound != null){
            System.err.println("VertexObject not unbound correctly: " + bound.toString());
            bound.unbind();
        }
        GL30.glBindVertexArray(vaoid);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        bound = this;
    }

    public void unbind(){
        if(bound != this){
            System.err.println("VertexObject was not bound to begin with: " + this.toString());
            return;
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
        bound = null;
    }

    public void draw(){
        if(bound != this){
            System.err.println("VertexObject not bound: "+ this.toString());
            return;
        }
        GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_BYTE, 0);
    }

}
