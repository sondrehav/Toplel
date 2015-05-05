package ecs.system;

import ecs.RenderingInterface;
import ecs.ScriptInterface;
import loaders.TextureLoader;
import org.lwjgl.util.vector.Vector2f;
import utils.renderer.Renderer;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.IOException;

import static ecs.ScriptInterface.printerr;

public class Script implements ScriptInterface, RenderingInterface {

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Invocable cx = (Invocable) engine;
    String path;

    @Override
    public void init(Object... args) {
        try{
            cx.invokeFunction("init", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    @Override
    public void event(Object... args) {
        try{
            cx.invokeFunction("event", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    @Override
    public void destroy(Object... args) {
        try{
            cx.invokeFunction("destroy", args);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage(), path);
        }
    }

    @Override
    public void draw(float x, float y, float rotation, String sprite) {
        Vector2f dim = TextureLoader.getDimensions(sprite);
        Renderer.draw(x, y, rotation, sprite, dim.x, dim.y);
    }

    @Override
    public void draw(float x, float y, float rotation, String sprite, float sx, float sy) {
        Renderer.draw(x, y, rotation, sprite, sx, sy);
    }

    @Override
    public void addSprite(String sprite) {
        try{
            TextureLoader.load(sprite);
        } catch (IOException e) {
            printerr(e.getLocalizedMessage(), path);
        }
    }
}
