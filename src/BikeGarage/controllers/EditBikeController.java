package BikeGarage.controllers;

import BikeGarage.AdminManager;
import BikeGarage.Bike;
import BikeGarage.Customer;
import BikeGarage.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller for editBike.fxml
 */
public class EditBikeController {
    private WindowManager windowManager;
    private AdminManager adminManager;
    private Bike bike;
    private Customer customer;
    private boolean isNewBike = false;


    @FXML
    private TextField tbxBikeBarcode;

    @FXML
    private TextField tbxBikeOwner;

    @FXML
    private ComboBox<String> cbxParkedState;

    @FXML
    private Button btnSpara;

    @FXML
    private CheckBox chkbIsParked;


    public void init(WindowManager windowManager, AdminManager adminManager, Bike bike, Customer customer){
        this.windowManager = windowManager;
        this.adminManager = adminManager;

        // Since there is no bike sent, it means that we must create one
        if(bike == null){
            isNewBike = true;
        }
        this.bike = bike;
        this.customer = customer;

        initializeAfterInit();
    }

    private void initializeAfterInit(){
        if(!isNewBike){
            tbxBikeBarcode.setText(bike.getBarcodeNrInString());
            tbxBikeOwner.setText(bike.getCustomer().getPersonNr());
            chkbIsParked.setSelected(bike.getParkingStatus());
        }
        else{
            // we have a new bike and it doesn't have a customer yet. However we have already selected a customer in tblUserList
            tbxBikeOwner.setText(customer.getPersonNr());
        }
    }

    @FXML
    void handleSaveButton(){
        Bike bikeToBeCreated = bike;
        if(isNewBike){
            bikeToBeCreated = adminManager.addBike(customer);
        }

        // If the checkbox is selected, it means that the bike is parked
        if(chkbIsParked.isSelected()){
            // If a bike is parked, its entry time will be bigger than its exittime. Here we set it to current time, which is greater than exittime.
            bikeToBeCreated.setEntryTime(System.currentTimeMillis());
        }
        else{
            bikeToBeCreated.setExitTime(System.currentTimeMillis());
        }

        // Check if we have made any change in personnr. If we have, then update it
        if(!isNewBike && customer != null && !(tbxBikeOwner.getText().equals(customer.getPersonNr()))){
            bikeToBeCreated.setCustomer(adminManager.findCustomer(tbxBikeOwner.getText()));
        }


        adminManager.updateBikes();
    }
}
