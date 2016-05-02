/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.BaseEvent;
import model.BaseCountry;
import model.BaseDrug;
import java.util.ArrayList;
import model.country.*;
import model.event.*;
import model.*;
import view.MafiaGameWindow;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * 
 * @author CHRIS
 */
public final class MafiaGame {
    
    private Player player;
    private int currentTurn = 0;
    private BaseCountry currentCountry;
    private ArrayList<BaseCountry> countries;
    private ArrayList<BaseEvent> events;
    private ArrayList<Highscore> highscore;
    
    public static void main(String[] args) throws IOException {
        MafiaGame main = new MafiaGame();
    }

    public MafiaGame() {
        readHighscoresFromDatabase();
        printHighscore();
        initCountries();
        initEvents();
        String playerName = JOptionPane.showInputDialog("Please type your name", "firstname lastname");
        player = new Player(playerName, 5000);
        BaseCountry denmark = getCountry("Denmark");
        setCountry(denmark);
        MafiaGameWindow mafiaGameGui = new MafiaGameWindow(this);
    }
    
    public void readHighscoresFromDatabase() {
        try {
            highscore = DatabaseHandler.read();
        } catch (IOException ex) {
            Logger.getLogger(MafiaGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void EndGame() throws IOException {
        DatabaseHandler.write(player.getName(), "" + player.getMoney());
    }
    
    public void printHighscore() {
        for (Highscore h : highscore) {
            System.out.println(h.toString());
        }
    }
    
    public void initCountries() {
        countries = new ArrayList<>();
        countries.add(new Afghanistan());
        countries.add(new Colombia());
        countries.add(new Denmark());
        countries.add(new France());
        countries.add(new Germany());
        countries.add(new USA());
    }
    
    public void initEvents() {
        events = new ArrayList<>();
        events.add(new CustomsAuthorityEvent());
        events.add(new AngryPusherAssault());
        events.add(new MafiaTerritoryTrespass());
    }
    
    public void Travel(BaseCountry country) {     //TODO! Missing calling all events and other triggers upon travel!
        advanceTurn();                  //Important to do in this order, or Countries will not update their stock and prices
        setCountry(country);
        for (BaseCountry c : countries) {
            c.setPlayer(player);
            c.init();
        }
        for (BaseEvent e : events) {
            if (e.shouldFire(player)) {
                e.effect(player);
            }
        }
    }
    
    public void setCountry(BaseCountry country) {
        currentCountry = country;
        country.setPlayer(player);
    }
    
    public BaseCountry getCurrentCountry() {
        return currentCountry;
    }
    
    public int getTurn() {
        return currentTurn;
    }
    
    public void advanceTurn() {
        currentTurn++;
        player.advanceTurn();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public ArrayList<BaseCountry> getCountries() {
        return countries;
    }
    
    public ArrayList<BaseEvent> getEvents() {
        return events;
    }
    
    public BaseCountry getCountry(String name) {
        for (BaseCountry c : countries) {
            String countryName = c.getClass().toString().replaceAll("class model.country.", "");
            if (countryName.equals(name)) {
                return c;
            }
        }
        return null;
    }

    public int getPlayerCurrentDrugAmount(String selectedSellingDrugName) {
        BaseDrug drug = getPlayer().getDrug(selectedSellingDrugName);
        if (drug != null) {
            int amount = drug.getAmount();
            return amount;
        }
        return 0;
    }

    public int getCountryCurrentDrugAmount(String selectedBuyingDrugName) {
        BaseCountry country = getCurrentCountry();
        BaseDrug drug = country.getDrug( selectedBuyingDrugName );
        int amount = drug.getAmount();
        return amount;
    }

    /**
     * Buys the drug from the country to the player, if the player can afford it.
     * @param drugName
     * @param drugAmount
     * @param price
     * @throws java.lang.Exception
     */
    public void buyDrug(String drugName, int drugAmount, int price) throws Exception {
        currentCountry.buyDrug(drugName, drugAmount, player.getMoney());
        BaseDrug newPlayerDrug = new BaseDrug(0, drugAmount).getNewBaseDrug(drugName);
        player.buyDrug(newPlayerDrug, price);
    }
    
    public void sellDrug(String drugName, int drugAmount) throws Exception {
        int price = currentCountry.sellStock(drugName, drugAmount);
        BaseDrug newPlayerDrug = new BaseDrug(0, drugAmount).getNewBaseDrug(drugName);
        player.sellDrug(newPlayerDrug, drugAmount, price);
    }

    /**
     * For 
     * @return 
     */
    public ArrayList<Highscore> getHighscores() {
        Collections.sort(highscore);
        return highscore;
    }

    /**
     * Buy's life to the player, by first calculationg the cost of the life, based on life price changes from each country travel.
     * After that removes money from the player's wallet, and heals the player's life, with the bought hp percentages.
     * 
     * @param lifeBought 
     * @return true if player had enough money to buy the life, otherwise returns false for displaying insufficient funds message
     */
    public boolean buyLife(int lifeBought) {
        int lifeCost = lifeBought * getCurrentCountry().getHealthPrice();   //check afford
        if (player.getMoney() >= lifeCost) { //if afford - do buying
            player.removeMoney(lifeCost);
            player.healDamage(lifeBought);
            return true;
        }
        return false;
    }

    /**
     * Changes the chance of an event happening, while traveling between country's permanently for the rest of the game time.
     * @param percentLessChance 
     */
    public void changeEventChance(int percentLessChance) {
        for (BaseEvent e : events) {
            int currentChance = e.getFirePercentage(); //Get the current chance of fire an effect in BaseEvent
            int newPercentChance = currentChance - percentLessChance; //Calculate the new percentChance for shouldFire method's
            if (newPercentChance > 2) {
                e.setFirePercentage(newPercentChance); //Set the new chance percent to the event's
            }
            //System.out.println("Event: " + e.toString() + "FirePercent: " + e.getFirePercentage() ); //TEST
        }
        
    }

    /**
     * Buy the highfriends that changes the event chance of getting caught during travel.
     * @param highFriendPrice
     * @param highFriendPercentDecrease
     * @return true or false depending on player's deep enough pockets.
     */
    public boolean buyHighFriend(int highFriendPrice) {
        if (player.getMoney() > highFriendPrice) { //Check if player can afford it.
            player.removeMoney(highFriendPrice); //Pay from players wallet.
            player.setHighFriends(true);
            return true;
        }
        return false;
    }
    
    public boolean buyGun(int gunPrice) {
        if (player.getMoney() > gunPrice) { //Check if player can afford it.
            player.removeMoney(gunPrice); //Pay from players wallet.
            player.setGun(true);
            return true;
        }
        return false;
    }
    
}
