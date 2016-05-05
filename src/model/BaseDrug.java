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
import model.drug.pricestrategy.BasePrice;
import model.drug.pricestrategy.DrugPriceModelInterface;

/**
 *
 * @author scheldejonas
 */
public class BaseDrug {
    
    int price;
    int amount;
    Random r;
    private DrugPriceModelInterface priceStrategy;

    public BaseDrug(int price, int amount) {
        r = new Random();
        this.price = price;
        this.amount = amount;
        priceStrategy = new BasePrice();
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
    
    public DrugPriceModelInterface getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(DrugPriceModelInterface priceStrategy) {
        this.priceStrategy = priceStrategy;
    }
    
    public void rollPrice() {
        setPrice( priceStrategy.rollPrice(price));
        setPrice( goldeNumber(price) );
    }
    
    public void rollStock() {
        setAmount( priceStrategy.rollStock(amount));
    }
    
    public int goldeNumber(int finalPrice) {
        if (r.nextInt(100) < 12) { //Use Golden Number if there is 12% chaance
            if (r.nextInt(2) < 1) { // Determine if the price goes up or down by 50%
                return (int) (finalPrice * 0.1); //Goes down by 10 times.
            }
            else { //Or goes up by 50%
                return finalPrice * 10; //Goes up by 10 times
            }
        }
        return finalPrice; //Golden Number chance was not inside 12% chance.
    }

}
