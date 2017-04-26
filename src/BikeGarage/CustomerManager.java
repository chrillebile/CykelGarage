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
        for(Customer c: customerList){
            if(c.getPersonNr() == personNr){
                return c;
            }
        }
        return null;
    }

    /**
     * Check if a pin exist
     * @param pin
     * @return
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
     * @param firstName
     * @param surname
     * @param personNr
     * @param pin
     * @param regTime
     * @return
     */
    public boolean createCustomer(String firstName, String surname, String personNr, String pin, long regTime){
        Customer temp1 = findCustomerByPersonNr(personNr);
        if(temp1 == null){
            Customer customer;
            if(regTime == 0){
                customer = new Customer(firstName, surname, personNr, pin);
            }
            else {
                customer = new Customer(firstName, surname, personNr, pin);
            }
            customerList.add(customer);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Removes a customer if he exist
     * @param personNr
     * @return
     */
    public boolean removeCustomer(String personNr){
        Customer temp = findCustomerByPersonNr(personNr);
        return customerList.remove(temp);
    }

    /**
     * Edit customers name if he exist
     * @param personNr
     * @param firsName
     * @param surname
     * @return
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
     * @param personNr
     * @param pin
     * @return
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
