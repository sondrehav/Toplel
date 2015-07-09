package com.toplel.test;

import com.toplel.util.objects.MyTexture;
import org.lwjgl.util.vector.Vector2f;

public class Tileset {

    private MyTexture tileImage;
    public final int tileWidth, tileHeight;
    public final String name;

//    private final int internalWidth;
//    private final int internalHeight;

    private int tilesPerRow;
    private int tilesPerCol;

    public int getTilesPerRow() {
        return tilesPerRow;
    }

    public int getTilesPerCol() {
        return tilesPerCol;
    }

    public Tileset(MyTexture tileImage, int tileWidth, int tileHeight) {
        this(tileImage, tileWidth, tileHeight, null);
    }

    public Tileset(MyTexture tileImage, int tileWidth, int tileHeight, String name) {

        this.tileImage = tileImage;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.name = name;

        setRowCol();

    }

    private void setRowCol(){
        this.tilesPerRow = Math.floorDiv(tileImage.WIDTH, tileWidth);
        this.tilesPerCol = Math.floorDiv(tileImage.HEIGHT, tileHeight);
    }

    public int getMax(){
        return tilesPerCol * tilesPerRow;
    }

    public Region getUVRegion(int id){
        if(id>=getMax()) return null;
        int x = (id % tilesPerRow);
        int y = Math.floorDiv(id, tilesPerRow);
        float x0 = ((float) x / tilesPerRow) * ((float)tilesPerRow*(float)tileWidth/(float)tileImage.WIDTH);
        float y0 = (((float) y / tilesPerCol) * ((float)tilesPerCol*(float)tileHeight/(float)tileImage.HEIGHT));
        float x1 = ((float) (x+1) / tilesPerRow) * ((float)tilesPerRow*(float)tileWidth/(float)tileImage.WIDTH);
        float y1 = ((float) (y+1) / tilesPerCol) * ((float)tilesPerCol*(float)tileHeight/(float)tileImage.HEIGHT);
        return new Region(x0, y0, x1, y1);
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
