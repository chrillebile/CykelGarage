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
        this.barcodeNr = barcodeNr;
        this.customer = customer;
        this.regTime = System.currentTimeMillis();
    }

    /**
     * Constructor for bikes. This is primarily used by the database to create bikes during startup.
     * @param barcodeNr The unique barcode.
     * @param customer The owner.
     * @param regTime Time when the bike was registered.
     * @param entryTime When the bike last entered the garage.
     * @param exitTime When the bike last exited the
     */
    public Bike(String barcodeNr, Customer customer, long regTime, long entryTime, long exitTime){
        this.barcodeNr = barcodeNr;
        this.customer = customer;
        this.regTime = regTime;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    /**
     *
     * @return The bike's customer
     */
    public Customer getCustomer(){
        return customer;
    }

    /**
     * @return The bike's barcodeNr
     */
    public String getBarcodeNr(){
        return barcodeNr;
    }

    /**
     * @return The bike's registration time
     */
    public long getRegTime(){
        return regTime;
    }

    /**
     * @return Time of entry
     */
    public long getEntryTime(){
        return entryTime;
    }

    /**
     * @return Time of exit
     */
    public long getExitTime(){
        return exitTime;
    }

    /**
     * Replace the bike's customer
     * @param customer
     */
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    /**
     * Set time of entry in the garage
     * @param entryTime
     */
    public void setEntryTime(long entryTime){
        this.entryTime = entryTime;
    }

    /**
     * Set time of exit in the garage
     * @param exitTime
     */
    public void setExitTime(long exitTime){
        this.exitTime = exitTime;
    }

    /**
     * Checks if object has same barcode as this bike
     * @param object
     * @return
     */
    public boolean equals(Object object){
        if(object instanceof Bike){
            return barcodeNr == ((Bike) object).barcodeNr;
        } else {
            return false;
        }
    }
}
