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
public class CrystalMeth extends BaseDrug {

    public CrystalMeth(int price, int amount) {
        super(price, amount);
    }
    
    @Override
    public String getName() {
        return "Crystal Meth";
    }

}