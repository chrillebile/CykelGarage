package BikeGarage;

/**
 * All setting for the program
 * @author Christian Bilevits
 */
public class Config {

    /**
     * Default constants
     */
    public static final int TIME_TIL_TERMINAL_DROP = 30;
    public static final int TIME_TIL_PIN_DROP = 10;
    /**
     * The time the customer has to enter the PIN correctly.
     */
    public static final int TIME_INCORRECT_PIN = 60;
    /**
     * Number of times a PIN is entered incorrectly before it it set on cooldown.
     */
    public static final int NUMBER_OF_INCORRECT_PIN_BEFORE_DROP = 3;
    public static final int NUMBER_OF_CHARACTER_OF_PIN = 6;
    public static final int MAX_NUMBER_OF_BARCODES = 100000;
    public static boolean SYSTEM_STARTED_BEFORE;
    public static final int MAX_USERS = 50000;
    public static final int MAX_BIKES_PER_PERSON = 2;
    public static int MAX_PARKING_SPOTS;
    public static final String FILENAME_BIKE = "databaseFiles/bikeTable.csv";
    public static final String FILENAME_CUSTOMER = "databaseFiles/customerTable.csv";
    public static final String FILENAME_CONFIG = "databaseFiles/config.csv";
    public static final String WINDOWFILE_LOCATION = "views/";
    public static final String DATE_FORMAT = "H:mm:ss d/MM-yy";

    /**
     * Time in seconds that a door is locked after being unlocked.
     */
    public static final int TIME_TIL_DOOR_LOCK = 5;

    /**
     * Time in seconds that an LED in a PincodeTerminal should stay on.
     */
    public static final int TIME_PINCODE_LED_ON = 5;

    /**
     * Add config file
     */
    public Config(){
    }

    /**
     * Set maximum of parking spots in the garage.
     * @param maxParkingSports
     */
    public static void setMaxParkingSports(int maxParkingSports){
        if(maxParkingSports > MAX_NUMBER_OF_BARCODES || maxParkingSports < 0){
            throw new NumberFormatException("Du kan bara välja mellan 0 och " + MAX_NUMBER_OF_BARCODES + " parkeringsplatser");
        }
        MAX_PARKING_SPOTS = maxParkingSports;
    }

    public static void setSystemStartedBefore(boolean value){
        SYSTEM_STARTED_BEFORE = value;
    }
}
