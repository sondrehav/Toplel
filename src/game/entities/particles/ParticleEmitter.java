package game.entities.particles;

import game.entities.Entity;
import loaders.TextureLoader;
import org.lwjgl.util.vector.Vector2f;
import utils.renderer.Renderer;
import utils.renderer.ShaderProgram;
import utils.renderer.Sprite;

import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

public class ParticleEmitter extends Entity {

    // FIRE!

    Vector2f position = new Vector2f();
    Vector2f size = new Vector2f();
    float rotation = 0f;

    Sprite fireTexture;
    Sprite smokeTexture;
    ShaderProgram shaderProgram;

    TreeSet<Particle> fireSet = new TreeSet<>();
    TreeSet<Particle> smokeSet = new TreeSet<>();

    int lifeTime = 10000;

    public ParticleEmitter(){
        try{
            this.fireTexture = TextureLoader.load("res/img/game/entities/particles/fire.png");
            this.smokeTexture = TextureLoader.load("res/img/game/entities/particles/smoke.png");
            this.shaderProgram = ShaderProgram.addShader("res/shaders/vertTest.vs", "res/shaders/fragTest.fs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        for(int i = 0; i < 20; i++){
            spawnFireParticle();
            spawnSmokeParticle();
        }
    }

    @Override
    public void event() {
        for(Particle p : smokeSet){

        }
        for(Particle p : fireSet){

        }
    }

    private void spawnFireParticle(){

    }

    private void spawnSmokeParticle(){
        
    }

    @Override
    public void render() {
        for(Particle p : smokeSet){
            Renderer.draw(p.position.x + this.position.x, p.position.y + this.position.y, this.rotation + p.rotation, fireTexture, p.sizeMult, p.sizeMult, shaderProgram);
        }
        for(Particle p : fireSet){
            Renderer.draw(p.position.x + this.position.x, p.position.y + this.position.y, this.rotation + p.rotation, fireTexture, p.sizeMult, p.sizeMult, shaderProgram);
        }
    }

    private class Particle implements Comparator<Particle> {

        Vector2f position = new Vector2f();
        Vector2f velocity = new Vector2f();
        float sizeMult = 1f;
        float angularVelocity = 0f;
        float rotation = 0f;
        int spawnTime = (int) System.nanoTime() / 1000000;
        int ttl = lifeTime;

        void event(){
            Vector2f.add(position, velocity, position);
            rotation += angularVelocity;
            ttl--;
        }

        @Override
        public int compare(Particle o1, Particle o2) {
            // o1 > o2 -> o1 is newer, o2 > o1 -> o1 is older
            return o1.spawnTime - o2.spawnTime;
        }
    }

}
