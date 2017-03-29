package sample;

import java.util.ArrayList;

/**
 * Created by chrillebile on 2017-03-28.
 */
public class Customer {
    private String name, idNr;
    private int pin;
    private static int newBarCode = 0;
    private ArrayList<Integer> barCodes;

    /**
     * @param name
     * @param idNr
     * @param pin
     * Create a customer with a name, idNr and pin
     */
    public Customer(String name, String idNr, int pin){
        this.name = name;
        this.idNr = idNr;
        this.pin = pin;
        barCodes = new ArrayList<>();
    }

    public String getName(){ return name; }

    public String getIdNr(){ return idNr; }

    public int getPin(){ return pin; }

    public ArrayList<Integer> getBarCodes(){ return barCodes; }

    /**
     * @param pin
     */
    public void changePin(int pin){
        this.pin = pin;
    }

    //ToDo: Kolla så att den inte redan finns och generera en random
    public void addBarCode(){
        newBarCode++;
        barCodes.add(newBarCode);
    }

    /**
     * Kollar om två objekt har samma personnummer (idNr)
     * kanske heller inte behövs då vi kör idNr
     */
    public boolean equals(Object object){
       if(object instanceof Customer){
           return idNr == ((Customer) object).idNr;
       } else {
           return false;
       }
    }

    public String toString(){
        return name + " " + idNr;
    }
}
