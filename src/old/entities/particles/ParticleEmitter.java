package old.entities.particles;

import old.entities.Entity;
import old.loaders.TextureLoader;
import org.lwjgl.util.vector.Vector2f;
import old.utils.renderer.Renderer;
import renderer.ShaderProgram;
import old.utils.renderer.Sprite;

import java.io.IOException;
import java.util.*;

public class ParticleEmitter extends Entity {

    // FIRE!

    public Vector2f position = new Vector2f();
    public Vector2f size = new Vector2f();
    public float rotation = 0f;

    Sprite fireTexture;
    Sprite smokeTexture;
    ShaderProgram shaderProgram;

    TreeSet<Particle> fireSet = new TreeSet<>();
    TreeSet<Particle> smokeSet = new TreeSet<>();

    int numOfFParticles = 10;
    int numOfSParticles = 20;
    int lifeTime = 70;
    float maxAlpha = .5f;

    float speedMult = 0.5f;

    public ParticleEmitter(){
        try{
            this.fireTexture = TextureLoader.load("res/img/game/entities/particles/fire.png");
            this.smokeTexture = TextureLoader.load("res/img/game/entities/particles/smoke.png");
            this.shaderProgram = ShaderProgram.addShader("res/shader/particleShader.vs", "res/shader/particleShader.fs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        for(int i = 0; i < numOfFParticles; i++){
            spawnFireParticle();
        }
        for(int i = 0; i < numOfSParticles; i++){
            spawnSmokeParticle();
        }
    }

    @Override
    public void event() {
        for(Particle p : smokeSet){
            p.event();
        }
        for(Particle p : fireSet){
            p.event();
        }
        while(!fToDelete.isEmpty()){
            fireSet.remove(fToDelete.poll());
            spawnFireParticle();
        }
        while(!sToDelete.isEmpty()){
            smokeSet.remove(sToDelete.poll());
            spawnSmokeParticle();
        }
    }

    Queue<Particle> fToDelete = new LinkedList<>();
    Queue<Particle> sToDelete = new LinkedList<>();

    private void spawnFireParticle(){
        if(fireSet.size() >= numOfFParticles) return;
        Particle p = new Particle(){
            @Override
            public void onDeath() {
                fToDelete.add(this);
            }
        };
        p.position.y = this.position.y;
        p.position.x = this.position.x;
        p.velocity.y = 1f*0.06f * speedMult;
        p.velocity.x = (float) (Math.random() - 0.5)*0.01f*speedMult;
        p.rotation = 360f * (float) Math.random();
        p.angularVelocity = (float) Math.random() * 0.1f * speedMult;
        p.sizeMult = (float) Math.random()*.5f;
        fireSet.add(p);
        System.out.println("FIRE COUNT: " + fireSet.size());
    }

    private void spawnSmokeParticle(){
        if(smokeSet.size() >= numOfSParticles) return;
        Particle p = new Particle(){
            @Override
            public void onDeath() {
                sToDelete.add(this);
            }
        };
        p.position.y = 0.1f + this.position.y;
        p.position.x = this.position.x;
        p.velocity.y = 0.1f* speedMult;
        p.velocity.x = (float) (Math.random() - 0.5)*0.08f* speedMult;
        p.rotation = 360f * (float) Math.random();
        p.angularVelocity = (float) Math.random() * 10f * 0.1f* speedMult;
        p.sizeMult = (float) Math.random()*1f;
        smokeSet.add(p);
        System.out.println("SMOKE COUNT: " + smokeSet.size());
    }

    @Override
    public void render() {
        for(Particle p : smokeSet){
            Renderer.draw(p.position.x + this.position.x, p.position.y, p.rotation, smokeTexture, p.getSize(), p.getSize(), shaderProgram, p.alpha);
        }
        for(Particle p : fireSet){
            Renderer.draw(p.position.x + this.position.x, p.position.y, p.rotation, fireTexture, p.getSize(), p.getSize(), shaderProgram, p.alpha);
        }
    }

    private static Random rand = new Random();

    private abstract class Particle implements Comparable<Particle> {

        Vector2f position = new Vector2f();
        Vector2f velocity = new Vector2f();
        float sizeMult = 1f;
        float angularVelocity = 0f;
        float rotation = 0f;
        long nanoSpawnTime = System.nanoTime();
        int ttl = lifeTime + (int)(lifeTime * (Math.random() - 0.5f));
        float minsize = 1f;

        final int initialttl;

        float alpha = 0f;

        public Particle(){
            initialttl = ttl;
        }

        void event(){
            Vector2f.add(position, velocity, position);
            rotation += angularVelocity;

            float lerp = 1f - (float)(ttl) / (float)initialttl;
            alpha = (lerp-1f)*(lerp-1f)*lerp*(27f/4f)*maxAlpha;

            ttl--;
            if(ttl<0) onDeath();
        }

        public float getSize(){
            return alpha * sizeMult + minsize;
        }

        public int compare(Particle o1, Particle o2) {
            // o1 > o2 -> o1 is newer, o2 > o1 -> o1 is older
            return (int) (o1.nanoSpawnTime - o2.nanoSpawnTime);
        }

        @Override
        public int compareTo(Particle o) {
            return compare(this, o);
        }

        public abstract void onDeath();

    }

}
