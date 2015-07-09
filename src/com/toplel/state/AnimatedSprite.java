package com.toplel.state;

import com.toplel.context.MyContext;
import com.toplel.test.AnimationDoneCallback;
import com.toplel.test.Region;
import com.toplel.test.Tileset;
import com.toplel.util.Console;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MyVertexObject;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;

public class AnimatedSprite {

    public enum Type{
        SAW, ONESHOT, TRIANGLE
    }

    private long timeLast = System.currentTimeMillis();

    private int counter = 0;

    private AnimationSet currentSet = null;
    private MyVertexObject currentFrame = null;

    HashMap<Integer, MyVertexObject> frames = new HashMap<>();
    HashMap<String, AnimationSet> sets = new HashMap<>();

    private Tileset tileset;
    private MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/default.vs", "res/shader/default.fs");

    public AnimatedSprite(Tileset tileset){
        this.tileset = tileset;
        generateVertexObjects(tileset);
    }

    private void generateVertexObjects(Tileset tileset){
        for (int i = 0; i < tileset.getMax(); i++) {
            float[] vertecies = new float[]{0f,0f,1f,0f,1f,1f,0f,1f};
            Region region = tileset.getUVRegion(i);
            float[] texCoords = new float[]{region.x0, region.y1, region.x1, region.y1, region.x1, region.y0, region.x0, region.y0};
            frames.put(i, new MyVertexObject(new float[][]{vertecies, texCoords}, new int[]{
                    0, 1, 2, 2, 3, 0
            }));
            if(i>=tileset.getMax()){
                Console.printErr("JEJEJEJEJEJE");
            }
        }
    }

    public void createSet(String identifier, long frameTimeMillis, int[] frames, Type loopType, boolean reversed, AnimationDoneCallback callback){
        sets.put(identifier, new AnimationSet(frameTimeMillis, frames, loopType, reversed, callback));
    }

    public void createSet(String identifier, long frameTimeMillis, int[] frames, Type loopType){
        sets.put(identifier, new AnimationSet(frameTimeMillis, frames, loopType));
    }

    public void createSet(String identifier, long frameTimeMillis, int[] frames, Type loopType, boolean reversed){
        sets.put(identifier, new AnimationSet(frameTimeMillis, frames, loopType, reversed));
    }

    public void useSet(String identifier){
        currentSet = sets.get(identifier);
        currentFrame = frames.get(currentSet.frames[0]);
        counter = 0;
        cb = false;
    }

    public void useSetNonReset(String identifier){
        if(currentSet.equals(sets.get(identifier))) return;
        currentSet = sets.get(identifier);
        currentFrame = frames.get(currentSet.frames[0]);
        counter = 0;
        cb = false;
    }

    public void event(){
        long nowTime = System.currentTimeMillis();

        if(nowTime - timeLast > currentSet.time){
            currentFrame = next();
             timeLast = nowTime;
        }
    }

    public void render(Matrix4f md_matrix){
        event();
        tileset.bind();
        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", MyContext.get("world").getViewProjection());
        shaderProgram.setUniformMat4("md_matrix", md_matrix);
        currentFrame.bind();
        currentFrame.draw();
        currentFrame.unbind();
        shaderProgram.unbind();
        tileset.unbind();
    }

    private boolean up = true;
    private boolean cb = false;
    private MyVertexObject next(){

        // TODO: If reversed it starts with the wrong frame (the first)

        switch (currentSet.type){
            case SAW:
                counter++;
                if(counter>=currentSet.frames.length){
                    counter = 0;
                }
                break;
            case TRIANGLE:
                if(up){
                    counter++;
                    if(counter>=currentSet.frames.length){
                        up = false;
                        counter = currentSet.frames.length - 2;
                    }
                } else {
                    counter--;
                    if(counter < 0){
                        up = true;
                        counter = 1;
                    }
                }
                break;
            case ONESHOT:
                if(counter < currentSet.frames.length - 1){
                    counter++;
                } else if(!cb) {
                    cb = true;
                    if(currentSet.callback!=null) currentSet.callback.onAnimationDone();
                }
                break;
        }

        if(currentSet.reversed){
            return frames.get(currentSet.frames[currentSet.frames.length - counter - 1]);
        }
        return frames.get(currentSet.frames[counter]);
    }

    private static class AnimationSet {

        long time; int[] frames; Type type; boolean reversed; AnimationDoneCallback callback;

        public AnimationSet(long time, int[] frames, Type type) {
            this(time, frames, type, false);
        }

        public AnimationSet(long time, int[] frames, Type type, boolean reversed) {
            this(time, frames, type, reversed, null);
        }

        public AnimationSet(long time, int[] frames, Type type, boolean reversed, AnimationDoneCallback callback) {
            this.time = time;
            this.frames = frames;
            this.type = type;
            this.reversed = reversed;
            this.callback = callback;
        }

    }

}
