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
    private static ArrayList<Double> recievedStockPrices;
    private ArrayList<String> stockShortNamesAmount;
    private static ArrayList<Double> recievedStockAmount;
    private StockFinder stockFinder;
    static boolean initialized;

    public StockQuote() {
        stockShortNamesPrices = new ArrayList();
        recievedStockPrices = new ArrayList();
        stockShortNamesAmount = new ArrayList();
        recievedStockAmount = new ArrayList();
        if (!initialized) {
            initStockLists();
            initialized = true;
        }
    }
    
    public void initStockLists() {
        int size = MafiaGame.excelShortNames.size();
        Random r =  new Random();
        for (int i = 0; i < 20; i++) {
            int random = r.nextInt(size);
            stockShortNamesAmount.add(MafiaGame.excelShortNames.get(random));
        }
        for (int i = 0; i < 20; i++) {
            int random = r.nextInt(size);
            stockShortNamesPrices.add(MafiaGame.excelShortNames.get(random));
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
