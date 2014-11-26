/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.audio.AudioSource.Status;

/**
 *
 * @author Ken
 */
public class GreenGumball  implements Gumball
{
    Gumball gumball;
    private AssetManager assetManager;
    private AudioNode boom;
    public GreenGumball(Gumball gumball, AssetManager assetManager)
    {
        this.gumball = gumball;
        boom = new AudioNode(assetManager, "Sound/3.wav");
    }
    public void makeSound() 
    {
        if(gumball!=null)
            gumball.makeSound();
        playNow();
    }
    
    public synchronized void playNow()
    {
        while(true)
        {
            if(gumball.ifLastStopped().getStatus() == Status.Stopped)
            {
                boom.play();
                break;
            }
        }
    }
    public AudioNode ifLastStopped()
    {
        return boom;
    }
    
}
