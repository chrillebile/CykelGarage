package BikeGarage;

import java.util.ArrayList;

/**
 * Manages all the customers
 * @author Christian Bilevits
 */
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
     * @param personNr The customers personal id-number.
     * @return The found customer, if no customer was found null returns.
     */
    public Customer findCustomerByPersonNr(String personNr){
        for(Customer c: customerList){
            if(c.getPersonNr().equals(personNr)){
                return c;
            }
        }
        return null;
    }

    /**
     * Check if a pin exist
     * @param pin A pin-code.
     * @return Returns if pin-code exist.
     */
    public boolean checkIfPinExist(String pin){
        for(Customer c: customerList){
            if(c.getPin() == pin){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns alla customers as an ArrayList
     * @return
     */
    public ArrayList<Customer> getCustomerList(){
        return customerList;
    }

    /**
     * Create new customer if he don't already exist.
     * @param firstName The customers first name
     * @param surname The customers surname
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     * @param phoneNr The customers phone number
     * @param regTime The customers registration time
     * @return Created customer. If customer already exist return null.
     */
    public Customer createCustomer(String firstName, String surname, String personNr, String pin, String phoneNr, long regTime){
        // Check that the personn does not exist. findCustomer returns a customer if it exists
        Customer temp1 = findCustomerByPersonNr(personNr);
        if(temp1 != null){
            throw new IllegalArgumentException("Personnummret redan finns");
        }
        else if(customerList.size() >= Config.MAX_USERS){
            throw  new IllegalArgumentException("Antalet användare överstiger systemets maximala antal användare");
        }
        else{
            Customer customer = new Customer(firstName, surname, personNr, pin, phoneNr, regTime);
            customerList.add(customer);
            return customer;
        }


    }

    /**
     * Removes a customer if he exist
     * @param personNr The customers personal id-number
     * @return If the removed customer was successfully removed
     */
    public boolean removeCustomer(String personNr){
        Customer temp = findCustomerByPersonNr(personNr);
        return customerList.remove(temp);
    }

    /**
     * Edit customers name if he exist
     * @param personNr The customers personal id-number
     * @param firsName The customers first name
     * @param surname The customers surname
     * @return If the edit was successful
     */
    public boolean editCustomerName(String personNr, String firsName, String surname){
        Customer temp = findCustomerByPersonNr(personNr);
        if(temp != null){
            temp.setFirstName(firsName);
            temp.setSurname(surname);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Edit customers pin if he exist
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     * @return If the edit was successful
     */
    public boolean editCustomerPin(String personNr, String pin){
        Customer temp = findCustomerByPersonNr(personNr);
        if(temp != null){
            temp.setPin(pin);
            return true;
        }else {
            return false;
        }
    }
}
