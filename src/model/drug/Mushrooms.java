/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import model.BaseDrug;
import model.drug.pricestrategy.SecondsOfTheClock;

/**
 *
 * @author CHRIS
 */
public class Mushrooms extends BaseDrug {

    public Mushrooms(int price, int amount) {
        super(price, amount);
        super.setPriceStrategy(new SecondsOfTheClock());
    }
    
    @Override
    public String getName() {
        return "Mushrooms";
    }

}