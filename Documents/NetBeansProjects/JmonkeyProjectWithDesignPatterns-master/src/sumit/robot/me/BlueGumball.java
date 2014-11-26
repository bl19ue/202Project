/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;

/**
 *
 * @author Ken
 */
public class BlueGumball  implements Gumball
{
    private Gumball gumball;
    private AudioNode boom;
    public BlueGumball(Gumball gumball, AssetManager assetManager)
    {
        this.gumball = gumball;
        try{
            boom = new AudioNode(assetManager, "Sound/1.wav");
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
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
            if(gumball == null || gumball.ifLastStopped().getStatus() == AudioSource.Status.Stopped)
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
