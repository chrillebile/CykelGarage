package BikeGarage;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Maneges all the managers
 * @author Christian Bilevits
 */
public class AdminManager {
    CustomerManager customerManager;
    BikeManager bikeManager;
    DatabaseManager dbManager;

    /**
     * This is the class that connects all major components of the garage. All administrative tasks are done by this.
     * @param customerManager Manager for customers
     * @param bikeManager Manager for bikes
     * @param dbManager Manager for database
     */
    public AdminManager(CustomerManager customerManager, BikeManager bikeManager, DatabaseManager dbManager){
        this.customerManager = customerManager;
        this.bikeManager = bikeManager;
        this.dbManager = dbManager;
    }

    /**
     * @param personNr The user's personal id.
     * @return A list of bikes a user has.
     */
    public ArrayList<Bike> findBikesByCustomer(String personNr){
        return bikeManager.findBikesByPersonNr(personNr);
    }

    /**
     * Create a new user with the given parameters.
     * @param firstName The user's first name.
     * @param surname The user's surname.
     * @param personNr The user's personal id number.
     * @param pin The user's pin code.
     * @param phoneNr The user's phone number.
     * @return The created user.
     */
    public Customer createCustomer(String firstName, String surname, String personNr, String pin, String phoneNr){
        return customerManager.createCustomer(firstName, surname, personNr, pin, phoneNr);
    }

    /**
     * Remove a customer with a given personNr.
     * @param personNr The user's personal id number.
     * @return Whether the removal was successful.
     */
    public boolean removeCustomer(String personNr){
        if(bikeManager.hasBikeParked(customerManager.findCustomerByPersonNr(personNr))){
            throw new IllegalArgumentException("The customer has a parked bike.");
        }
        ArrayList<Bike> tempList = bikeManager.findBikesByPersonNr(personNr);
        for(Bike b: tempList){
            removeBike(b.getBarcodeNr());
        }
        return customerManager.removeCustomer(personNr);
    }

    /**
     * Add a new bike to a specific user. The customer is already given.
     * @param customer The customer object.
     * @return The created bike.
     */
    public Bike addBike(Customer customer){
        return bikeManager.addBike(customer);
    }

    /**
     * Add a new bike to a specific user. The customer is searched by personNr.
     * @param personNr The personal id of the user.
     * @return The bike that has been created.
     */
    public Bike addBike(String personNr){
        return (findCustomer(personNr) != null) ? bikeManager.addBike(findCustomer(personNr)) : null;
    }

    /**
     * Remove a bike with a given barcodeNr.
     * @param barcodeNr This is the bike's identification number. It is unique.
     * @return Whether the bike has been successfully removed.
     */
    public boolean removeBike(long barcodeNr){
        return bikeManager.removeBike(barcodeNr);
    }

    /**
     * Edit a bike's customer.
     * @param barcodeNr The bike.
     * @param newCustomerPersonNr The new bike's customer personNr.
     * @return Whether the edit was successful.
     */
    public boolean editBikeCustomer(long barcodeNr, String newCustomerPersonNr){
        Customer customer = findCustomer(newCustomerPersonNr);
        if(customer == null){
            throw new IllegalArgumentException("Kund ej hitta användare med given personnr.");
        }
        return bikeManager.editBikeCustomer(barcodeNr, customer);
    }

    /**
     * @return The list of customers.
     */
    public ArrayList<Customer> getCustomerList(){
        return customerManager.getCustomerList();
    }

    /**
     * @return The list of all bikes.
     */
    public ArrayList<Bike> getBikeList(){
        return bikeManager.getBikeList();
    }

    /**
     * @return The list of all parked bikes.
     */
    public ArrayList<Bike> getParkedBikeList(){
        return bikeManager.getParkedBikeList();
    }

    /**
     * Changes unix time to readable format
     * @param unixTime Unix time
     * @return Readable time
     */
    public String getFormatUnixTime(long unixTime){
        if(unixTime != 0){
            return new SimpleDateFormat(Config.DATE_FORMAT).format(unixTime);
        }else {
            return "Ingen";
        }
    }

    /**
     * Change boolean to readable format
     * @param status Status of parked bike
     * @return Returns "Ja" if bikes is parked and "Nej" if it's not parked
     */
    public String getParkingStatus(Boolean status){
        return status ? "Ja" : "Nej";
    }

    /**
     * Set a bike's entry time and adds bike to parkedBikeList if it's parked.
     * @param barcodeNr The bike
     * @param entryTime The time specified in unix time.
     * @return Whether the edit was successful.
     */
    public void setBikeEntryTime(long barcodeNr, long entryTime){
        bikeManager.setBikeEntryTime(barcodeNr, entryTime);
    }

    /**
     * Set a bike's exit time and removes bike from parkedBikeList if it's not parked.
     * @param barcodeNr The bike
     * @param exitTime The exit time specified in unix time.
     * @return Whether the edit was successful.
     */
    public void setBikeExitTime(long barcodeNr, long exitTime){
        bikeManager.setBikeExitTime(barcodeNr, exitTime);
    }
    /**
     * Seearch a specific customer given its personNr.
     * @param personNr Unique identification for the customer
     * @return The found customer.
     */
    public Customer findCustomer(String personNr){
        return customerManager.findCustomerByPersonNr(personNr);
    }

    /**
     * Search a specific bike given its barcodeNr.
     * @param barcodeNr Unique identification for the bike.
     * @return The found bike.
     */
    public Bike findBike(long barcodeNr){
        return bikeManager.findBikeByBarcodeNr(barcodeNr);
    }

    /**
     * Check if a pin exist
     * @param pin A pin-code.
     * @return Returns if pin-code exist.
     */
    public boolean checkIfPinExist(String pin){
        for(Bike b: bikeManager.getParkedBikeList()){
            if(b.getCustomer().getPin().equals(pin)){
                return true;
            }
        }
        return false;
    }
    /**
     * @return Number of free parking spots.
     */
    public int numberOfFreeParkingSpots(){
        return Config.MAX_PARKING_SPOTS - bikeManager.getParkedBikeList().size();
    }

    /**
     * Calls the databasemanager and performs and updates the list in database
     */
    public void updateCustomers(){
        dbManager.updateCustomers();
    }

    /**
     * Calls the databasemanager and performs and updates the list in database
     */
    public void updateBikes(){
        dbManager.updateBikes();
    }

    /**
     * Calls the databasemanager and performs and updates the list in database
     */
    public void updateConfig(){
        dbManager.updateConfig();
    }
}
