package BikeGarage.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller for editBike.fxml
 */
public class EditBikeController {

    @FXML
    private TextField tbxBikeBarcode;

    @FXML
    private TextField tbxBikeOwner;

    @FXML
    private ComboBox<?> cbxParkedState;

    @FXML
    private Button btnSpara;

}
