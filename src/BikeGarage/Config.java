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
     * Add config file
     */
    public Config(){
    }

    /**
     * Set maximum of parking spots in the garage.
     * @param maxParkingSports
     */
    public void setMaxParkingSports(int maxParkingSports){
        MAX_PARKING_SPOTS = maxParkingSports;
    }

    public void setSystemStartedBefore(boolean value){
        SYSTEM_STARTED_BEFORE = value;
    }
}
