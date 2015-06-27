package com.toplel.test;

import com.toplel.util.objects.MyTexture;
import org.lwjgl.util.vector.Vector2f;

public class Tileset {

    private MyTexture tileImage;
    public final int tileWidth, tileHeight, startIndex;
    public final String name;

    public final int tilesPerRow;
    public final int tilesPerCol;

    public Tileset(MyTexture tileImage, int tileWidth, int tileHeight, int startIndex, String name) {

        this.tileImage = tileImage;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.startIndex = startIndex;
        this.name = name;

        this.tilesPerRow = Math.floorDiv(tileImage.WIDTH, tileWidth);
        this.tilesPerCol = Math.floorDiv(tileImage.HEIGHT, tileHeight);

    }

    public boolean inRange(int id){
        if(id-startIndex>=0&&id-startIndex<tilesPerCol*tilesPerRow) return true;
        return false;
    }

    public Region getUVRegion(int id){
        int x = ((id - startIndex) % tilesPerRow);
        int y = Math.floorDiv((id - startIndex), tilesPerRow);
        float x0 = (float) x / tilesPerRow;
        float y0 = (float) y / tilesPerCol;
        float x1 = (float) (x+1) / tilesPerRow;
        float y1 = (float) (y+1) / tilesPerCol;
        return new Region(new Vector2f(x0, y0), new Vector2f(x1, y1));
    }

    public MyTexture getTexture(){
        return tileImage;
    }

    public void bind(){
        tileImage.bind();
    }

    public void unbind(){
        tileImage.unbind();
    }

}
