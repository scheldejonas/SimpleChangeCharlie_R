/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import model.BaseDrug;
import model.drug.pricestrategy.SimpleTwoChoice;

/**
 *
 * @author CHRIS
 */
public class Hash extends BaseDrug {
    
    public Hash(int price, int amount) {
        super(price, amount);
        super.setPriceStrategy(new SimpleTwoChoice());
    }
    
    @Override
    public String getName() {
        return "Hash";
    }
    
}