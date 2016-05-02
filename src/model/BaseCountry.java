/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;
import model.drug.*;

/**
 *
 * @author scheldejonas
 */
public abstract class BaseCountry {
    
    Player player;
    ArrayList<BaseDrug> drugs;
    Random r;
    private int healthPrice = 5000;
    
    public final void init() {
        r = new Random();
        if (drugs == null) {
            drugs = new ArrayList<>();
            Cocaine cocaine = new Cocaine(1200, 30);
            Heroin heroin = new Heroin(1600, 15);
            Amphetamine amphetamine = new Amphetamine(200, 50);
            Acid acid = new Acid(550, 33);
            AngelDust angelDust = new AngelDust(400, 60);
            CrystalMeth crystalMeth = new CrystalMeth(800, 38);
            Hash hash = new Hash(180, 100);
            Weed weed = new Weed(150, 115);
            Mushrooms mushrooms = new Mushrooms(120, 95);
            Valium valium = new Valium(290, 80);
            drugs.add(cocaine);
            drugs.add(heroin);
            drugs.add(amphetamine);
            drugs.add(acid);
            drugs.add(angelDust);
            drugs.add(crystalMeth);
            drugs.add(hash);
            drugs.add(weed);
            drugs.add(mushrooms);
            drugs.add(valium);
        }
        if (player != null && player.getTurn() != 0) {
            rollPrices();
            rollStock();
        }
    }

    public int getHealthPrice() {
        return healthPrice;
    }

    public void setHealthPrice(int healthPrice) {
        this.healthPrice = healthPrice;
    }
    
    public abstract String getName();
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public ArrayList<BaseDrug> getDrugs() {
        return drugs;
    }
    
    public BaseDrug getDrug(String drugName) {
        for (BaseDrug d : drugs) {
            if (d.getName().equals(drugName)) {
                return d;
            }
        }
        return null;
    }
    
    public int getPrice(String drugName, int amount) {
        BaseDrug d = getDrug(drugName);
        if (d != null) {
            return d.getPrice() * amount;
        }
        return -1;
    }
    
    /**
     * This is only removing the drug amount from the country, if the player has enough money for it at the runtime.
     * @param drugName
     * @param amount
     * @param playerMoney
     * @return true as done or throws an exception of why buying was not possible.
     * @throws Exception 
     */
    public boolean buyDrug(String drugName, int amount, int playerMoney) throws Exception {
        if (playerMoney >= getPrice(drugName, amount)) {
            BaseDrug d = getDrug(drugName);
            if (d != null) {
                if (d.getAmount() >= amount) {
                    d.remove(amount);
                    return true;
                } else {
                    throw new Exception("Not enough drugs in stock!");
                }
            } else {
                throw new Exception("No drug with the name " + drugName + " found!");
            }
        } else {
            throw new Exception("Not enough money to buy this amount!");
        }
    }
    
    public int sellStock(String drugName, int amount) {
        BaseDrug d = getDrug(drugName);
        if (d != null) {
            d.add(amount);
            return d.getPrice() * amount;
        }
        return 0;
    }
    
    public void rollHealthPrice() {
        int randomNumber = r.nextInt(100);
        if (randomNumber <= 65) { //There is a 65% chance to change the price of the health
            boolean increasePrice = r.nextBoolean();
            int priceChange = r.nextInt(85) + 1; 
            int currentPrice = getHealthPrice();
            int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
            int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
            setHealthPrice(newPrice);
        }
    }
    
    public void rollPrices() {
        for (int i = 0; i < drugs.size(); i++) {
            BaseDrug d = drugs.get(i);
            d.rollPrice();
        }
        rollHealthPrice();
    }
    
    public void rollStock() {
        for (int i = 0; i < drugs.size(); i++) {
            BaseDrug d = drugs.get(i);
            d.rollStock();
        }
    }
}
