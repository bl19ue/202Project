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
public abstract class GumballColorRobot implements Speak
{
    public abstract void makeSound();
    public abstract void playNow();
    public abstract AudioNode ifLastStopped();
}
