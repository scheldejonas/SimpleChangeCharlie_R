/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug.pricestrategy;

import model.StockFinder;
import java.util.ArrayList;

/**
 *
 * @author scheldejonas
 */
public class StockQuote implements DrugPriceModelInterface {
    
    private StockFinder excelReader;
    private ArrayList<String> stockShortNamesPrices;
    private ArrayList<Double> recievedStockPrices;
    private ArrayList<String> stockShortNamesAmount;
    private ArrayList<Double> recievedStockAmount;

    public StockQuote() {
        stockShortNamesPrices = new ArrayList();
    }
    
    public void initPriceList() {
        stockShortNamesPrices.add("ACUR");
        stockShortNamesPrices.add("ADMP");
        stockShortNamesPrices.add("AEY");
        stockShortNamesPrices.add("BV");
        stockShortNamesPrices.add("CSQ");
        stockShortNamesPrices.add("CALM");
        stockShortNamesPrices.add("CAMP");
        stockShortNamesPrices.add("CASH");
        stockShortNamesPrices.add("MCBI");
        stockShortNamesPrices.add("MGCD");
        stockShortNamesPrices.add("MFI");
        stockShortNamesPrices.add("MU");
        stockShortNamesPrices.add("MSFT");
        stockShortNamesPrices.add("PME");
        stockShortNamesPrices.add("PZZI");
        stockShortNamesPrices.add("PSTI");
        stockShortNamesPrices.add("SAFT");
        stockShortNamesPrices.add("SGNT");
        stockShortNamesPrices.add("SLXP");
        stockShortNamesPrices.add("GCVRZ");
        this.excelReader = new StockFinder();
        recievedStockPrices = excelReader.getStockData(stockShortNamesPrices);
    }
    
    public void initAmountList() {
        stockShortNamesAmount.add("MPWR");
        stockShortNamesAmount.add("TYPE");
        stockShortNamesAmount.add("MRCC");
        stockShortNamesAmount.add("MONT");
        stockShortNamesAmount.add("OSBC");
        stockShortNamesAmount.add("OXLC");
        stockShortNamesAmount.add("OXGN");
        stockShortNamesAmount.add("PCAR");
        stockShortNamesAmount.add("PCO");
        stockShortNamesAmount.add("ACUR");
        stockShortNamesAmount.add("PDLI");
        stockShortNamesAmount.add("PENN");
        stockShortNamesAmount.add("PELT");
        stockShortNamesAmount.add("PEOP");
        stockShortNamesAmount.add("PFBX");
        stockShortNamesAmount.add("PBCT");
        stockShortNamesAmount.add("PWRD");
        stockShortNamesAmount.add("SEAC");
        stockShortNamesAmount.add("STX");
        stockShortNamesAmount.add("SMTC");
        
        this.excelReader = new StockFinder();
        recievedStockAmount = excelReader.getStockData(stockShortNamesAmount);
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
