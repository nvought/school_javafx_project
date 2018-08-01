/*
 * Nathan Vought
 * Student ID:#000370819
 * WGU course C482
 */
package wguc482;

import java.util.ArrayList;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


 public class Product {

   

     
    private  SimpleIntegerProperty Id  = new SimpleIntegerProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty inStock  = new SimpleIntegerProperty();
    private final SimpleDoubleProperty price  = new SimpleDoubleProperty();
    private final SimpleIntegerProperty min  = new SimpleIntegerProperty();
    private final SimpleIntegerProperty max  = new SimpleIntegerProperty();
    private ObservableList<Part> list;


     public Product(Integer Id,String name,Integer inStock,Double price, Integer min, Integer max, ArrayList<Part> parts) {
        setID(Id);
        setName(name);
        setInStock(inStock);
        setPrice(price);
        setMin(min);
        setMax(max);
        list = FXCollections.observableArrayList(parts);
        
        
       
    }
     
   
   
   

    
    
    public SimpleIntegerProperty IdProperty() {
        return Id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty inStockProperty() {
        return inStock;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleIntegerProperty minProperty() {
        return min;
    }

    public SimpleIntegerProperty maxProperty() {
        return max;
    }

    
     public void setID(Integer pID) {
        Id.set(pID);
    }
    
    
    public void setName(String pName) {
        name.set(pName);
    }
     
    public void setInStock(Integer inv) {
        inStock.set(inv);
    }
    
    public void setPrice(Double pPrice) {
        price.set(pPrice);
    }
    public void setMin(Integer pMin) {
        min.set(pMin);
    }
    public void setMax(Integer pMax) {
        max.set(pMax);
    }
   
    public ObservableList<Part> getList() {
        return FXCollections.observableArrayList(list);
    }

    public void setList(ObservableList<Part> list) {
        this.list = list;
    }
    
    
    
//addPart(part): void- refer to Controller line 927 pressAddPartToProductAdd
    // and 1215 pressAddPartToProductModify
    //nv
    
//removePart(int): boolean- refer to Controller line 1110 pressDeletePartAddProduct
    // and 1406 pressDeletePartModifyProduct
    //nv
    
//lookupPart(int): part- refer to Product.Java line 105
    //nv
    
//updatePart(int): void- refer to Product.Java line 109
    //
   
    
  
    

   
    
}
  
    
    
    
    
    
    
    
    
    

 