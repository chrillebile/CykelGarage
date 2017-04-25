package BikeGarage;

public class Bike {

    private Customer customer;
    private long barcodeNr;
    private long regTime, entryTime, exitTime;

    /**
     * Constructor for bikes.
     * @param barcodeNr The assigned unique barcode the bike should have.
     * @param customer The bike's owner.
     */
    public Bike(long barcodeNr, Customer customer){
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
    public Bike(long barcodeNr, Customer customer, long regTime, long entryTime, long exitTime){
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
     * Get a bikes barcodeNr in long form. Notice: There is no guarantee for it to be 5 digits.
     * @return The bike's barcodeNr in long form
     */
    public long getBarcodeNr(){
        return barcodeNr;
    }

    /**
     * Get a bike's barcode in string form. This will return a 5 digit barcodeNr. Use this when printing/showing barcode.
     * @return The bike's barcodeNr in string form
     */
    public String getBarcodeNrInString(){
        String returnedString =  String.format("%05d", barcodeNr);

        return returnedString;
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
