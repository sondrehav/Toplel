package oldold.old.ecs.system;

import oldold.old.ecs.component.Component;
import oldold.old.ecs.entity.Entity;
import org.lwjgl.util.vector.Vector2f;
import oldold.loaders.MySimpleFileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

public class ScriptHandler {

    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    Invocable cx = (Invocable) engine;
    String path;

    public ScriptHandler(String path){
        this.path = path;
        try{
            engine.eval(MySimpleFileReader.read(path));
        } catch (ScriptException e){
            printerr(e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Component comp = null;
        try{
            comp = Component.load("res/sys/comp.comp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Vector2f pos = comp.getVar("position");
        } catch(NullPointerException e) {
            e.printStackTrace();
        }


    }

    public void init(){
        try{
            Entity obj = (Entity)cx.invokeFunction("init", new Entity());
            System.out.println(obj.id);
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage());
        }
    }

    public void event(){
        try{
            cx.invokeFunction("event");
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage());
        }
    }

    public void render(){
        try{
            cx.invokeFunction("render");
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage());
        }
    }

    public void destroy(){
        try{
            cx.invokeFunction("close");
        } catch(NoSuchMethodException e){} catch (ScriptException e){
            printerr(e.getLocalizedMessage());
        }
    }

    private void printerr(String localizedMessage){
        System.err.println(localizedMessage + " at '" + path + "'.");
    }

}
