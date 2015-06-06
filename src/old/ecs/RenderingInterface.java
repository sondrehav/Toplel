package old.ecs;

public interface RenderingInterface {

    public void draw(float x, float y, float rotation, String sprite);
    public void draw(float x, float y, float rotation, String sprite, float sx, float sy);
    public void addSprite(String sprite);

}
