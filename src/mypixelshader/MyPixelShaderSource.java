package mypixelshader;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MyPixelShaderSource implements Runnable {

    HashMap<File, Long> files = new HashMap<>();

    long step = 1000L;

    boolean running = true;

    public MyPixelShaderSource(){
        new Thread(this).start();
    }

    public void addFileListener(File f){
        toAdd.add(f);
    }

    public void stop(){
        running = false;
    }

    LinkedList<File> toAdd = new LinkedList<>();

    @Override
    public void run() {
        LinkedList<File> modified = new LinkedList<>();
        try{
            while(running){
                Thread.sleep(step);
                for(Map.Entry<File, Long> entry : files.entrySet()){
                    if(entry.getKey().lastModified()!=entry.getValue()){
                        System.out.println("File '" + entry.getKey().getPath() + "' is modified.");
                        MyPixelShader.reload(entry.getKey().getPath());
                        modified.add(entry.getKey());
                    }
                }
                while(!modified.isEmpty()){
                    File f = modified.poll();
                    files.put(f, f.lastModified());
                }
                while(!toAdd.isEmpty()){
                    File f = toAdd.poll();
                    files.put(f, f.lastModified());
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
