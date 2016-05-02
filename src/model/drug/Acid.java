/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import java.util.Random;
import model.BaseDrug;

/**
 *
 * @author CHRIS
 */
public class Acid extends BaseDrug {

    public Acid(int price, int amount) {
        super(price, amount);
    }

    @Override
    public String getName() {
        return "Acid";
    }
    
    @Override
    public void rollPrice() {
        Random r = new Random();
        setPrice(r.nextInt(12000) + 10);
    }

    @Override
    public void rollStock() {
        Random r = new Random();
        setAmount(r.nextInt(400) + 2);
    }
    
}