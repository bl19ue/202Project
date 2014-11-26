/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumit.robot.me;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jme3.audio.AudioSource.Status;

/**
 *
 * @author Ken
 */
public class GreenGumball extends GumballColorRobot
{
    private Speak gumball;
    private AudioNode boom;
    public GreenGumball(Speak gumball, AssetManager assetManager)
    {
        this.gumball = gumball;
        boom = new AudioNode(assetManager, "Sounds/Green.wav");
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
