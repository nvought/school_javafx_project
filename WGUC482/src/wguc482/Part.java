/*
 * Nathan Vought
 * Student ID:#000370819
 * WGU course C482
 */
package wguc482;

import javafx.beans.property.*;


 public abstract class Part {

   

     
    private  SimpleIntegerProperty Id  = new SimpleIntegerProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty inStock  = new SimpleIntegerProperty();
    private final SimpleDoubleProperty price  = new SimpleDoubleProperty();
    private final SimpleIntegerProperty min  = new SimpleIntegerProperty();
    private final SimpleIntegerProperty max  = new SimpleIntegerProperty();


   
     public Part(Integer Id,String name,Integer inStock,Double price, Integer min, Integer max) {
        setID(Id);
        setName(name);
        setInStock(inStock);
        setPrice(price);
        setMin(min);
        setMax(max);
       
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
    
  
    

   
    
}
  
    
    
    
    
    
    
    
    
    

 