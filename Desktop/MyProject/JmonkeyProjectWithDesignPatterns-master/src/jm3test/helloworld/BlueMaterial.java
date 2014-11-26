/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author Anu
 */
public class BlueMaterial extends BallMaterial{
    
    public  BlueMaterial(AssetManager assetManager){
        super(assetManager);
    }

    @Override
    public void setColor() {
       this.setColor("Color", ColorRGBA.Blue);
    }
    
}
