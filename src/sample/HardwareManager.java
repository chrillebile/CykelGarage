package sample;

import hardware_interfaces.*;
import hardware_testdrivers.*;
/**
 * Created by chrillebile on 2017-04-05.
 */
public class HardwareManager {
    BarcodeScanner entryScanner;


    public HardwareManager() {
        entryScanner = new BarcodeScannerTestDriver("BarcodeScanner", 0, 0);
        entryScanner.registerObserver(s -> barcodeScanned());
    }



    private void barcodeScanned() {
        // Kod för vad som ska hända när en streckkod skannas
    }
}