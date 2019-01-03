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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyPartController implements Initializable {

    @FXML
    private AnchorPane modifyInHousePartId;

    @FXML
    private RadioButton radioInHouse;

    @FXML
    private RadioButton radioOutSource;

    @FXML
    private Label switchLabel;

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
    private TextField switchTextField;


    private boolean isOutsourced;


    @FXML
    void handleModifyPartCancel(ActionEvent event) throws IOException {

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
    void handleModifyPartSave(ActionEvent event) throws IOException {

        if(isOutsourced == false){
            InHousePart partIH = new InHousePart();
            partIH.setPartId(Integer.parseInt(idTextField.getText()));
            partIH.setName(nameTextField.getText());
            partIH.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partIH.setPrice(Double.parseDouble(priceTextField.getText()));
            partIH.setMin(Integer.parseInt(minTextField.getText()));
            partIH.setMax(Integer.parseInt(maxTextField.getText()));
            partIH.setMachineID(Integer.parseInt(switchTextField.getText()));
            Inventory.updatePart(MainController.getModifyPartIndex(),partIH);

        }

        else if(isOutsourced == true){
            OutsourcedPart partOS = new OutsourcedPart();
            partOS.setPartId(Integer.parseInt(idTextField.getText()));
            partOS.setName(nameTextField.getText());
            partOS.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partOS.setPrice(Double.parseDouble(priceTextField.getText()));
            partOS.setMin(Integer.parseInt(minTextField.getText()));
            partOS.setMax(Integer.parseInt(maxTextField.getText()));
            partOS.setCompanyName(switchLabel.getText());
            Inventory.updatePart(MainController.getModifyPartIndex(),partOS);

        }

        //switch to home screen
        Parent modifyPartsScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene addPartsScene = new Scene(modifyPartsScreen);
        //next line is getting stage information
        Stage addPartsStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addPartsStage.setScene((addPartsScene));
        addPartsStage.show();


    }

    ToggleGroup sourceToggleGroup;
    {
        sourceToggleGroup = new ToggleGroup();
    }


    @FXML
    void handleRadioInHouse(ActionEvent event) {
        isOutsourced = false;
        this.switchLabel.setText("Machine ID:");
        this.radioInHouse.setToggleGroup(sourceToggleGroup);
        radioInHouse.setSelected(true);
    }

    @FXML
    void handleRadioOutSourced(ActionEvent event) {
        isOutsourced = true;
        this.switchLabel.setText("Company Name:");
        this.radioOutSource.setToggleGroup(sourceToggleGroup);
        radioOutSource.setSelected(true);
    }





    //initialize method that loads when screen loads
    @Override
    public void initialize (URL url, ResourceBundle rb){
        Part partToBeModified = Inventory.getPartsInventory().get(MainController.getModifyPartIndex());

        idTextField.setText(String.valueOf(partToBeModified.getPartId()));
        nameTextField.setText(partToBeModified.getName());
        inventoryTextField.setText(String.valueOf(partToBeModified.getInStock()));
        priceTextField.setText(String.valueOf(partToBeModified.getPrice()));
        minTextField.setText(String.valueOf(partToBeModified.getMin()));
        maxTextField.setText(String.valueOf(partToBeModified.getMax()));

        if(partToBeModified instanceof InHousePart){
            switchLabel.setText("Machine ID:");
            switchTextField.setText(String.valueOf(((InHousePart) partToBeModified).getMachineID()));
            radioInHouse.setSelected(true);
        }
        else if(partToBeModified instanceof  OutsourcedPart){
            switchLabel.setText("Company Name:");
            switchTextField.setText(((OutsourcedPart) partToBeModified).getCompanyName());
            radioOutSource.setSelected(true);
        }

    }

}
