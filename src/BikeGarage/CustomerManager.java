package BikeGarage;

import java.util.ArrayList;

public class CustomerManager {

    private ArrayList<Customer> customerList;

    /**
     * New Customer manager
     */
    public CustomerManager(){
        customerList = new ArrayList<Customer>();
    }

    /**
     * Returns a customer if he exist
     * @param personNr
     * @return
     */
    public Customer findCustomerByPersonNr(String personNr){
        return null;
    }

    /**
     * Check if a pin exist
     * @param pin
     * @return
     */
    public boolean checkIfPinExist(String pin){
        return false;
    }

    /**
     * Returns alla customers as an ArrayList
     * @return
     */
    public ArrayList<Customer> getCustomerList(){
        return null;
    }

    /**
     * Create new customer if he don't already exist.
     * @param firstName
     * @param surname
     * @param personNr
     * @param pin
     * @return
     */
    public boolean createCustomer(String firstName, String surname, String personNr, String pin){
        return false;
    }

    /**
     * Removes a customer if he exist
     * @param personNr
     * @return
     */
    public boolean removeCustomer(String personNr){
        return false;
    }

    /**
     * Edit customers name if he exist
     * @param personNr
     * @param firsName
     * @param surname
     * @return
     */
    public boolean editCustomerName(String personNr, String firsName, String surname){
        return false;
    }

    /**
     * Edit customers pin if he exist
     * @param personNr
     * @param pin
     * @return
     */
    public boolean editCustomerPin(String personNr, String pin){
        return false;
    }
}
