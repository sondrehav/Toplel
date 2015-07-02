package com.toplel.test;

import com.toplel.context.MyContext;
import com.toplel.util.objects.MyShaderProgram;
import com.toplel.util.objects.MySimpleFileReader;
import com.toplel.util.objects.MyTexture;
import com.toplel.util.objects.MyVertexObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Split in regions for more efficient drawing.

public class WorldMap {

    private enum Collision{
        COLLISION, NONCOLLISION, UNSPECIFIED
    }

    boolean[][] collisionMap;

    WorldLayer[] layers;
    static MyShaderProgram shaderProgram = MyShaderProgram.addShaderProgram("res/shader/regionShader.vs", "res/shader/regionShader.fs");

    public final int width;
    public final int height;

    private float size = 100f;

    private final Matrix4f md_matrix;

    private Tileset[] tilesets;

    public boolean collidesAt(float x, float y){
        int X = (int)(x / size);
        int Y = (int)(y / size);
        return collisionMap[Y][X];
    }

    public WorldMap(String path){

        JSONObject object = null;
        try {
            String fullFile = MySimpleFileReader.read(path);
            object = new JSONObject(fullFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = object.getInt("width");
        height = object.getInt("height");

        collisionMap = new boolean[height][width];
        for (int i = 0; i < collisionMap.length; i++) {
            for (int j = 0; j < collisionMap[0].length; j++) {
                collisionMap[i][j]=false;
            }
        }

        md_matrix = Matrix4f.setIdentity(new Matrix4f());
        Matrix4f.scale(new Vector3f(size, size, 1f), md_matrix, md_matrix);

        JSONArray layers = object.getJSONArray("layers");
        this.layers = new WorldLayer[layers.length()];

        JSONArray jsonTilesets = object.getJSONArray("tilesets");
        tilesets = new Tileset[jsonTilesets.length()];
        for (int i = 0; i < jsonTilesets.length(); i++) {
            JSONObject jsonTileset = jsonTilesets.getJSONObject(i);
            int startIndex = jsonTileset.getInt("firstgid");
            String name = jsonTileset.getString("name");
            int tileWidth = jsonTileset.getInt("tilewidth");
            int tileHeight = jsonTileset.getInt("tileheight");
            String image = jsonTileset.getString("image");
            Matcher mat = Pattern.compile("img\\/.*").matcher(image);
            mat.find();
            image = "res/"+mat.group(0);
            tilesets[i] = new Tileset(MyTexture.addTexture(image), tileWidth, tileHeight, startIndex, name);
        }
        for (int i = 0; i < layers.length(); i++) {
            JSONObject object1 = layers.getJSONObject(i);
            /*if(!object1.getString("type").contentEquals("tilelayer")){
                continue;
            }*/
            JSONArray dataArray = object1.getJSONArray("data");
            int[] data = new int[dataArray.length()];
            int count = 0;
            for (int j = 0; j < data.length; j++) {
                data[j] = dataArray.getInt(j);
                if(data[j]!=0){
                    count++;
                }
            }
            String name = object1.getString("name");

            Collision collision = Collision.UNSPECIFIED;
            if(object1.has("properties")){
                JSONObject properties = object1.getJSONObject("properties");
                String t = properties.has("collision") ? properties.getString("collision") : null;
                if(t!=null){
                    if(t.contentEquals("true")) collision = Collision.COLLISION;
                    else if(t.contentEquals("false")) collision = Collision.NONCOLLISION;
                }
            }

            WorldLayer l = new WorldLayer(data, tilesets, name, collision);
            this.layers[i] = l;
        }

    }

    public void render(Vector2f light, float intensity){

        Vector2f li = new Vector2f(light);
        li.x /= size;
        li.y /= size;

        shaderProgram.bind();
        shaderProgram.setUniformMat4("prvw_matrix", MyContext.get("world").getViewProjection());
        shaderProgram.setUniform2f("light", li.x, li.y);
        shaderProgram.setUniform2f("lightAttrib", intensity, 4f);
        for (int i = 0; i < layers.length; i++) {
            float depth = .9f*(float)(-i-1) / layers.length + 1f;
            WorldLayer l = layers[i];
            Matrix4f tempMDMatrix = new Matrix4f(md_matrix);
            Matrix4f.translate(new Vector3f(0f,0f,depth), tempMDMatrix, tempMDMatrix);
            shaderProgram.setUniformMat4("md_matrix", tempMDMatrix);
            for (int j = 0; j < l.vertexObjects.length; j++) {
                l.tilesets[j].bind();
                l.vertexObjects[j].bind();
                l.vertexObjects[j].draw();
                l.vertexObjects[j].unbind();
                l.tilesets[j].unbind();
            }
        }
        shaderProgram.unbind();
    }

    private class WorldLayer  {

        String name;
        MyVertexObject[] vertexObjects;
        Tileset[] tilesets;
        Collision collision = Collision.UNSPECIFIED;

        public WorldLayer(int[] data, Tileset[] tilesets, String name, Collision collision){

            this.name = name;
            this.collision = collision;

            class Temp{
                ArrayList<float[]> vertecies = new ArrayList<>();
                ArrayList<float[]> texcoords = new ArrayList<>();
                Tileset tileset;
                Temp(Tileset t){
                    tileset = t;
                }
            }

            ArrayList<Temp> temp = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {

                if(data[i]==0) continue;
                int ID = data[i];
                Tileset t = select(ID, tilesets);
                Temp tempTemp = null;
                for(Temp s : temp){
                    if(s.tileset.equals(t)){
                        tempTemp = s;
                    }
                }
                if(tempTemp==null){
                    Temp ueuee = new Temp(t);
                    temp.add(ueuee);
                    tempTemp = ueuee;
                }

                float[] quadVerts = new float[12];
                float[] quadTexCoords = new float[12];

                int x = i % width;
                int y = Math.floorDiv(i, width);

                if(collision==Collision.COLLISION){
                    collisionMap[width - y - 1][x] = true;
                } else if(collision==Collision.NONCOLLISION){
                    collisionMap[width - y - 1][x] = false;
                }

                Region region = tempTemp.tileset.getUVRegion(data[i]);

                float x0 = x;
                float y0 = height - y;
                float x1 = x+1;
                float y1 = height - y - 1;

                float tx0 = region.x0;
                float ty0 = region.y0;
                float tx1 = region.x1;
                float ty1 = region.y1;

                quadTexCoords[0] = tx0;
                quadTexCoords[1] = ty0;
                quadTexCoords[2] = tx1;
                quadTexCoords[3] = ty0;
                quadTexCoords[4] = tx1;
                quadTexCoords[5] = ty1;
                quadTexCoords[6] = tx0;
                quadTexCoords[7] = ty0;
                quadTexCoords[8] = tx1;
                quadTexCoords[9] = ty1;
                quadTexCoords[10]= tx0;
                quadTexCoords[11]= ty1;

                tempTemp.texcoords.add(quadTexCoords);

                quadVerts[0] = x0;
                quadVerts[1] = y0;
                quadVerts[2] = x1;
                quadVerts[3] = y0;
                quadVerts[4] = x1;
                quadVerts[5] = y1;
                quadVerts[6] = x0;
                quadVerts[7] = y0;
                quadVerts[8] = x1;
                quadVerts[9] = y1;
                quadVerts[10] = x0;
                quadVerts[11] = y1;

                tempTemp.vertecies.add(quadVerts);

            }

            this.vertexObjects = new MyVertexObject[temp.size()];
            this.tilesets = new Tileset[temp.size()];
            for (int i = 0; i < vertexObjects.length; i++) {
                Temp current = temp.get(i);
                float[][] allData = new float[2][current.texcoords.size()*12];
                for (int j = 0; j < current.texcoords.size(); j++) {
                    for (int k = 0; k < 12; k++) {
                        allData[0][j*12+k] = current.vertecies.get(j)[k]; // Verts
                        allData[1][j*12+k] = current.texcoords.get(j)[k]; // TexCoords
                    }
                }
                this.vertexObjects[i] = new MyVertexObject(allData);
                this.tilesets[i] = current.tileset;
            }

        }

        private Tileset select(int id, Tileset[] tilesets){
            for (Tileset t : tilesets){
                if(t.inRange(id)) return t;
            }
            return null;
        }

    }

    private class WorldChunk{

        private static final int WIDTH = 16;
        private static final int HEIGHT = 16;
        private MyVertexObject vertexObject;
        private Matrix4f md_matrix = Matrix4f.setIdentity(new Matrix4f());

        public Vector2f position;

        public WorldChunk(float[][] data, int fromX, int fromY){
            position = new Vector2f(fromX, fromY);
            vertexObject = new MyVertexObject(data);
            Matrix4f.translate(position, md_matrix, md_matrix);
        }

        public void render(){
            shaderProgram.setUniformMat4("md_matrix", md_matrix);
            vertexObject.bind();
            vertexObject.draw();
            vertexObject.unbind();
        }

    }

    private class Grid {

        WorldChunk[][] grid;

    }

}
