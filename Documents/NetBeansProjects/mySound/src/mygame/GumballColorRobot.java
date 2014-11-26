/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.audio.AudioNode;

/**
 *
 * @author Ken
 */
public interface Gumball 
{
    public void makeSound();
    public void playNow();
    public AudioNode ifLastStopped();
}
