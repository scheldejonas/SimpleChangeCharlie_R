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
public interface DrugPriceModelInterface {
    
    /**
     * Use this on the specific drug, to change the price model to the drug.
     * @see BaseDrug
     */
    public int rollPrice(int superPrice);
    
    /**
     * Use this on the specific drug, to change the amount model to the drug.
     * @see BaseDrug
     */
    public int rollStock(int superAmount);
    
}
