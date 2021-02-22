package AirlinesReservationSystem;

import java.io.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Admin extends User implements FileInterface, Serializable {

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
    public boolean logIn(String username, String password)
            throws IOException, FileNotFoundException, ClassNotFoundException {
        return super.logIn(username, password, system.adminsFilePath);
    }

    // Register ----------------------------------------------------------------
    public static void addAdmin(Object a) throws IOException {
        File file = new File(system.adminsFilePath);
        ObjectOutputStream output;

        if (file.length() == 0) {
            output = new ObjectOutputStream(
                    new FileOutputStream(system.adminsFilePath, true));
        } else {
            output = new AppendingObjectOutputStream(
                    new FileOutputStream(system.adminsFilePath, true));
        }

        output.writeObject(a);
        output.close();
    }

    // Returns the currently used Admin object ---------------------------------
    public Admin getAdmin(String userName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        Admin currentAdmin = new Admin();

        if (!system.isReadable(system.adminsFilePath)) {
            return currentAdmin;
        }

        FileInputStream fileInput = new FileInputStream(system.adminsFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        while (fileInput.available() != 0) {

            currentAdmin = (Admin) input.readObject();

            if (currentAdmin.getUserName().equals(userName)) {
                break;
            }
        }
        input.close();

        if (getLoggedIn()) {
            currentAdmin.setLoggedIn(true);
        }

        return currentAdmin;
    }

    // Add a flight ------------------------------------------------------------
    public void addObject(Object o) throws IOException {

        File file = new File(system.flightsFilePath);
        ObjectOutputStream output;

        if (file.length() == 0) {
            output = new ObjectOutputStream(
                    new FileOutputStream(system.flightsFilePath, true));
        } else {
            output = new AppendingObjectOutputStream(
                    new FileOutputStream(system.flightsFilePath, true));
        }

        output.writeObject(o);
        output.close();
    }

    // Remove a flight ---------------------------------------------------------
    public void removeObject(String flightID) throws IOException, ClassNotFoundException {

        ArrayList<Flight> flights = new ArrayList<>();

        // Read from file and detect the flight position
        FileInputStream fileInput = new FileInputStream(system.flightsFilePath);
        ObjectInputStream input = new ObjectInputStream(
                fileInput);

        Flight currentFlight;

        while (fileInput.available() != 0) {
            currentFlight = (Flight) input.readObject();

            if (!currentFlight.getFlightNumber().equals(flightID)) {
                flights.add(currentFlight);
            }
        }
        input.close();

        // Write the updated file
        writeObjects(system.flightsFilePath, flights);
    }

    // Updates a certain Flight in the file ------------------------------------
    public static void updateFlight(String flightID, Flight updatedFlight)
            throws IOException, ClassNotFoundException {

        ArrayList<Flight> flights = new ArrayList<>();

        // Read from file and detect the flight position
        FileInputStream fileInput = new FileInputStream(system.flightsFilePath);
        ObjectInputStream input = new ObjectInputStream(
                fileInput);

        Flight currentFlight;

        while (fileInput.available() != 0) {
            currentFlight = (Flight) input.readObject();

            if (currentFlight.getFlightNumber().equals(flightID)) {
                flights.add(updatedFlight);
            } else {
                flights.add(currentFlight);
            }
        }
        input.close();

        // Write the updated file
        writeObjects(system.flightsFilePath, flights);
    }

    // Returns all available flights -------------------------------------------
    public static ArrayList<Flight> getFlights()
            throws IOException, ClassNotFoundException {

        ArrayList<Flight> flights = new ArrayList<>();

        if (!system.isReadable(system.flightsFilePath)) {
            return flights;
        }

        FileInputStream fileInput = new FileInputStream(system.flightsFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        Flight currentFlight;

        while (fileInput.available() != 0) {
            currentFlight = (Flight) input.readObject();

            if (currentFlight.getSeatsAvaliable() != 0) {
                flights.add(currentFlight);
            }
        }
        input.close();

        return flights;
    }

    // Returns all the available flights from a specific city to another one ---
    public static ArrayList<Flight> getFlights(String fromCode, String toCode)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ArrayList<Flight> validFlights = new ArrayList<>();

        if (!system.isReadable(system.flightsFilePath)) {
            return validFlights;
        }

        FileInputStream fileInput = new FileInputStream(system.flightsFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        Flight currentFlight;

        while (fileInput.available() != 0) {
            currentFlight = (Flight) input.readObject();
            if (currentFlight.getFrom().equals(fromCode)
                    && currentFlight.getTo().equals(toCode)
                    && currentFlight.getSeatsAvaliable() != 0) {
                validFlights.add(currentFlight);
            }
        }

        input.close();
        return validFlights;
    }

    // Returns a specific Flight -----------------------------------------------
    public static Flight getFlight(String flightID)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileInput = new FileInputStream(system.flightsFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        Flight currentFlight = new Flight();

        while (fileInput.available() != 0) {
            currentFlight = (Flight) input.readObject();
            if (currentFlight.getFlightNumber().equals(flightID)) {
                break;
            }
        }
        input.close();
        return currentFlight;
    }

    // Returns a specific flight's price ---------------------------------------
    public static double getFlightPrice(String flightID, Seat s)
            throws FileNotFoundException, IOException, ClassNotFoundException {
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

    // Writes a list of flights to the file ------------------------------------
    private static void writeObjects(String fileName, ArrayList<Flight> objects)
            throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(system.flightsFilePath));

        for (Flight outputFlight : objects) {
            output.writeObject(outputFlight);
        }
        output.close();
    }

    //--------------------------------------------------------------------------
    public static void reserveSeat(Reservation r)
            throws FileNotFoundException, IOException, ClassNotFoundException {

        Seat ChosenSeat = r.getSeat();
        Flight currentFlight = r.getFlight();

        ChosenSeat.setReservation(r);
        if (r.getStatus() == r.status.Confirmed) {
            ChosenSeat.setIsEmpty(false);
        }
        currentFlight.updateSeat(ChosenSeat.getSeatNumber(), ChosenSeat);
        currentFlight.setSeatsAvaliable(currentFlight.getSeatsAvaliable() - 1);
        updateFlight(r.getFlight().getFlightNumber(), currentFlight);
    }

}