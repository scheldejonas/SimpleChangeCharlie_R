/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

import java.util.Calendar;
import java.util.TimeZone;
import model.BaseDrug;

/**
 *
 * @author CHRIS
 */
public class Weed extends BaseDrug {

    public Weed(int price, int amount) {
        super(price, amount);
    }
    
    @Override
    public String getName() {
        return "Weed";
    }
    
    private int getSeconds() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); 
        return cal.get(Calendar.SECOND);
    }
    
    @Override
    public void rollPrice() {
        setPrice(getSeconds() * 2);
    }

    @Override
    public void rollStock() {
        setAmount(getSeconds());
    }
    
}