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

import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

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
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jm3test.helloworld.GameScoreObserver;
import sumit.robot.me.*;
import akrati.score.me.*;
/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 *
 * @author base code by double1984, updated by zathras
 */
public class HelloPhysics extends SimpleApplication implements PhysicsCollisionListener, GameSubject {
    /*Dhanu */

    private GameScoreObserver observer;
    public Firework firework;

    public void attach(GameScoreObserver o) {
        observer = o;
    }

    public void notifyGameObserver() {
        observer.update(collisionFlag, hit_count, ball_count);
    }

    public static void main(String args[]) {
        HelloPhysics app = new HelloPhysics();
        app.start();
        //app.setDisplayFps(false);
        //app.setDisplayStatView(false);
    }
    /*Dhanu*/
    /**
     * Prepare the Physics Application State (jBullet)
     */
    private BulletAppState bulletAppState;
    /**
     * Prepare Materials
     */
    Material wall_mat;
    Material stone_mat;
    Material floor_mat;
    /**
     * Prepare geometries and physical nodes for bricks and cannon balls.
     */
    private static final Box box;
    private RigidBodyControl ball_phy;
    private static final Sphere sphere;
    private RigidBodyControl floor_phy;
    private static final Box floor;
    private RigidBodyControl gumball_phy;
    private static final Spatial gumball;
    private int ball_count = 0;
    private int gumball_count = 0;
    private int ref = 0;
    private int hit_count = 0;
    private boolean flag = false;
    public static boolean collisionFlag = false;
    public boolean ack = false;
    public boolean isAction = false;
    public boolean isCollision = false;
    private float time = 0;
    private boolean countTime = false;
    /**
     * dimensions used for bricks and wall
     */
    private static final float brickLength = 0.48f;
    private static final float brickWidth = 0.24f;
    private static final float brickHeight = 0.12f;
    /**
     * logging of error instead of println
     */
    private static final Logger logger = Logger.getLogger(HelloPhysics.class.getName());

    static {
        /**
         * Initialize the cannon ball geometry
         */
        sphere = new Sphere(32, 32, 0.4f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
        /**
         * Initialize the brick geometry
         */
        box = new Box(brickLength, brickHeight, brickWidth);
        box.scaleTextureCoordinates(new Vector2f(1f, .5f));
        /**
         * Initialize the floor geometry
         */
        floor = new Box(10f, 0.1f, 5f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));

        gumball = new Spatial("Gumball") {
            @Override
            public void updateModelBound() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setModelBound(BoundingVolume modelBound) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getVertexCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getTriangleCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Spatial deepClone() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void depthFirstTraversal(SceneGraphVisitor visitor) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void breadthFirstTraversal(SceneGraphVisitor visitor, Queue<Spatial> queue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public int collideWith(Collidable other, CollisionResults results) throws UnsupportedCollisionException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            }
        };
        sphere.setTextureMode(TextureMode.Projected);
    }

    @Override
    public void simpleInitApp() {
        /**
         * Set up Physics Game
         */
        /**
         * *******vimp*******************
         */
        
        observer = new GameScoreObserver();
        firework = new Firework();
        stateManager.attach(observer);
        stateManager.attach(firework);
        /*vimp*/

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //bulletAppState.getPhysicsSpace().enableDebug(assetManager);

        //ADDED THIS for collision detection of gumball and cannon ball
        bulletAppState.getPhysicsSpace().addCollisionListener(this);


        /**
         * Configure cam to look at scene
         */
        cam.setLocation(new Vector3f(0, 4f, 6f));
        cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
        /**
         * Add InputManager action: Left click triggers shooting.
         */
        inputManager.addMapping("shoot",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "shoot");

        /**
         * Initialize the scene, materials, and physics space
         */
        //add gumballmachine
        Spatial mymodel = assetManager.loadModel(
                "Textures/MyModel/GumballMachine.obj");
        mymodel.scale(0.6f, 0.6f, 0.6f);
        this.rootNode.attachChild(mymodel);
        /*DirectionalLight sun = new DirectionalLight();
         sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)));
         sun.setColor(ColorRGBA.White);
         rootNode.addLight(sun); */

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        gumball_phy = new RigidBodyControl(0.0f);
        rootNode.addControl(gumball_phy);
        bulletAppState.getPhysicsSpace().add(gumball_phy);


        /**
         * Initialize the scene, materials, and physics space
         */
        initMaterials();
        initFloor();
        initCrossHairs();
        gumballrobot = new StartSpeak(assetManager);

    }

    //Displaying gumball when game ends
    public void displayGumball() {
        // add image

        if (gumball_count == 4) {
            gumball_count++;
        }
        /*    logger.log(Level.WARNING, "before{0} !", new Object[]{gumball_count});
         else    gumball_count--;  
         logger.log(Level.WARNING, "after{0} !", new Object[]{gumball_count});*/

        for (int g = 0; g < gumball_count; g++) {
            Picture pic = new Picture("Gumball Picture");
            pic.setImage(assetManager, "Interface/gumball.png", true);
            pic.setWidth(settings.getWidth() / 8);
            pic.setHeight(settings.getHeight() / 8);
            pic.setPosition(settings.getWidth() / 4 + g * 125, settings.getHeight() / 12);
            guiNode.attachChild(pic);

        }

    }
    /**
     * Every time the shoot action is triggered, a new cannon ball is produced.
     * The ball is set up to fly from the camera position in the camera
     * direction.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {

            if ((name.equals("shoot") && !keyPressed) && ball_count <= 5) { ///// should be <5


                makeCannonBall();

                ball_count++;
                increaseGumballCount();

                int i = 0;
                isAction = true;


            }
            //display gumballs when game ends
            if (ref == 0 && ball_count == 5) {
                displayGumball();
                
                //         logger.log(Level.WARNING, "After game over gumballs earned! {0} !", new Object[]{gumball_count});
                ref++;
            }

            collisionFlag = false;
        }
    };
    
    public void showScore(Balls ball)
    {
        BitmapText hudText = new BitmapText(guiFont, false);          
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.White);                             // font color
        if(!scoreFlag)
            ball = new Nohit();
        System.out.println(ball_count);
        hudText.setText(ball.getOrder()+" "+ ball.point());             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 10); // position
        guiNode.attachChild(hudText);
    }
    public Speak gumballrobot;
    public Balls scoreballs;
    public boolean scoreFlag = false;
    @Override
    public void simpleUpdate(float tpf) {
        if (countTime) {
            time += tpf / 3;
        }
        if (isAction) {
            countTime = true;
            //time=0;

            //logger.log(Level.WARNING, ""+ball_count+"hello  "+time+"  collision flag  "+collisionFlag+" can you see me? "+gumball_count+"", new Object[]{true});

            if (time > 0.1f) {
                //if(isCollision)
                isAction = false;
                if (collisionFlag == true) {
                    hit_count++;
                    scoreFlag = true;
                    switch(randomColor)
                    {
                        
                        case 1:{
                            gumballrobot = new WhiteGumball(gumballrobot, assetManager);
                            scoreballs = new W(scoreballs);
                            break;
                        }
                        case 2:{
                            gumballrobot = new MagentaGumball(gumballrobot, assetManager);
                            scoreballs = new M(scoreballs);
                            break;
                        }
                        case 3:{
                            gumballrobot = new YellowGumball(gumballrobot, assetManager);
                            scoreballs = new Y(scoreballs);
                            break;
                        }
                        case 4:{
                            gumballrobot = new BlueGumball(gumballrobot, assetManager);
                            scoreballs = new B(scoreballs);
                            break;
                        }
                        case 5:{
                            gumballrobot = new GreenGumball(gumballrobot, assetManager);
                            scoreballs = new G(scoreballs);
                            break;
                        }
                    }
                        
                }
                logger.log(Level.WARNING, "" + ball_count + "hello  " + time + "  collision flag  " + collisionFlag + " can you see me? " + hit_count + "", new Object[]{true});

                notifyGameObserver();
                collisionFlag = false;
                time = 0;
                countTime = false;

            }


        }

    }

    public void increaseGumballCount() {
        if (flag == true) {
            gumball_count++;
            flag = false;

        }
    }

    /**
     * Initialize the materials used in this scene.
     */
    public void initMaterials() {
        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
        key3.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(key3);
        tex3.setWrap(WrapMode.Repeat);
        floor_mat.setTexture("ColorMap", tex3);
    }

    /**
     * Make a solid floor and add it to the scene.
     */
    public void initFloor() {
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setLocalTranslation(0, -0.1f, 0);
        this.rootNode.attachChild(floor_geo);
        /* Make the floor physical with mass 0.0f! */
        floor_phy = new RigidBodyControl(0.0f);
        floor_geo.addControl(floor_phy);
        //   bulletAppState.getPhysicsSpace().add(floor_phy);
    }

    /**
     * This loop builds a wall out of individual bricks.
     */
    public void initWall() {
        float startpt = brickLength / 4;
        float height = 0;
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 6; i++) {
                Vector3f vt =
                        new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
//        makeBrick(vt);
            }
            startpt = -startpt;
            height += 2 * brickHeight;
        }
    }

    /**
     * This method creates one individual physical cannon ball. By defaul, the
     * ball is accelerated and flies from the camera position in the camera direction.
     */
    int randomColor = 0;
    ArrayList<String> number = new ArrayList<String>();
    public void makeCannonBall() {


        /**
         * Create a cannon ball geometry and attach to scene graph.
         */
        Geometry ball_geo = new Geometry("cannon ball", sphere);

        //FACTORY pattern used to set the color of the ball
        BallMaterialFactory ballMaterialFactory = new BallMaterialFactory();

       
        Random random = new Random();
        //for (int m = 1; m < 6; m++) {
            randomColor = random.nextInt(5)+1;
            //System.out.println(randomColor);

            ball_geo.setMaterial(ballMaterialFactory.getMaterial(randomColor, assetManager));
            //System.out.println(ballMaterialFactory.getMaterial(randomColor, assetManager));
            //    logger.log(Level.WARNING, "random {0}!", new Object[]{randomColor});
        //}
        
        //number.add(ballMaterialFactory.getMaterial(randomColor, assetManager).toString());
        
        //  ball_geo.setMaterial(ballMaterialFactory.getMaterial(1, assetManager));
        rootNode.attachChild(ball_geo);
        /**
         * Position the cannon ball
         */
        ball_geo.setLocalTranslation(cam.getLocation());
        /**
         * Make the ball physcial with a mass > 0.0f
         */
        ball_phy = new RigidBodyControl(1f);
        /**
         * Add physical ball to physics space.
         */
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        /**
         * Accelerate the physcial ball to shoot it.
         */
        ball_phy.setLinearVelocity(cam.getDirection().mult(25));
    }

    /**
     * A plus sign used as crosshairs to help the player with aiming.
     */
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


        //collisions only detected here.
        Spatial.BatchHint a = gumball.getBatchHint();
        
        if (a.toString().equalsIgnoreCase("Always")) {
            flag = true;
            collisionFlag = true;
            //System.out.println(randomColor);

        }

        //    logger.log(Level.WARNING, "getBatchHInt {0} !", new Object[]{true});
    }
}
