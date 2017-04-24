package BikeGarage;

public class Config {
    public static int NUMBER_OF_CHARACTER_OF_PIN = 5;
    public static int TIME_TIL_TERMINAL_DROP = 30;
    public static int TIME_TIL_PIN_DROP = 10;
    public static int MAX_USERS = 50000;
    public static int MAX_PARKING_SPOTS;

    /**
     * Add config file
     * @param MAX_PARKING_SPOTS Maximum of sports in the parking garage
     */
    public Config(int MAX_PARKING_SPOTS){
        this.MAX_PARKING_SPOTS = MAX_PARKING_SPOTS;
    }
}
