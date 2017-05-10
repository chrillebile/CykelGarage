package BikeGarage;


import java.io.*;

/**
 * Reads and writes to fil.
 * @author Christian Bilevits
 */
public class DatabaseManager {

    private BikeManager bikeManager;
    private CustomerManager customerManager;
    private Config config;

    private FileWriter fileWriter;
    private BufferedReader fileReader;

    /**
     * Default constants
     */
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER_CUSTOMER = "firstName;surname;personNr;pin;phoneNr;regTime";
    private static final String FILE_HEADER_BIKE = "barcodeNr;personNr;regTime;entryTime;exitTime";
    private static final String FILE_HEADER_CONFIG = "MaxParkingSpots";

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

    /**
     * Create a database
     */
    public DatabaseManager(){
        bikeManager = new BikeManager();
        customerManager = new CustomerManager();
        config = new Config();
    }

    /**
     * Reads file of bikes.
     * @return BikeManager
     */
    public BikeManager loadBikes(){
        try {
            fileReader = new BufferedReader(new FileReader(Config.FILENAME_BIKE));
            String line;
            fileReader.readLine();
            while((line = fileReader.readLine()) != null){
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
     * Reads file of customers
     * @return CustomerManager
     */
    public CustomerManager loadCustomers(){
        try{
            fileReader = new BufferedReader(new FileReader(Config.FILENAME_CUSTOMER));
            String line;
            fileReader.readLine();
            while((line = fileReader.readLine()) != null){
                String[] split = line.split(COMMA_DELIMITER);
                customerManager.createCustomer(split[CUSTOMER_FNAME], split[CUSTOMER_LNAME], split[CUSTOMER_PERSONNR], split[CUSTOMER_PIN], split[CUSTOMER_PHONENR], Long.parseLong(split[CUSTOMER_REGTIME]));
            }
        } catch (FileNotFoundException e){
            try {
                File file = new File(Config.FILENAME_CUSTOMER);
                file.getParentFile().mkdir();
                updateCustomers();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            finallyForLoad();
        }
        return customerManager;
    }

    /**
     * Reads config file
     * @return config
     */
    public Config loadConfig(){
        try {
            fileReader = new BufferedReader(new FileReader(Config.FILENAME_CONFIG));
            fileReader.readLine();
            String line;
            //If line is not empty it will read and set max parking spots
            if((line = fileReader.readLine()) != null) {
                config.setMaxParkingSports(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e){
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
     * All loaders uses the same finally
     */
    private void finallyForLoad(){
        if(fileReader != null) {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Updates bike file
     */
    public void updateBikes(){
        try {
            fileWriter = new FileWriter(Config.FILENAME_BIKE);
            fileWriter.append(FILE_HEADER_BIKE.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);

            for(Bike b: bikeManager.getBikeList()){
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
                } catch(Error e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Update Customer file
     */
    public void updateCustomers(){
        try {
            fileWriter = new FileWriter(Config.FILENAME_CUSTOMER);
            fileWriter.append(FILE_HEADER_CUSTOMER.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);

            for(Customer c: customerManager.getCustomerList()){
                StringBuilder customer = new StringBuilder();
                try{
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
                } catch (Error e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Update Config file
     */
    public void updateConfig(){
        try {
            fileWriter = new FileWriter(Config.FILENAME_CONFIG);
            fileWriter.append(FILE_HEADER_CONFIG.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(String.valueOf(Config.MAX_PARKING_SPOTS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}