package BikeGarage;

import java.util.ArrayList;

public class BikeManager {
    private ArrayList<Bike>  bikeList;
    private long lastUsedBarcode;

    /**
     * The manager that manages bikes registered in the garage.
     */
    public BikeManager(){
        bikeList = new ArrayList<Bike>();
    }

    /**
     * Search after a bike that is owned by a specific user.
     * @param personNr The user's personal identification number.
     * @return The found bike. If not found, return null.
     */
    public Bike findBikeByPersonNr(String personNr){
        for (Bike bike : bikeList) {
            // Check so that the bike's personnr matches the given personr
            if(bike.getCustomer().getPersonNr().equals(personNr)){
                return bike;
            }
        }

        // If no bike is found, return null
        return null;
    }

    /**
     * Search after a bike given its barcode number.
     * @param barcodeNr Unique identification for the bike.
     * @return The found bike. If not found, return null.
     */
    public Bike findBikeByBarcodeNr(long barcodeNr){
        for (Bike bike : bikeList) {
            if(bike.getBarcodeNr() == barcodeNr){
                return bike;
            }
        }
        return null;
    }

    /**
     * @return A list of all bikes stored in the system.
     */
    public ArrayList<Bike> getBikeList(){
        return bikeList;
    }

    /**
     * Add a bike to a given customer.
     * @param customer The customer where the bike will be added.
     * @return The complete bike object, including the customer.
     */
    public Bike addBike(Customer customer){
        Bike bikeToBeAdded = new Bike(getNextFreeBarcode(), customer);
        bikeList.add(bikeToBeAdded);
        return bikeToBeAdded;
    }

    /**
     * Iterate through the bikelist and get the next free barcode. Ideally to be used when adding a bike.
     * @return The next barcode. This barcode is not used by anyone and is one (1) larger than the current maximum barcode.
     */
    private long getNextFreeBarcode(){
        long localMaximumBarcode = 0;
        for (Bike bike : bikeList) {
            if(bike.getBarcodeNr() > localMaximumBarcode){
                localMaximumBarcode = bike.getBarcodeNr();
            }
        }

        // localMaximumBarcode is already taken. Since it is maximum, localMaximumBarcode + 1 is not taken.
        return localMaximumBarcode + 1;
    }

    /**
     * Remove a bike with a specific barcode.
     * @param barcodeNr Unique identification for the bike.
     * @return Whether the removal was successful.
     */
    public boolean removeBike(String barcodeNr){
        return false;
    }

    /**
     * Edit a bike's customer.
     * @param barcodeNr The bike.
     * @param newCustomer The bike's customer.
     * @return Whether the edit was successful.
     */
    public boolean editBikeCustomer(String barcodeNr, Customer newCustomer){
        return false;
    }

    /**
     * Set a bike's entry time.
     * @param barcodeNr Unique identification for the bike.
     * @param entryTime The time specified in unix time.
     * @return Whether the edit was successful.
     */
    public boolean setBikeEntryTime(String barcodeNr, long entryTime){
        return false;
    }

    /**
     * Set a bike's exit time.
     * @param barcodenr Unique identification for the bike.
     * @param exitTime The exit time specified in unix time.
     * @return Whether the edit was successful.
     */
    public boolean setBikeExitTime(String barcodenr, long exitTime){
        return false;
    }

    /**
     * Check if a given bike is parked in the garage.
     * @param barcodeNr Unique identification for the bike.
     * @return Whether the bike is parked.
     */
    public boolean isBikeParked(String barcodeNr){
        return false;
    }


}
