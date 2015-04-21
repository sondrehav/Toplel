package script;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LUAHandler {

    public static void main(String[] args){
        LuaValue v = JsePlatform.standardGlobals();
        v.get("dofile").call(LuaValue.valueOf("res/script/test.lua"));
        LuaValue add = v.get("event");
        LuaValue ret = add.call(LuaValue.valueOf(4), LuaValue.valueOf(2));
        int val = ret.toint();
        System.out.println("val = " + val);
    }

}
