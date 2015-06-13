package com.toplel.ui.elements;

import com.toplel.math.MyRegion;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;

public class MyElementFrame {

    MyTexture texture;

    MyRegion region;

    MyVertexObject vertexObject;

    float size;

    public MyElementFrame(String path){
        texture = MyTexture.addTexture(path);

    }

    public void setRegion(MyRegion region){

    }

}
