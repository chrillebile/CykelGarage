package BikeGarage.controllers;

import BikeGarage.AdminManager;
import BikeGarage.Bike;
import BikeGarage.Customer;
import BikeGarage.WindowManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private AdminManager adminManager;

    private ObservableList<Customer> customerList;
    private ObservableList<Bike> bikeList;


    @FXML
    private MenuItem menuBtnClose;

    @FXML
    private MenuItem menuBtnNumberOfUsers;

    @FXML
    private MenuItem menuBtnNumberOfBikes;

    @FXML
    private TableView<Customer> tblUserList;

    @FXML
    private TableColumn<Customer, String> tblColUserPersonnr;

    @FXML
    private TableColumn<Customer, String> tblColUserFirstName;

    @FXML
    private TableColumn<Customer, String> tblColUserLastName;

    @FXML
    private TableColumn<Customer, String> tblColUserRegisterTime;

    @FXML
    private TableColumn<Bike, String> tblColUserTelephoneNr;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnRemoveUser;

    @FXML
    private Button btnEditUser;

    @FXML
    private TextField tbxSearchPersonalNr;

    @FXML
    private TableView<Bike> tblBikeList;

    @FXML
    private TableColumn<Bike, String> tblColBikeBarcode;

    @FXML
    private TableColumn<Bike, String> tblColBikeOwner;

    @FXML
    private TableColumn<Bike, String> tblColBikeParkingStart;

    @FXML
    private TableColumn<Bike, String> tblColBikeParkingEnd;

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
        //windowManager.initEditUser();
    }

    @FXML
    void handleEditUserButton(ActionEvent event){
        Customer customerToEdit = tblUserList.getSelectionModel().getSelectedItem();
        if(customerToEdit != null){
            windowManager.initEditUser(customerToEdit);
        }

    }

    /**
     * Used to initialize the controller. Here we set all variables ths controller requires (e.g. access to managers). Then the components get filled.
     * @param windowManager The instance of WindowManager
     * @param adminManager The instance of AdminManager the system uses
     */
    public void init(WindowManager windowManager, AdminManager adminManager){
        this.windowManager = windowManager;
        this.adminManager = adminManager;

        initializeAfterInit();
    }


    /**
     * A custom function to initialize and fill all components with start data. To be used inside init()
     */
    public void initializeAfterInit(){
        System.out.println("init");

        // Set the data for the customer tableview. The bike tableview is updated when clicking on a customer
        customerList = FXCollections.observableArrayList(adminManager.getCustomerList());


        tblColUserPersonnr.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPersonNr()));
        tblColUserFirstName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getFirstName()));
        tblColUserLastName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getSurname()));
        tblColUserRegisterTime.setCellValueFactory(p -> new SimpleStringProperty(Long.toString(p.getValue().getRegTime())));

        tblUserList.setItems(customerList);

        tblUserList.getSelectionModel().selectedItemProperty().addListener(this::handleSelectionChangeTblUserList);


        // Initialize the tblBikeList's attributes
        tblColBikeBarcode.setCellValueFactory(bike -> new SimpleStringProperty(bike.getValue().getBarcodeNrInString()));
        tblColBikeOwner.setCellValueFactory(bike -> new SimpleStringProperty(( bike.getValue().getCustomer().getFirstName() + " " + bike.getValue().getCustomer().getSurname())));
        tblColBikeParkingStart.setCellValueFactory(bike -> new SimpleStringProperty(Long.toString(bike.getValue().getEntryTime())));
        tblColBikeParkingEnd.setCellValueFactory(bike -> new SimpleStringProperty((Long.toString(bike.getValue().getExitTime()))));
    }

    /**
     * Handles the change of selection in tblUserList. This mainly changes the items in tblBikeList.
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleSelectionChangeTblUserList(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue){
        bikeList = FXCollections.observableArrayList(adminManager.findBikesByCustomer(newValue.getPersonNr()));
        tblBikeList.setItems(bikeList);
        // We have to refresh in order for it to show
        tblBikeList.refresh();
    }



}
