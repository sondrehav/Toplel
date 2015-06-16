package com.toplel.ui.elements.elements;

import com.toplel.math.MyMatrix4f;
import com.toplel.ui.elements.MyElement;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class MyControlPoint extends MyElement {

    public static final int RESOLUTION = 36;

    private MyVertexObject vertexObject;
    private MyShaderProgram shaderProgram;
    private MyTexture texture;

    public MyControlPoint(Vector2f position, float inner, float outer, String img){
        super(position, new Vector2f(outer, outer));
        float[][] f = new float[2][36*2*2+4];
        float uv_length = outer - inner;
        for (int i = 0; i < RESOLUTION*2*2; i+=4) {
            float sc = 2f * (float)(((float)i/(RESOLUTION*2*2))*Math.PI);
            float uv_sc = sc * uv_length;
            float sin = (float) Math.sin(sc);
            float cos = (float) Math.cos(sc);
            f[0][i] = (cos * inner / outer + 1f) * .5f;
            f[0][i+1] = (sin * inner / outer + 1f) * .5f;
            f[0][i+2] = (cos + 1f) * .5f;
            f[0][i+3] = (sin + 1f) * .5f;
            int uuuv = (int)((float)(i%8)/4f);
            f[1][i] = uuuv;
            f[1][i+1] = 1- uuuv;
            f[1][i+2] = uuuv;
            f[1][i+3] = 1-uuuv;
        }
        f[0][f[0].length-4] = f[0][0];
        f[0][f[0].length-3] = f[0][1];
        f[0][f[0].length-2] = f[0][2];
        f[0][f[0].length-1] = f[0][3];
        f[1][f[0].length-4] = f[1][0];
        f[1][f[0].length-3] = f[1][1];
        f[1][f[0].length-2] = f[1][2];
        f[1][f[0].length-1] = f[1][3];
        vertexObject = new MyVertexObject(f, null, GL11.GL_TRIANGLE_STRIP);
        texture = MyTexture.addTexture(img);
        shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");
    }

    public void render(){
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", getContext().getViewProjection());
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        shaderProgram.setUniform1f("alpha", 1f);
        texture.bind();
        vertexObject.bind();
        vertexObject.draw();
        vertexObject.unbind();
        texture.unbind();
        shaderProgram.unbind();
    }

}
