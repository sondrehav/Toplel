    package loaders;

/**
 * Created by Sondre_ on 22.03.2015.
 */

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

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


    public static final void destroy(String path) throws IllegalArgumentException{
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

    public static int loadShader(String filename, int type) throws IOException{
        if (loadedShaders.containsKey(filename)) {
            return loadedShaders.get(filename);
        }
        System.out.println("Loading shader \"" + filename + "\".");
        int shaderID;

        String file = MySimpleFileReader.read(filename);

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, file);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader \"" + filename + "\".");
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 1024));
            System.exit(-1);
        }
        loadedShaders.put(filename, shaderID);
        return shaderID;
    }

    private static class Shader{

        final int id;


        Shader(int id){
            this.id=id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Shader shader = (Shader) o;

            if (id != shader.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

}
