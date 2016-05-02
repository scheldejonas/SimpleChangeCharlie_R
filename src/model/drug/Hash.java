/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import model.BaseDrug;

/**
 *
 * @author CHRIS
 */
public class Hash extends BaseDrug {

    int[][] prices = {
        //White
        {
            //Price
            90,
            //Stock
            50
        },
        //Black
        {
            //Price
            90,
            //Stock
            50
        }
    };
    boolean firstPrice, firstStock;
    
    public Hash(int price, int amount) {
        super(price, amount);
    }
    
    @Override
    public String getName() {
        return "Hash";
    }
    
    @Override
    public void rollPrice() {
        firstPrice = !firstPrice;
        if (firstPrice) {
            setPrice(prices[0][0]);
        } else {
            setPrice(prices[1][0]);
        }
    }

    @Override
    public void rollStock() {
        firstStock = !firstStock;
        if (firstStock) {
            setAmount(prices[0][1]);
        } else {
            setAmount(prices[1][1]);
        }
    }
    
}