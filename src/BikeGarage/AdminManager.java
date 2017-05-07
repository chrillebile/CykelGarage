package BikeGarage;

import java.util.ArrayList;

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
        return customerManager.createCustomer(firstName, surname, personNr, pin, phoneNr, 0);
    }

    /**
     * Remove a customer with a given personNr.
     * @param personNr The user's personal id number.
     * @return Whether the removal was successful.
     */
    public boolean removeCustomer(String personNr){
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
    public boolean removeBike(Long barcodeNr){
        return bikeManager.removeBike(barcodeNr);
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
    public Bike findBike(Long barcodeNr){
        return bikeManager.findBikeByBarcodeNr(barcodeNr);
    }

    /**
     * @return Number of free parking spots.
     */
    public int numberOfFreeParkingSpots(){
        return totalNumberOfParkingSpots() - bikeManager.getParkedBikeList().size();
    }

    /**
     * @return Total number of parking spots the garage has.
     */
    public int totalNumberOfParkingSpots(){
        return Config.MAX_PARKING_SPOTS;
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
