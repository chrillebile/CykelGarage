package BikeGarage;

import hardware_interfaces.*;
import hardware_testdrivers.*;

public class HardwareManager {
    private BikeManager bikeManager;
    private CustomerManager customerManager;

    private BarcodeScanner barcodeScanner;
    private BarcodePrinter barcodePrinter;
    private ElectronicLock electronicLock;
    private PincodeTerminal pincodeTerminal;


    /**
     * Create the class that handles connection to the hardware
     * @param customerManager Manager that manages customers. The hardware uses this to edit customers.
     * @param bikeManager Manager that manages bikes. The hardware uses this to edit bikes.
     */
    public HardwareManager(CustomerManager customerManager, BikeManager bikeManager) {
        this.customerManager = customerManager;
        this.bikeManager = bikeManager;
        initElectronicLock();
        initPincodeTerminal();
    }

    /**
     * Initialize the barcodeScanner. Create the scanner window and register its observer.
     */
    public void initBarcodeScanner(){
        barcodeScanner = new BarcodeScannerTestDriver("BarcodeScanner", 0, 0);
        barcodeScanner.registerObserver(s -> handleScannedBarcode(s));
    }

    /**
     * Initialize the barcodePrinter (create the window).
     */
    public void initBarcodePrinter() {
        barcodePrinter = new BarcodePrinterTestDriver("BarcodePrinter", 0, 0);
    }

    /**
     * Initialize the ElectronicLock (create the window).
     */
    public void initElectronicLock(){
        electronicLock = new ElectronicLockTestDriver("ElectronicLock", 0, 0);
    }

    /**
     * Initialize the pincodeTerminal. Create the pincode window and register its observer.
     */
    public void initPincodeTerminal(){
        pincodeTerminal = new PincodeTerminalTestDriver("PincodeTerminal", 0, 0);
        pincodeTerminal.registerObserver(s -> handlePincodeTerminalInput(s));
    }


    /**
     * Handle the characters that are input through the terminal.
     * @param character The character input.
     */
    private void handlePincodeTerminalInput(char character) {

    }

    /**
     * Handle the input barcode through the hardware.
     * @param barcode The input barcode
     */
    private void handleScannedBarcode(String barcode) {
    }
}