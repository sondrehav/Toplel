    package loaders;

/**
 * Created by Sondre_ on 22.03.2015.
 */

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import utils.SimpleFileReader;

import java.io.IOException;
import java.util.HashMap;

public abstract class ShaderLoader {

    public static final void destroy(String path) throws IllegalArgumentException{
        if(!loadedShaders.containsKey(path)){
            throw new IllegalArgumentException("No shader loaded with path \'" + path + "\'.");
        }
        GL20.glUseProgram(0);
        GL20.glDeleteShader(loadedShaders.get(path));
        loadedShaders.remove(path);
    }

    private static HashMap<String, Integer> loadedShaders = new HashMap<String, Integer>();

    public static int loadShader(String filename, int type) throws IOException{
        if (loadedShaders.containsKey(filename)) {
            return loadedShaders.get(filename);
        }
        System.out.println("Loading shader \"" + filename + "\".");
        int shaderID;

        String file = SimpleFileReader.read(filename);

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader \"" + filename + "\".");
            System.exit(-1);
        }
        loadedShaders.put(filename, shaderID);
        return shaderID;
    }

}
