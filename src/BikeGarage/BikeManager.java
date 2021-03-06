package BikeGarage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Manages all the bikes
 *
 * @author Ennio Mara & Christian Bilevits
 */
public class BikeManager {
    private ArrayList<Bike> bikeList;
    private ArrayList<Bike> parkedBikeList;

    /**
     * The manager that manages bikes registered in the garage.
     */
    public BikeManager() {
        bikeList = new ArrayList<Bike>();
        parkedBikeList = new ArrayList<Bike>();
    }

    /**
     * Search after bikes that are owned by a specific user.
     *
     * @param personNr The user's personal identification number.
     * @return The found bikes. If not found, return empty list.
     */
    public ArrayList<Bike> findBikesByPersonNr(String personNr) {
        ArrayList<Bike> tempBikeList = new ArrayList<>();
        for (Bike bike : bikeList) {
            // Check so that the bike's personnr matches the given personr
            if (bike.getCustomer().getPersonNr().equals(personNr)) {
                tempBikeList.add(bike);
            }
        }
        return tempBikeList;
    }

    /**
     * Search after a bike given its barcode number.
     *
     * @param barcodeNr Unique identification for the bike.
     * @return The found bike. If not found, return null.
     */
    public Bike findBikeByBarcodeNr(long barcodeNr) {
        for (Bike bike : bikeList) {
            if (bike.getBarcodeNr() == barcodeNr) {
                return bike;
            }
        }
        return null;
    }

    /**
     * @return A list of all bikes stored in the system.
     */
    public ArrayList<Bike> getBikeList() {
        return bikeList;
    }

    /**
     * @return A list of all parked bikes in the system.
     */
    public ArrayList<Bike> getParkedBikeList() {
        return parkedBikeList;
    }

    /**
     * Add a bike to a given customer.
     *
     * @param customer The customer where the bike will be added.
     * @return The complete bike object, including the customer.
     * @throws IllegalArgumentException when the customer has reached maximum number of bikes per customer.
     */
    public Bike addBike(Customer customer) throws IllegalArgumentException {
        if (findBikesByPersonNr(customer.getPersonNr()).size() >= Config.MAX_BIKES_PER_PERSON) {
            throw new IllegalArgumentException("Du kan inte ha mer än 2 cyklar");
        }
        Bike bikeToBeAdded = new Bike(getNextFreeBarcode(), customer);
        if (bikeList.size() > 0 && bikeToBeAdded.getBarcodeNr() < bikeList.get(bikeList.size() - 1).getBarcodeNr()) {
            bikeList.add(bikeToBeAdded);
            sortBikeList();
            return bikeToBeAdded;
        }
        bikeList.add(bikeToBeAdded);
        changeBikeParkingStatus(bikeToBeAdded);
        return bikeToBeAdded;
    }

    /**
     * Add a bike used by DatabaseManager.
     *
     * @param barcodeNr Barcode number.
     * @param customer  The bike's owner.
     * @param regTime   Registration time.
     * @param entryTime Entry time.
     * @param exitTime  Exit time.
     * @return The created bike object.
     */
    public Bike addBike(long barcodeNr, Customer customer, long regTime, long entryTime, long exitTime) {
        Bike bikeToBeAdded = new Bike(barcodeNr, customer, regTime, entryTime, exitTime);
        bikeList.add(bikeToBeAdded);
        changeBikeParkingStatus(bikeToBeAdded);
        return bikeToBeAdded;
    }

    /**
     * Sorting the bikeList by barcodeNr, used by addBike so we can reuse barcode numbers.
     */
    private void sortBikeList() {
        Collections.sort(bikeList, new Comparator<Bike>() {
            @Override
            public int compare(Bike b1, Bike b2) {
                return b1.getBarcodeNrInString().compareTo(b2.getBarcodeNrInString());
            }
        });
    }

    /**
     * Iterate through the bikelist and get the next free barcode. Ideally to be used when adding a bike.
     *
     * @return The next barcode. This barcode is not used by anyone and is one (1) larger than the current maximum barcode.
     */
    private long getNextFreeBarcode() {
        long localMaximumBarcode = -1;
        for (Bike bike : bikeList) {
            if (bike.getBarcodeNr() > localMaximumBarcode) {
                if (bikeList.get(bikeList.size() - 1).getBarcodeNr() == (Config.MAX_NUMBER_OF_BARCODES - 1) && (bike.getBarcodeNr() - (localMaximumBarcode + 1)) >= 1) {
                    return (localMaximumBarcode + 1);
                }
                localMaximumBarcode = bike.getBarcodeNr();
            }
        }
        // localMaximumBarcode is already taken. Since it is maximum, localMaximumBarcode + 1 is not taken.
        return localMaximumBarcode + 1;
    }

    /**
     * Remove a bike with a specific barcode.
     *
     * @param barcodeNr Unique identification for the bike.
     * @return Whether the removal was successful.
     * @throws IllegalArgumentException when the bike is parked (a parked bike cannot be removed).
     */
    public boolean removeBike(long barcodeNr) throws IllegalArgumentException {
        for (Bike bike : bikeList) {
            // Check that the barcodes match
            if (bike.getBarcodeNr() == barcodeNr) {
                if (bike.getParkingStatus()) {
                    throw new IllegalArgumentException("Cykeln är parkerad och kan inte tas bort");
                }
                bikeList.remove(bike);
                // Removal was successful
                return true;
            }
        }

        return false;
    }

    /**
     * Edit a bike's customer.
     *
     * @param barcodeNr   The bike's barcode number.
     * @param newCustomer The new owner the bike must have.
     * @return Whether the edit was successful.
     * @throws IllegalArgumentException when the number of bikes exceeds the maximum number of bikes per customer.
     */
    public boolean editBikeCustomer(long barcodeNr, Customer newCustomer) throws IllegalArgumentException {
        if (findBikesByPersonNr(newCustomer.getPersonNr()).size() >= Config.MAX_BIKES_PER_PERSON) {
            throw new IllegalArgumentException("Du kan inte ha mer än 2 cyklar");
        }
        for (Bike bike : bikeList) {
            if (bike.getBarcodeNr() == barcodeNr) {
                bike.setCustomer(newCustomer);
                return true;
            }
        }
        return false;
    }

    /**
     * Set a bike's entry time and adds bike to parkedBikeList if it's parked.
     *
     * @param barcodeNr A bikes barcodeNr.
     * @param entryTime The time specified in unix time.
     */
    public void setBikeEntryTime(long barcodeNr, long entryTime) {
        for (Bike b : bikeList) {
            if (b.getBarcodeNr() == barcodeNr) {
                b.setEntryTime(entryTime);
                changeBikeParkingStatus(b);
            }
        }
    }

    /**
     * Set a bike's exit time and removes bike from parkedBikeList if it's not parked.
     *
     * @param barcodeNr A bikes barcodeNr.
     * @param exitTime  The exit time specified in unix time.
     */
    public void setBikeExitTime(long barcodeNr, long exitTime) {
        for (Bike b : bikeList) {
            if (b.getBarcodeNr() == barcodeNr) {
                b.setExitTime(exitTime);
                changeBikeParkingStatus(b);
            }
        }
    }

    /**
     * Change a bikes parking status. This, depending on the parking status of the bike, either adds or removes it from the parked bikes arraylist.
     *
     * @param bike The bike we want to change parking status on.
     */
    private void changeBikeParkingStatus(Bike bike) {
        if (bike.getParkingStatus() && !parkedBikeList.contains(bike)) {
            parkedBikeList.add(bike);
        } else {
            parkedBikeList.remove(bike);
        }
    }

    /**
     * Check if a customer has any bike parked in the garage.
     *
     * @param customer The customer to check.
     * @return Whether a customer has a parked bike.
     */
    public boolean hasBikeParked(Customer customer) {
        for (Bike bike : parkedBikeList) {
            if (bike.getCustomer() == customer) {
                return true;
            }
        }
        return false;
    }
}
