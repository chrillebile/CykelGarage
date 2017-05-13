package BikeGarage.controllers;

import BikeGarage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

            for (int i = 0; i < lsvBikeList.getItems().size(); i++) {
                adminManager.addBike(customer);
            }

        }
        else{
            String firstName = customer.getFirstName();
            String lastName = customer.getSurname();
            String pinKod = customer.getPin();
            String phoneNr = customer.getPhoneNr();

            try{
                // We have an existing user. Use his setters to set the data
                customer.setFirstName(tbxFirstName.getText());
                customer.setSurname(tbxLastName.getText());
                customer.setPin(tbxPin.getText());
                customer.setPhoneNr(tbxPhoneNr.getText());

                for (int i = 0; i < lsvBikeList.getItems().size(); i++) {
                    if (lsvBikeList.getItems().get(i).contentEquals("Ny Cykel")) {
                        adminManager.addBike(customer);
                    }
                }
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

        // Close the window
        ((Stage) btnSave.getScene().getWindow()).close();
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
            if(lsvBikeList.getItems().size() >= Config.MAX_BIKES_PER_PERSON){
                btnAddBike.setDisable(true);
            }
        }
    }

    @FXML
    void handleAddBikeButton(){
        lsvBikeList.getItems().add("Ny Cykel");
        if(lsvBikeList.getItems().size() >= Config.MAX_BIKES_PER_PERSON){
            btnAddBike.setDisable(true);
        }
    }

    @FXML
    void handleRemoveBikeButton(){
        if(lsvBikeList.getSelectionModel().isEmpty()){
            windowManager.openPopup("Du måste välja en cykel för att kunna ta bort");
            return;
        }
        if(!lsvBikeList.getSelectionModel().getSelectedItem().contentEquals("Ny Cykel")){
            try {
                adminManager.removeBike(Long.parseLong(lsvBikeList.getSelectionModel().getSelectedItem()));
            } catch (IllegalArgumentException e){
                windowManager.openPopup(e.getMessage());
                return;
            }
        }
        lsvBikeList.getItems().remove(lsvBikeList.getSelectionModel().getSelectedIndex());
        if(lsvBikeList.getItems().size() < Config.MAX_BIKES_PER_PERSON){
            btnAddBike.setDisable(false);
        }
    }
}
