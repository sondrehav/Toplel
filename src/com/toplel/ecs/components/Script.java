package com.toplel.ecs.components;

import com.toplel.ecs.entity.GameObject;
import com.toplel.util.objects.MySimpleFileReader;
import org.json.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

public class Script extends Component {

    private String script = null;
    private String fullScript = null;

    private ScriptEngine engine;
    private Invocable invocable;

    @Override
    public void setValues(JSONObject object) {

        script = object.has("scriptPath") ? object.getString("scriptPath") : null;

        try{
            System.out.println("Loading script \"" + script + "\".");
            fullScript = "var gameObject = com.toplel.ecs.entity.GameObject;\n"
                    + "function registerGameObject(inputObject){\n"
                    + "\t gameObject = inputObject;\n"
                    + "}\n\n";
            fullScript += MySimpleFileReader.read(script);
            engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.eval(fullScript);
            invocable = (Invocable) engine;
            invocable.invokeFunction("registerGameObject", parent);
        } catch (IOException | ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public Script(GameObject parent) {
        super(parent);
    }

    @Override
    public void start() {
        try{
            invocable.invokeFunction("init");
        } catch (NoSuchMethodException e) {
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try{
            invocable.invokeFunction("update");
        } catch (NoSuchMethodException e) {
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLate() {
        try{
            invocable.invokeFunction("updateLate");
        } catch (NoSuchMethodException e) {
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fixedUpdate() {
        try{
            invocable.invokeFunction("fixedUpdate");
        } catch (NoSuchMethodException e) {
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try{
            invocable.invokeFunction("destroy");
        } catch (NoSuchMethodException e) {
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String key() {
        return "script";
    }
}
