package BikeGarage;

public class Bike {

    private Customer customer;
    private String barcodeNr;
    private long regTime, entryTime, exitTime;

    /**
     * Constructor for bikes.
     * @param barcodeNr The assigned unique barcode the bike should have.
     * @param customer The bike's owner.
     */
    public Bike(String barcodeNr, Customer customer){

    }

    /**
     * Constructor for bikes. This is primarily used by the database to create bikes during startup.
     * @param customer The owner.
     * @param barcodeNr The unique barcode.
     * @param regTime Time when the bike was registered.
     * @param entryTime When the bike last entered the garage.
     * @param exitTime When the bike last exited the
     */
    public Bike(Customer customer, String barcodeNr, long regTime, long entryTime, long exitTime){

    }

    /**
     *
     * @return The bike's customer
     */
    public Customer getCustomer(){
        return null;
    }

    /**
     * @return The bike's barcodeNr
     */
    public String getBarcodeNr(){
        return null;
    }

    /**
     * @return The bike's registration time
     */
    public long getRegTime(){
        return 0;
    }

    /**
     * @return Time of entry
     */
    public long getEntryTime(){
        return 0;
    }

    /**
     * @return Time of exit
     */
    public long getExitTime(){
        return 0;
    }

    /**
     * Replace the bike's customer
     * @param customer
     */
    public void setCustomer(Customer customer){

    }

    /**
     * Set time of entry in the garage
     * @param entryTime
     */
    public void setEntryTime(long entryTime){

    }

    /**
     * Set time of exit in the garage
     * @param exitTime
     */
    public void setExitTime(long exitTime){

    }

    /**
     * Checks if object has same barcode as this bike
     * @param object
     * @return
     */
    public boolean equals(Object object){
        return false;
    }
}
