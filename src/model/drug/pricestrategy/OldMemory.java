/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import java.util.Random;

/**
 *
 * @author scheldejonas
 */
public class OldMemory implements DrugPriceModelInterface {
    
    int priceChanges;
    int stockChanges;

    @Override
    public int rollPrice(int superPrice) {
        Random r = new Random();
        priceChanges++;
        if ((priceChanges - 1) == 0) {
            return 1000;
        } else if ((priceChanges - 1) == 1) {
            boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
            int priceChange = r.nextInt(55) + 10;     //Amount (percentage) to increase/decrease price
            int currentPrice = superPrice;         //Get current price
            int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
            int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
            return newPrice + 75;
        }
        boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
        int priceChange = r.nextInt(25) + 10;     //Amount (percentage) to increase/decrease price
        int currentPrice = superPrice;         //Get current price
        int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
        int newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
        return newPrice + 17;
    }

    @Override
    public int rollStock(int superAmount) {
        Random r = new Random();
        stockChanges++;
        if ((stockChanges - 1) == 0) {
            return 100;
        } else if ((stockChanges - 1) == 1) {
            return superAmount+7;
        }
        return superAmount+13;
    }
    
}
