package oldold.old.ecs;

public interface ScriptInterface {

    public void init(Object ... args);
    public void event(Object ... args);
    public void destroy(Object ... args);

    public static void printerr(String localizedMessage, String path){
        System.err.println("SCRIPTERROR: " + localizedMessage + " at '" + path + "'.");
    }

}
