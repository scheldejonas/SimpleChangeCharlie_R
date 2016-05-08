/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import java.util.ArrayList;
import model.ExcelReader;
import control.*;
import java.util.Random;
import model.StockFinder;

/**
 *
 * @author scheldejonas
 */
public class StockQuote implements DrugPriceModelInterface {
    
    private ArrayList<String> stockShortNamesPrices;
    private ArrayList<Double> recievedStockPrices;
    private ArrayList<String> stockShortNamesAmount;
    private ArrayList<Double> recievedStockAmount;
    private StockFinder stockFinder;

    public StockQuote() {
        stockShortNamesPrices = new ArrayList();
        recievedStockPrices = new ArrayList();
        stockShortNamesAmount = new ArrayList();
        recievedStockAmount = new ArrayList();
        initStockLists();
    }
    
    public void initStockLists() {
        
        Random r =  new Random();
        for (String string : MafiaGame.excelShortNames) {
            if (r.nextBoolean() && stockShortNamesPrices.size() < 20) {
                stockShortNamesPrices.add(string);
            }
            else if (r.nextBoolean() && stockShortNamesAmount.size() < 20) {
                stockShortNamesAmount.add(string);
            }
        }
        recievedStockPrices = stockFinder.getStockData(stockShortNamesPrices);
        recievedStockAmount = stockFinder.getStockData(stockShortNamesAmount);
    }

    @Override
    public int rollPrice(int superPrice) {
        return 0; //This is missing the calculations from Stock Quote
    }

    @Override
    public int rollStock(int superAmount) {
        return 0; //This is missing the calculations from Stock Quote
    }
    
}
