/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author CHRIS
 */
public class ExcelReader {

    ArrayList<String> shortNameList;
    private String excelPathName = "NASDAQ companylist.xls";
    
    public ExcelReader() {
        System.out.println("Running Excel Reader");
        shortNameList = new ArrayList<>();
        File f = new File(excelPathName);
        try {
            Workbook book = Workbook.getWorkbook(f);
            Sheet sheet = book.getSheet(0);
            String line = "";
            int index = 0;
            while (line != null) {
                try {
                    line = sheet.getCell(0, index++).getContents();
                    if (line == null || line.isEmpty() || line.equals("")) {
                        break;
                    }
                    shortNameList.add(line);
                } catch (Exception e) {
                    if (e.getMessage().equals("2775")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Message:::: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Please use Excel 97-2003 type when reading from file.");
        }
        System.out.println("List is: " + shortNameList.size() + " long");
    }
 
    public ArrayList<String> getShortNames() {
        return shortNameList;
    }
    
}