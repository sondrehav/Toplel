package math;

import java.util.ArrayList;

public class MyLine {

    public static void main(String[] args) {

    }

    ArrayList<MyVec3> points = new ArrayList<>();

    public MyLine(){}

    public void addSegment(MyVec3 point){
        points.add(point);
    }

    public void subdivide(){
        for (int i = 0; i < points.size() - 1; i++) {

        }
    }

}
