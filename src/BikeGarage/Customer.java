package BikeGarage;

public class Customer {

    private String firstName, surname, personNr, pin;
    private long regTime;

    /**
     * Constructor for customers.
     * @param firstName
     * @param surname
     * @param personNr
     * @param pin
     */
    public Customer(String firstName, String surname, String personNr, String pin){
        this.firstName = firstName;
        this.surname = surname;
        this.personNr = personNr;
        this.pin = pin;
        this.regTime = regTime; //ToDo: Fixa detta
    }

    /**
     * New customer used by startup
     * @param firstName
     * @param surname
     * @param personNr
     * @param pin
     * @param regTime
     */
    public Customer(String firstName, String surname, String personNr, String pin, long regTime){
        this.firstName = firstName;
        this.surname = surname;
        this.personNr = personNr;
        this.pin = pin;
        this.regTime = regTime;
    }

    /**
     * 
     * @return Returns customers first name
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     *
     * @return Returns customers surname
     */
    public String getSurname(){
        return surname;
    }

    /**
     *
     * @return Returns person-number
     */
    public String getPersonNr(){
        return personNr;
    }

    /**
     *
     * @return Returns pin
     */
    public String getPin(){
        return pin;
    }

    /**
     * Setting new first name
     * @param firstName
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Setting new surname
     * @param surname
     */
    public void setSurname(String surname){
        this.surname = surname;
    }

    /**
     * Setting new pin-code
     * @param pin
     */
    public void setPin(String pin){
        this.pin = pin;
    }

    /**
     * Checks if a customer has the same personNr as this customer.
     * @param object
     * @return
     */
    public boolean equals(Object object){
        if(object instanceof Customer){
            return personNr == ((Customer) object).personNr;
        } else {
            return false;
        }
    }
}
