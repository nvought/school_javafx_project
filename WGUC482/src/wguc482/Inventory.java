/*
 * Nathan Vought
 * Student ID:#000370819
 * WGU course C482
 */
package wguc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
 
    private  ObservableList<Product> inventory = FXCollections.observableArrayList();

    public Inventory() {
    }

    
    

    public ObservableList<Product> getInventory() {
        return inventory;
    }

    public void addProduct( Product product) {
        inventory.add(product);
    }
    
    public void removeProduct(int index) {
        inventory.remove(index);
    }
    
    

    //lookupProduct(int): product- refer to Controller.Java line 426 pressSearchPart
    //nv
    
    //updateProduct(int): void- refer to Controller.Java line 267 pressModifyProduct
    // and 1280 pressSaveModifyProduct
    //nv
    
    
}