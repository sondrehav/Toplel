package old.loaders;

public class Gamemode {

    public static Gamemode current = null;

    public final String path;

    public Gamemode(String path){
        this.path = path;
    }

    public void start(){
        if(current==null){
            current = this;
        }
    }

    public void stop(){
        if(current==this){
            current = null;
        }
    }

}
