/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.drug;

/**
 *
 * @author scheldejonas
 */
public interface DrugPriceModelInterface {
    
    /**
     * Use this on the specific drug, to change the price model to the drug.
     * @see BaseDrug
     */
    public void rollPrice();
    
    /**
     * Use this on the specific drug, to change the amount model to the drug.
     * @see BaseDrug
     */
    public void rollStock();
    
}
