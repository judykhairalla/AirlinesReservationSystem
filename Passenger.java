package AirlinesReservationSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Passenger extends User implements Serializable {

    private ArrayList<Reservation> bookings;

    // Default Constructor -----------------------------------------------------
    public Passenger() {
        super(" ", " ", " ", " ", -1, ' ', " ", " ");
        bookings = new ArrayList<>();
    }

    // Parameterized constructor -----------------------------------------------
    public Passenger(String userName, String password, String firstName,
            String lastName, int age, char gender, String phoneNumber, String email) {
        super(userName, password, firstName, lastName, age, gender, phoneNumber, email);
        bookings = new ArrayList<>();
    }

    // Login -------------------------------------------------------------------
    public static boolean logIn(String username, String password){
        
        if (Main.passengers.containsKey(username)) {
            Passenger currentPassenger = Main.passengers.get(username) ;
            if (currentPassenger.getPassword().equals(password)) 
            {
                currentPassenger.setLoggedIn(true);
                return true;
            }
        }
        return false;
    }

    // Register a new Passenger ------------------------------------------------
    public void addObject(Passenger o) {
        Main.passengers.put(o.getUserName(), o);
        Main.saveFiles();
    }

    // Making a reservation ----------------------------------------------------
    public void bookFlight(Reservation r){
        Admin.reserveSeat(r);
        bookings.add(r);
        updatePassenger(this.getUserName(), this);
    }

    // Cancelling a reservation ------------------------------------------------
    public void removeObject(String r){
        bookings.remove(Integer.parseInt(r));
        updatePassenger(this.getUserName(), this);
    }

    // Return an array list with all the reservations --------------------------
    public ArrayList<Reservation> getReservations() {
        return bookings;
    }

    // Return a specific reservation -------------------------------------------
    public Reservation getReservation(int ReservationIndex) {
        return bookings.get(ReservationIndex);
    }

    // Updating a reservation Status -------------------------------------------
    public void updateReservation(String reservationID, Reservation r)
            throws IOException, ClassNotFoundException {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getReservationID().equalsIgnoreCase(r.getReservationID())) {
                bookings.set(i, r);
                updatePassenger(this.getUserName(), this);
            }
        }
    }

    // Checks wether the username has been already taken -----------------------
    public static boolean AuthenticateUserName(String username){
        return Main.passengers.containsKey(username) ? false : true;
    }

    // Returns the currently used Passenger object  ----------------------------
    public static Passenger getPassenger(String userName){

        Passenger currentPassenger = new Passenger();
        currentPassenger = Main.passengers.get(userName);
        return currentPassenger;
    }

    // Updates a specific passenger in the Passengers' file --------------------
    public void updatePassenger(String userName, Passenger updatedPassenger){
        Main.passengers.replace(userName, updatedPassenger);
        Main.saveFiles();
    }

}