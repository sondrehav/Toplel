package mypixelshader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class MyShaderProgram {

    private String vs, fs;

    private static HashMap<MyShaderProgram, Integer> shaderPrograms = new HashMap<>();

    private static final MyShaderProgram setUpShaders(String vs, String fs) throws IOException {

        MyShaderProgram shader = new MyShaderProgram(vs, fs);

        if(shaderPrograms.containsKey(shader)){
            return shader;
        }

        int vShaderId = MyShaderLoader.loadShader(vs, GL20.GL_VERTEX_SHADER);
        int fShaderId = MyShaderLoader.loadShader(fs, GL20.GL_FRAGMENT_SHADER);

        int shaderId = GL20.glCreateProgram();

        GL20.glAttachShader(shaderId, vShaderId);
        GL20.glAttachShader(shaderId, fShaderId);

        GL20.glLinkProgram(shaderId);
        GL20.glValidateProgram(shaderId);

        shaderPrograms.put(shader, shaderId);

        return shader;
    }

    public static void forceRefresh(MyShaderProgram p){
        int id = shaderPrograms.get(p);
        int vShaderId, fShaderId;
        try{
            vShaderId = MyShaderLoader.loadShader(p.vs, GL20.GL_VERTEX_SHADER);
            fShaderId = MyShaderLoader.loadShader(p.fs, GL20.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        GL20.glAttachShader(id, vShaderId);
        GL20.glAttachShader(id, fShaderId);
        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);
    }

    public static MyShaderProgram addShader(String vs, String fs) throws IOException {
        return setUpShaders(vs, fs);
    }

    private MyShaderProgram(String vs, String fs){
        this.vs = vs;
        this.fs = fs;
    }

    @Override
    public boolean equals(Object o){
        if(o==null){
            return false;
        }
        if(o==this){
            return true;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        MyShaderProgram other = (MyShaderProgram) o;
        if(other.fs.contentEquals(this.vs)&&other.vs.contentEquals(this.vs)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vs == null) ? 0 : vs.hashCode());
        result = prime * result + ((fs == null) ? 0 : fs.hashCode());
        return result;
    }

    public void bind(){
        GL20.glUseProgram(shaderPrograms.get(this));
    }

    public void unbind(){
        GL20.glUseProgram(0);
    }

    private HashMap<String, Integer> uniforms = new HashMap<>();

    public int getUniform(String name){
        if(!uniforms.containsKey(name)){
            uniforms.put(name, GL20.glGetUniformLocation(shaderPrograms.get(this), name));
        }
        return uniforms.get(name);
    }

    public void setUniform1i(String name, int value){
        this.bind();
        GL20.glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value){
        this.bind();
        GL20.glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float value1, float value2){
        this.bind();
        GL20.glUniform2f(getUniform(name), value1, value2);
    }

    public void setUniform3f(String name, float value1, float value2, float value3){
        this.bind();
        GL20.glUniform3f(getUniform(name), value1, value2, value3);
    }

    private static FloatBuffer buf = BufferUtils.createFloatBuffer(16);
    public void setUniformMat4(String name, Matrix4f matrix){
        this.bind();
        matrix.store(buf);
        buf.flip();
        GL20.glUniformMatrix4(getUniform(name), false, buf);
    }

}