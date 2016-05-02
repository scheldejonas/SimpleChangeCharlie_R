/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

/**
 *
 * @author scheldejonas
 */
public class SimpleTwoChoice implements DrugPriceModelInterface {
    
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

    @Override
    public int rollPrice(int superPrice) {
        firstPrice = !firstPrice;
        if (firstPrice) {
            return prices[0][0];
        } else {
            return prices[1][0];
        }
    }

    @Override
    public int rollStock(int superAmount) {
        firstStock = !firstStock;
        if (firstStock) {
            return prices[0][1];
        } else {
            return prices[1][1];
        }
    }
    
    
    
}
