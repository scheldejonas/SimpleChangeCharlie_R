/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import java.util.ArrayList;

/**
 *
 * @author scheldejonas
 */
public class StockQuote implements DrugPriceModelInterface {
    
    private StockFinder stockHandler;
    private ArrayList<String> stockShortNames;
    private ArrayList<Double> recievedStockPrices;

    public StockQuote() {
        stockShortNames = new ArrayList();
        stockShortNames.add("ACUR");
        stockShortNames.add("ADMP");
        stockShortNames.add("AEY");
        stockShortNames.add("BV");
        stockShortNames.add("CSQ");
        stockShortNames.add("CALM");
        stockShortNames.add("CAMP");
        stockShortNames.add("CASH");
        stockShortNames.add("MCBI");
        stockShortNames.add("MGCD");
        stockShortNames.add("MFI");
        stockShortNames.add("MU");
        stockShortNames.add("MSFT");
        stockShortNames.add("PME");
        stockShortNames.add("PZZI");
        stockShortNames.add("PSTI");
        stockShortNames.add("SAFT");
        stockShortNames.add("SGNT");
        stockShortNames.add("SLXP");
        stockShortNames.add("GCVRZ");
        this.stockHandler = new StockFinder();
        recievedStockPrices = stockHandler.getStockData(stockShortNames);
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
