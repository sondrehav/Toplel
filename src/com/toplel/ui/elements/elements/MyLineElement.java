package com.toplel.ui.elements.elements;

import com.toplel.math.MyLine;
import com.toplel.math.MyMat3;
import com.toplel.math.MyMath;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.MyElement;

public class MyLineElement extends MyElement {

    MyVec2[] points;

    float smoothness = .5f;

    public MyLineElement(MyVec2[] points, MyVec2 position, MyVec2 size){
        super(position, size);
        this.points = points;
    }

    @Override
    public void render(MyMat3 viewMatrix){
        MyVec2 point = null;
        MyVec2 last_point = null;
        for (int i = 0; i < points.length - 2; i++) {
            for(float f = 0f; f < 1f; f += smoothness){
                last_point = point;
                point = MyMath.quadraticBezier(points[i], points[i+1], points[i+2], f);
                MyVec2 direction = last_point == null ? new MyVec2(1f, 0f) : point.subtract(last_point);
                MyMat3 vw_matrix = MyMat3.getIdentity();
                vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
                vw_matrix = vw_matrix.translate(point);
                System.out.println(viewMatrix.mult(vw_matrix));
                for(MyElement e : children){
                    e.render(viewMatrix.mult(vw_matrix));
                }
            }
        }
    }

}
