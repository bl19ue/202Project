/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumit.robot.me;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;

/**
 *
 * @author Ken
 */
public class BlueGumball extends GumballColorRobot
{
    private Speak gumball;
    private AudioNode boom;
    public BlueGumball(Speak gumball, AssetManager assetManager)
    {
        this.gumball = gumball;
        try{
            boom = new AudioNode(assetManager, "Sounds/Blue.wav");
            
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
