package oldold.old.utils.geom;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Region {

    // stored in a clockwise rotation
    public Vector2f pointA, pointB, pointC, pointD;

    private Region(){

    }

    public static boolean intersects(Vector2f world_pos_a, Vector2f world_size_a, float world_rotation_a,
                                     Vector2f world_pos_b, Vector2f world_size_b, float world_rotation_b){
        Region _a = getWorldPoints(world_pos_a, world_size_a, world_rotation_a);
        Region _b = getWorldPoints(world_pos_b, world_size_b, world_rotation_b);
        Vector2f axis_a1 = new Vector2f(_a.pointA.x - _a.pointD.x,
                _a.pointA.y - _a.pointD.x);
        Vector2f axis_a2 = new Vector2f(_a.pointA.x - _a.pointB.x,
                _a.pointA.y - _a.pointB.x);
        Vector2f axis_b1 = new Vector2f(_b.pointA.x - _b.pointD.x,
                _b.pointA.y - _b.pointD.x);
        Vector2f axis_b2 = new Vector2f(_b.pointA.x - _b.pointB.x,
                _b.pointA.y - _b.pointB.x);
        return false;
    }



    private static Region getWorldPoints(Vector2f world_pos, Vector2f world_size, float world_rotation){
        //Scale
        Matrix3f scale = Matrix3f.setIdentity(new Matrix3f());
        scale.m00 = world_size.x;
        scale.m11 = world_size.y;
        //Rotation
        Matrix3f rotation = new Matrix3f();
        rotation.m00 = (float) Math.cos(Math.toRadians(world_rotation));
        rotation.m11 = (float) Math.cos(Math.toRadians(world_rotation));
        rotation.m10 = - (float) Math.sin(Math.toRadians(world_rotation));
        rotation.m01 = (float) Math.sin(Math.toRadians(world_rotation));
        //Translation
        Matrix3f translation = Matrix3f.setIdentity(new Matrix3f());
        translation.m20 = world_pos.x;
        translation.m21 = world_pos.y;
        //Final
        Matrix3f finalMatrix = Matrix3f.mul(scale, rotation, null);
        Matrix3f.mul(finalMatrix, translation, finalMatrix);
        //Get actual points
        Region r = new Region();
        Vector3f pointA = new Vector3f(-.5f,-.5f, 1f);
        Vector3f pointB = new Vector3f(-.5f,.5f, 1f);
        Vector3f pointC = new Vector3f(.5f,.5f, 1f);
        Vector3f pointD = new Vector3f(.5f,-.5f, 1f);
        Matrix3f.transform(finalMatrix, pointA, pointA);
        Matrix3f.transform(finalMatrix, pointB, pointB);
        Matrix3f.transform(finalMatrix, pointC, pointC);
        Matrix3f.transform(finalMatrix, pointD, pointD);
        r.pointA = new Vector2f(pointA.x, pointA.y);
        r.pointB = new Vector2f(pointB.x, pointB.y);
        r.pointC = new Vector2f(pointC.x, pointC.y);
        r.pointD = new Vector2f(pointD.x, pointD.y);
        return r;
    }

}