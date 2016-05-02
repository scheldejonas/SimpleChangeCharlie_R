/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.event;

import java.util.Random;
import javax.swing.JOptionPane;
import model.BaseDrug;
import model.BaseEvent;
import model.Player;

/**
 *
 * @author CHRIS
 */
public class MafiaTerritoryTrespass extends BaseEvent {
    
    @Override
    public void effect(Player player) {
        for (BaseDrug d : player.getDrugs()) {
            if (d.getAmount() > 0) {
                int confiscated = d.getAmount() / 2;
                if (d.getAmount() % 2 == 1) {
                    confiscated++;
                }
                d.remove(confiscated);
            }
        }
        player.takeDamage(10);
        player.removeMoney(player.getMoney());
        JOptionPane.showMessageDialog(null, "You have trespassed on mafia territory!" + System.lineSeparator() +
                                            "Half of all your drugs have been taken" + System.lineSeparator() + 
                                            "All of your money has been taken" + System.lineSeparator() + 
                                            "And you lose 10% of your health!");
    }

@Override
    public boolean shouldFire(Player player) {
        int change = (player.hasHighFriends() ? 2 : 0);
        change += (player.hasGun() ? 1 : 0);
        int firePercentage = getFirePercentage() - change;
        Random r = new Random();
        int random = r.nextInt(100);
        System.out.println("[MafiaTerritoryTrespass.shouldFire] random: " + random + ", firePercentage: " + firePercentage);
        return random <= firePercentage;
    }
    
}
