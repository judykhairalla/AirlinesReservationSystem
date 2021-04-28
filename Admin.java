package AirlinesReservationSystem;

import java.io.*;
import java.io.IOException;
import java.util.*;

public class Admin extends User implements Serializable {

    // Constructor -------------------------------------------------------------
    public Admin() {
        super(" ", " ", " ", " ", 0, ' ', " ", " ");
    }

    // Parameterized Constructor -----------------------------------------------
    public Admin(String userName, String password, String firstName,
            String lastName, int age, char gender, String phoneNumber, String email) {
        super(userName, password, firstName, lastName, age, gender, phoneNumber, email);
    }

    // logIn -------------------------------------------------------------------
    public static boolean logIn(String username, String password) {
        if (Main.admins.containsKey(username)) {
            Admin currentAdmin = Main.admins.get(username) ;
            if (currentAdmin.getPassword().equals(password)) 
            {
                currentAdmin.setLoggedIn(true);
                return true;
            }
        }
        return false;
    }

    // Register ----------------------------------------------------------------
    public static void addAdmin(Admin admin) {
        Main.admins.put(admin.getUserName(), admin);
        Main.saveFiles();
    }

    // Returns the currently used Admin object ---------------------------------
    public static Admin getAdmin(String userName){
        Admin currentAdmin = new Admin();
        currentAdmin = Main.admins.get(userName);
        return currentAdmin;
    }

    // Add a flight ------------------------------------------------------------
    public void addObject(Flight o){
        Main.flights.put(o.getFlightNumber(), o);
        Main.saveFiles();
    }

    // Remove a flight ---------------------------------------------------------
    public void removeObject(String flightID){
        Main.flights.remove(flightID);
        Main.saveFiles();
    }

    // Updates a certain Flight in the file ------------------------------------
    public static void updateFlight(String flightID, Flight updatedFlight){

        Main.flights.replace(flightID, updatedFlight);
        Main.saveFiles();
    }

    // Returns all available flights -------------------------------------------
    public static ArrayList<Flight> getFlights() {
        return new ArrayList<>(Main.flights.values());
    }

    // Returns all the available flights from a specific city to another one ---
    public static ArrayList<Flight> getFlights(String fromCode, String toCode){
        
        ArrayList<Flight> flightsList = new ArrayList<>();
        for(Flight currentFlight: Main.flights.values())
        {
            if(currentFlight.getFrom().equals(fromCode) && currentFlight.getTo().equals(toCode))
                flightsList.add(currentFlight);
        }
        return flightsList;
    }

    // Returns a specific Flight -----------------------------------------------
    public static Flight getFlight(String flightID) {
        
        Flight currentFlight = new Flight();
        currentFlight = Main.flights.get(flightID); 
        return currentFlight;
    }

    // Returns a specific flight's price ---------------------------------------
    public static double getFlightPrice(String flightID, Seat s) {
        Flight currentFlight = getFlight(flightID);

        switch (s.getTypeOfSeat()) {
            case "First":
                return currentFlight.getPrice() * (1.5);
            case "Business":
                return currentFlight.getPrice() * (1.2);
            default:
                return currentFlight.getPrice();
        }

    }

    //--------------------------------------------------------------------------
    public static void reserveSeat(Reservation r) {

        Seat ChosenSeat = r.getSeat();
        Flight currentFlight = r.getFlight();

        ChosenSeat.setReservation(r);
        if (r.getStatus() == r.status.Confirmed ) {
            ChosenSeat.setIsEmpty(false);
        }
        currentFlight.updateSeat(ChosenSeat.getSeatNumber(), ChosenSeat);
        currentFlight.setSeatsAvaliable(currentFlight.getSeatsAvaliable() - 1);
        updateFlight(r.getFlight().getFlightNumber(), currentFlight);
    }

}