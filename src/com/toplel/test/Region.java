package com.toplel.test;

import org.lwjgl.util.vector.Vector2f;

public class Region{

    Vector2f regionFrom;
    Vector2f regionTo;
    public Region(Vector2f regionFrom, Vector2f regionTo) {
        this.regionFrom = regionFrom;
        this.regionTo = regionTo;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionFrom=" + regionFrom +
                ", regionTo=" + regionTo +
                '}';
    }
}