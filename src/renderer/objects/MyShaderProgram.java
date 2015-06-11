package renderer.objects;

import loaders.MySimpleFileReader;
import math.MyMat3;
import math.MyRegion;
import math.MyVec2;
import math.MyVec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import util.MyHelpers;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class MyShaderProgram {

    private static MyShaderProgram bound = null;
    private static HashMap<Program, MyShaderProgram> shaderPrograms = new HashMap<>();

    public static MyShaderProgram addShaderProgram(String vs, String fs) throws IOException {

        if(shaderPrograms.containsKey(new Program(vs, fs))){
            return shaderPrograms.get(new Program(vs, fs));
        }

        int vShaderId = loadShader(vs);
        int fShaderId = loadShader(fs);

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
        System.out.println("Loading shader \"" + filename + "\".");

        int type;
        switch (MyHelpers.getFileExtension(filename)){
            case "vs":
                type = GL20.GL_VERTEX_SHADER;
                break;
            case "fs":
                type = GL20.GL_FRAGMENT_SHADER;
                break;
            default:
                System.err.println("Not a valid extension: " + filename);
                return 0;
        }

        int shaderID = GL20.glCreateShader(type);
        String file = MySimpleFileReader.read(filename);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader \"" + filename + "\".");
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 1024));
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
            System.err.println("Uniform '" + name + "' does not exist in shader '" + this.vs + "'.");
        }
        return handle;
    }

    public void setUniform1i(String name, int value){
        if(bound!=this){
            System.err.println("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value){
        if(bound!=this){
            System.err.println("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float value1, float value2){
        if(bound!=this){
            System.err.println("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform2f(getUniform(name), value1, value2);
    }

    public void setUniform3f(String name, float value1, float value2, float value3){
        if(bound!=this){
            System.err.println("ShaderProgram not bound!");
            return;
        }
        GL20.glUniform3f(getUniform(name), value1, value2, value3);
    }

    public void setVec3(String name, MyVec3 value){
        setUniform3f(name, value.vector[0], value.vector[1], value.vector[2]);
    }

    public void setVec2(String name, MyVec2 value){
        setUniform2f(name, value.vector[0], value.vector[1]);
    }

    public void setRegion(String name, MyRegion region){
        GL20.glUniform4f(getUniform(name), region.x0, region.y0, region.x1, region.y1);
    }

    public void setUniformMat3(String name, MyMat3 matrix){
        if(bound!=this){
            System.err.println("ShaderProgram not bound!");
            return;
        }
        FloatBuffer buf = MyMat3.store(matrix);
        buf.flip();
        GL20.glUniformMatrix3(getUniform(name), false, buf);
    }

    public void bind(){
        if(bound != null){
            System.err.println("Shader not unbound correctly: " + bound.toString());
            bound.unbind();
        }
        GL20.glUseProgram(this.handle);
        bound = this;
    }

    public void unbind(){
        if(bound != this) return;
        GL20.glUseProgram(0);
        bound = null;
    }

}