package BikeGarage;

/**
 * The bike and its properties.
 *
 * @author Christian Bilevits
 */
public class Bike {

    private Customer customer;
    private long barcodeNr;
    private long regTime, entryTime, exitTime;

    /**
     * Constructor for bikes.
     *
     * @param barcodeNr The assigned unique barcode the bike should have.
     * @param customer  The bike's owner.
     */
    public Bike(long barcodeNr, Customer customer) {
        setBarcodeNr(barcodeNr);
        this.customer = customer;
        this.regTime = System.currentTimeMillis();
    }

    /**
     * Constructor for bikes. This is primarily used by the database to create bikes during startup.
     *
     * @param barcodeNr The unique barcode.
     * @param customer  The owner.
     * @param regTime   Time when the bike was registered.
     * @param entryTime When the bike last entered the garage.
     * @param exitTime  When the bike last exited the
     */
    public Bike(long barcodeNr, Customer customer, long regTime, long entryTime, long exitTime) {
        setBarcodeNr(barcodeNr);
        this.customer = customer;
        this.regTime = regTime;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    /**
     * Get the customer for this bike.
     *
     * @return The bike's customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Replace the bike's customer
     *
     * @param customer The customer that the current owner should be replaced with.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Get a bikes barcodeNr in long form. Notice: There is no guarantee for it to be 5 digits.
     *
     * @return The bike's barcodeNr in long form
     */
    public long getBarcodeNr() {
        return barcodeNr;
    }

    /**
     * To check that the barcode doesn't have more than 5 digits and setting the barcode
     *
     * @param barcodeNr The barcode
     * @throws IllegalArgumentException when the barcode exceeds the maximum length a barcode can have.
     */
    private void setBarcodeNr(long barcodeNr) throws IllegalArgumentException {
        if (barcodeNr >= Config.MAX_NUMBER_OF_BARCODES) {
            throw new IllegalArgumentException("Du kan inte få fler barcodes");
        }
        this.barcodeNr = barcodeNr;
    }

    /**
     * Get a bike's barcode in string form. This will return a 5 digit barcodeNr. Use this when printing/showing barcode.
     *
     * @return The bike's barcodeNr in string form
     */
    public String getBarcodeNrInString() {
        String returnedString = String.format("%05d", barcodeNr);
        return returnedString;
    }

    /**
     * @return The bike's registration time
     */
    public long getRegTime() {
        return regTime;
    }

    /**
     * @return Time of entry (UNIX time)
     */
    public long getEntryTime() {
        return entryTime;
    }

    /**
     * Set time of entry in the garage
     *
     * @param entryTime The time to set it to given in UNIX time.
     */
    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * @return Time of exit (UNIX time)
     */
    public long getExitTime() {
        return exitTime;
    }

    /**
     * Set time of exit in the garage
     *
     * @param exitTime
     */
    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * Checks if object has same barcode as this bike
     *
     * @param object
     * @return
     */
    public boolean equals(Object object) {
        if (object instanceof Bike) {
            return barcodeNr == ((Bike) object).barcodeNr;
        } else {
            return false;
        }
    }

    /**
     * Returns whether a bike is parked in the garage. If exitTime is smaller than entryTime, then the bike is in the garage
     *
     * @return True if bike is parked in the garage.
     */
    public boolean getParkingStatus() {
        if (exitTime < entryTime) {
            return true;
        }
        return false;
    }
}
