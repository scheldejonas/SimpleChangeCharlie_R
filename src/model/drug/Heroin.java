/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;


import model.BaseDrug;
import model.drug.pricestrategy.TenProcent;

/**
 *
 * @author CHRIS
 */
public class Heroin extends BaseDrug {

    public Heroin(int price, int amount) {
        super(price, amount);
        super.setPriceStrategy(new TenProcent());
    }
    
    @Override
    public String getName() {
        return "Heroin";
    }
    
}