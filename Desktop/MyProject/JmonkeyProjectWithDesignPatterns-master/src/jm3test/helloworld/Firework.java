/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;




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

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;

public class Firework extends AbstractAppState {

private Node              rootNode;
private AssetManager      assetManager;
private AppStateManager   stateManager;
private InputManager      inputManager;
private ViewPort          viewPort;
private BulletAppState    physics;
   
    private HelloPhysics app;
    private boolean startFirework=false;
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

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
      super.initialize(stateManager, app); 
      this.app = (HelloPhysics)app;          // cast to a more specific class
 
    this.rootNode     = this.app.getRootNode();
    this.assetManager = this.app.getAssetManager();
    this.stateManager = this.app.getStateManager();
    this.inputManager = this.app.getInputManager();
    this.viewPort     = this.app.getViewPort();
    this.physics      = this.stateManager.getState(BulletAppState.class);
    
    
  
      // init stuff that is independent of whether state is PAUSED or RUNNING
                           // call custom methods...
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
        Material mat = new Material(this.app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", this.app.getAssetManager().loadTexture("Effects/Explosion/roundspark.png"));
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
        
        Material mat = new Material(this.assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", this.app.getAssetManager().loadTexture("Effects/Explosion/spark.png"));
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
        Material mat = new Material(this.app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", this.app.getAssetManager().loadTexture("Effects/Explosion/shockwave.png"));
        shockwaveArr[index].setMaterial(mat);
        firework[index].attachChild(shockwaveArr[index]);
    }

   
    public void simpleInitApp() {
        startFirework=true;
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
        this.app.getRenderManager().preloadScene(firework[index]);
        //firework[index].setLocalTranslation(new Vector3f(x,y,z));
        
        
       

        

        this.app.getCamera().setLocation(new Vector3f(0, 3.5135868f, 10));
        this.app.getCamera().setRotation(new Quaternion(1.5714673E-4f, 0.98696727f, -0.16091813f, 9.6381607E-4f));

        this.app.getRootNode().attachChild(firework[index]);
        
        }
        
        
        firework[0].setLocalTranslation(3, 5, -10);
        firework[1].setLocalTranslation(-3, 5, -10);
        firework[2].setLocalTranslation(-5, 6, -10);
        firework[3].setLocalTranslation(5, 6, -10);
        firework[4].setLocalTranslation(4, 5, -10);
        firework[5].setLocalTranslation(-4, 5, -10);
        firework[6].setLocalTranslation(6, 6, -10);
        firework[7].setLocalTranslation(-6, 6, -10);
        firework[8].setLocalTranslation(7, 5, -10);
        firework[9].setLocalTranslation(-7,5, -10);
        
       
        initAudio();
    


    }
    
    private void initAudio() {
    /* gun shot sound is to be triggered by a mouse click. */
    //audioFirework = new AudioNode(assetManager, "Sound/Effects/Bang.wav", false);
    audioFirework = new AudioNode(this.app.getAssetManager(), "Sounds/Firework1.wav", false);

    audioFirework.setPositional(false);
    audioFirework.setLooping(false);
    audioFirework.setVolume(2);
    this.app.getRootNode().attachChild(audioFirework);
    }

    @Override
    public void update(float tpf){ 
        
       
        if(startFirework)
        {
        for(int index=0; index<10; index++)
        {
        time += tpf / (3*1.0);
        if (time > 1f && state == 0){
            
            sparkArr[index].emitAllParticles();
            
            shockwaveArr[index].emitAllParticles();
            state++;
            audioFirework.playInstance();
        }
        if (time > 1f + .05f / 1.0 && state == 1){
           
            roundsparkArr[index].emitAllParticles();
            state++;
        }
        
        // rewind the effect
        if (time > 5 / 1.0 && state == 2){
            state = 0;
            time = 0;
            sparkArr[index].killAllParticles();
            roundsparkArr[index].killAllParticles();
            shockwaveArr[index].killAllParticles();
        }
        }
        }
    }

}
