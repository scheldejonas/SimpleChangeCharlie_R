/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.country;

import model.BaseCountry;

/**
 *
 * @author CHRIS
 */
public class USA extends BaseCountry {

    public USA() {
        init();
    }

    @Override
    public String getName() {
        return "USA";
    }
    
}