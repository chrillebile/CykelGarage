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
import javafx.scene.layout.AnchorPane;

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
    private MenuItem menuBtnNumberOfParkedBikes;

    @FXML
    private MenuItem menuBtnNumberOfFreeParkingSpots;

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
    private AnchorPane anchPaneCustomerButtons;

    @FXML
    private AnchorPane anchPaneBikeButtons;

    @FXML Button btnShowAllBikes;

    @FXML
    void handleAddUserButton(ActionEvent event) {
        windowManager.initEditUser(null);
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
        customerList = FXCollections.observableList(adminManager.getCustomerList());


        tblColUserPersonnr.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPersonNr()));
        tblColUserFirstName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getFirstName()));
        tblColUserLastName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getSurname()));
        tblColUserRegisterTime.setCellValueFactory(p -> new SimpleStringProperty(adminManager.getFormatUnixTime(p.getValue().getRegTime())));

        tblUserList.setItems(customerList);

        tblUserList.getSelectionModel().selectedItemProperty().addListener(this::handleSelectionChangeTblUserList);


        // Initialize the tblBikeList's attributes
        bikeList = FXCollections.observableList(adminManager.getBikeList());
        tblColBikeBarcode.setCellValueFactory(bike -> new SimpleStringProperty(bike.getValue().getBarcodeNrInString()));
        tblColBikeOwner.setCellValueFactory(bike -> new SimpleStringProperty(( bike.getValue().getCustomer().getFirstName() + " " + bike.getValue().getCustomer().getSurname())));
        tblColBikeParkingStart.setCellValueFactory(bike -> new SimpleStringProperty(adminManager.getFormatUnixTime(bike.getValue().getEntryTime())));
        tblColBikeParkingEnd.setCellValueFactory(bike -> new SimpleStringProperty((adminManager.getFormatUnixTime(bike.getValue().getExitTime()))));
        tblBikeList.setItems(bikeList);

        tblBikeList.getSelectionModel().selectedItemProperty().addListener(this::handleSelectionChangeTblBikeList);


        // Set the number of bikes and customers in menubar
        menuBtnNumberOfParkedBikes.setText("Antal parkerade cyklar: " + (adminManager.totalNumberOfParkingSpots() - adminManager.numberOfFreeParkingSpots()));
        menuBtnNumberOfFreeParkingSpots.setText("Antal lediga platser :" + adminManager.numberOfFreeParkingSpots());
    }

    /**
     * Handles the change of selection in tblUserList. This mainly changes the items in tblBikeList.
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleSelectionChangeTblUserList(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue){
        // selectedItemProperty does send null when there isn't a row selected. When that happens, we set bikelist to show all bikes
        if(newValue == null){
            btnEditUser.setDisable(true);
            btnRemoveUser.setDisable(true);
            btnAddBike.setDisable(true);

            bikeList = FXCollections.observableList(adminManager.getBikeList());
        }
        else{
            bikeList = FXCollections.observableArrayList(adminManager.findBikesByCustomer(newValue.getPersonNr()));

            btnEditUser.setDisable(false);
            btnRemoveUser.setDisable(false);
            btnAddBike.setDisable(false);
        }


        tblBikeList.setItems(bikeList);
        // We have to refresh in order for it to show
        tblBikeList.refresh();


    }

    /**
     * Handles the change of selection in tblBikeList.
     * @param observable
     * @param oldBike The old bike that was selected
     * @param newBike The new bike that is selected
     */
    private void handleSelectionChangeTblBikeList(ObservableValue<? extends  Bike> observable, Bike oldBike, Bike newBike){
        // if no selection is made, disable buttons
        if(newBike == null){
            btnEditBike.setDisable(true);
            btnRemoveBike.setDisable(true);
        }
        else{
            btnEditBike.setDisable(false);
            btnRemoveBike.setDisable(false);
        }
    }


    @FXML
    void handleRefreshUserListButton(){
        tblUserList.refresh();
    }

    @FXML
    void handleShowAllBikesButton(){
        // When changing selection, handleSelectionChangeTblUserList is fired. There we do the update
        tblUserList.getSelectionModel().select(null);
        tblBikeList.getSelectionModel().select(null);
        tblUserList.refresh();
        tblBikeList.refresh();

    }

    @FXML
    void handleEditBikeButton(){
        Bike bikeToEdit = tblBikeList.getSelectionModel().getSelectedItem();
        if(bikeToEdit != null){
            windowManager.initEditBike(bikeToEdit, bikeToEdit.getCustomer());
        }
    }

    @FXML
    void handleRemoveUserButton(){
        Customer customerToBeRemoved = tblUserList.getSelectionModel().getSelectedItem();

        adminManager.removeCustomer(customerToBeRemoved.getPersonNr());

        // Save the customers
        adminManager.updateCustomers();


        handleRefreshUserListButton();
    }

    @FXML
    void handleRemoveBikeButton(){
        Bike bikeToBeRemoved = tblBikeList.getSelectionModel().getSelectedItem();
        adminManager.removeBike(bikeToBeRemoved.getBarcodeNr());

        // If a user is selected, the tableview cannot automatically update its internal list as its pointing in the list in adminManager.findBikesByCustomer(). Adminmanager removes the bike in adminManager.getBikeList()
        if(tblUserList.getSelectionModel().getSelectedItem() != null){
            tblBikeList.getItems().remove(bikeToBeRemoved);
        }
        // Save the new bikelist
        adminManager.updateBikes();

        tblBikeList.refresh();
    }

    @FXML
    void handleAddBikeButton(){
        Customer owner = tblUserList.getSelectionModel().getSelectedItem();
        windowManager.initEditBike(null, owner);
    }

    @FXML
    void handleSearchByBarcodeButton(){
        String searchInput = tbxSearchBarcode.getText();
        Bike foundBike = adminManager.findBike(Long.parseLong(searchInput));

        tblUserList.getSelectionModel().select(foundBike.getCustomer());
        tblBikeList.getSelectionModel().select(foundBike);
    }

    @FXML
    void handleSearchByPersonNrButton(){
        String searchInput = tbxSearchPersonalNr.getText();
        Customer foundCustomer = adminManager.findCustomer(searchInput);

        tblUserList.getSelectionModel().select(foundCustomer);
    }

    @FXML
    void handlePrintBarcodeButton(){

    }

}
