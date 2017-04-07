package BikeGarage;

import java.util.ArrayList;

public class BikeManager {
    private ArrayList<Bike>  bikeList;

    /**
     * The manager that manages bikes registered in the garage.
     */
    public BikeManager(){
    }

    /**
     * Search after a bike that is owned by a specific user.
     * @param personNr The user's personal identification number.
     * @return The found bike.
     */
    public Bike findBikeByPersonNr(String personNr){

    }

    /**
     * Search after a bike given its barcode number.
     * @param barcodeNr Unique identification for the bike.
     * @return The found bike.
     */
    public Bike findBikeByBarcodeNr(String barcodeNr){

    }

    /**
     * @return A list of all bikes stored in the system.
     */
    public ArrayList<Bike> getBikeList(){

    }

    /**
     * Add a bike to a given customer.
     * @param customer The customer where the bike will be added.
     * @return The complete bike object, including the customer.
     */
    public Bike addBike(Customer customer){

    }

    /**
     * Remove a bike with a specific barcode.
     * @param barcodeNr Unique identification for the bike.
     * @return Whether the removal was successful.
     */
    public boolean removeBike(String barcodeNr){

    }

    /**
     * Edit a bike's customer.
     * @param barcodeNr The bike.
     * @param newCustomer The bike's customer.
     * @return Whether the edit was successful.
     */
    public boolean editBikeCustomer(String barcodeNr, Customer newCustomer){

    }

    /**
     * Set a bike's entry time.
     * @param barcodeNr Unique identification for the bike.
     * @param entryTime The time specified in unix time.
     * @return Whether the edit was successful.
     */
    public boolean setBikeEntryTime(String barcodeNr, long entryTime){

    }

    /**
     * Set a bike's exit time.
     * @param barcodenr Unique identification for the bike.
     * @param exitTime The exit time specified in unix time.
     * @return Whether the edit was successful.
     */
    public boolean setBikeExitTime(String barcodenr, long exitTime){

    }

    /**
     * Check if a given bike is parked in the garage.
     * @param barcodeNr Unique identification for the bike.
     * @return Whether the bike is parked.
     */
    public boolean isBikeParked(String barcodeNr){

    }


}
