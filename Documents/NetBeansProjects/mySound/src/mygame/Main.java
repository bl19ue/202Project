package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource.Status;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    private AudioNode boom1;
    private AudioNode boom2;
    private AudioNode boom3;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        Gumball gumball = new BlueGumball(null, assetManager);
        gumball = new GreenGumball(gumball, assetManager);
        gumball = new RedGumball(gumball, assetManager);
        gumball.makeSound();
        
        /*
        boom1 = new AudioNode(assetManager, "Sound/1.wav");
        boom2 = new AudioNode(assetManager, "Sound/4.wav");
        boom3 = new AudioNode(assetManager, "Sound/3.wav");
        //boom1 = new AudioNode(assetManager, "Sound/3.wav");
        boolean flag1 = false, flag2 = false;
        boom1.play();
        while(true)
        {
            if(boom1.getStatus() == Status.Stopped && flag1 == false)
            {
                boom2.play();
                flag1 = true;
            }
            if(boom2.getStatus() == Status.Stopped && flag1 == true)
            {
                boom3.play();
                break;
            }
            
            
        }
      */
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
       
    }
}
//
