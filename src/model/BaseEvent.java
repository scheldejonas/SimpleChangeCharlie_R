/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author scheldejonas
 */
public abstract class BaseEvent {
    
    private int firePercentage;

    public BaseEvent() {
        this.firePercentage = 5;
    }

    public abstract void effect(Player player);

    /**
     * Default chance is 5%, override if other percentage is needed
     * @param player The player of whom it will check for effect trigger/changes
     * @return 
     */
    public boolean shouldFire(Player player) {
        Random r = new Random();
        int random = r.nextInt(100);
        return random <= firePercentage;
    }

    public int getFirePercentage() {
        return firePercentage;
    }

    public void setFirePercentage(int firePercentage) {
        this.firePercentage = firePercentage;
    }
    
}
