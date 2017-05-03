package BikeGarage.controllers;

import BikeGarage.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

/**
 * Controller for editUser.fxml
 */
public class EditUserController {

    private WindowManager windowManager;
    private AdminManager adminManager;
    private Customer customer;
    private ObservableList<Bike> bikeList;
    private boolean isNewUser = false;

    @FXML
    private TextField tbxPin;

    @FXML
    private TextField tbxLastName;

    @FXML
    private TextField tbxPersonNr;

    @FXML
    private TextField tbxFirstName;

    @FXML
    private TableView<Bike> tableViewBikeList;

    @FXML
    private TableColumn<Bike, String> tblColBarcode;

    @FXML
    private TableColumn<Bike, String> tblColOwner;

    @FXML
    private TableColumn<Bike, String> tblColParkingStatus;

    @FXML
    private Button btnAddBike;

    @FXML
    private Button btnRemoveBike;

    @FXML
    private Button btnSave;

    @FXML
    void handleSaveButton(){
        Customer createdCustomer = adminManager.createCustomer(tbxFirstName.getText(), tbxLastName.getText(), tbxPersonNr.getText(), tbxPin.getText(), "");
    }

    /**
     * Used to initialize the controller. Here we set all variables ths controller requires (e.g. access to managers). Then the components get filled.
     * @param windowManager The instance of WindowManager
     * @param adminManager The instance of AdminManager the system uses
     * @param customer The customer that will be edited. If the customer is null, it means that a new user will be created
     */
    public void init(WindowManager windowManager, AdminManager adminManager, Customer customer){
        this.windowManager = windowManager;
        this.adminManager = adminManager;

        if(customer == null){
            isNewUser = true;
        }
        this.customer = customer;

        initializeAfterInit();
    }

    /**
     * A custom function to initialize and fill all components with start data. To be used inside init()
     */
    public void initializeAfterInit() {
        // If we have a new user then we cannot get its name because it doesn't have one yet
        if(isNewUser == false){
            tbxFirstName.setText(customer.getFirstName());
            tbxLastName.setText(customer.getSurname());
            tbxPersonNr.setText(customer.getPersonNr());
            tbxPin.setText(customer.getPin());

            // Initialize the textbox
            bikeList = FXCollections.observableArrayList(adminManager.findBikesByCustomer(customer.getPersonNr()));
            tblColBarcode.setCellValueFactory(bike -> new SimpleStringProperty(bike.getValue().getBarcodeNrInString()));
            tblColOwner.setCellValueFactory(bike -> new SimpleStringProperty(( bike.getValue().getCustomer().getFirstName() + " " + bike.getValue().getCustomer().getSurname())));

            tableViewBikeList.setItems(bikeList);

        }
    }




}
