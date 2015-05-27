package loaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;
import utils.renderer.Sprite;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sondre_ on 22.03.2015.
 */
public class TextureLoader {

    private static HashMap<Sprite, Texture> textures = new HashMap<Sprite, Texture>();

    private static String getFileExtension(String s){
        return s.split("\\.")[1];
    }

    public static Sprite load(String path) throws IOException{
        Sprite sprite = new Sprite(path);
        if(textures.containsKey(sprite)){
            return sprite;
        }
        String extension = getFileExtension(path).toUpperCase().trim();
        System.out.println("Loading imeage \""+path+"\".");
        Texture t = org.newdawn.slick.opengl.TextureLoader.getTexture(extension, ResourceLoader.getResourceAsStream(path));
        t.setTextureFilter(GL11.GL_NEAREST);
        textures.put(sprite, t);
        return sprite;
    }

    public static Sprite getDefault() throws IOException{
        return load("res/default.png");
    }

    public static Vector2f getDimensions(Sprite path){
        Texture tex = textures.get(path);
        return new Vector2f(tex.getWidth(), tex.getHeight());
    }

    public static void bind(Sprite sprite){
        textures.get(sprite).bind();
    }

}
