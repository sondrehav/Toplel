package loaders;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;

public class MyTextureLoader {

    private static HashMap<String, Texture> textures = new HashMap<>();

    private static String getFileExtension(String s){
        return s.split("\\.")[1].toLowerCase().trim();
    }

    public static Texture load(String path) throws IOException{
        if(textures.containsKey(path)){
            return textures.get(path);
        }
        String extension = getFileExtension(path);
        System.out.println("Loading image \""+path+"\".");
        Texture t = TextureLoader.getTexture(extension, ResourceLoader.getResourceAsStream(path));
        t.setTextureFilter(GL11.GL_NEAREST);
        textures.put(path, t);
        return t;
    }

}
