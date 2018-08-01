/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wguc482;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mac
 */
public class Outsourced extends Part {

    
    private  SimpleStringProperty company = new SimpleStringProperty();
    
    
    public Outsourced(Integer Id, String name, Integer inStock, Double price, Integer min, Integer max,String company) {
        super(Id, name, inStock, price, min, max);
        setCompany(company);
    }
    
   

   
   

 
 

    public SimpleStringProperty companyProperty() {
        return company;
    }
    
    public void setCompany(String companyName){
        
        company.set(companyName);
    }  
    
    
    
    
    
    
}
