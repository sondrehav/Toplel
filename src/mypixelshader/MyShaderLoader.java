package mypixelshader;

/**
* Created by Sondre_ on 22.03.2015.
*/

import old.loaders.SimpleFileReader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class MyShaderLoader {

    private static void _dest(String path) throws IllegalArgumentException{
        if(!loadedShaders.containsKey(path)){
            throw new IllegalArgumentException("No shader loaded with path \'" + path + "\'.");
        }
        GL20.glUseProgram(0);
        GL20.glDeleteShader(loadedShaders.get(path));

    }


    public static void destroy(String path) throws IllegalArgumentException{
        _dest(path);
        loadedShaders.remove(path);
    }

    public static void destroyAll(){
        for(Map.Entry<String, Integer> e : loadedShaders.entrySet()){
            _dest(e.getKey());
        }
        loadedShaders.clear();
    }

    private static HashMap<String, Integer> loadedShaders = new HashMap<String, Integer>();
//    private static HashMap<String, String> shaderSource = new HashMap<String, String>();

    public static void reload(String path) throws IOException{
        int shaderID = loadedShaders.get(path.replace("\\", "/"));
        System.out.println("Reloading shader \"" + path.replace("\\", "/") + "\".");
        String file = SimpleFileReader.read(path);
//        shaderSource.put(path, file);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader \"" + path + "\".");
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 1024));
//            System.exit(-1);
        }
        loadedShaders.put(path, shaderID);
    }

    static MyPixelShaderSource shaderSource = new MyPixelShaderSource();
    public static int loadShader(String filename, int type) throws IOException{
        if (loadedShaders.containsKey(filename)) {
            return loadedShaders.get(filename);
        }
        shaderSource.addFileListener(new File(filename));
        System.out.println("Loading shader \"" + filename + "\".");
        int shaderID;
        String file = null;
        if(type==GL20.GL_FRAGMENT_SHADER){
            file = SimpleFileReader.read(filename);
        } else {
            file = SimpleFileReader.read(filename);
        }

//        Matcher matcher = Pattern.compile("(uniform\\s+)(float|mat4|int|vec2|vec3)\\s+(\\S*)(?=;)").matcher(file);
//        while(matcher.find()){
//            System.out.println(matcher.group(2) + ": " + matcher.group(3));
//        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader \"" + filename + "\".");
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 1024*4));
            System.exit(-1);
        }
        loadedShaders.put(filename, shaderID);
        return shaderID;
    }

}
