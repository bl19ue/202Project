package jm3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.logging.Level;
import java.util.logging.*;


public class TryFirework2 extends SimpleApplication {

   private static final Logger logger = Logger.getLogger(TryFirework2.class.getName());
    
    private float time = 0;
    private int state = 0;
    

   private Node[] firework = new Node[10];
   private AudioNode audioFirework;

   
  

    //private ParticleEmitter spark, roundspark,shockwave;

    private ParticleEmitter[] sparkArr= new ParticleEmitter[10];
    private ParticleEmitter[] roundsparkArr= new ParticleEmitter[10];
    private ParticleEmitter[] shockwaveArr= new ParticleEmitter[10];


    private static final int COUNT_FACTOR = 1;
    private static final float COUNT_FACTOR_F = 1f;

    private static final boolean POINT_SPRITE = true;
    private static final Type EMITTER_TYPE = POINT_SPRITE ? Type.Point : Type.Triangle;

    public static void main(String[] args){
        TryFirework2 app = new TryFirework2();
        
        app.start();
    }

    private void createRoundSpark(int index){
        roundsparkArr[index] = new ParticleEmitter("RoundSpark", EMITTER_TYPE, 20 * COUNT_FACTOR);
        roundsparkArr[index].setStartColor(new ColorRGBA(1f, 0.29f, 0.34f, (float) (1.0 / COUNT_FACTOR_F)));
        roundsparkArr[index].setEndColor(new ColorRGBA(0, 0, 0, (float) (0.5f / COUNT_FACTOR_F)));
        //roundspark.setStartSize(1.2f);
        //roundspark.setEndSize(1.8f);
        
        roundsparkArr[index].setStartSize(0.2f);
        roundsparkArr[index].setEndSize(0.8f);
        
        roundsparkArr[index].setShape(new EmitterSphereShape(Vector3f.ZERO, 2f));
        roundsparkArr[index].setParticlesPerSec(0);
        roundsparkArr[index].setGravity(0, -.5f, 0);
        roundsparkArr[index].setLowLife(1.8f);
        roundsparkArr[index].setHighLife(2f);
        roundsparkArr[index].setInitialVelocity(new Vector3f(0, 3, 0));
        roundsparkArr[index].setVelocityVariation(.5f);
        roundsparkArr[index].setImagesX(1);
        roundsparkArr[index].setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/roundspark.png"));
        mat.setBoolean("PointSprite", POINT_SPRITE);
        roundsparkArr[index].setMaterial(mat);
       // explosionEffect.attachChild(roundspark
        firework[index].attachChild(roundsparkArr[index]);

    }

    private void createSpark(int index){
        sparkArr[index] = new ParticleEmitter("Spark", Type.Triangle, 30 * COUNT_FACTOR);
        sparkArr[index].setStartColor(new ColorRGBA(1f, 0.8f, 0.36f, (float) (1.0f / COUNT_FACTOR_F)));
        sparkArr[index].setEndColor(new ColorRGBA(1f, 0.8f, 0.36f, 0f));
        sparkArr[index].setStartSize(.5f);
        sparkArr[index].setEndSize(.5f);
        sparkArr[index].setFacingVelocity(true);
        sparkArr[index].setParticlesPerSec(0);
        sparkArr[index].setGravity(0, 5, 0);
        sparkArr[index].setLowLife(1.1f);
        sparkArr[index].setHighLife(1.5f);
        sparkArr[index].getParticleInfluencer().setInitialVelocity(new Vector3f(0, 20, 0));
        sparkArr[index].getParticleInfluencer().setVelocityVariation(1);
        sparkArr[index].setImagesX(1);
        sparkArr[index].setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/spark.png"));
        sparkArr[index].setMaterial(mat);
        firework[index].attachChild(sparkArr[index]);
    }

    private void createShockwave(int index){
        shockwaveArr[index] = new ParticleEmitter("Shockwave", Type.Triangle, 1 * COUNT_FACTOR);
//        shockwave.setRandomAngle(true);
       shockwaveArr[index].setFaceNormal(Vector3f.UNIT_Z);
        shockwaveArr[index].setStartColor(new ColorRGBA(.48f, 0.17f, 0.01f, (float) (.8f / COUNT_FACTOR_F)));
        shockwaveArr[index].setEndColor(new ColorRGBA(.48f, 0.17f, 0.01f, 0f));

        //shockwave.setStartSize(0f);
        //shockwave.setEndSize(7f);
        
        shockwaveArr[index].setStartSize(0f);
        shockwaveArr[index].setEndSize(3f);

        shockwaveArr[index].setParticlesPerSec(0);
        shockwaveArr[index].setGravity(0, 0, 0);
        shockwaveArr[index].setLowLife(0.5f);
        shockwaveArr[index].setHighLife(0.5f);
        shockwaveArr[index].setInitialVelocity(new Vector3f(0, 0, 0));
        shockwaveArr[index].setVelocityVariation(0f);
        shockwaveArr[index].setImagesX(1);
        shockwaveArr[index].setImagesY(1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/shockwave.png"));
        shockwaveArr[index].setMaterial(mat);
        firework[index].attachChild(shockwaveArr[index]);
    }

    @Override
    public void simpleInitApp() {
        int x=0;
        int y=0;
        int z=0;
        for(int index=0;index<10;index++)
        {
        
        firework[index]=new Node("Firework");
            
        createSpark(index);
        createRoundSpark(index);
        createShockwave(index);
        
        
        firework[index].setLocalScale(0.5f);
        renderManager.preloadScene(firework[index]);
        //firework[index].setLocalTranslation(new Vector3f(x,y,z));
        
        
       

        

        cam.setLocation(new Vector3f(0, 3.5135868f, 10));
        cam.setRotation(new Quaternion(1.5714673E-4f, 0.98696727f, -0.16091813f, 9.6381607E-4f));

        rootNode.attachChild(firework[index]);
        
        }
        
        
        firework[0].setLocalTranslation(3, 3, 0);
        firework[1].setLocalTranslation(-3, 1, 0);
        firework[2].setLocalTranslation(0, 0, 0);
        firework[3].setLocalTranslation(-3, 3, 0);
        firework[4].setLocalTranslation(3, -3, 0);
        firework[5].setLocalTranslation(1, -1, 0);
        firework[6].setLocalTranslation(-1, 3, 0);
        firework[7].setLocalTranslation(-1, -3, 0);
        firework[8].setLocalTranslation(3, -1, 0);
        firework[9].setLocalTranslation(2,0, 0);
        
       
        initAudio();
    


    }
    
    private void initAudio() {
    /* gun shot sound is to be triggered by a mouse click. */
    //audioFirework = new AudioNode(assetManager, "Sound/Effects/Bang.wav", false);
    audioFirework = new AudioNode(assetManager, "Sounds/Firework1.wav", false);

    audioFirework.setPositional(false);
    audioFirework.setLooping(false);
    audioFirework.setVolume(2);
    rootNode.attachChild(audioFirework);
    }

    @Override
    public void simpleUpdate(float tpf){ 
        
        for(int index=0; index<10; index++)
        {
        time += tpf / (3*speed);
        
        logger.log(Level.WARNING, "Speed"+speed, new Object[]{});

        if (time > 1f && state == 0){
            
            sparkArr[index].emitAllParticles();
            
            shockwaveArr[index].emitAllParticles();
            state++;
            audioFirework.playInstance();
        }
        if (time > 1f + .05f / speed && state == 1){
           
            roundsparkArr[index].emitAllParticles();
            state++;
        }
        
        // rewind the effect
        if (time > 5 / speed && state == 2){
            state = 0;
            time = 0;
            sparkArr[index].killAllParticles();
            roundsparkArr[index].killAllParticles();
            shockwaveArr[index].killAllParticles();
        }
        }
    }

}