package BikeGarage.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for editUser.fxml
 */
public class EditUserController {

    @FXML
    private TextField tbxPin;

    @FXML
    private TextField tbxLastName;

    @FXML
    private TextField tbxPersonNr;

    @FXML
    private TextField tbxFirstName;

    @FXML
    private TableView<?> tableViewBikeList;

    @FXML
    private TableColumn<?, ?> tblColBarcode;

    @FXML
    private TableColumn<?, ?> tblColOwner;

    @FXML
    private TableColumn<?, ?> tblColParkingStatus;

    @FXML
    private Button btnAddBike;

    @FXML
    private Button btnRemoveBike;

    @FXML
    private Button btnSave;

}
