/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import java.util.Random;

/**
 *
 * @author scheldejonas
 */
public class CompletelyRandom implements DrugPriceModelInterface {

    @Override
    public int rollPrice(int superPrice) {
        Random r = new Random();
        return r.nextInt(12000) + 10;
    }

    @Override
    public int rollStock(int superAmount) {
        Random r = new Random();
        return r.nextInt(400) + 2;
    }
    
}
