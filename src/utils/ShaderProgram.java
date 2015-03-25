package utils;

import loaders.ShaderLoader;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.util.HashMap;

public class ShaderProgram {

    private String vs, fs;

    private static HashMap<ShaderProgram, Shader> shaderPrograms = new HashMap<ShaderProgram, Shader>();

    private static final ShaderProgram setUpShaders(String vs, String fs) throws IOException, IllegalArgumentException {

        ShaderProgram shader = new ShaderProgram(vs, fs);

        if(shaderPrograms.containsKey(shader)){
            return shader;
        }

        int vShaderId = ShaderLoader.loadShader(vs, GL20.GL_VERTEX_SHADER);
        int fShaderId = ShaderLoader.loadShader(fs, GL20.GL_FRAGMENT_SHADER);

        int shaderId = GL20.glCreateProgram();
        GL20.glAttachShader(shaderId, vShaderId);
        GL20.glAttachShader(shaderId, fShaderId);

        GL20.glBindAttribLocation(shaderId, 0, "in_Position");
        GL20.glBindAttribLocation(shaderId, 1, "in_TextureCoord");

        GL20.glLinkProgram(shaderId);
        GL20.glValidateProgram(shaderId);
        GL20.glValidateProgram(shaderId);

        Shader sh = new Shader();
        sh.modelMatLoc = GL20.glGetUniformLocation(shaderId, "projectionMatrix");
        sh.projMatLoc = GL20.glGetUniformLocation(shaderId, "viewMatrix");
        sh.viewMatLoc = GL20.glGetUniformLocation(shaderId, "modelMatrix");
        sh.programID = shaderId;

        shaderPrograms.put(shader, sh);

        return shader;
    }

    public static ShaderProgram addShader(String vs, String fs) throws IOException {
        return setUpShaders(vs, fs);
    }


    private ShaderProgram(String vs, String fs){
        this.vs = vs;
        this.fs = fs;
    }

    private static class Shader {
        int modelMatLoc;
        int projMatLoc;
        int viewMatLoc;
        int programID;
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
        ShaderProgram other = (ShaderProgram) o;
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

    public int getViewMatLoc(){
        return shaderPrograms.get(this).viewMatLoc;
    }
    public int getModelMatLoc(){
        return shaderPrograms.get(this).modelMatLoc;
    }
    public int getProjMatLoc(){
        return shaderPrograms.get(this).projMatLoc;
    }

    public void bind(){
        try{
            GL20.glUseProgram(shaderPrograms.get(this).programID);
        } catch (Exception e){}
    }

}