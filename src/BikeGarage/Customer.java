package BikeGarage;

/**
 * Users and its properties
 * @author Christian Bilevits
 */
public class Customer {

    private String firstName, surname, personNr, pin, phoneNr;
    private long regTime;

    /**
     * Constructor for customers.
     * @param firstName The customers first name.
     * @param surname The customers surname.
     * @param personNr The customers personal id-number.
     * @param pin The customers pin-code.
     * @param phoneNr The customers phone number.
     * @param regTime The customers registration time.
     * @throws IllegalArgumentException when the personal id-number has an incorrect length or if it contains other characters than numbers.
     */
    public Customer(String firstName, String surname, String personNr, String pin, String phoneNr, long regTime) throws IllegalArgumentException{
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
     * @return The customers first name.
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * @return The customers surname.
     */
    public String getSurname(){
        return surname;
    }

    /**
     * @return The customers personal id-number.
     */
    public String getPersonNr(){
        return personNr;
    }

    /**
     * @return The customer's PIN.
     */
    public String getPin(){
        return pin;
    }

    /**
     * @return The customers phone number.
     */
    public String getPhoneNr(){
        return phoneNr;
    }
    /**
     * @return The bike's registration time.
     */
    public long getRegTime(){
        return regTime;
    }

    /**
     * Setter for first name.
     * @param firstName The customers first name.
     * @throws IllegalArgumentException when the name contains other characters than letters.
     */
    public void setFirstName(String firstName) throws IllegalArgumentException{
        if(!firstName.matches("[a-zA-ZåäöÅÄÖ]+")){
            throw new IllegalArgumentException("Förnamnet får bara innehålla bokstäver");
        }
        this.firstName = firstName;
    }

    /**
     * Setter for th surname.
     * @param surname The customers surname.
     * @throws IllegalArgumentException when the surname contains other characters than letters.
     */
    public void setSurname(String surname) throws IllegalArgumentException{
        if(!surname.matches("[a-zA-ZåäöÅÄÖ]+")){
            throw new IllegalArgumentException("Efternament får bara innehålla bokstäver");
        }
        this.surname = surname;
    }

    /**
     * Setter for the PIN.
     * @param pin The customers pin-code.
     * @throws IllegalArgumentException when the PIN contains other characters than numbers.
     */
    public void setPin(String pin){
        if(pin.matches("(.*)\\D(.*)") || pin.length() != Config.NUMBER_OF_CHARACTER_OF_PIN){
            throw new IllegalArgumentException("Pin-koden måste vara 6 siffror (0-9)");
        }
        this.pin = pin;
    }

    /**
     * Setter for phone number
     * @param phoneNr The customers phone number.
     * @throws IllegalArgumentException when the phone number contains other characters than numbers.
     */
    public void setPhoneNr(String phoneNr){
        if(phoneNr.matches("(.*)\\D(.*)")){
            throw new IllegalArgumentException("Ogiltigt telefonnummer.");
        }
        this.phoneNr = phoneNr;
    }
}
