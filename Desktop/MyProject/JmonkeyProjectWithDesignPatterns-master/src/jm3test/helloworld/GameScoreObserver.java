/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;

import com.jme3.app.Application;
import com.jme3.audio.AudioNode;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author samirarya
 */
public class GameScoreObserver extends AbstractAppState implements GameObserver {

    private HelloPhysics app;
    int misses = 0;
    Firework firework;

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (HelloPhysics) app;

        firework = this.app.firework;// cast to a more specific class

        // init stuff that is independent of whether state is PAUSED or RUNNING
        // call custom methods...
    }

    public void update(boolean hit, int noOfHits, int noOfBalls) {

        


        if (hit) {
            
            AudioNode audioFirework = new AudioNode(this.app.getAssetManager(), "Sounds/success.wav", false);
            audioFirework.setPositional(false);
            audioFirework.setLooping(false);
            audioFirework.setVolume(2);
            this.app.getRootNode().attachChild(audioFirework);
            audioFirework.play();
        } else {
            AudioNode audioFirework = new AudioNode(this.app.getAssetManager(), "Sounds/boing.wav", false);
            audioFirework.setPositional(false);
            audioFirework.setLooping(false);
            audioFirework.setVolume(2);
            this.app.getRootNode().attachChild(audioFirework);
            audioFirework.play();
        }
        
        AudioNode audioFirework;
        if (noOfBalls == 5) {

            if (noOfHits > 3) {
                firework.simpleInitApp();
            } else {
                audioFirework = new AudioNode(this.app.getAssetManager(), "Sounds/game_over.wav", false);
                audioFirework.setPositional(false);
                audioFirework.setLooping(false);
                audioFirework.setVolume(2);
                this.app.getRootNode().attachChild(audioFirework);
                audioFirework.play();
            }
            //if(audioFirework.getStatus() == )
            //this.app.scoreballs.getOrder();
            //this.app.scoreballs.point();
            this.app.showScore(this.app.scoreballs);
            this.app.gumballrobot.makeSound();
        }
    }
}
