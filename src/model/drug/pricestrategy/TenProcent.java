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
public class TenProcent implements DrugPriceModelInterface {
    
    int priceChanges = 0;
    int stockChanges = 0;

    @Override
    public int rollPrice(int superPrice) {
        priceChanges++;
        if ((priceChanges - 1) == 0) {
            return (int) (180 * 1.1);
        }
        int newPrice = (int) (superPrice * 1.1);
        return newPrice;
    }

    @Override
    public int rollStock(int superAmount) {
        stockChanges++;
        if ((stockChanges - 1) == 0) {
            return (int) (40 * 1.1);
        }
        int newAmount = (int) (superAmount * 1.1);
        return newAmount;
    }
    
}
