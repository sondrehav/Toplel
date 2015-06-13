package com.toplel.ui.elements.elements;

import com.toplel.math.MyMat3;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.MyElement;

public class MyButton extends MyElement {

    public String label;

    public MyButton(MyVec2 pos, MyVec2 size, String label){
        super(pos, size);
        this.label = label;
        addChild(new MyLabel(pos, size, "res/font.json"));
    }

    @Override
    public void render(MyMat3 pr){

    }

}
