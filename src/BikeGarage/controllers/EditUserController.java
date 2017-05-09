package BikeGarage.controllers;

import BikeGarage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for editUser.fxml
 */
public class EditUserController {

    private WindowManager windowManager;
    private AdminManager adminManager;
    private Customer customer;
    private ObservableList<Bike> bikeList;
    private boolean isNewUser = false;
    private MainController mainController;

    @FXML
    private TextField tbxPin;

    @FXML
    private TextField tbxLastName;

    @FXML
    private TextField tbxPersonNr;

    @FXML
    private TextField tbxFirstName;

    @FXML
    private TextField tbxPhoneNr;

    @FXML
    private ListView<String> lsvBikeList;

    @FXML
    private Button btnAddBike;

    @FXML
    private Button btnRemoveBike;

    @FXML
    private Button btnSave;

    @FXML
    void handleSaveButton(){
        if(isNewUser) {
            try{
                customer = adminManager.createCustomer(tbxFirstName.getText(), tbxLastName.getText(), tbxPersonNr.getText(), tbxPin.getText(), tbxPhoneNr.getText());
            }
            catch (IllegalArgumentException e){
                windowManager.openPopup(e.getMessage());
                return;
            }

            // Get the number of bikes that need to be added to  the customer
            int numOfBikes = lsvBikeList.getItems().size();

            for (int i = 0; i < numOfBikes; i++) {
                adminManager.addBike(customer);
            }

        }
        else{
            String firstName = customer.getFirstName();
            String lastName = customer.getSurname();
            String personNr = customer.getPersonNr();
            String pinKod = customer.getPin();
            String phoneNr = customer.getPhoneNr();

            try{
                // We have an existing user. Use his setters to set the data
                customer.setFirstName(tbxFirstName.getText());
                customer.setSurname(tbxLastName.getText());
                customer.setPin(tbxPin.getText());
                customer.setPhoneNr(tbxPhoneNr.getText());
            }
            catch (IllegalArgumentException e){
                // Something went wrong. Revert the attributes to what they were
                customer.setFirstName(firstName);
                customer.setSurname(lastName);
                customer.setPin(pinKod);
                customer.setPhoneNr(phoneNr);


                windowManager.openPopup(e.getMessage());
                return;
            }
        }

        mainController.handleShowAllBikesButton();

        adminManager.updateCustomers();
        adminManager.updateBikes();
    }

    /**
     * Used to initialize the controller. Here we set all variables ths controller requires (e.g. access to managers). Then the components get filled.
     * @param windowManager The instance of WindowManager
     * @param adminManager The instance of AdminManager the system uses
     * @param customer The customer that will be edited. If the customer is null, it means that a new user will be created
     */
    public void init(WindowManager windowManager, AdminManager adminManager, Customer customer, MainController mainController){
        this.windowManager = windowManager;
        this.adminManager = adminManager;

        if(customer == null){
            isNewUser = true;
        }
        this.customer = customer;
        this.mainController = mainController;

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
            tbxPersonNr.setDisable(true);
            tbxPin.setText(customer.getPin());
            tbxPhoneNr.setText(customer.getPhoneNr());

            // Initialize the textbox
            bikeList = FXCollections.observableArrayList(adminManager.findBikesByCustomer(customer.getPersonNr()));
            for(Bike b: bikeList){
                lsvBikeList.getItems().add(b.getBarcodeNrInString());
            }
        }
    }

    @FXML
    void handleAddBikeButton(){
        lsvBikeList.getItems().add("Bike");
    }

    @FXML
    void handleRemoveBikeButton(){
        lsvBikeList.getItems().remove(lsvBikeList.getSelectionModel().getSelectedIndex());
    }
}
