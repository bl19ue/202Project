/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Anu
 */
public class BallMaterialFactory {
    
    
    
    public BallMaterial getMaterial(int i, AssetManager assetManager){
        if (i==1){
            BallMaterial white = new WhiteMaterial(assetManager);
            white.setColor();
            return white;
        }
        if (i==2){
            BallMaterial magenta = new MagentaMaterial(assetManager);
            magenta.setColor();
            return magenta;
        }
        if (i==3){
            BallMaterial yellow = new YellowMaterial(assetManager);
            yellow.setColor();
            return yellow;
        }
        if (i==4){
            BallMaterial blue = new BlueMaterial(assetManager);
            blue.setColor();
            return blue;
        }
        if (i==5){
            BallMaterial green = new GreenMaterial(assetManager);
            green.setColor();
            return green;
        }
        return null;
    }
}
