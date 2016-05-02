/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;
import model.drug.Acid;
import model.drug.Amphetamine;
import model.drug.AngelDust;
import model.drug.Cocaine;
import model.drug.CrystalMeth;
import model.drug.Hash;
import model.drug.Heroin;
import model.drug.Mushrooms;
import model.drug.Valium;
import model.drug.Weed;

/**
 *
 * @author scheldejonas
 */
public class BaseDrug {
    
    int price;
    int amount;
    Random r;

    public BaseDrug(int price, int amount) {
        r = new Random();
        this.price = price;
        this.amount = amount;
    }
    
    public BaseDrug getNewBaseDrug(String drugName) {
        switch (drugName) {
            case "Acid": 
                return new Acid(price, amount);
            case "Amphetamine": 
                return new Amphetamine(price, amount);
            case "Angel Dust": 
                return new AngelDust(price, amount);
            case "Cocaine": 
                return new Cocaine(price, amount);
            case "Crystal Meth": 
                return new CrystalMeth(price, amount);
            case "Hash":
                return new Hash(price, amount);
            case "Heroin": 
                return new Heroin(price, amount);
            case "Mushrooms":
                return new Mushrooms(price, amount);
            case "Valium":
                return new Valium(price, amount);
            case "Weed": 
                return new Weed(price, amount);
        }
        return null;
    }
    
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getAmount() {
        return amount;
    }
        
    public void remove(int amount) {
        this.amount -= amount;
    }
        
    public void add(int amount) {
        this.amount += amount;
    }
    
    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    public String getName() {
        return "null";
    }
    
    public void rollPrice() {
        int randomNumber = r.nextInt(100);
        if (randomNumber <= 65) { //There is a 65% chance to change price for this drug
            boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
            int priceChange = r.nextInt(85) + 1;     //Amount (percentage) to increase/decrease price
            int currentPrice = getPrice();         //Get current price
            int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
            int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
            setPrice(newPrice);
            /*
            System.out.println((increasePrice ? "Increasing " : "Decreasing ") + d.getName() + " with " 
                             + priceChange + "%, currentPrice: " + currentPrice + ", Price difference: " + 
                               priceDifference + ", totaling: " + newPrice);
            */
        }
    }
    
    public void rollStock() {
        if (r.nextInt(100) <= 65) { //There is a 65% chance to change stock for this drug
            boolean increaseStock = r.nextBoolean(); //Random if it should increase or decrease stock
            int stockChange = r.nextInt(41) + 15;     //Amount (percentage) to increase/decrease stock
            int currentStock = getAmount();         //Get current stock
            int stockDifference = (currentStock * stockChange) / 100;   //Find price difference
            setAmount(currentStock + (increaseStock ? stockDifference : -stockDifference));
        }
    }
    
}
