package jm3test.helloworld;
 
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 * @author base code by double1984, updated by zathras
 */
public class HelloPhysics_backup extends SimpleApplication implements PhysicsCollisionListener
{
  /*  public HelloPhysics(){
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }*/
 
  public static void main(String args[]) {
    HelloPhysics_backup app = new HelloPhysics_backup();
    app.start();
  }
 
  /** Prepare the Physics Application State (jBullet) */
  private BulletAppState bulletAppState;
 
  /** Prepare Materials */
  Material wall_mat;
  Material stone_mat;
  Material floor_mat;
 
  /** Prepare geometries and physical nodes for bricks and cannon balls. */
  private RigidBodyControl    brick_phy;
  private static final Box    box;
  private RigidBodyControl    ball_phy;
  private static final Sphere sphere;
  private RigidBodyControl    floor_phy;
  private static final Box    floor;
  
  private RigidBodyControl    gumball_phy;
 // private static final Spatial gumball;
  private int count=1;
 
  /** dimensions used for bricks and wall */
  private static final float brickLength = 0.48f;
  private static final float brickWidth  = 0.24f;
  private static final float brickHeight = 0.12f;
  
  private static int i =0;
  /**logging of error instead of println*/
  private static final Logger logger = Logger.getLogger(HelloPhysics.class.getName());
 
  static {
    /** Initialize the cannon ball geometry */
    sphere = new Sphere(32, 32, 0.4f, true, false);
    sphere.setTextureMode(Sphere.TextureMode.Projected);
    /** Initialize the brick geometry */
    box = new Box(brickLength, brickHeight, brickWidth);
    box.scaleTextureCoordinates(new Vector2f(1f, .5f));
    /** Initialize the floor geometry */
    floor = new Box(10f, 0.1f, 5f);
    floor.scaleTextureCoordinates(new Vector2f(3, 6));
    
  }
 
  @Override
  public void simpleInitApp() {
    /** Set up Physics Game */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    
    //ADDED THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     bulletAppState.getPhysicsSpace().addCollisionListener(this);
 
    /** Configure cam to look at scene */
    cam.setLocation(new Vector3f(0, 4f, 6f));
    cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
    /** Add InputManager action: Left click triggers shooting. */
    inputManager.addMapping("shoot", 
            new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(actionListener, "shoot");
    /** Initialize the scene, materials, and physics space */
    
    //add gumballmachine
    Spatial mymodel = assetManager.loadModel(
      "Textures/MyModel/GumballMachine.obj"); 
    mymodel.scale(0.6f,0.6f,0.6f);
  this.rootNode.attachChild(mymodel);
  DirectionalLight sun = new DirectionalLight();
  sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)));
  sun.setColor(ColorRGBA.White);
 // sun.setColor(new ColorRGBA(1,0,0,0.5f));
 rootNode.addLight(sun); 
  gumball_phy = new RigidBodyControl(0.0f);
  rootNode.addControl(gumball_phy);
  bulletAppState.getPhysicsSpace().add(gumball_phy);
 
  
  /** Initialize the scene, materials, and physics space */
    initMaterials();
 //   initWall();
    initFloor();
    initCrossHairs();
  }
 
  /**
   * Every time the shoot action is triggered, a new cannon ball is produced.
   * The ball is set up to fly from the camera position in the camera direction.
   */
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("shoot") && !keyPressed) {
        makeCannonBall();
      }
    }
  };
 
  /** Initialize the materials used in this scene. */
  public void initMaterials() {
    wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
    key.setGenerateMips(true);
    Texture tex = assetManager.loadTexture(key);
    wall_mat.setTexture("ColorMap", tex);
 
    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);
    stone_mat.setTexture("ColorMap", tex2);
 
    floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
    key3.setGenerateMips(true);
    Texture tex3 = assetManager.loadTexture(key3);
    tex3.setWrap(Texture.WrapMode.Repeat);
    floor_mat.setTexture("ColorMap", tex3);
  }
 
  /** Make a solid floor and add it to the scene. */
  public void initFloor() {
    Geometry floor_geo = new Geometry("Floor", floor);
    floor_geo.setMaterial(floor_mat);
    floor_geo.setLocalTranslation(0, -0.1f, 0);
    this.rootNode.attachChild(floor_geo);
    /* Make the floor physical with mass 0.0f! */
    floor_phy = new RigidBodyControl(0.0f);
    floor_geo.addControl(floor_phy);
    bulletAppState.getPhysicsSpace().add(floor_phy);
  }
 
  /** This loop builds a wall out of individual bricks. */
  public void initWall() {
    float startpt = brickLength / 4;
    float height = 0;
    for (int j = 0; j < 15; j++) {
      for (int i = 0; i < 6; i++) {
        Vector3f vt =
         new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
        makeBrick(vt);
      }
      startpt = -startpt;
      height += 2 * brickHeight;
    }
  }
 
  /** This method creates one individual physical brick. */
  public void makeBrick(Vector3f loc) {
    /** Create a brick geometry and attach to scene graph. */
    Geometry brick_geo = new Geometry("brick", box);
    brick_geo.setMaterial(wall_mat);
    rootNode.attachChild(brick_geo);
    /** Position the brick geometry  */
    brick_geo.setLocalTranslation(loc);
    /** Make brick physical with a mass > 0.0f. */
    brick_phy = new RigidBodyControl(2f);
    /** Add physical brick to physics space. */
    brick_geo.addControl(brick_phy);
    bulletAppState.getPhysicsSpace().add(brick_phy);
  }
 
  /** This method creates one individual physical cannon ball.
   * By defaul, the ball is accelerated and flies
   * from the camera position in the camera direction.*/
   public void makeCannonBall() {
    /** Create a cannon ball geometry and attach to scene graph. */
    Geometry ball_geo = new Geometry("cannon ball", sphere);
    ball_geo.setMaterial(stone_mat);
    rootNode.attachChild(ball_geo);
    /** Position the cannon ball  */
    ball_geo.setLocalTranslation(cam.getLocation());
    /** Make the ball physcial with a mass > 0.0f */
    ball_phy = new RigidBodyControl(1f);
    /** Add physical ball to physics space. */
    ball_geo.addControl(ball_phy);
    bulletAppState.getPhysicsSpace().add(ball_phy);
    /** Accelerate the physcial ball to shoot it. */
    ball_phy.setLinearVelocity(cam.getDirection().mult(25));
  }
 
  /** A plus sign used as crosshairs to help the player with aiming.*/
  protected void initCrossHairs() {
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");        // fake crosshairs :)
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
      settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);
  }

    private RigidBodyControl RigidBodyControl(float f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    public void collision(PhysicsCollisionEvent event) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
           count++;
         //   logger.log(Level.WARNING, "check this {0}!", 
         //             new Object[]{count});
        
        CollisionResults results = new CollisionResults();
    
//       gumball.collideWith(rootNode, results);
        logger.log(Level.WARNING, "size {0}!", new Object[]{results.size()});
            logger.log(Level.WARNING, "getObjectA {0}!", new Object[]{event.getObjectA().getCollisionShape()});
         //   logger.log(Level.WARNING, "result{0}!", new Object[]{results.getCollision(count).getGeometry().getName()});
            
            
            
        if(event.getObjectA().getCollisionShape().getCShape().equals(sphere)){
         
           
            logger.log(Level.WARNING, "insider @@@@{0}!", 
                      new Object[]{count});
            
        }
    }
}