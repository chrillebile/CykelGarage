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
     * Returns a customer if he exists.
     * @param personNr The customers personal id-number.
     * @return The found customer. If no customer was found null is returned.
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
     * @return All customers this manager stores.
     */
    public ArrayList<Customer> getCustomerList(){
        return customerList;
    }

    /**
     * Create new customer if he doesn't already exists.
     * @param firstName The customers first name.
     * @param surname The customers surname.
     * @param personNr The customers personal id-number.
     * @param pin The customers pin-code.
     * @param phoneNr The customers phone number.
     * @return The created customer. If customer already exists, return null.
     * @throws IllegalArgumentException when the personal-id number si taken.
     * @throws IllegalArgumentException when the system has reached maximum number of users.
     */
    public Customer createCustomer(String firstName, String surname, String personNr, String pin, String phoneNr) throws IllegalArgumentException{
        // Check that the person does not exist. findCustomer returns a customer if it exists
        Customer temp1 = findCustomerByPersonNr(personNr);
        if(temp1 != null){
            throw new IllegalArgumentException("Personnummret redan finns");
        }
        else if(customerList.size() >= Config.MAX_USERS){
            throw  new IllegalArgumentException("Antalet användare överstiger systemets maximala antal användare");
        }
        else{
            Customer customer = new Customer(firstName, surname, personNr, pin, phoneNr, 0);
            customerList.add(customer);
            return customer;
        }
    }

    /**
     * Create new customer used by the DatabaseManager.
     * @param firstName The customers first name
     * @param surname The customers surname
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     * @param phoneNr The customers phone number
     * @param regTime The customers registration time
     * @return Created customer. If customer already exist return null.
     */
    public Customer createCustomer(String firstName, String surname, String personNr, String pin, String phoneNr, long regTime){
        Customer customer = new Customer(firstName, surname, personNr, pin, phoneNr, regTime);
        customerList.add(customer);
        return customer;
    }

    /**
     * Removes a customer if he exists
     * @param personNr The customers personal id-number
     * @return If the removed customer was successfully removed
     */
    public boolean removeCustomer(String personNr){
        Customer temp = findCustomerByPersonNr(personNr);
        return customerList.remove(temp);
    }
}
