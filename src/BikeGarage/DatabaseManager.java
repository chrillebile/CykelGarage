package BikeGarage;


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Reads and writes system information to file
 *
 * @author Christian Bilevits
 */
public class DatabaseManager {

    /**
     * Default constants
     */
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER_CUSTOMER = "firstName;surname;personNr;pin;phoneNr;regTime";
    private static final String FILE_HEADER_BIKE = "barcodeNr;personNr;regTime;entryTime;exitTime";
    private static final String FILE_HEADER_CONFIG = "isFirstTimeSystemStart;MaxParkingSpots";
    /**
     * Customer index
     */
    private static final int CUSTOMER_FNAME = 0;
    private static final int CUSTOMER_LNAME = 1;
    private static final int CUSTOMER_PERSONNR = 2;
    private static final int CUSTOMER_PIN = 3;
    private static final int CUSTOMER_PHONENR = 4;
    private static final int CUSTOMER_REGTIME = 5;
    /**
     * Bike index
     */
    private static final int BIKE_BARCODENR = 0;
    private static final int BIKE_PERSONNR = 1;
    private static final int BIKE_REGTIME = 2;
    private static final int BIKE_ENTRYTIME = 3;
    private static final int BIKE_EXITTIME = 4;
    private BikeManager bikeManager;
    private CustomerManager customerManager;
    private Config config;
    private BufferedWriter fileWriter;
    private BufferedReader fileReader;

    /**
     * Create a database
     */
    public DatabaseManager() {
        bikeManager = new BikeManager();
        customerManager = new CustomerManager();
        config = new Config();
    }

    /**
     * Reads and loads the bikes from file to BikeManager
     *
     * @return The loaded BikeManager instance.
     */
    public BikeManager loadBikes() {
        try {
            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(Config.FILENAME_BIKE), Config.FILE_ENCODING_TYPE));
            String line;
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(COMMA_DELIMITER);
                bikeManager.addBike(Long.parseLong(split[BIKE_BARCODENR]), customerManager.findCustomerByPersonNr(split[BIKE_PERSONNR]), Long.parseLong(split[BIKE_REGTIME]), Long.parseLong(split[BIKE_ENTRYTIME]), Long.parseLong(split[BIKE_EXITTIME]));
            }
        } catch (FileNotFoundException e) {
            try {
                File file = new File(Config.FILENAME_BIKE);
                file.getParentFile().mkdir();
                updateBikes();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyForLoad();
        }
        return bikeManager;
    }

    /**
     * Read and load the customer into CustomerManager
     *
     * @return The loaded CustomerManager instance
     */
    public CustomerManager loadCustomers() {
        try {
            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(Config.FILENAME_CUSTOMER), Config.FILE_ENCODING_TYPE));
            String line;
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] split = line.split(COMMA_DELIMITER);
                customerManager.createCustomer(split[CUSTOMER_FNAME], split[CUSTOMER_LNAME], split[CUSTOMER_PERSONNR], split[CUSTOMER_PIN], split[CUSTOMER_PHONENR], Long.parseLong(split[CUSTOMER_REGTIME]));
            }
        } catch (FileNotFoundException e) {
            try {
                File file = new File(Config.FILENAME_CUSTOMER);
                file.getParentFile().mkdir();
                updateCustomers();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyForLoad();
        }
        return customerManager;
    }

    /**
     * Read and load the config into Config
     *
     * @return The Config instance
     */
    public Config loadConfig() {
        try {
            fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(Config.FILENAME_CONFIG), Config.FILE_ENCODING_TYPE));
            fileReader.readLine();
            String line;
            //If line is not empty it will read and set max parking spots
            if ((line = fileReader.readLine()) != null) {
                String[] split = line.split(COMMA_DELIMITER);

                // Find whether to set it to true or false
                switch (split[0]) {
                    case "0":
                        config.setSystemStartedBefore(false);
                        break;
                    case "1":
                        config.setSystemStartedBefore(true);
                        break;
                }

                config.setMaxParkingSports(Integer.parseInt(split[1]));
            }
        } catch (FileNotFoundException e) {
            try {
                File file = new File(Config.FILENAME_CONFIG);
                file.getParentFile().mkdir();
                updateConfig();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyForLoad();
        }
        return config;
    }

    /**
     * Used when the loading of a file has been finished.
     */
    private void finallyForLoad() {
        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the stored bike file with information from BikeManager.
     */
    public void updateBikes() {
        try {
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.FILENAME_BIKE), Config.FILE_ENCODING_TYPE));
            fileWriter.append(FILE_HEADER_BIKE);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Bike b : bikeManager.getBikeList()) {
                StringBuilder bike = new StringBuilder();
                try {
                    bike.append(b.getBarcodeNrInString());
                    bike.append(COMMA_DELIMITER);
                    bike.append(b.getCustomer().getPersonNr());
                    bike.append(COMMA_DELIMITER);
                    bike.append(String.valueOf(b.getRegTime()));
                    bike.append(COMMA_DELIMITER);
                    bike.append(String.valueOf(b.getEntryTime()));
                    bike.append(COMMA_DELIMITER);
                    bike.append(String.valueOf(b.getExitTime()));
                    fileWriter.append(bike.toString());
                    fileWriter.append(NEW_LINE_SEPARATOR);
                } catch (Error e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the stored customers file with information from CustomerManager.
     */
    public void updateCustomers() {
        try {
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.FILENAME_CUSTOMER), Config.FILE_ENCODING_TYPE));
            fileWriter.append(FILE_HEADER_CUSTOMER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Customer c : customerManager.getCustomerList()) {
                StringBuilder customer = new StringBuilder();
                try {
                    customer.append(c.getFirstName());
                    customer.append(COMMA_DELIMITER);
                    customer.append(c.getSurname());
                    customer.append(COMMA_DELIMITER);
                    customer.append(c.getPersonNr());
                    customer.append(COMMA_DELIMITER);
                    customer.append(c.getPin());
                    customer.append(COMMA_DELIMITER);
                    customer.append(c.getPhoneNr());
                    customer.append(COMMA_DELIMITER);
                    customer.append(String.valueOf(c.getRegTime()));
                    fileWriter.append(customer.toString());
                    fileWriter.append(NEW_LINE_SEPARATOR);
                } catch (Error e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the stored config file with information from Config.
     */
    public void updateConfig() {
        try {
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.FILENAME_CONFIG), Config.FILE_ENCODING_TYPE));
            fileWriter.append(FILE_HEADER_CONFIG);
            fileWriter.append(NEW_LINE_SEPARATOR);

            // Since SYSTEM_STARTED_BEFORE is a boolean, 1 equals true and 0 equals false
            if (Config.SYSTEM_STARTED_BEFORE) {
                fileWriter.append(String.valueOf(1));
                fileWriter.append(COMMA_DELIMITER);
            } else {
                fileWriter.append(String.valueOf(0));
                fileWriter.append(COMMA_DELIMITER);
            }

            fileWriter.append(String.valueOf(Config.MAX_PARKING_SPOTS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}