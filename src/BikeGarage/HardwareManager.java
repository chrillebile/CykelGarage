package BikeGarage;

import hardware_interfaces.*;
import hardware_testdrivers.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages and connects all the hardware.
 * @author Ennio Mara
 */
public class HardwareManager {
    private AdminManager adminManager;

    private ElectronicLock entryLock;
    private ElectronicLock exitLock;

    private BarcodeScanner entryScanner;
    private BarcodeScanner exitScanner;

    private PincodeTerminal entryPincodeTerminal;
    private StringBuilder entryPincodeTerminalInput;
    private Timeline clearPincodeInputTimer;

    private BarcodePrinter barcodePrinter;




    /**
     * Create the class that handles connection to the hardware. This class will automatically start the components
     * @param adminManager Manager that manages the entire system.
     */
    public HardwareManager(AdminManager adminManager) {
        this.adminManager = adminManager;

        initElectronicLocks();
        initPincodeTerminal();
        initBarcodeScanners();
        initBarcodePrinter();
    }

    /**
     * Initialize the entry and exit barcode scanners. Also sets their observers.
     */
    private void initBarcodeScanners(){
        // Create the entry and exit scanners
        entryScanner = new BarcodeScannerTestDriver("Entry Barcode Scanner", 0, 0);
        exitScanner = new BarcodeScannerTestDriver("Exit Barcode Scanner", 0, 0);

        entryScanner.registerObserver(s -> handleScannedBarcode(s, entryLock));
        exitScanner.registerObserver(s -> handleScannedBarcode(s, exitLock));
    }

    /**
     * Initialize the barcodePrinter.
     */
    private void initBarcodePrinter() {
        barcodePrinter = new BarcodePrinterTestDriver("BarcodePrinter", 0, 0);
    }

    /**
     * Initialize the entry and exit electronic locks.
     */
    private void initElectronicLocks(){
        entryLock = new ElectronicLockTestDriver("Entry Lock", 0, 0);
        exitLock = new ElectronicLockTestDriver("Exit lock", 0, 0);
    }

    /**
     * Initialize the system's pincode-terminal. Also registers its observer.
     */
    private void initPincodeTerminal(){
        entryPincodeTerminal = new PincodeTerminalTestDriver("PincodeTerminal", 0, 0);

        // Create the stringbuilder that will store the input
        entryPincodeTerminalInput = new StringBuilder();

        // This handles the character input
        entryPincodeTerminal.registerObserver(s -> handlePincodeTerminalInput(s));

        // Create the clearPincodeInputTimer that will clear the pincode input
        clearPincodeInputTimer = new Timeline(new KeyFrame(Duration.seconds(Config.TIME_TIL_PIN_DROP), e-> clearPincodeTerminalInput()));
        clearPincodeInputTimer.setCycleCount(Animation.INDEFINITE);
    }


    /**
     * Handle the characters that are inputted through the terminal.
     *
     * The characters are then set up in a string and sent for verification when the pincode has reached required length.
     * @param character The character inputted. Only one character can be sent at a time.
     */
    private void handlePincodeTerminalInput(char character) {
        // Reset the clearPincodeInputTimer that clears the inputted pincodes
        clearPincodeInputTimer.stop();
        clearPincodeInputTimer.play();

        // The character that is sent to this method is appended to the global variable that stores current char input.
        // This method does a check to see the number of characters in that string. If the characters are more than the max characters allowed, the variable is emptied.
        entryPincodeTerminalInput.append(character);

        // The PIN has reached it's required length and is sent on for verification
        if(entryPincodeTerminalInput.length() >= Config.NUMBER_OF_CHARACTER_OF_PIN){
            // TODO - Verify how many times the pin has entered.
            // If the PIN exists then the door should be opened
            if(adminManager.checkIfPinExist(entryPincodeTerminalInput.toString())){
                // Open the entry door since the terminal is there
                entryLock.open(Config.TIME_TIL_DOOR_LOCK);
                entryPincodeTerminal.lightLED(PincodeTerminal.GREEN_LED, Config.TIME_PINCODE_LED_ON);
            }
            else{
                entryPincodeTerminal.lightLED(PincodeTerminal.RED_LED, Config.TIME_PINCODE_LED_ON);
            }

            // Empty the string
            entryPincodeTerminalInput.setLength(0);


            // Stop the clearPincodeInputTimer. It will be restarted when the next characer is added ( the stringbuilder starts getting filled)
            clearPincodeInputTimer.stop();
        }
    }

    /**
     * Clear the saved input from pincode and stop the clearPincodeInputTimer that clears it
     */
    private void clearPincodeTerminalInput(){
        entryPincodeTerminalInput.setLength(0);
        clearPincodeInputTimer.stop();
    }

    /**
     * Handle the input barcode through the barcode scanner.
     * @param barcode The input barcode
     * @param lock The ElectronicLock that is connected to this barcodescanner
     */
    private void handleScannedBarcode(String barcode, ElectronicLock lock) {
        long barcodeInLong;
        try{
            barcodeInLong = Long.parseLong(barcode);
        }
        catch (NumberFormatException e){
            e.printStackTrace();

            // Break the method here. Barcode could not be parsed
            return;
        }


        Bike searchedBike = adminManager.findBike(barcodeInLong);
        // Check so that there is a bike with that barcode
        if(searchedBike!= null){
            if(lock == entryLock){
                // Bike cannot enter when there are no parking spots left
                if(adminManager.numberOfFreeParkingSpots() <= 0){
                    return;
                }

                // Bike cannot enter when it's already parked.
                if(searchedBike.getParkingStatus()){
                    return;
                }
                adminManager.setBikeEntryTime(barcodeInLong, System.currentTimeMillis());
            }
            else if(lock == exitLock){
                // If bikeis not parked then it cannot get out
                if(!searchedBike.getParkingStatus()){
                    return;
                }
                adminManager.setBikeExitTime(barcodeInLong, System.currentTimeMillis());
            }

            adminManager.updateBikes();
            lock.open(Config.TIME_TIL_DOOR_LOCK);
        }
    }

    /**
     * Print a given barcode.
     * @param barcode The barcode that will be printed. It's length should be 5 characters
     * @throws IllegalArgumentException if the barcode is not 5 characters.
     */
    public void printBarcode(String barcode){
        if(barcode.length() != 5){
            throw new IllegalArgumentException("The inputted barcode was not 5 characters");
        }
        barcodePrinter.printBarcode(barcode);
    }
}