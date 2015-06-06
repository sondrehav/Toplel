package old.loaders;

public abstract class SceneLoader {

//    static HashMap<String, Scene> loaded = new HashMap<String, Scene>();

    public static String load(String path) throws Exception {
//        if(loaded.containsKey(path)){
//            return loaded.get(path);
//        }
//
//        JSONObject file = new JSONObject(SimpleFileReader.read(path));
//        Scene scene = new Scene(path);
//
//        scene.setName(file.getString("title"));
//        JSONArray entities = file.getJSONArray("entities");
//        for(int i=0;i<entities.length();i++){
//            JSONObject obj = entities.getJSONObject(i);
//            if(obj.has("path")){
//                obj = new JSONObject(SimpleFileReader.read(obj.getString("path")));
//            }
////            Entity e = new Entity(obj);
////            scene.addEntity(e);
//        }
//        Main.setTitle(scene.getName());
        return null;
    }

}
