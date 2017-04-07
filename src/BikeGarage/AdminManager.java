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

    }

    /**
     * @param personNr The user's personal id.
     * @return A list of bikes a user has.
     */
    public ArrayList<Bike> findBikesByCustomer(String personNr){

    }

    /**
     * Create a new user with the given parameters.
     * @param firstName
     * @param surname
     * @param personNr The user's personal id number.
     * @param pin
     * @return The created user.
     */
    public Customer createCustomer(String firstName, String surname, String personNr, String pin){

    }

    /**
     * Remove a customer with a given personNr.
     * @param personNr The user's personal id number.
     * @return Whether the removal was successful.
     */
    public boolean removeCustomer(String personNr){

    }

    /**
     * Add a new bike to a specific user. The customer is already given.
     * @param customer The customer object.
     * @return The created bike.
     */
    public Bike addBike(Customer customer){

    }

    /**
     * Add a new bike to a specific user. The customer is searched by personNr.
     * @param personNr The personal id of the user.
     * @return The bike that has been created.
     */
    public Bike addBike(String personNr){

    }

    /**
     * Remove a bike with a given barcodeNr.
     * @param barcodeNr This is the bike's identification number. It is unique.
     * @return Whether the bike has been successfully removed.
     */
    public boolean removeBike(String barcodeNr){

    }

    /**
     * @return The list of stored customers.
     */
    public ArrayList<Customer> getCustomerList(){

    }

    /**
     * @return The list of all stored bikes.
     */
    public ArrayList<Bike> getBikeList(){

    }

    /**
     * Seearch a specific customer given its personNr.
     * @param personNr Unique identification for the customer
     * @return The found customer.
     */
    public Customer findCustomer(String personNr){

    }

    /**
     * Search a specific bike given its barcodeNr.
     * @param barcodeNr Unique identification for the bike.
     * @return The found bike.
     */
    public Bike findBike(String barcodeNr){

    }

    /**
     * @return Number of free parking spots.
     */
    public int numberOfFreeParkingSpots(){

    }

    /**
     * @return Total number of parking spots the garage has.
     */
    public int totalNumberOfParkingSpots(){

    }
}
