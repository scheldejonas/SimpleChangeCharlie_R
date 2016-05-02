/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import java.util.Random;
import model.BaseDrug;

/**
 *
 * @author CHRIS
 */
public class Cocaine extends BaseDrug {

    int priceChanges, stockChanges;
    
    public Cocaine(int price, int amount) {
        super(price, amount);
    }
    
    @Override
    public String getName() {
        return "Cocaine";
    }

    @Override
    public void rollPrice() {
        Random r = new Random();
        priceChanges++;
        if ((priceChanges - 1) == 0) {
            setPrice(1000);
            return;
        } else if ((priceChanges - 1) == 1) {
            boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
            int priceChange = r.nextInt(55) + 10;     //Amount (percentage) to increase/decrease price
            int currentPrice = getPrice();         //Get current price
            int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
            int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
            setPrice(newPrice + 75);
            return;
        }
        boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
        int priceChange = r.nextInt(25) + 10;     //Amount (percentage) to increase/decrease price
        int currentPrice = getPrice();         //Get current price
        int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
        int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
        setPrice(newPrice + 17);
    }

    @Override
    public void rollStock() {
        Random r = new Random();
        stockChanges++;
        if ((stockChanges - 1) == 0) {
            setAmount(100);
            return;
        } else if ((stockChanges - 1) == 1) {
            add(7);
            return;
        }
        add(13);
    }

    
    
}