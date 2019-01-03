package InventoryManagementSystem.view_controller;
import InventoryManagementSystem.model.Inventory;
import InventoryManagementSystem.model.Part;
import InventoryManagementSystem.model.Product;
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

public class AddProductController implements Initializable {

    @FXML
    private AnchorPane addProductId;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField addPartId;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addInventory;

    @FXML
    private TextField addPrice;

    @FXML
    private TextField addMin;

    @FXML
    private TextField addMax;

    @FXML
    private TableView<Part> tvParts;

    @FXML
    private TableColumn<Part, Integer> partId1;

    @FXML
    private TableColumn<Part, String> partName1;

    @FXML
    private TableColumn<Part, Integer> inventory1;

    @FXML
    private TableColumn<Part, Double> price1;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Part> tvAssociatedParts;

    @FXML
    private TableColumn<Part, Integer> partId2;

    @FXML
    private TableColumn<Part, String> partName2;

    @FXML
    private TableColumn<Part, Integer> inventory2;

    @FXML
    private TableColumn<Part, Double> price2;


    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();


   //product functions
    @FXML
    void handleAddAddProduct(ActionEvent event)  {
        Part selectedPart = tvParts.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);

    }

    @FXML
    void handleCancelAddProduct(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            Parent addPartsScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene addPartsScene = new Scene(addPartsScreen);
            //next line is getting stage information
            Stage addPartsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addPartsStage.setScene((addPartsScene));
            addPartsStage.show();
        }
    }

    @FXML
    void handleDeleteAddProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> outcome = alert.showAndWait();


        if(outcome.get() == ButtonType.OK) {
            associatedParts.remove(tvAssociatedParts.getSelectionModel().getSelectedItem());
            partId2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
            partName2.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
            inventory2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
            price2.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        }

    }

    @FXML
    void handleSaveAddProduct(ActionEvent event) throws IOException {
        Product product = new Product();
        product.setProductId(Integer.parseInt(addPartId.getText()));
        product.setName(addPartName.getText());
        product.setInStock(Integer.parseInt(addInventory.getText()));
        product.setPrice(Double.parseDouble(addPrice.getText()));
        product.setMin(Integer.parseInt(addMin.getText()));
        product.setMax(Integer.parseInt(addMax.getText()));
        Inventory.addProduct(product);

        Parent addPartsScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene addPartsScene = new Scene(addPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();

    }

    @FXML
    void handleSearchAddProduct(ActionEvent event) {
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
        partId1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
        partName1.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventory1.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        price1.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //load data
        tvParts.setItems(Inventory.getPartsInventory());


        partId2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partId"));
        partName2.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        inventory2.setCellValueFactory(new PropertyValueFactory<Part, Integer>("inStock"));
        price2.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //load data
        tvAssociatedParts.setItems(associatedParts);


    }

}
