package BikeGarage;

/**
 * All setting for the program
 * @author Christian Bilevits
 */
public class Config {

    /**
     * Default constants
     */
    public static final int NUMBER_OF_CHARACTER_OF_PIN = 5;
    public static final int TIME_TIL_TERMINAL_DROP = 30;
    public static final int TIME_TIL_PIN_DROP = 10;
    public static final int MAX_USERS = 50000;
    public static int MAX_PARKING_SPOTS;
    public static final String FILENAME_BIKE = "databaseFiles/bikeTable.csv";
    public static final String FILENAME_CUSTOMER = "databaseFiles/customerTable.csv";
    public static final String FILENAME_CONFIG = "databaseFiles/config.csv";

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
}
