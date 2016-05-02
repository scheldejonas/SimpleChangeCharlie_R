/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import model.BaseDrug;
import model.drug.pricestrategy.BasePrice;
import model.drug.pricestrategy.StockQuote;

/**
 *
 * @author CHRIS
 */
public class Valium extends BaseDrug {

    public Valium(int price, int amount) {
        super(price, amount);
        super.setPriceStrategy(new StockQuote());
    }
    
    @Override
    public String getName() {
        return "Valium";
    }
    
}