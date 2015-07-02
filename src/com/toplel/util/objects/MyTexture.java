package com.toplel.util.objects;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.HashMap;

public class MyTexture {

    public static final String DEFAULT_IMAGE = "res/img/defaultImage.png";

    public final String PATH;
    public final int HANDLE;
    public final int WIDTH, HEIGHT;

    public final static int FILTER = GL11.GL_NEAREST;

    private MyTexture(String path, int handle, int width, int height){
        this.PATH = path;
        this.HANDLE = handle;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyTexture myTexture = (MyTexture) o;

        if (PATH != null ? !PATH.equals(myTexture.PATH) : myTexture.PATH != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return PATH != null ? PATH.hashCode() : 0;
    }

    public void bind(){
        if(bound != null){
            System.err.println("Texture not unbound correctly: " + bound.toString());
            bound.unbind();
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.HANDLE);
        bound = this;
    }

    public void unbind(){
        if(bound != this) return;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        bound = null;
    }

    //------------------------------------ STATIC ------------------------------------

    private static MyTexture bound = null;
    private static HashMap<String, MyTexture> textures = new HashMap<>();

    public static MyTexture addTexture(String path){
        if(textures.containsKey(path)){
            return textures.get(path);
        }
        System.out.println("Loading image \""+path+"\".");
        int[] pixels = null;
        int width = 0, height = 0;
        try{
            BufferedImage image = ImageIO.read(new File(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] data = new int[width * height];
        for (int y = 0; y < data.length; y++) {
            int a = (pixels[y] & 0xff000000) >> 24;
            int r = (pixels[y] & 0xff0000) >> 16;
            int g = (pixels[y] & 0xff00) >> 8;
            int b = (pixels[y] & 0xff);
            data[y] = a << 24 | b << 16 | g << 8 | r;
        }

        IntBuffer pixelBuffer = BufferUtils.createIntBuffer(pixels.length);
        pixelBuffer.put(data).flip();
        int res = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, res);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, FILTER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, FILTER);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        MyTexture tex = new MyTexture(path, res, width, height);
        textures.put(path, tex);
        return tex;
    }


}
