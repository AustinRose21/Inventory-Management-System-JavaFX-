package InventoryManagementSystem.view_controller;

import InventoryManagementSystem.model.InHousePart;
import InventoryManagementSystem.model.Inventory;
import InventoryManagementSystem.model.OutsourcedPart;
import InventoryManagementSystem.model.Part;
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

public class AddPartController implements Initializable {

    @FXML
    private AnchorPane addPartId;

    @FXML
    private RadioButton radioInHouse;

    @FXML
    private RadioButton radioOutSource;

    @FXML
    private Label switchLabel;

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


    private boolean isOutsourced = false;
    private String errorMsg = new String();




    @FXML
    void handleCancelAddPart(ActionEvent event) throws IOException {

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

    //include if statement to determine in house vs outsource
    //create a new part and add it to the list on main.fxml
    //get all the items from the table and add new part to the list
    @FXML
    public void handleSaveAddPart(ActionEvent event) throws IOException {

        try {
            errorMsg = Part.isValidPart(nameTextField.getText(), Integer.parseInt(minTextField.getText()), Integer.parseInt(maxTextField.getText()),
                    Integer.parseInt(inventoryTextField.getText()), Double.parseDouble(priceTextField.getText()), errorMsg);
            if (errorMsg.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding current part");
                alert.setContentText(errorMsg);
                alert.showAndWait();
                errorMsg = "";
            }
            else {
                if (isOutsourced == false) {
                    InHousePart partIH = new InHousePart();
                    partIH.setPartId(Integer.parseInt(idTextField.getText()));
                    partIH.setName(nameTextField.getText());
                    partIH.setInStock(Integer.parseInt(inventoryTextField.getText()));
                    partIH.setPrice(Double.parseDouble(priceTextField.getText()));
                    partIH.setMin(Integer.parseInt(minTextField.getText()));
                    partIH.setMax(Integer.parseInt(maxTextField.getText()));
                    partIH.setMachineID(Integer.parseInt(switchTextField.getText()));
                    Inventory.addParts(partIH);
                } else if (isOutsourced == true) {
                    OutsourcedPart partOS = new OutsourcedPart();
                    partOS.setPartId(Integer.parseInt(idTextField.getText()));
                    partOS.setName(nameTextField.getText());
                    partOS.setInStock(Integer.parseInt(inventoryTextField.getText()));
                    partOS.setPrice(Double.parseDouble(priceTextField.getText()));
                    partOS.setMin(Integer.parseInt(minTextField.getText()));
                    partOS.setMax(Integer.parseInt(maxTextField.getText()));
                    partOS.setCompanyName(switchLabel.getText());
                    Inventory.addParts(partOS);

                }

                //switch to home screen
                Parent addPartsScreen = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene addPartsScene = new Scene(addPartsScreen);
                //next line is getting stage information
                Stage addPartsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addPartsStage.setScene((addPartsScene));
                addPartsStage.show();

            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding part");
            alert.setContentText("Blank fields are not allowed");
            alert.showAndWait();
        }

    }

    //initialize method that loads when screen loads
    @Override
    public void initialize (URL url, ResourceBundle rb){
        ;

    }

}
