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
public class PickOneOfTen implements DrugPriceModelInterface {
    
    private int[] priceArray = new int[]{150,230,180,2350,17,360,190,440,550};
    private int[] amountArray = new int[]{100,190,200000,2,95,30,165,185,250};

    @Override
    public int rollPrice(int superPrice) {
        Random random = new Random();
        return priceArray[random.nextInt(9)];
    }

    @Override
    public int rollStock(int superAmount) {
        Random random = new Random();
        return amountArray[random.nextInt(9)];
    }
    
}
