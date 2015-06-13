    package com.toplel.ui.elements.elements;

import com.toplel.math.MyMat3;
import com.toplel.math.MyMath;
import com.toplel.math.MyVec2;
import com.toplel.ui.elements.MyElement;

public class MyLineElement extends MyElement {

    public MyVec2[] points;

    public int steps = 10;

    public MyLineElement(MyVec2[] points, MyVec2 position, MyVec2 size){
        super(position, size);
        this.points = points;
    }

    public void compile(){
        //TODO: Make
    }

    @Override
    public void render(MyMat3 viewMatrix){
        float smoothness = 1f / steps;
        MyVec2 currentPoint =  MyMath.quadraticBezier(points[0], points[1], points[2], -smoothness);
        MyVec2 lastPoint;
        for(float f = 0f; f < .5f; f += smoothness){
            MyVec2 mid = points[0].add(points[1]).scale(.5f);
            MyVec2 point = MyMath.quadraticBezier(points[0], mid, points[1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            MyVec2 direction = currentPoint.subtract(lastPoint);

            MyMat3 vw_matrix = MyMat3.getIdentity();
            vw_matrix = vw_matrix.translate(point);
            vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
        for (int i = 1; i < points.length - 1; i++) {
            MyVec2 mid_a = points[i].add(points[i-1]).scale(.5f);
            MyVec2 mid_b = points[i].add(points[i+1]).scale(.5f);
            for(float f = 0f; f < 1f; f += smoothness){
                MyVec2 point = MyMath.quadraticBezier(mid_a, points[i], mid_b, f);
                lastPoint = currentPoint;
                currentPoint = point;
                MyVec2 direction = currentPoint.subtract(lastPoint);

                MyMat3 vw_matrix = MyMat3.getIdentity();
                vw_matrix = vw_matrix.translate(point);
                vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
                for(MyElement e : children){
                    e.render(viewMatrix.mult(vw_matrix));
                }
            }
        }
        for(float f = .5f; f < 1f; f += smoothness){
            MyVec2 mid = points[points.length-1].add(points[points.length-2]).scale(.5f);
            MyVec2 point = MyMath.quadraticBezier(points[points.length-2], mid, points[points.length-1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            MyVec2 direction = currentPoint.subtract(lastPoint);
            MyMat3 vw_matrix = MyMat3.getIdentity();
            vw_matrix = vw_matrix.translate(point);
            vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
        for (int i = 0; i < points.length; i++) {
            MyMat3 vw_matrix = MyMat3.getIdentity();
            vw_matrix = vw_matrix.translate(points[i]);
            vw_matrix = vw_matrix.scale(new MyVec2(10f, 10f));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
    }

}
