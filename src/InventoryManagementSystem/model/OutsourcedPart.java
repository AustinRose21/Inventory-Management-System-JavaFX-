package InventoryManagementSystem.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class OutsourcedPart extends Part {

    private final StringProperty companyName;




    //constructor calling the superconstructor and adding outsourced property

    public OutsourcedPart() {
        super();
        companyName = new SimpleStringProperty();
    }




    //getter and setter

    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }

    public String getCompanyName(){
        return this.companyName.get();
    }
}
