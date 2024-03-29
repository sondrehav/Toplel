package com.toplel.util.objects;

import com.toplel.util.Console;
import com.toplel.util.MyHelpers;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class MyShaderProgram {

    public static final String DEFAULT_VERTEX_SHADER = "res/shader/default.vs";
    public static final String DEFAULT_FRAGMENT_SHADER = "res/shader/default.fs";

    private static MyShaderProgram bound = null;
    private static HashMap<Program, MyShaderProgram> shaderPrograms = new HashMap<>();

    private static boolean enabled = true;

    public static MyShaderProgram addShaderProgram(String vs, String fs) {

        if(shaderPrograms.containsKey(new Program(vs, fs))){
            return shaderPrograms.get(new Program(vs, fs));
        }

        int vShaderId = -1;
        int fShaderId = -1;
        try{
            vShaderId = loadShader(vs);
            fShaderId = loadShader(fs);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }

        int shaderId = GL20.glCreateProgram();

        GL20.glAttachShader(shaderId, vShaderId);
        GL20.glAttachShader(shaderId, fShaderId);

        GL20.glLinkProgram(shaderId);
        GL20.glValidateProgram(shaderId);

        MyShaderProgram shader = new MyShaderProgram(vs, fs, shaderId);

        shaderPrograms.put(new Program(vs, fs), shader);

        return shader;
    }

    private static HashMap<String, Integer> loadedShaders = new HashMap<>();
    private static int loadShader(String filename) throws IOException{

        if (loadedShaders.containsKey(filename)) {
            return loadedShaders.get(filename);
        }
        Console.printLn("Loading shader \"" + filename + "\".");

        int type;
        switch (MyHelpers.getFileExtension(filename)){
            case "vs":
                type = GL20.GL_VERTEX_SHADER;
                break;
            case "fs":
                type = GL20.GL_FRAGMENT_SHADER;
                break;
            default:
                Console.printErr("Not a valid extension: " + filename);
                return 0;
        }

        int shaderID = GL20.glCreateShader(type);
        String file = MySimpleFileReader.read(filename);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            Console.printErr("Could not compile shader \"" + filename + "\".");
            Console.printErr(GL20.glGetShaderInfoLog(shaderID, 1024));
            System.exit(10);
            return 0;
        }
        loadedShaders.put(filename, shaderID);
        return shaderID;
    }

    private static class Program{
        final String vs, fs;

        public Program(String vs, String fs) {
            this.vs = vs;
            this.fs = fs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Program program = (Program) o;

            if (fs != null ? !fs.equals(program.fs) : program.fs != null) return false;
            if (vs != null ? !vs.equals(program.vs) : program.vs != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = vs != null ? vs.hashCode() : 0;
            result = 31 * result + (fs != null ? fs.hashCode() : 0);
            return result;
        }
    }

    //--------------------------- NON-STATIC ---------------------------

    public final String vs, fs;
    public final int handle;

    private MyShaderProgram(String vs, String fs, int handle){
        this.vs = vs;
        this.fs = fs;
        this.handle = handle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyShaderProgram that = (MyShaderProgram) o;

        if (handle != that.handle) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return handle;
    }

    private HashMap<String, Integer> uniforms = new HashMap<>();

    public int getUniform(String name){
        if(!uniforms.containsKey(name)){
            uniforms.put(name, GL20.glGetUniformLocation(this.handle, name));
        }
        int handle = uniforms.get(name);
        if(handle == -1){
            Console.printErr("Uniform '" + name + "' does not exist in shader '" + this.vs + "'.");
        }
        return handle;
    }

    public void setUniform1i(String name, int value){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform1f(getUniform(name), value);
    }

    public void setRegion(String name, Vector2f vec_a, Vector2f vec_b){
        setUniform4f(name, vec_a.x, vec_a.y,
                vec_b.x, vec_b.y);
    }

    public void setUniform2f(String name, float value1, float value2){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform2f(getUniform(name), value1, value2);
    }

    public void setUniform3f(String name, float value1, float value2, float value3){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform3f(getUniform(name), value1, value2, value3);
    }

    public void setUniform4f(String name, float value1, float value2, float value3, float value4){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform4f(getUniform(name), value1, value2, value3, value4);
    }

    public void setVec3(String name, Vector3f value){
        setUniform3f(name, value.x, value.y, value.z);
    }

    public void setVec2(String name, Vector2f value){
        setUniform2f(name, value.x, value.y);
    }

    public void setUniformMat3(String name, Matrix3f matrix){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        FloatBuffer buf = BufferUtils.createFloatBuffer(9);
        matrix.store(buf);
        buf.flip();
        GL20.glUniformMatrix3(getUniform(name), false, buf);
    }

    public void setUniformMat4(String name, Matrix4f matrix){
        if(bound!=this){
            Console.printErr("ShaderProgram not bound!");
            return;
        }
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        matrix.store(buf);
        buf.flip();
        GL20.glUniformMatrix4(getUniform(name), false, buf);
    }

    public void bind(){
        if(!enabled){
            GL11.glColor3f(1f, 1f, 1f);
            return;
        }
        if(bound != null){
            Console.printErr("Shader not unbound correctly: " + bound.toString());
            bound.unbind();
        }
        GL20.glUseProgram(this.handle);
        bound = this;
    }

    public void unbind(){
        if(!enabled) return;
        if(bound != this) return;
        GL20.glUseProgram(0);
        bound = null;
    }

    public static void enableShaders(boolean shaders){
        enabled = shaders;
    }

}