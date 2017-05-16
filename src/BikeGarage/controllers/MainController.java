package BikeGarage.controllers;

import BikeGarage.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for main.fxml file.
 */
public class MainController {

    private WindowManager windowManager;
    private AdminManager adminManager;
    private HardwareManager hardwareManager;

    private ObservableList<Customer> customerList;
    private ObservableList<Bike> bikeList;

    @FXML
    private MenuItem menuBtnNumberOfParkedBikes;

    @FXML
    private MenuItem menuBtnNumberOfFreeParkingSpots;

    @FXML
    private MenuItem menuBtnTotalNumberOfParkingSpots;

    @FXML
    private Menu menuBtnStatisticsMenu;

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
    private TableColumn<Customer, String> tblColUserTelephoneNr;

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
    private TableColumn<Bike, String> tblColBikeParkingStatus;

    @FXML
    private Button btnAddBike;

    @FXML
    private Button btnRemoveBike;

    @FXML
    private Button btnEditBike;

    @FXML
    private TextField tbxSearchBarcode;

    @FXML
    private AnchorPane anchPaneBikeButtons;

    @FXML
    private Button btnPrintBarcode;

    @FXML
    private void handleAddUserButton(ActionEvent event) {
        windowManager.initEditUser(null, this);
    }

    @FXML
    private void handleEditUserButton(ActionEvent event) {
        Customer customerToEdit = tblUserList.getSelectionModel().getSelectedItem();
        if (customerToEdit != null) {
            windowManager.initEditUser(customerToEdit, this);
        }
    }

    /**
     * Used to initialize the controller. Here we set all variables ths controller requires (e.g. access to managers). Then the components get filled.
     *
     * @param windowManager   The instance of WindowManager
     * @param adminManager    The instance of AdminManager the system uses
     * @param hardwareManager The instance of HardwareManager the system uses
     */
    public void init(WindowManager windowManager, AdminManager adminManager, HardwareManager hardwareManager) {
        this.windowManager = windowManager;
        this.adminManager = adminManager;
        this.hardwareManager = hardwareManager;

        initializeAfterInit();
    }


    /**
     * A custom function to initialize and fill all components with start data. To be used inside init()
     */
    private void initializeAfterInit() {
        System.out.println("init");

        // Set the data for the customer tableview. The bike tableview is updated when clicking on a customer
        customerList = FXCollections.observableList(adminManager.getCustomerList());


        tblColUserPersonnr.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPersonNr()));
        tblColUserFirstName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getFirstName()));
        tblColUserLastName.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getSurname()));
        tblColUserRegisterTime.setCellValueFactory(customer -> new SimpleStringProperty(adminManager.getFormatUnixTime(customer.getValue().getRegTime())));
        tblColUserTelephoneNr.setCellValueFactory(customer -> new SimpleStringProperty(customer.getValue().getPhoneNr()));
        tblUserList.setItems(customerList);

        tblUserList.getSelectionModel().selectedItemProperty().addListener(this::handleSelectionChangeTblUserList);


        // Initialize the tblBikeList's attributes
        bikeList = FXCollections.observableList(adminManager.getBikeList());
        tblColBikeBarcode.setCellValueFactory(bike -> new SimpleStringProperty(bike.getValue().getBarcodeNrInString()));
        tblColBikeOwner.setCellValueFactory(bike -> new SimpleStringProperty((bike.getValue().getCustomer().getFirstName() + " " + bike.getValue().getCustomer().getSurname())));
        tblColBikeParkingStart.setCellValueFactory(bike -> new SimpleStringProperty(adminManager.getFormatUnixTime(bike.getValue().getEntryTime())));
        tblColBikeParkingEnd.setCellValueFactory(bike -> new SimpleStringProperty(adminManager.getFormatUnixTime(bike.getValue().getExitTime())));
        tblColBikeParkingStatus.setCellValueFactory(bike -> new SimpleStringProperty(adminManager.getParkingStatus(bike.getValue().getParkingStatus())));
        tblBikeList.setItems(bikeList);

        tblBikeList.getSelectionModel().selectedItemProperty().addListener(this::handleSelectionChangeTblBikeList);

        // When clicking on Statistics we update the shown statistics
        menuBtnStatisticsMenu.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                menuBtnNumberOfParkedBikes.setText("Antal parkerade cyklar: " + (Config.MAX_PARKING_SPOTS - adminManager.numberOfFreeParkingSpots()));
                menuBtnNumberOfFreeParkingSpots.setText("Antal lediga platser: " + adminManager.numberOfFreeParkingSpots());
                menuBtnTotalNumberOfParkingSpots.setText("Totalt antal parkeringsplatser: " + Config.MAX_PARKING_SPOTS);
            }
        });
    }

    /**
     * Handles the change of selection in tblUserList. This mainly changes the items in tblBikeList.
     */
    private void handleSelectionChangeTblUserList(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
        // selectedItemProperty does send null when there isn't a row selected. When that happens, we set bikelist to show all bikes
        if (newValue == null) {
            btnEditUser.setDisable(true);
            btnRemoveUser.setDisable(true);
            btnAddBike.setDisable(true);

            bikeList = FXCollections.observableList(adminManager.getBikeList());
        } else {
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
     *
     * @param observable
     * @param oldBike    The old bike that was selected
     * @param newBike    The new bike that is selected
     */
    private void handleSelectionChangeTblBikeList(ObservableValue<? extends Bike> observable, Bike oldBike, Bike newBike) {
        // if no selection is made, disable buttons
        if (newBike == null) {
            btnEditBike.setDisable(true);
            btnRemoveBike.setDisable(true);
            btnPrintBarcode.setDisable(true);
        } else {
            btnEditBike.setDisable(false);
            btnRemoveBike.setDisable(false);
            btnPrintBarcode.setDisable(false);
        }
    }


    @FXML
    private void handleRefreshUserListButton() {
        customerList = FXCollections.observableList(adminManager.getCustomerList());
        tblUserList.setItems(customerList);
        tblUserList.refresh();
    }

    /**
     * Actionhandler for when pressing the show-all-bikes button.
     */
    @FXML
    public void handleShowAllBikesButton() {
        // When changing selection, handleSelectionChangeTblUserList is fired. There we do the update
        tblUserList.getSelectionModel().select(null);
        tblBikeList.getSelectionModel().select(null);
        handleRefreshUserListButton();
        tblBikeList.refresh();
    }

    @FXML
    private void handleEditBikeButton() {
        Bike bikeToEdit = tblBikeList.getSelectionModel().getSelectedItem();
        if (bikeToEdit != null) {
            windowManager.initEditBike(bikeToEdit, bikeToEdit.getCustomer(), this);
        }
    }

    @FXML
    private void handleRemoveUserButton() {
        Customer customerToBeRemoved = tblUserList.getSelectionModel().getSelectedItem();

        try {
            adminManager.removeCustomer(customerToBeRemoved.getPersonNr());
            // Save the customers
            adminManager.updateCustomers();

            handleShowAllBikesButton();
        } catch (IllegalArgumentException e) {
            windowManager.openPopup(e.getMessage());
            return;
        }

    }

    @FXML
    private void handleRemoveBikeButton() {
        Bike bikeToBeRemoved = tblBikeList.getSelectionModel().getSelectedItem();

        try {
            adminManager.removeBike(bikeToBeRemoved.getBarcodeNr());

            // If a user is selected, the tableview cannot automatically update its internal list as its pointing in the list in adminManager.findBikesByCustomer(). Adminmanager removes the bike in adminManager.getBikeList()
            if (tblUserList.getSelectionModel().getSelectedItem() != null) {
                tblBikeList.getItems().remove(bikeToBeRemoved);
            }
            // Save the new bikelist
            adminManager.updateBikes();
            tblBikeList.refresh();

        } catch (IllegalArgumentException e) {
            windowManager.openPopup(e.getMessage());
            return;
        }

    }

    @FXML
    private void handleAddBikeButton() {
        if (tblUserList.getSelectionModel().isEmpty()) {
            windowManager.openPopup("Du måste välja en användare att lägga till en cykel på.");
            return;
        }
        Customer owner = tblUserList.getSelectionModel().getSelectedItem();
        windowManager.initEditBike(null, owner, this);
    }

    @FXML
    private void handleSearchByBarcodeButton() {
        long searchInput;

        try {
            searchInput = Long.parseLong(tbxSearchBarcode.getText());
        } catch (NumberFormatException e) {
            windowManager.openPopup("Inkorrekt inmatad streckkod");
            return;
        }
        Bike foundBike = adminManager.findBike(searchInput);

        if (foundBike == null) {
            windowManager.openPopup("Ingen cykel hittades.");
            return;
        }

        tblUserList.getSelectionModel().select(foundBike.getCustomer());
        tblBikeList.getSelectionModel().select(foundBike);
    }

    @FXML
    private void handleSearchByPersonNrButton() {
        String searchInput = tbxSearchPersonalNr.getText();
        Customer foundCustomer = adminManager.findCustomer(searchInput);

        tblUserList.getSelectionModel().select(foundCustomer);
    }

    @FXML
    private void handlePrintButton() {
        Bike bike = tblBikeList.getSelectionModel().getSelectedItem();
        if (bike == null) {
            return;
        }

        try {
            hardwareManager.printBarcode(bike.getBarcodeNrInString());
        } catch (IllegalArgumentException e) {
            windowManager.openPopup(e.getMessage());
        }
    }

    @FXML
    private void handleMenuBtnSettings() {
        windowManager.initSettings(null);
    }

    @FXML
    private void handleButtonClose() {
        adminManager.updateCustomers();
        adminManager.updateBikes();
        adminManager.updateConfig();
        ((Stage) anchPaneBikeButtons.getScene().getWindow()).close();
    }
}
