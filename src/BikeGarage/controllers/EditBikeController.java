package BikeGarage.controllers;

import BikeGarage.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for editBike.fxml
 */
public class EditBikeController {
    private WindowManager windowManager;
    private AdminManager adminManager;
    private Bike bike;
    private Customer customer;
    private boolean isNewBike = false;
    private MainController mainController;
    private HardwareManager hardwareManager;


    @FXML
    private TextField tbxBikeBarcode;

    @FXML
    private TextField tbxBikeOwner;

    @FXML
    private Button btnSpara;

    @FXML
    private CheckBox chkbIsParked;


    /**
     * Initialize the variables this class uses.
     *
     * @param windowManager
     * @param adminManager
     * @param bike
     * @param customer
     * @param mainController
     * @param hardwareManager
     */
    public void init(WindowManager windowManager, AdminManager adminManager, Bike bike, Customer customer, MainController mainController, HardwareManager hardwareManager) {
        this.windowManager = windowManager;
        this.adminManager = adminManager;
        this.hardwareManager = hardwareManager;

        // Since there is no bike sent, it means that we must create one
        if (bike == null) {
            isNewBike = true;
        }
        this.bike = bike;
        this.customer = customer;
        this.mainController = mainController;

        initializeAfterInit();
    }

    /**
     * Initialize the visual components of the window. To be used after initializing the variables.
     */
    private void initializeAfterInit() {
        if (!isNewBike) {
            tbxBikeBarcode.setText(bike.getBarcodeNrInString());
            tbxBikeOwner.setText(bike.getCustomer().getPersonNr());
            chkbIsParked.setSelected(bike.getParkingStatus());
        } else {
            // we have a new bike and it doesn't have a customer yet. However we have already selected a customer in tblUserList
            tbxBikeOwner.setText(customer.getPersonNr());
        }
    }

    @FXML
    private void handleSaveButton() {
        Bike bikeToBeCreated = bike;
        if (isNewBike) {
            try {
                bikeToBeCreated = adminManager.addBike(customer);
            } catch (IllegalArgumentException e) {
                windowManager.openPopup(e.getMessage());
                return;
            }
        }

        // If the checkbox is selected, it means that the bike is parked
        if (chkbIsParked.isSelected()) {
            // If a bike is parked, its entry time will be bigger than its exittime. Here we set it to current time, which is greater than exittime.
            try {
                adminManager.setBikeEntryTime(bikeToBeCreated.getBarcodeNr(), System.currentTimeMillis());
            } catch (IllegalArgumentException e) {
                windowManager.openPopup(e.getMessage());
            }
        } else {
            adminManager.setBikeExitTime(bikeToBeCreated.getBarcodeNr(), System.currentTimeMillis());
        }

        // Check if we have made any change in personnr. If we have, then update it
        if (!isNewBike && customer != null && !(tbxBikeOwner.getText().equals(customer.getPersonNr()))) {
            try {
                adminManager.editBikeCustomer(bike.getBarcodeNr(), tbxBikeOwner.getText());
            } catch (IllegalArgumentException e) {
                windowManager.openPopup(e.getMessage());
            }
        }

        // print the barcode only if we are creating the bike
        if (isNewBike) {
            hardwareManager.printBarcode(bikeToBeCreated.getBarcodeNrInString());
        }


        mainController.handleShowAllBikesButton();

        adminManager.updateBikes();


        // Close the window
        ((Stage) btnSpara.getScene().getWindow()).close();
    }
}
