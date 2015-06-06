package old.ecs.system;

import old.loaders.TextureLoader;
import org.lwjgl.util.vector.Vector2f;
import old.utils.renderer.Renderer;
import old.utils.renderer.Sprite;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.IOException;

import static old.ecs.ScriptInterface.printerr;

public class Script {

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Invocable cx = (Invocable) engine;
    String path;

    public void init(Object... args) {
        try{
            cx.invokeFunction("init", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    public void event(Object... args) {
        try{
            cx.invokeFunction("event", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    public void destroy(Object... args) {
        try{
            cx.invokeFunction("destroy", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    public void draw(float x, float y, float rotation, Sprite sprite) {
        Vector2f dim = TextureLoader.getDimensions(sprite);
        Renderer.draw(x, y, rotation, sprite, dim.x, dim.y);
    }

    public void draw(float x, float y, float rotation, Sprite sprite, float sx, float sy) {
        Renderer.draw(x, y, rotation, sprite, sx, sy);
    }

    public void addSprite(String sprite) {
        try{
            TextureLoader.load(sprite);
        } catch (IOException e) {
            printerr(e.getLocalizedMessage(), path);
        }
    }
}
