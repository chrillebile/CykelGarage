package sample;

import java.util.ArrayList;

/**
 * Created by chrillebile on 2017-03-29.
 */
public class CustomerManager {
    private ArrayList<Customer> customerList = new ArrayList<>();

    /**
     * Retunerar en lista med customers
     * @return
     */
    public String[] printCustomerNameList(){
        String[] temp = new String[customerList.size()];
        for (int i = 0; i < customerList.size(); i++) {
            temp[i] = customerList.get(i).toString();
        }
        return temp;
    }

    /**
     * Lägger till en ny customer i listan
     * @param customer
     */
    public void addCustomer(Customer customer){ customerList.add(customer); }

}
