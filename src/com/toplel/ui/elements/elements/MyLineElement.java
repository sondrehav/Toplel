    package com.toplel.ui.elements.elements;

import com.toplel.math.MyMath;
import com.toplel.ui.elements.MyElement;
import org.lwjgl.util.vector.Vector2f;

    public class MyLineElement extends MyElement {

    public Vector2f[] points;

    public int steps = 10;

    public MyLineElement(Vector2f[] points, Vector2f position, Vector2f size){
        super(position, size);
        this.points = points;
    }

    public void compile(){/*
        float smoothness = 1f / steps;
        Vector2f currentPoint =  MyMath.quadraticBezier(points[0], points[1], points[2], -smoothness);
        Vector2f lastPoint;
        for(float f = 0f; f < .5f; f += smoothness){
            Vector2f mid = points[0].add(points[1]).scale(.5f);
            Vector2f point = MyMath.quadraticBezier(points[0], mid, points[1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            Vector2f direction = currentPoint.subtract(lastPoint);

            MyMat3 vw_matrix = MyMat3.getIdentity();
            vw_matrix = vw_matrix.translate(point);
            vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
        for (int i = 1; i < points.length - 1; i++) {
            Vector2f mid_a = points[i].add(points[i-1]).scale(.5f);
            Vector2f mid_b = points[i].add(points[i+1]).scale(.5f);
            for(float f = 0f; f < 1f; f += smoothness){
                Vector2f point = MyMath.quadraticBezier(mid_a, points[i], mid_b, f);
                lastPoint = currentPoint;
                currentPoint = point;
                Vector2f direction = currentPoint.subtract(lastPoint);

                MyMat3 vw_matrix = MyMat3.getIdentity();
                vw_matrix = vw_matrix.translate(point);
                vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
                for(MyElement e : children){
                    e.render(viewMatrix.mult(vw_matrix));
                }
            }
        }
        for(float f = .5f; f < 1f; f += smoothness){
            Vector2f mid = points[points.length-1].add(points[points.length-2]).scale(.5f);
            Vector2f point = MyMath.quadraticBezier(points[points.length-2], mid, points[points.length-1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            Vector2f direction = currentPoint.subtract(lastPoint);
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
            vw_matrix = vw_matrix.scale(new Vector2f(10f, 10f));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
    }

    @Override
    public void render(){/*
        float smoothness = 1f / steps;
        Vector2f currentPoint =  MyMath.quadraticBezier(points[0], points[1], points[2], -smoothness);
        Vector2f lastPoint;
        for(float f = 0f; f < .5f; f += smoothness){
            Vector2f mid = points[0].add(points[1]).scale(.5f);
            Vector2f point = MyMath.quadraticBezier(points[0], mid, points[1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            Vector2f direction = currentPoint.subtract(lastPoint);

            MyMat3 vw_matrix = MyMat3.getIdentity();
            vw_matrix = vw_matrix.translate(point);
            vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }
        for (int i = 1; i < points.length - 1; i++) {
            Vector2f mid_a = points[i].add(points[i-1]).scale(.5f);
            Vector2f mid_b = points[i].add(points[i+1]).scale(.5f);
            for(float f = 0f; f < 1f; f += smoothness){
                Vector2f point = MyMath.quadraticBezier(mid_a, points[i], mid_b, f);
                lastPoint = currentPoint;
                currentPoint = point;
                Vector2f direction = currentPoint.subtract(lastPoint);

                MyMat3 vw_matrix = MyMat3.getIdentity();
                vw_matrix = vw_matrix.translate(point);
                vw_matrix = vw_matrix.rotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
                for(MyElement e : children){
                    e.render(viewMatrix.mult(vw_matrix));
                }
            }
        }
        for(float f = .5f; f < 1f; f += smoothness){
            Vector2f mid = points[points.length-1].add(points[points.length-2]).scale(.5f);
            Vector2f point = MyMath.quadraticBezier(points[points.length-2], mid, points[points.length-1], f);
            lastPoint = currentPoint;
            currentPoint = point;
            Vector2f direction = currentPoint.subtract(lastPoint);
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
            vw_matrix = vw_matrix.scale(new Vector2f(10f, 10f));
            for(MyElement e : children){
                e.render(viewMatrix.mult(vw_matrix));
            }
        }*/
    }

}
