package BikeGarage.controllers;

import BikeGarage.AdminManager;
import BikeGarage.Bike;
import BikeGarage.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller for editBike.fxml
 */
public class EditBikeController {
    private WindowManager windowManager;
    private AdminManager adminManager;
    private Bike bike;


    @FXML
    private TextField tbxBikeBarcode;

    @FXML
    private TextField tbxBikeOwner;

    @FXML
    private ComboBox<String> cbxParkedState;

    @FXML
    private Button btnSpara;


    public void init(WindowManager windowManager, AdminManager adminManager, Bike bike){
        this.windowManager = windowManager;
        this.adminManager = adminManager;
        this.bike = bike;

        initializeAfterInit();
    }

    private void initializeAfterInit(){
        tbxBikeBarcode.setText(bike.getBarcodeNrInString());
        tbxBikeOwner.setText(bike.getCustomer().getPersonNr());
        cbxParkedState.getItems().addAll(
                "Ja",
                "Nej"
        );
    }
}
