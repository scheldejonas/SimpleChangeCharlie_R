/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import java.util.Random;
import model.BaseDrug;
import model.drug.pricestrategy.OldMemory;

/**
 *
 * @author CHRIS
 */
public class Cocaine extends BaseDrug {

    int priceChanges;
    int stockChanges;
    
    public Cocaine(int price, int amount) {
        super(price, amount);
        super.setPriceStrategy(new OldMemory());
    }
    
    @Override
    public String getName() {
        return "Cocaine";
    }
    
}