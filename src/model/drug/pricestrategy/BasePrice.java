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
public class BasePrice implements DrugPriceModelInterface {
    
    Random r = new Random();

    @Override
    public int rollPrice(int superPrice) {
        int randomNumber = r.nextInt(100);
        int newPrice = superPrice;
        if (randomNumber <= 65) { //There is a 65% chance to change price for this drug
            boolean increasePrice = r.nextBoolean(); //Random if it should increase or decrease price
            int priceChange = r.nextInt(85) + 1;     //Amount (percentage) to increase/decrease price
            int currentPrice = superPrice;         //Get current price
            int priceDifference = (currentPrice * priceChange) / 100;   //Find price difference
            newPrice = currentPrice + (increasePrice ? priceDifference : -priceDifference);
            
            /*
            System.out.println((increasePrice ? "Increasing " : "Decreasing ") + d.getName() + " with " 
                             + priceChange + "%, currentPrice: " + currentPrice + ", Price difference: " + 
                               priceDifference + ", totaling: " + newPrice);
            */
        }
        return newPrice;
    }

    @Override
    public int rollStock(int superAmount) {
        int newStock = superAmount;
        if (r.nextInt(100) <= 65) { //There is a 65% chance to change stock for this drug
            boolean increaseStock = r.nextBoolean(); //Random if it should increase or decrease stock
            int stockChange = r.nextInt(41) + 15;     //Amount (percentage) to increase/decrease stock
            int currentStock = superAmount;         //Get current stock
            int stockDifference = (currentStock * stockChange) / 100;   //Find price difference
            newStock = currentStock + (increaseStock ? stockDifference : -stockDifference);
        }
        return newStock;
    }
    
}
