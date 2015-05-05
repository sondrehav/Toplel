package loaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class TextureLoader {

    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    private static String getFileExtension(String s){
        return s.split("\\.")[1];
    }

    public static Texture load(String path) throws IOException{
        if(textures.containsKey(path)){
            return textures.get(path);
        }
        String extension = getFileExtension(path).toUpperCase().trim();
        System.out.println("Loading imeage \""+path+"\".");
        Texture t = org.newdawn.slick.opengl.TextureLoader.getTexture(extension, ResourceLoader.getResourceAsStream(path));
        t.setTextureFilter(GL11.GL_NEAREST);
        textures.put(path, t);
        return t;
    }

    public static Texture get(String path){
        if(textures.containsKey(path)){
            return textures.get(path);
        }
        try{
            return getDefault();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Texture getDefault() throws IOException{
        return load("res/default.png");
    }

    public static Vector2f getDimensions(String path){
        Texture tex = get(path);
        return new Vector2f(tex.getWidth(), tex.getHeight());
    }

}
