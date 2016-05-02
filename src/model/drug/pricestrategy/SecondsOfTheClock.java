/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author scheldejonas
 */
public class SecondsOfTheClock implements DrugPriceModelInterface {
    
    private int getSeconds() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault()); 
        return cal.get(Calendar.SECOND);
    }

    @Override
    public int rollPrice(int superPrice) {
        return getSeconds() * 2;
    }

    @Override
    public int rollStock(int superAmount) {
        return getSeconds();
    }
    
}
