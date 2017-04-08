package BikeGarage;

public class Customer {

    private String firstName, surname, personNr, pin;
    private long regTime;

    /**
     * Constructor for customers.
     * @param firstName The customers first name
     * @param surname The customers surname
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     */
    public Customer(String firstName, String surname, String personNr, String pin){
        this.firstName = firstName;
        this.surname = surname;
        this.personNr = personNr;
        this.pin = pin;
        this.regTime = System.currentTimeMillis();
    }

    /**
     * Constructor for customer used by startup
     * @param firstName The customers first name
     * @param surname The customers surname
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     * @param regTime The customers registration time
     */
    public Customer(String firstName, String surname, String personNr, String pin, long regTime){
        this.firstName = firstName;
        this.surname = surname;
        this.personNr = personNr;
        this.pin = pin;
        this.regTime = regTime;
    }

    /**
     * @return The customers first name
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * @return The customers surname
     */
    public String getSurname(){
        return surname;
    }

    /**
     * @return The customers personal id-number
     */
    public String getPersonNr(){
        return personNr;
    }

    /**
     * @return The customers pin
     */
    public String getPin(){
        return pin;
    }

    /**
     * @return The bike's registration time
     */
    public long getRegTime(){
        return regTime;
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
