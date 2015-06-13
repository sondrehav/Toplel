package oldold.old.ecs.system;

import oldold.loaders.MyTextureLoader;
import oldold.old.utils.renderer.Renderer;
import oldold.old.utils.renderer.Sprite;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.IOException;

import static oldold.old.ecs.ScriptInterface.printerr;

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
//        Vector2f dim = MyTextureLoader.getDimensions(sprite);
//        Renderer.draw(x, y, rotation, sprite, dim.x, dim.y);
    }

    public void draw(float x, float y, float rotation, Sprite sprite, float sx, float sy) {
        Renderer.draw(x, y, rotation, sprite, sx, sy);
    }

    public void addSprite(String sprite) {
        try{
            MyTextureLoader.load(sprite);
        } catch (IOException e) {
            printerr(e.getLocalizedMessage(), path);
        }
    }
}
