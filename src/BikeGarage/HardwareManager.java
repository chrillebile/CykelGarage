package BikeGarage;

import hardware_interfaces.*;
import hardware_testdrivers.*;

public class HardwareManager {
    BarcodeScanner entryScanner;


    public HardwareManager() {
        entryScanner = new BarcodeScannerTestDriver("BarcodeScanner", 0, 0);
        entryScanner.registerObserver(s -> barcodeScanned());
    }



    private void barcodeScanned() {
    }
}