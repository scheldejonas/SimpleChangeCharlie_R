/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.event;

import java.util.Random;
import javax.swing.JOptionPane;
import model.BaseEvent;
import model.Player;

/**
 *
 * @author CHRIS
 */
public class NewMinionPusher extends BaseEvent {

    @Override
    public void effect(Player player) {
        int amount = new Random().nextInt(19500) + 500;
        player.addMoney(amount);
        JOptionPane.showMessageDialog(null, "You aquired a new minion pusher!" + System.lineSeparator() +
                                            "You gain " + amount + " of money from him!");
    }

    @Override
    public boolean shouldFire(Player player) {
        int change = (player.hasHighFriends() ? 1 : 0);
        change += (player.hasGun() ? 2 : 0);
        int firePercentage = getFirePercentage() - change;
        Random r = new Random();
        int random = r.nextInt(100);
        System.out.println("[AngryPusher.shouldFire] random: " + random + ", firePercentage: " + firePercentage);
        return random <= firePercentage;
    }
    
}