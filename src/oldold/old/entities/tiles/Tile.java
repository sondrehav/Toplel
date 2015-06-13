package oldold.old.entities.tiles;

import org.lwjgl.util.vector.Vector2f;
import oldold.old.utils.renderer.Renderer;
import oldold.old.utils.renderer.Sprite;

public class Tile {

    Sprite sprite;

    Vector2f position;
    Vector2f size;
    float rotation;

    Vector2f dim;

    int x_num, y_num;

    public Tile(String tileName, Vector2f pos, Vector2f size, float rotation){

//        try{
//            sprite = MyTextureLoader.load(tileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        this.position = pos;
        this.size = size;
        this.rotation = rotation;

//        dim = MyTextureLoader.getDimensions(sprite);
        x_num = (int)Math.ceil(size.x / dim.x);
        y_num = (int)Math.ceil(size.y / dim.y);

    }

    public void render(){

        for(int i=0;i<y_num;i++){
            for (int j = 0; j < x_num; j++) {
                Renderer.draw(position.x + dim.x * j, position.y + dim.y * i, rotation, sprite, 1f, 1f);
            }
        }

    }

}
