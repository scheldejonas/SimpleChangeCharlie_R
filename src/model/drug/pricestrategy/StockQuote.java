/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

/**
 *
 * @author scheldejonas
 */
public class StockQuote implements DrugPriceModelInterface {

    @Override
    public int rollPrice(int superPrice) {
        return 0; //This is missing the calculations from Stock Quote
    }

    @Override
    public int rollStock(int superAmount) {
        return 0; //This is missing the calculations from Stock Quote
    }
    
}
