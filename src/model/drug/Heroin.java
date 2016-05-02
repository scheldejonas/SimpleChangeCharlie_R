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
public class Heroin extends BaseDrug implements DrugPriceModelInterface {

    public Heroin(int price, int amount) {
        super(180,40);
    }
    
    @Override
    public String getName() {
        return "Heroin";
    }
    
    @Override
    public void rollPrice() {
        int newPrice = (int) (super.getPrice() * 1.1);
        setPrice(newPrice);
    }
    
    @Override
    public void rollStock() {
        int newStock = (int) (super.getAmount() * 1.1);
        setAmount(newStock);
    }
    
}