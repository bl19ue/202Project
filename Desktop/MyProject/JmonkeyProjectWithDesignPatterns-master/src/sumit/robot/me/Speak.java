/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumit.robot.me;

import com.jme3.audio.AudioNode;

/**
 *
 * @author Ken
 */
public interface Speak 
{
    public void makeSound();
    public void playNow();
    public AudioNode ifLastStopped();
}
