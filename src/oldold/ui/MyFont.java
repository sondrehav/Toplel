package oldold.ui;

import oldold.loaders.MySimpleFileReader;
import oldold.math.MyRegion;
import oldold.math.vector.MyVec2;
import org.json.JSONObject;
import oldold.renderer.objects.MyTexture;

import java.io.IOException;

public class MyFont {

    public final MyTexture texture;
    public final int XDIM;
    public final int YDIM;
    public final int OFFSET;
    public final int RANGE;
    public final int CHARPERROW;

    private MyFont(MyTexture texture, int XDIM, int YDIM, int offset, int range) {
        this.texture = texture;
        this.XDIM = XDIM;
        this.YDIM = YDIM;
        this.OFFSET = offset;
        this.RANGE = range;
        this.CHARPERROW = (int) Math.floor((float) texture.WIDTH / (float)XDIM);
    }

    public MyRegion getRegion(char c){
        int codepoint = (int) c - OFFSET;
        int x = codepoint % CHARPERROW;
        int y = Math.floorDiv(codepoint, CHARPERROW);
        int img_x = x * XDIM;
        int img_y = y * YDIM;
        return new MyRegion(
                img_x, img_y, img_x + XDIM, img_y + YDIM
        );
    }

    public MyRegion getNormalizedRegion(char c){
        return getRegion(c).mult(new MyVec2(1f / texture.WIDTH, 1f / texture.HEIGHT));
    }

    //---------- STATIC -----------

    public static MyFont addFont(String fontObjectPath){
        JSONObject object = null;
        try{
            object = new JSONObject(MySimpleFileReader.read(fontObjectPath));
        } catch (IOException e){
            e.printStackTrace();
        }
        String imagePath = object.getString("imagePath");
        int xdim = object.getInt("xdim");
        int ydim = object.getInt("ydim");
        int offset = object.has("offset") ? object.getInt("offset") : 32;
        int range = object.has("range") ? object.getInt("range") : 95;
        MyTexture texture = MyTexture.addTexture(imagePath);
        return new MyFont(texture, xdim, ydim, offset, range);
    }

}
