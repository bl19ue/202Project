/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jm3test.helloworld;

/**
 *
 * @author samirarya
 */
public interface GameSubject {
    public void attach(GameScoreObserver o);
    public void notifyGameObserver();
}
