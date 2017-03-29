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
    public ArrayList<String> printCustomerNameList(){
        ArrayList<String> temp = new ArrayList<>();
        for (Customer c: customerList) {
            temp.add(c.toString());
        }
        return temp;
    }

    /**
     * Lägger till en ny customer i listan
     * @param customer
     */
    public void addCustomer(Customer customer){ customerList.add(customer); }

}
