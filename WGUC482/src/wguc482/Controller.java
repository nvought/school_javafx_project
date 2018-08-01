/*
 * Nathan Vought
 * Student ID:#000370819
 * WGU course C482
 */
package wguc482;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import wguc482.Part;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import wguc482.Inventory;

/**
 *
 * @author mac
 */
public class Controller implements Initializable {
    
    
   // Pane for each window; Main Menu, Add Part, Modify Part, Add Product, Modify Product
    //nv
    @FXML private Pane paneMainMenu, paneAddPart, paneModifyPart, paneAddProduct,paneModifyProduct;
    
  
    ////////////// Part Variables///////////////////////////////////////
    // Table Views for Main Menu, Add Product, Modify Product
    //nv
    @FXML private TableView<Part> partTableView,partTableViewAdd, partTableViewModify,
            partTableViewAddToProductAdd, partTableViewAddToProductModify;
    
    // Array list for parts
    //nv
    @FXML private ObservableList<Part> partData, productAddPartData,productModifyPartData, productParts, tempObs;
    
    
    // Sorted list for parts
    //nv
    @FXML private SortedList<Part> sortedData;
    
    /// Add Part screen text fields
    //nv
    @FXML private TextField txtAddPartID, txtAddPartName, txtAddPartINV, txtAddPartMax, 
            txtAddPartMin,txtAddPartPrice, txtAddPartCompany, txtAddPartMachineID;
    
    //// Modify Part screen text fields
    //nv
    @FXML private TextField txtModifyPartID, txtModifyPartName, txtModifyPartINV, txtModifyPartMax, 
            txtModifyPartMin,txtModifyPartPrice, txtModifyPartCompany, txtModifyPartMachineID;
    
    //// Add Part screen radio buttons
    //nv
    @FXML private ToggleGroup addPartRadio;
    @FXML private RadioButton radioAddPartOutsourced, radioAddPartInHouse;
    
    //// Modify Part screen radio buttons
    //nv
    @FXML private ToggleGroup modifyPartRadio;
    @FXML private RadioButton radioModifyPartOutsourced, radioModifyPartInHouse;
    
    // ID generator for parts
    //nv
    @FXML private static AtomicInteger partIDCreator = new AtomicInteger(1);
    
    // Variables for part index, max, min and inventory;
    //nv
    @FXML private int tblIndex, max, min, inventory;
    
    // Text fields for part search in Main Menu, Add Product, Modify Product
    @FXML private TextField partSearch, partSearchAdd,partSearchModify;
    
    // Table Column identifier for search
    //nv
    @FXML
    private TableColumn<Part, String> partNameColumn, partNameColumnAdd,partNameColumnModify;
    
    ////////////// Product Variables///////////////////////////////////////
    
    /// Add Product screen text fields
    //nv
    @FXML private TextField txtAddProductID, txtAddProductName, txtAddProductINV, txtAddProductMax, 
            txtAddProductMin,txtAddProductPrice;
    
    //// Modify Product screen text fields
    //nv
    @FXML private TextField txtModifyProductID, txtModifyProductName, txtModifyProductINV, txtModifyProductMax, 
            txtModifyProductMin,txtModifyProductPrice;
    
   // Product Inventory array list
   //nv 
    @FXML private Inventory productInventory = new Inventory();
    
    // Product Inventory Observable list
   //nv 
    @FXML private ObservableList<Product> products;
    
    // Product Inventory table view
    //nv
    @FXML private TableView<Product> productTableView;
    
    // ID generator for products
    //nv
    @FXML private static AtomicInteger productIDCreator = new AtomicInteger(101);
    
    // Product search text field
    //nv
    @FXML private TextField productSearch;
    
    // Sorted list for parts
    //nv
    @FXML private SortedList<Product> sortedProducts;
    
    // Table column for product search by name
    //nv
    @FXML
    private TableColumn<Product, String> productNameColumn;
    
    
    
    
   
    
    
 ////////////////////////Main Menu controls////////////////////////
    
    
  // Method for pressing the Add Part button in the Main Menu
 // Hides the Main Menu pane, shows the add part screen and disables ID field
 // In House radio is selected by default so Company textfield is disabled 
 //nv   
  @FXML
  public void pressAddPart(ActionEvent event) {               
          txtAddPartID.setDisable(true);
          txtAddPartID.setPromptText("Auto-Generated");
          txtAddPartName.setPromptText("Name");
          txtAddPartINV.setPromptText("Inventory");
          txtAddPartPrice.setPromptText("Price");
          txtAddPartMin.setPromptText("Min");
          txtAddPartMax.setPromptText("Max");
         
          paneMainMenu.setVisible(false);
          paneAddPart.setVisible(true);
          
              
     }
  
  // Method for pressing the Add Product button in the Main Menu
 // Hides the Main Menu pane, shows the Add Product screen and disables ID field
 //nv 
  @FXML
  public void pressAddProduct(ActionEvent event) {               
          paneMainMenu.setVisible(false);
          paneAddProduct.setVisible(true);
          txtAddProductID.setDisable(true);
          txtAddProductID.setPromptText("Auto-Generated");
          txtAddProductName.setPromptText("Product Name");
          txtAddProductINV.setText("0");
          txtAddProductPrice.setPromptText("Price");
          txtAddProductMin.setPromptText("Min");
          txtAddProductMax.setPromptText("Max");
          
     }
 // Method for pressing the Modify Part button in the Main Menu
 // Hides the Main Menu pane, shows the Modify Part screen and disables ID field
 // This method gets the selected part and find out what type of subclass it is
 // If the class is In House company text field is disabled and vice versa for Outsourced
 // After getting the class it sets all text fields with the appropriate information 
 // from the part 
 //nv   
  @FXML
  public void pressModifyPart(ActionEvent event) throws Exception{ 
      
      try{ 
          paneMainMenu.setVisible(false);
          paneModifyPart.setVisible(true);
          txtModifyPartID.setDisable(true);
             
      int index =  partTableView.getSelectionModel().getSelectedIndex();
      Part part= partTableView.getItems().get(index);
     
        if(part.getClass().toString().matches("class wguc482.InHouse")){
            
           txtModifyPartCompany.setDisable(true);
           
          String mID = partTableView.getColumns().get(7).getCellData(index).toString();
      
          txtModifyPartID.setText(part.IdProperty().asString().getValue());
          txtModifyPartName.setText(part.nameProperty().getValue());
          txtModifyPartINV.setText(part.inStockProperty().asString().getValue());
          txtModifyPartPrice.setText(part.priceProperty().asString().getValue());
          txtModifyPartMax.setText(part.maxProperty().asString().getValue());
          txtModifyPartMin.setText(part.minProperty().asString().getValue());
          txtModifyPartMachineID.setText(mID);
        }
        
        else{
            
            txtModifyPartMachineID.setDisable(true);
               
         String company = partTableView.getColumns().get(6).getCellObservableValue(index).getValue().toString(); 
          
          txtModifyPartID.setText(part.IdProperty().asString().getValue());
          txtModifyPartName.setText(part.nameProperty().getValue());
          txtModifyPartINV.setText(part.inStockProperty().asString().getValue());
          txtModifyPartPrice.setText(part.priceProperty().asString().getValue());
          txtModifyPartMax.setText(part.maxProperty().asString().getValue());
          txtModifyPartMin.setText(part.minProperty().asString().getValue());  
          txtModifyPartCompany.setText(company);

        }
  
     }

  catch(Exception e){
      
   
    if(partTableView.getSelectionModel().getSelectedIndex() == -1){
       
          paneMainMenu.setVisible(true);
          paneModifyPart.setVisible(false);
      Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Please Select the part you want to modify");

alert.showAndWait(); 

}
    
    else{
        
    }
  }
}
  
  // Method for pressing the Modify Product button in the Main Menu
 // Hides the Main Menu pane, shows the Modify Product screen and disables ID field
 // This method gets the selected product and finds the associated parts and 
 // displays the data in the appropriate fields
 //nv  
  
  @FXML
  public void pressModifyProduct(ActionEvent event) throws Exception {               
          
      try{
          
      int index =  productTableView.getSelectionModel().getSelectedIndex();
      Product product= productTableView.getItems().get(index);
          paneMainMenu.setVisible(false);
      
          paneModifyProduct.setVisible(true);
          txtModifyProductID.setDisable(true);
     
         
      
      
          txtModifyProductID.setText(product.IdProperty().asString().getValue());
          txtModifyProductName.setText(product.nameProperty().getValue());
          txtModifyProductINV.setText(product.inStockProperty().asString().getValue());
          txtModifyProductPrice.setText(product.priceProperty().asString().getValue());
          txtModifyProductMax.setText(product.maxProperty().asString().getValue());
          txtModifyProductMin.setText(product.minProperty().asString().getValue());
         
          
         tempObs= product.getList();
          
       
          productModifyPartData.clear();
          
          productModifyPartData  = tempObs;
          partTableViewAddToProductModify.setItems(productModifyPartData);
          partTableViewAddToProductModify.getItems();
          
          partTableViewAddToProductModify.getItems();
      
          
     }
      
      catch(Exception e){
      
   
    if(productTableView.getSelectionModel().getSelectedIndex() == -1){
       
          paneMainMenu.setVisible(true);
          paneModifyPart.setVisible(false);
      Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Please Select the product you want to modify");

alert.showAndWait(); 

}
    
    else{
        
    }
  }
}
      
     
  
  // This is the delete part method
  // This gets the selected index finds the index value for the part number and 
  //removes it from the array
  //nv
   @FXML
  public void pressDeletePart(ActionEvent event){
       
       int index = partTableView.getSelectionModel().getSelectedIndex();
       Part part= partTableView.getItems().get(index);
      
       tblIndex = part.IdProperty().intValue();
      
Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to delete this part?");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    partData.remove(part);
    
} else {
    
    }   
          
  }
 
    // This is the delete product method
  // This gets the selected index finds the index value for the product number and 
  //removes it from the array
  //nv
   @FXML
  public void pressDeleteProduct(ActionEvent event){
       
       int index = productTableView.getSelectionModel().getSelectedIndex();
       Product product= productTableView.getItems().get(index);
      
       tblIndex = product.IdProperty().intValue();
      
Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to delete this product?"
        + "  This product has at least one part associated with it");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    products.remove(product);
    
} else {
    
    }   
          
  }
 
  // This is the Search part method 
  // After the search button has been pressed the method gets the search field text
  // comapares it in lower case to the arraylist and sets it to a sorted list
  //nv
  @FXML
  public void pressSearchPart(ActionEvent event) throws Exception {               
        try {

        FilteredList<Part> filteredData = new FilteredList<>(partData, p -> true);

        filteredData.setPredicate(part -> {
               
            String newValue = partSearch.getText();
            
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (part.nameProperty().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 
                return false; 
            });
         
        SortedList<Part> sortedData = new SortedList<>(filteredData);

        partTableView.setItems(sortedData);

            
        }
  
                
         catch(Exception e) {
           e.printStackTrace();
          }
        }
  
  // This is the Search part method 
  // After the search button has been pressed the method gets the search field text
  // comapares it in lower case to the arraylist and sets it to a sorted list
  //nv
  @FXML
  public void pressSearchProduct(ActionEvent event) throws Exception {               
        try {

        FilteredList<Product> filteredData = new FilteredList<>(products, p -> true);

        filteredData.setPredicate(product -> {
               
            String newValue = productSearch.getText();
            
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.nameProperty().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 
                return false; 
            });
         
        SortedList<Product> sortedProducts= new SortedList<>(filteredData);

        productTableView.setItems(sortedProducts);

            
        }
  
                
         catch(Exception e) {
           e.printStackTrace();
          }
        }
  
  // This is the Exit method
  // This method asks the user for confirmation before exiting
  //nv
  @FXML
  public void pressExit(ActionEvent event) {
      
      
      Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to exit? Your changes will not be saved.");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    Platform.exit();
    
} else {
    
    }   
      
          
     }
 
  
  
  
  
////////////////////////Add Part controls//////////////////////// 
  
  // This is the toggle button method for In House 
  // If the this toggle is selected the Company text field will be disabled
  //nv
  @FXML
    public void toggleInHouseAddPart(ActionEvent event) { 
        
       if (addPartRadio.getSelectedToggle().toString().endsWith("se\'")){
        
        txtAddPartMachineID.setDisable(false);
        txtAddPartMachineID.setPromptText("");
        
        txtAddPartCompany.setDisable(true);
        txtAddPartCompany.setPromptText("Disabled");
        
       }
        
       
       
        
        }
    
    // This is the toggle button method for Outsourced 
  // If the this toggle is selected the Machnie ID text field will be disabled
  //nv
    @FXML
     public void toggleOutsourcedAddPart(ActionEvent event) { 
           
            txtAddPartCompany.setDisable(false);
            txtAddPartCompany.setPromptText("");
            
            txtAddPartMachineID.setDisable(true);
            txtAddPartMachineID.setPromptText("Disabled");
     }
   
     //This is the Save Add Part method
     // This checks to see if the minimum value is greater than the maximum 
     // If the min is greater than the max it produces an error
     // It also checks to see if the inventory is less than the minimum or higher
     // if so it will produce an error
     // If the criteria is met it checks to see what radio button is selected
     // When the In House radio is selected it gets the text field data and saves
     // it as an In House part
     // It does the same for Outsourced
  @FXML
  public void pressSaveAddPart(ActionEvent event) throws Exception  {               
       
       int num;
       min = Integer.parseInt(txtAddPartMin.getText());
       max = Integer.parseInt(txtAddPartMax.getText());
       inventory = Integer.parseInt(txtAddPartINV.getText());
       
       
       if(min > max){
               Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The minimum can not be bigger than the maximum");

alert.showAndWait();  
               
           }
           
           else if(inventory < min || inventory > max){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Inventory levels must be more than the minimum and less than the maximum");

alert.showAndWait(); 
           }

       else { 

       try{
       
      
         if(addPartRadio.getSelectedToggle().toString().endsWith("se\'")){ 
             
            num = partIDCreator.getAndIncrement(); 
            partTableView.getItems();
            Part inHouse = new InHouse(num,txtAddPartName.getText(),
            inventory,Double.parseDouble(txtAddPartPrice.getText()),
            min,max,Integer.parseInt(txtAddPartMachineID.getText()));
            
            
          partData.add(num, inHouse);
     
          txtAddPartID.setDisable(true);
          txtAddPartID.setText("Auto-Generated");
          txtAddPartName.setText("");
          txtAddPartINV.setText("");
          txtAddPartPrice.setText("");
          txtAddPartMax.setText("");
          txtAddPartMin.setText("");
          txtAddPartCompany.setText("");
          txtAddPartMachineID.setText("");
          paneMainMenu.setVisible(true);
          paneAddPart.setVisible(false);
          
          
          
                      
           
         }
       
         
         else{
         
             num = partIDCreator.getAndIncrement(); 
             partTableView.getItems();
        Part outsourced = new Outsourced(num,txtAddPartName.getText(),
            Integer.parseInt(txtAddPartINV.getText()),Double.parseDouble(txtAddPartPrice.getText()),
            Integer.parseInt(txtAddPartMin.getText()),Integer.parseInt(txtAddPartMax.getText()),
                txtAddPartCompany.getText());

        partData.add(num, outsourced);  
          txtAddPartID.setDisable(true);
          txtAddPartID.setText("Auto-Generated");
          txtAddPartName.setText("");
          txtAddPartINV.setText("");
          txtAddPartPrice.setText("");
          txtAddPartMax.setText("");
          txtAddPartMin.setText("");
          txtAddPartCompany.setText("");
          txtAddPartMachineID.setText("");
          paneMainMenu.setVisible(true);
          paneAddPart.setVisible(false);
          
         
          
          
         }
        }    
        
       
       catch(Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Please make sure all necessary fields have been filled"
        + "and have the correct values");

alert.showAndWait();
                
               
           }
       }
  }

        
  //This is the Cancel Add part method
  // This method returns the Main Menu pane visible and hides the Add Part pane
  //nv
  @FXML
  public void pressCancelAddPart(ActionEvent event) {               
        
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to cancel?  None of your changes will be saved");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    
          
          txtAddPartID.setText("");
          txtAddPartName.setText("");
          txtAddPartINV.setText("");
          txtAddPartPrice.setText("");
          txtAddPartMax.setText("");
          txtAddPartMin.setText("");
          txtAddPartCompany.setText("");
          txtAddPartMachineID.setText("");  
          paneMainMenu.setVisible(true);
          paneAddPart.setVisible(false);

    }
else{
    
}

         
        
        }

  
  ////////////////////////Modify Part controls//////////////////////// 
  
  // This is the toggle button method for In House 
  // If the this toggle is selected the Company text field will be disabled
  //nv
  @FXML
    public void toggleInHouseModifyPart(ActionEvent event) { 
        
        txtModifyPartMachineID.setDisable(false);
        txtModifyPartCompany.setDisable(true);        
        }
   // This is the toggle button method for Outsourced 
  // If the this toggle is selected the Machnie ID text field will be disabled
  //nv
    @FXML
     public void toggleOutsourcedModifyPart(ActionEvent event) { 
            txtModifyPartCompany.setDisable(false); 
            txtModifyPartMachineID.setDisable(true);
     }
     
     ///This is the Save Modify Part method
     // This checks to see if the minimum value is greater than the maximum 
     // If the min is greater than the max it produces an error
     // It also checks to see if the inventory is less than the minimum or higher
     // if so it will produce an error
     // If the criteria is met it checks to see what radio button is selected
     // When the In House radio is selected it gets the text field data and saves
     // it as an In House part
     // It does the same for Outsourced
  @FXML
  public void pressSaveModifyPart(ActionEvent event) throws Exception  {               
       
       min = Integer.parseInt(txtModifyPartMin.getText());
       max = Integer.parseInt(txtModifyPartMax.getText());
       inventory = Integer.parseInt(txtModifyPartINV.getText()); 
       
      int index =  partTableView.getSelectionModel().getSelectedIndex();
      Part part= partTableView.getItems().get(index);
      tblIndex = partData.size();
      
      
      
      if(min > max || max < min){
               Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The minimum can not be bigger than the maximum");

alert.showAndWait();  
               
           }
           
           else if(inventory < min || inventory > max){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Inventory levels must be more than the minimum and less than the maximum");

alert.showAndWait(); 
           }
       
            
       else {
      
        
        try{ 
            
      
      
         if(modifyPartRadio.getSelectedToggle().toString().endsWith("se\'")){
             
             
             
            Part inHouse = new InHouse(Integer.parseInt(txtModifyPartID.getText()),txtModifyPartName.getText(),
                    inventory,Double.parseDouble(txtModifyPartPrice.getText()), min,max,
                    Integer.parseInt(txtModifyPartMachineID.getText())

            
            );
           
           partData.add(tblIndex, inHouse);
           partData.remove(part);  

        paneMainMenu.setVisible(true);
        paneModifyPart.setVisible(false);  
          
          txtModifyPartCompany.setText("");
          txtModifyPartMachineID.setText("");
        
         }
         
         
         else{
             
        Part outsourced = new Outsourced(Integer.parseInt(txtModifyPartID.getText()),txtModifyPartName.getText(),
                    inventory,Double.parseDouble(txtModifyPartPrice.getText()),
                    min,max,txtModifyPartCompany.getText()
          );
            
          
        partData.add(tblIndex, outsourced);
        partData.remove(part);
        
          paneMainMenu.setVisible(true);
          paneModifyPart.setVisible(false);
          
         
          txtModifyPartCompany.setText("");
          txtModifyPartMachineID.setText("");

         }
                
         }
            
        
        catch(Exception e) {
              
               Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Missing Information, check to make sure all fields have been filled out");

alert.showAndWait(); 
          }
        
        
        
        }
      
  }
   //This is the Cancel Modify part method
  // This method returns the Main Menu pane visible and hides the Add Part pane
  //nv
  @FXML
  public void pressCancelModifyPart(ActionEvent event) throws Exception  {               
        
       Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to cancel?  None of your changes will be saved");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    

            
            
       
          txtModifyPartID.setText("");
          txtModifyPartName.setText("");
          txtModifyPartINV.setText("");
          txtModifyPartPrice.setText("");
          txtModifyPartMax.setText("");
          txtModifyPartMin.setText("");
          txtModifyPartCompany.setText("");
          txtModifyPartMachineID.setText("");
          paneMainMenu.setVisible(true);
          paneModifyPart.setVisible(false);
          
          
         }
  
  else{
    
}
                
  }   
        
            
           
       
        
        
        
        
  
  ////////////////////////Add Product controls//////////////////////// 
  
  //This is the Cancel Add product method
  // This method returns the Main Menu pane visible and hides the Add Part pane
  //nv
  @FXML
  public void pressCancelAddProduct(ActionEvent event)  {               
        
       Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to cancel?  None of your changes will be saved");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    
          
          txtAddProductID.setText("");
          txtAddProductName.setText("");
          txtAddProductINV.setText("");
          txtAddProductPrice.setText("");
          txtAddProductMax.setText("");
          txtAddProductMin.setText("");
            
          paneMainMenu.setVisible(true);
          paneAddProduct.setVisible(false);
         
  }

else{
    
}
  }
  // This is the Search part method in the Add Product screen
  // After the search button has been pressed the method gets the search field text
  // comapares it in lower case to the arraylist and sets it to a sorted list
  //nv
  @FXML
  public void pressSearchPartAdd(ActionEvent event) throws Exception {               
        try {

        FilteredList<Part> filteredData = new FilteredList<>(partData, p -> true);

        filteredData.setPredicate(part -> {
               
            String newValue = partSearchAdd.getText();
            
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (part.nameProperty().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 
                return false; 
            });
         
        SortedList<Part> sortedData = new SortedList<>(filteredData);

        partTableViewAdd.setItems(sortedData);

            
        }
  
                
         catch(Exception e) {
           e.printStackTrace();
          }
        }
  
  // This is the Add part to Product method
  // This method gets the selected part/parts from the main part arraylist and adds it
  // to the product
  //nv
  @FXML
  public void pressAddPartToProductAdd(ActionEvent event) throws Exception  {               
       
       
      int index =  partTableViewAdd.getSelectionModel().getSelectedIndex();
      Part part= partTableViewAdd.getItems().get(index);
      
      int theID = part.IdProperty().get();
      String theName =part.nameProperty().get();
      int theInv =part.inStockProperty().get();
      double thePrice = part.priceProperty().get();
      int theMin = part.minProperty().get();
      int theMax = part.maxProperty().get();

        try{ 

         if(part.getClass().toString().matches("class wguc482.InHouse")){
        
           int theMach = Integer.parseInt(partTableViewAdd.getColumns().get(7).getCellData(index).toString());  
             
            Part inHouse = new InHouse(theID,theName,theInv,thePrice,theMin,theMax,theMach); 
            
           
           productAddPartData.add(inHouse);
           partTableViewAddToProductAdd.setItems(productAddPartData);
           partTableViewAddToProductAdd.getItems();

         }
         
         
         else{
             
            String theComp = partTableViewAdd.getColumns().get(6).getCellObservableValue(index).toString();
            
            Part outsourced = new Outsourced(theID,theName,theInv,thePrice,theMin,theMax,theComp); 
            
           
           productAddPartData.add(outsourced);
           partTableViewAddToProductAdd.setItems(productAddPartData);
           partTableViewAddToProductAdd.getItems();

         }
                
         }
            
        
        catch(Exception e) {
            e.printStackTrace();  
               
          }
        
        
        
        }
  
  //This is the Save Add Part method
     // This checks to see if the minimum value is greater than the maximum 
     // If the min is greater than the max it produces an error
     // It also checks to see if the inventory is less than the minimum or higher
     // if so it will produce an error
     // If the criteria is met it checks to see what radio button is selected
     // When the In House radio is selected it gets the text field data and saves
     // it as an In House part and vice versa for outsourced
     // nv
  @FXML
  public void pressSaveAddProduct(ActionEvent event) throws Exception  {               
       
       int num;
       min = Integer.parseInt(txtAddProductMin.getText());
       max = Integer.parseInt(txtAddProductMax.getText());
       inventory = Integer.parseInt(txtAddProductINV.getText());
       
       double productPrice = Double.parseDouble(txtAddProductPrice.getText());
       double partsTotal=0;
       
       
        for(int i = 0; i< productAddPartData.size(); i++){
            
            
           double price= productAddPartData.get(i).priceProperty().getValue();
           
           partsTotal = partsTotal+price;
                
        }
        
        
       
       if(min > max){
               Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The minimum can not be bigger than the maximum");

alert.showAndWait();  
               
           }
           
           else if(inventory < min && inventory != 0 || inventory > max && inventory!=0 ){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Inventory levels must be more than the minimum and less than the maximum");

alert.showAndWait(); 
           }
           
           
           else if(productPrice<partsTotal){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The price of the product can't be less than the total price of the parts");

alert.showAndWait(); 
           }
           
           
           else if(partTableViewAddToProductAdd.getItems().isEmpty()){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("At least one part must be added");

alert.showAndWait(); 
           }
           
       else { 

       try{
           ArrayList<Part> tempData = new ArrayList<Part>();
           
           for(int i = 0; i<productAddPartData.size(); i++ ){
               Part part = productAddPartData.get(i);
               tempData.add(i,part );
               
           }
            
           
          

           
           num = productIDCreator.getAndIncrement();
           
           Product product = new Product(num,txtAddProductName.getText(),
                   inventory,Double.parseDouble(txtAddProductPrice.getText()),
           Integer.parseInt(txtAddProductMin.getText()),Integer.parseInt(txtAddProductMax.getText()),
             tempData     );
          
           
       // add product to observable arraylist    
       productInventory.addProduct(product);
       
       // convert to obervable Product
       products = productInventory.getInventory();
       
       productTableView.setItems(products);
       
          txtAddProductID.setText("");
          txtAddProductName.setText("");
          txtAddProductINV.setText("");
          txtAddProductPrice.setText("");
          txtAddProductMin.setText("");
          txtAddProductMax.setText("");
          productAddPartData.clear();
          
          
            
           paneMainMenu.setVisible(true);
           paneAddProduct.setVisible(false);
           
        }    
        
       
       catch(Exception e) {
         e.printStackTrace();
                
               
           }
       }
  }
  
  // This is the delete part from from product method
  // prompts the user for confirmation before executing
  //nv
 @FXML
  public void pressDeletePartAddProduct(ActionEvent event){
       
       int index = partTableViewAddToProductAdd.getSelectionModel().getSelectedIndex();
       Part part= partTableViewAddToProductAdd.getItems().get(index);
      
       tblIndex = part.IdProperty().intValue();
      
Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to delete this part from this product?");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    productAddPartData.remove(part);
    
} else {
    
    }   
          
  }
      
 

  
    ////////////////////////Modify Product controls//////////////////////// 
  
 //This is the Cancel Modify product method
  // This method returns the Main Menu pane visible and hides the Add Part pane
  //nv 
  @FXML
  public void pressCancelModifyProduct(ActionEvent event)  {               
        
            
            
          Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to cancel?  None of your changes will be saved");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    
          
          txtModifyProductID.setText("");
          txtModifyProductName.setText("");
          txtModifyProductINV.setText("");
          txtModifyProductPrice.setText("");
          txtModifyProductMax.setText("");
          txtModifyProductMin.setText("");
             
            
          paneMainMenu.setVisible(true);
          paneModifyProduct.setVisible(false);

    }

         
  }
  
  // This is the Search part method in the Modify Product screen
  // After the search button has been pressed the method gets the search field text
  // comapares it in lower case to the arraylist and sets it to a sorted list
  //nv
  @FXML
  public void pressSearchPartModify(ActionEvent event) throws Exception {               
        try {

        FilteredList<Part> filteredData = new FilteredList<>(partData, p -> true);

        filteredData.setPredicate(part -> {
               
            String newValue = partSearchModify.getText();
            
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (part.nameProperty().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 
                return false; 
            });
         
        SortedList<Part> sortedData = new SortedList<>(filteredData);

        partTableViewModify.setItems(sortedData);

            
        }
  
                
         catch(Exception e) {
           e.printStackTrace();
          }
        }
  
   // This is the Add part to Product method
  // This method gets the selected part/parts from the main part arraylist and adds it
  // to the product
  //nv
  @FXML
  public void pressAddPartToProductModify(ActionEvent event) throws Exception  {               
       
       
      int index =  partTableViewModify.getSelectionModel().getSelectedIndex();
      Part part= partTableViewModify.getItems().get(index);
      
      int theID = part.IdProperty().get();
      String theName =part.nameProperty().get();
      int theInv =part.inStockProperty().get();
      double thePrice = part.priceProperty().get();
      int theMin = part.minProperty().get();
      int theMax = part.maxProperty().get();

        try{ 

         if(part.getClass().toString().matches("class wguc482.InHouse")){
        
           int theMach = Integer.parseInt(partTableViewModify.getColumns().get(7).getCellData(index).toString());  
             
            Part inHouse = new InHouse(theID,theName,theInv,thePrice,theMin,theMax,theMach); 
            
           
           productModifyPartData.add(inHouse);
           partTableViewAddToProductModify.setItems(productModifyPartData);
           partTableViewAddToProductModify.getItems();

         }
         
         
         else{
             
            String theComp = partTableViewModify.getColumns().get(6).getCellObservableValue(index).toString();
            
            Part outsourced = new Outsourced(theID,theName,theInv,thePrice,theMin,theMax,theComp); 
            
           
           productModifyPartData.add(outsourced);
           partTableViewAddToProductModify.setItems(productModifyPartData);
           partTableViewAddToProductModify.getItems();

         }
                
         }
            
        
        catch(Exception e) {
            e.printStackTrace();  
               
          }
        
        
        
        }
  
  // This is the Save modify product method
     // This checks to see if the minimum value is greater than the maximum 
     // If the min is greater than the max it produces an error
     // It also checks to see if the inventory is less than the minimum or higher
     // if so it will produce an error
     // If the criteria is met it checks to see what radio button is selected
     // When the In House radio is selected it gets the text field data and saves
     // it as an In House part and vice versa for outsourced
     // nv
  
  @FXML
  public void pressSaveModifyProduct(ActionEvent event) throws Exception  {               
       
       
    int   productMin = Integer.parseInt(txtModifyProductMin.getText());
    int   productMax = Integer.parseInt(txtModifyProductMax.getText());
          inventory = Integer.parseInt(txtModifyProductINV.getText());
       
      double productPrice = Double.parseDouble(txtModifyProductPrice.getText());
       double partsTotal=0;
       
       
        for(int i = 0; i< productModifyPartData.size(); i++){
            
            
           double price= productModifyPartData.get(i).priceProperty().getValue();
           
           partsTotal = partsTotal+price;
                
        } 
       if(productMin > productMax){
               Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The minimum can not be bigger than the maximum");

alert.showAndWait();  
               
           }
           
           else if(inventory < min && inventory != 0 || inventory > max && inventory!=0 ){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("Inventory levels must be more than the minimum and less than the maximum");

alert.showAndWait(); 
           }
           
           else if(productPrice<partsTotal){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("The price of the product can't be less than the total price of the parts");

alert.showAndWait(); 
           }
           
           
           else if(partTableViewAddToProductModify.getItems().isEmpty()){
               
                Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Warning");
alert.setContentText("At least one part must be added");

alert.showAndWait(); 
           }

       else { 

       try{
           
      int productIndex =  productTableView.getSelectionModel().getSelectedIndex();
      Product oldProduct= productTableView.getItems().get(productIndex);
      tblIndex = products.size();
          
      ArrayList<Part> tempData = new ArrayList<Part>();
           
           for(int i = 0; i<productModifyPartData.size(); i++ ){
               Part part = productModifyPartData.get(i);
               tempData.add(i,part );
               
           }
            
           
           
           
           Product product = new Product(Integer.parseInt(txtModifyProductID.getText()),txtModifyProductName.getText(),
                   inventory,Double.parseDouble(txtModifyProductPrice.getText()),
           Integer.parseInt(txtModifyProductMin.getText()),Integer.parseInt(txtModifyProductMax.getText()),
             tempData     );
           
          
           
       // add product to observable arraylist    
      // productInventory.addProduct(product);
       
      
       products.add(tblIndex, product);
       products.remove(oldProduct); 
       
       
       // convert to obervable Product
       products = productInventory.getInventory();
       
       
       productTableView.setItems(products);
       
          txtModifyProductID.setText("");
          txtModifyProductName.setText("");
          txtModifyProductINV.setText("");
          txtModifyProductPrice.setText("");
          txtModifyProductMin.setText("");
          txtModifyProductMax.setText("");
          
          
          
            
           paneMainMenu.setVisible(true);
           paneModifyProduct.setVisible(false);
           
        }    
        
       
       catch(Exception e) {
         e.printStackTrace();
                
               
           }
       }
  }
  
  
  //This is the delete part from product method
  // Asks user for confirmation before executing
  //nv
  
   @FXML
  public void pressDeletePartModifyProduct(ActionEvent event){
       
       int index = partTableViewAddToProductModify.getSelectionModel().getSelectedIndex();
       Part part= partTableViewAddToProductModify.getItems().get(index);
      
       tblIndex = part.IdProperty().intValue();
      
Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Warning");
alert.setHeaderText("Are you sure you want to delete this part from this product?");


Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    productModifyPartData.remove(part);
    
} else {
    
    }   
          
  }
  
  
  
  
  // The initialize method that sets the presets for the intial launch
  //nv
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       paneAddPart.setVisible(false);
       paneModifyPart.setVisible(false);
       paneAddProduct.setVisible(false);
       paneModifyProduct.setVisible(false);
       paneMainMenu.setVisible(true);
       partData = partTableView.getItems();
       productAddPartData = partTableViewAddToProductAdd.getItems();
       productModifyPartData = partTableViewAddToProductAdd.getItems();
       partData.add(new InHouse( 0,"testPart", 2,3.6,1,5,64));
       
        txtAddPartCompany.setDisable(true);
        txtAddPartCompany.setPromptText("Disabled");
        
        txtModifyPartCompany.setDisable(true);
        txtModifyPartCompany.setPromptText("Disabled");
       
      
       
       
       partTableViewModify.setItems(partData);
       partTableViewModify.getItems();
       
       partTableViewAdd.setItems(partData);
       partTableViewAdd.getItems();
       
      
       
       products = productInventory.getInventory();
       
       
       
       productTableView.setItems(products);
       productTableView.getItems();
       
       partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
       
       partNameColumnAdd.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
       
       partNameColumnModify.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
       
       productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
      
       partSearch.textProperty().addListener((observable, oldValue, newValue) -> {
          
        });
       
       partSearchAdd.textProperty().addListener((observable, oldValue, newValue) -> {
          
        });
       
       partSearchModify.textProperty().addListener((observable, oldValue, newValue) -> {
          
        });
       
             
       productSearch.textProperty().addListener((observable, oldValue, newValue) -> {
          
        });
      
      
       
        
    
      
    }    
    
}
