package InventoryManagementSystem.view_controller;
import InventoryManagementSystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private AnchorPane modifyProductId;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField inventoryTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TableView<Part> tvParts;

    @FXML
    private TableColumn<Part, Integer> tvPartIdColumn;

    @FXML
    private TableColumn<Part, String> tvPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvPartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> tvPartPriceColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Part> tvProducts;

    @FXML
    private TableColumn<Part, Integer> tvProductIdColumn;

    @FXML
    private TableColumn<Part, String> tvProductNameColumn;

    @FXML
    private TableColumn<Part, Integer> tvProductInventoryColumn;

    @FXML
    private TableColumn<Part, Double> tvProductPriceColumn;


    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    @FXML
    void handleAddModifyProduct(ActionEvent event) {
        Part selectedPart = tvParts.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);

    }

    @FXML
    void handleCancelModifyProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            Parent modifyProductScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene addPartsScene = new Scene(modifyProductScreen);
            //next line is getting stage information
            Stage addPartsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addPartsStage.setScene((addPartsScene));
            addPartsStage.show();
        }
    }

    @FXML
    void handleDeleteModifyProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            associatedParts.remove(tvProducts.getSelectionModel().getSelectedItem());
            tvProductIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("productId"));
            tvProductNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            tvProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            tvProductPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }
    }

    @FXML
    void handleSaveModifyProduct(ActionEvent event) throws IOException {
        Product product = new Product();
        product.setProductId(Integer.parseInt(idTextField.getText()));
        product.setName(nameTextField.getText());
        product.setInStock(Integer.parseInt(inventoryTextField.getText()));
        product.setPrice(Double.parseDouble(priceTextField.getText()));
        product.setMin(Integer.parseInt(minTextField.getText()));
        product.setMax(Integer.parseInt(maxTextField.getText()));
        Inventory.updateProduct(MainController.getModifyProductIndex(), product);

        Parent modifyProductScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene addPartsScene = new Scene(modifyProductScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();
    }

    @FXML
    void handleSearchModifyProduct(ActionEvent event) {
        String searchedPart = searchTextField.getText();
        int tvPartIndex;
        if (Inventory.lookupPart(searchedPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error in searching!");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search text entered did not match any parts");
            alert.showAndWait();
        } else {
            tvPartIndex = Inventory.lookupPart(searchedPart);
            Part tempSearchPart = Inventory.getPartsInventory().get(tvPartIndex);
            ObservableList<Part> tempObservableList = FXCollections.observableArrayList();
            tempObservableList.add(tempSearchPart);
            tvParts.setItems(tempObservableList);
        }
    }



    //initialize method that loads when screen loads

        @Override
        public void initialize (URL url, ResourceBundle rb){
            tvPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
            tvPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            tvPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            tvPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
            //load data
            tvParts.setItems(Inventory.getPartsInventory());


            tvProductIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
            tvProductNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            tvProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            tvProductPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
            tvProducts.setItems(associatedParts);


            Product productToBeModified = Inventory.getProductsInventory().get(MainController.getModifyProductIndex());

            idTextField.setText(String.valueOf(productToBeModified.getProductId()));
            nameTextField.setText(productToBeModified.getName());
            inventoryTextField.setText(String.valueOf(productToBeModified.getInStock()));
            priceTextField.setText(String.valueOf(productToBeModified.getPrice()));
            minTextField.setText(String.valueOf(productToBeModified.getMin()));
            maxTextField.setText(String.valueOf(productToBeModified.getMax()));


    }

}




