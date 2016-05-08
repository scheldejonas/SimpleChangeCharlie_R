package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
* The class contains helper method to get prices (stock quotes) for stocks. *
* Example of use: *
*       //Create a new ArrayList to hold stock names:
*       ArrayList<String> stockNames = new ArrayList();
* 
*       //Adding stock names
*       stockNames.add("Z"); //Zillow
*       stockNames.add("GOOG"); //Google.com
*       stockNames.add("FXCB"); //Fox Chase Bancorp, Inc.
*       stockNames.add("HBHC"); //Hancock Holding Company","36.5
*       stockNames.add("SCTY"); //SolarCity Corporation","75.1 *
* 
*       //New ArrayList to hold the answer:
*       ArrayList<Double> result;
*       result = StockFinder.getStockData(stockNames); //Getting the answer *
* 
*       //Running through the answer arraylist
*       for (Double price : result) {
*           System.out.println("Price: " + price);
*       }
*
* @author Peter Lorensen 
*/

public class StockFinder {

    /**
     * The method gets a list of stock names and returns the price for each one.
     *
     *
     * @param stockShortNames ArrayList<String> of stock names. These must be
     * the short names used by NASDAQ (like GOOG for google.com).
     * @return ArrayList<Double> the corosponding prices from each stock. * A
     * default of 1.0 is used if the price for a stock cannot be found and this
     * is also printed: System.out.println("Could not get stock quote and used
     * default value of 1.0 instead.");
     */
    public static ArrayList<Double> getStockData(ArrayList<String> stockShortNames) {
        ArrayList<Double> answer = new ArrayList<Double>();
        String infoToGet = "l1"; //Changing this will change the info you get. &f=snl1" l: stock quote. n: company name. s: short name
        String allStocks = "";
        String temp = "";
        
        //Running through the parameter and getting all the stock names into a format that Yahoo finance needs
        for (String stock : stockShortNames) {
            allStocks += stock + "+";
        }
        allStocks = allStocks.substring(0, allStocks.length() - 1); //Cutting of the last, extra '+'-sign
        try {
            //Creating a connection and support object and getting the stock prices from yahoo finance:
            // URL yahoofinance = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + allStocks + "&f=" + infoToGet);
            URL yahoofinance = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + allStocks + "&f=" + infoToGet);
            URLConnection yc = yahoofinance.openConnection(); //Opening a connection 
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())); //Creating a bufferedreader to hold stuff and loading it up instead 

            //Running through the answer
            while ((temp = in.readLine()) != null) {
                if (temp.equalsIgnoreCase("N/A")) { //Ifpriceisnotthere,then... { temp="1.0"; //..defaultvalueof1.0isused
                    System.out.println("Could not get stock quote and used default value of 1.0 instead.");
                }
            }
            answer.add(Double.parseDouble(temp)); //Addingpricetoresult arraylist
            in.close(); //Closing the stream 
        }
        //Error handling:
        catch (IOException ex) {
        System.out.println("Oops something went wrong in method getStockData(). Sorry. Try again.");
            System.out.println("Message from Exception: " + ex.getMessage());
            ex.printStackTrace();
        } 
        catch(NumberFormatException n) {
        System.out.println("Oops something went wrong in method getStockData(): A NumberFormatException was thrown because Double.parseDouble() tried to pass this: " + temp);
            System.out.println("Message from Exception: " + n.getMessage());
        }
        return answer ;
    }
    
}
