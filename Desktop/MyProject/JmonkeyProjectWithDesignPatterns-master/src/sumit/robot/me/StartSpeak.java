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
public class StartSpeak implements Speak
{
    private Speak gumball;
    private AssetManager assetManager;
    private AudioNode boom;
    public StartSpeak(AssetManager assetManager)
    {
        boom = new AudioNode(assetManager, "Sounds/StartSpeak.wav");
    }
    public void makeSound() 
    {
        playNow();
    }
    public synchronized void playNow()
    {
        boom.play();
    }
    public AudioNode ifLastStopped()
    {
        return boom;
    }
}
