package BikeGarage;

/**
 * Users and its properties
 * @author Christian Bilevits
 */
public class Customer {

    private String firstName, surname, personNr, pin, phoneNr;
    private long regTime;

    /**
     * Constructor for customers
     * @param firstName The customers first name
     * @param surname The customers surname
     * @param personNr The customers personal id-number
     * @param pin The customers pin-code
     * @param phoneNr The customers phone number
     * @param regTime The customers registration time
     */
    public Customer(String firstName, String surname, String personNr, String pin, String phoneNr, long regTime){
        setFirstName(firstName);
        setSurname(surname);
        if(personNr.matches("(.*)\\D(.*)") || personNr.length() != 10){
            throw new IllegalArgumentException("Vänligen skriv in ditt personnummmer som 10 siffror");
        }
        this.personNr = personNr;
        setPin(pin);
        setPhoneNr(phoneNr);
        if(regTime == 0){
            this.regTime = System.currentTimeMillis();
        }else {
            this.regTime = regTime;
        }
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
     * @return The customers phone number
     */
    public String getPhoneNr(){
        return phoneNr;
    }
    /**
     * @return The bike's registration time
     */
    public long getRegTime(){
        return regTime;
    }

    /**
     * Setting new first name
     * @param firstName The customers first name
     */
    public void setFirstName(String firstName){
        if(!firstName.matches("[a-zA-ZåäöÅÄÖ]+")){
            throw new IllegalArgumentException("Förnamnet får bara innehålla bokstäver");
        }
        this.firstName = firstName;
    }

    /**
     * Setting new surname.
     * @param surname The customers surname.
     */
    public void setSurname(String surname){
        if(!surname.matches("[a-zA-ZåäöÅÄÖ]+")){
            throw new IllegalArgumentException("Efternament får bara innehålla bokstäver");
        }
        this.surname = surname;
    }

    /**
     * Setting new pin-code.
     * @param pin The customers pin-code.
     */
    public void setPin(String pin){
        if(pin.matches("(.*)\\D(.*)") || pin.length() != Config.NUMBER_OF_CHARACTER_OF_PIN){
            throw new IllegalArgumentException("Pin-koden måste vara 6 siffror (0-9)");
        }
        this.pin = pin;
    }

    /**
     * Setting new Phone number.
     * @param phoneNr The customers phone number.
     */
    public void setPhoneNr(String phoneNr){
        if(phoneNr.matches("(.*)\\D(.*)")){
            throw new IllegalArgumentException("Ogiltigt telefonnummer.");
        }
        this.phoneNr = phoneNr;
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

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
