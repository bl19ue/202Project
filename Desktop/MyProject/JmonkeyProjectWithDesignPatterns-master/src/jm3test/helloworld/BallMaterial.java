/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

/**
 *
 * @author Anu
 */
public abstract class BallMaterial extends Material {
    
    public BallMaterial(AssetManager assetManager){
        super(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
    } 
    
    
    public abstract void setColor();
}
