package BikeGarage.controllers;

import BikeGarage.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for main.fxml file.
 */
public class MainController {

    private WindowManager windowManager;


    @FXML
    private MenuItem menuBtnClose;

    @FXML
    private MenuItem menuBtnNumberOfUsers;

    @FXML
    private MenuItem menuBtnNumberOfBikes;

    @FXML
    private TableView<?> tblUserList;

    @FXML
    private TableColumn<?, ?> tblColUserPersonnr;

    @FXML
    private TableColumn<?, ?> tblColUserFirstName;

    @FXML
    private TableColumn<?, ?> tblColUserLastName;

    @FXML
    private TableColumn<?, ?> tblColUserRegisterTime;

    @FXML
    private TableColumn<?, ?> tblColUserTelephoneNr;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnRemoveUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private TextField tbxSearchPersonalNr;

    @FXML
    private TableView<?> tblBikeList;

    @FXML
    private TableColumn<?, ?> tblColBikeBarcode;

    @FXML
    private TableColumn<?, ?> tblColBikeOwner;

    @FXML
    private TableColumn<?, ?> tblColBikeParkingStart;

    @FXML
    private TableColumn<?, ?> tblColBikeParkingEnd;

    @FXML
    private Button btnAddBike;

    @FXML
    private Button btnRemoveBike;

    @FXML
    private Button btnEditBike;

    @FXML
    private TextField tbxSearchBarcode;

    @FXML
    void handleButton(ActionEvent event) {
        windowManager.initEditUser();
    }

    /**
     * Used by WindowManager to give access to itself to this class
     * @param windowManager
     */
    public void setWindowManager(WindowManager windowManager){
        this.windowManager = windowManager;
    }

}
