package InventoryManagementSystem.view_controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import InventoryManagementSystem.model.Inventory;
import InventoryManagementSystem.model.Part;
import InventoryManagementSystem.model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainController implements Initializable {


    @FXML
    private AnchorPane mainScreenId;

    @FXML
    private Button partSearchButton;

    @FXML
    private TextField partsSearchField;

    @FXML
    private Button productSearchButton;

    @FXML
    private TextField productsSearchField;



    @FXML
    private TableView<Part> tvParts;

    @FXML
    private TableColumn<Part, Integer> partsIDColumn;

    @FXML
    private TableColumn<Part, String> partsNameColumn;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelColumn;

    @FXML
    private TableColumn<Part, Double> pricePartUnitColumn;



    @FXML
    private TableView<Product> tvProducts;

    @FXML
    private TableColumn<Part, Integer> productsIDColumn;

    @FXML
    private TableColumn<Part, String> productsNameColumn;

    @FXML
    private TableColumn<Part, Integer> inventoryProductsColumn;

    @FXML
    private TableColumn<Part, Double> priceProductsUnitColumn;


    private static int modifyPartIndex;
    private static int modifyProductIndex;


    public static int getModifyPartIndex(){
        return modifyPartIndex;
    }
    public static int getModifyProductIndex(){
        return modifyProductIndex;
    }
    private static Part modifiedPart;
    private static Product modifiedProduct;






    //part handler

    @FXML
    private void handlePartSearch(ActionEvent event){
        String searchedPart = partsSearchField.getText();
        int tvPartIndex;
        if(Inventory.lookupPart(searchedPart) == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error in searching!");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search text entered did not match any parts");
            alert.showAndWait();
        }
        else{
            tvPartIndex = Inventory.lookupPart(searchedPart);
            Part tempSearchPart = Inventory.getPartsInventory().get(tvPartIndex);
            ObservableList<Part> tempObservableList = FXCollections.observableArrayList();
            tempObservableList.add(tempSearchPart);
            tvParts.setItems(tempObservableList);
        }
    }





    @FXML
    public void handleGoToModifyParts(ActionEvent event)throws IOException{
        Part tempPart = tvParts.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getPartsInventory().indexOf(tempPart);
        Parent addPartsScreen = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene addPartsScene = new Scene(addPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();

    }

    @FXML
    private void handleDeletePart(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            Inventory.removePart(tvParts.getSelectionModel().getSelectedItem());
            partsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
            partsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            pricePartUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }

    @FXML
    private void handleGoToAddParts(ActionEvent event) throws IOException{
        Parent addPartsScreen = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartsScene = new Scene(addPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();
    }



    //product handler

    @FXML
    private void handleProductSearch(ActionEvent event){
        String searchedProduct = productsSearchField.getText();
        int tvProductIndex;
        if(Inventory.lookupProduct(searchedProduct) == -1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error in searching!");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search text entered did not match any products");
            alert.showAndWait();
        }
        else{
            tvProductIndex = Inventory.lookupProduct(searchedProduct);
            Product tempSearchProduct = Inventory.getProductsInventory().get(tvProductIndex);
            ObservableList<Product> tempObservableList = FXCollections.observableArrayList();
            tempObservableList.add(tempSearchProduct);
            tvProducts.setItems(tempObservableList);
        }

    }


    @FXML
    private void handleGoToModifyProducts(ActionEvent event)throws IOException{
        Product tempProduct = tvProducts.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getProductsInventory().indexOf(tempProduct);
        Parent addPartsScreen = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addPartsScene = new Scene(addPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();

        modifiedPart = tvParts.getSelectionModel().getSelectedItem();


    }

    @FXML
    private void handleGoToAddProducts (ActionEvent event)throws IOException{
        Parent addPartsScreen = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartsScene = new Scene(addPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();
    }



    @FXML
    private void handleDeleteProduct(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            Inventory.removeProduct(tvProducts.getSelectionModel().getSelectedItem());
            productsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("productId"));
            productsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            inventoryProductsColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            priceProductsUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }

    //exit handler

    @FXML
    private void handleExitApplication(ActionEvent event){
        Platform.exit();
    }



    //initialize method that loads when screen loads, load the current parts and assign to columns in tv
    @Override
    public void initialize (URL url, ResourceBundle rb){
        //sets up columns
        partsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        pricePartUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //load data
        tvParts.setItems(Inventory.getPartsInventory());

        //load Products
        productsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("productId"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventoryProductsColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        priceProductsUnitColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //load data
        tvProducts.setItems(Inventory.getProductsInventory());
    }









}

