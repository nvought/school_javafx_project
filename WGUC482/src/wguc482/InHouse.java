/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wguc482;

import javafx.beans.property.SimpleIntegerProperty;


/**
 *
 * @author mac
 */
public class InHouse extends Part {

    
    private final  SimpleIntegerProperty machineID = new SimpleIntegerProperty();
    
    
    public InHouse(Integer Id, String name, Integer inStock, Double price, Integer min, Integer max,Integer machineID) {
        super(Id, name, inStock, price, min, max);
        setMachineID(machineID);
    }
    
   

   
   

 
 

    public SimpleIntegerProperty machineIDProperty() {
        return machineID;
    }
    
    public void setMachineID(Integer MachineID){
        
        machineID.set(MachineID);
    }  
    
    
    
    
    
    
}
