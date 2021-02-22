package AirlinesReservationSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Passenger extends User implements Serializable, FileInterface {

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
    public boolean logIn(String username, String password)
            throws IOException, FileNotFoundException, ClassNotFoundException {
        return super.logIn(username, password, system.passengersFilePath);
    }

    // Register a new Passenger ------------------------------------------------
    public void addObject(Object o) throws IOException {
        File f = new File(system.passengersFilePath);
        ObjectOutputStream output;

        if (f.length() == 0) {
            output = new ObjectOutputStream(
                    new FileOutputStream(system.passengersFilePath, true));
        } else {
            output = new AppendingObjectOutputStream(
                    new FileOutputStream(system.passengersFilePath, true));
        }

        output.writeObject(o);
        output.close();
    }

    // Making a reservation ----------------------------------------------------
    public void bookFlight(Reservation r) throws IOException, ClassNotFoundException {
        Admin.reserveSeat(r);      
        bookings.add(r);
        updatePassenger(this.getUserName(), this);
    }

    // Cancelling a reservation ------------------------------------------------
    public void removeObject(String r) throws IOException, ClassNotFoundException {
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
    public static boolean AuthenticateUserName(String username)
            throws FileNotFoundException, IOException, ClassNotFoundException {

        File file = new File(system.passengersFilePath);
        if (file.length() == 0) {
            return true;
        }

        Passenger passenger = new Passenger();
        if (passenger.getPassenger(username).getEmail().equals(" ")) {
            return true;
        } else {
            return false;
        }

    }

    // Returns the currently used Passenger object  ----------------------------
    public Passenger getPassenger(String userName) throws IOException, ClassNotFoundException {

        Passenger currentPassenger = new Passenger();

        if (!system.isReadable(system.passengersFilePath)) {
            return currentPassenger;
        }

        FileInputStream fileInput = new FileInputStream(system.passengersFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        while (fileInput.available() != 0) {
            currentPassenger = (Passenger) input.readObject();
            if (currentPassenger.getUserName().equals(userName)) {
                break;
            }
        }

        if (getLoggedIn()) {
            currentPassenger.setLoggedIn(true);
        }

        input.close();
        return currentPassenger;
    }

    // Updates a specific passenger in the Passengers' file --------------------
    public void updatePassenger(String userName, Passenger updatedPassenger)
            throws IOException, ClassNotFoundException {

        if (!system.isReadable(system.passengersFilePath)) {
            System.out.println("Passengers file is not readable");
            return;
        }

        ArrayList<Passenger> passengers = new ArrayList<>();

        // Read from file and detect the Passenger position
        FileInputStream fileInput = new FileInputStream(system.passengersFilePath);
        ObjectInputStream input = new ObjectInputStream(fileInput);

        Passenger currentPassenger;

        while (fileInput.available() != 0) {
            currentPassenger = (Passenger) input.readObject();

            if (currentPassenger.getUserName().equals(userName)) {
                passengers.add(updatedPassenger);
            } else {
                passengers.add(currentPassenger);
            }
        }
        input.close();
        fileInput.close();

        // Write the updated file
        writeObjects(system.passengersFilePath, passengers);
    }

    // Writes a list of Passengers to the file ---------------------------------
    private static void writeObjects(String fileName, ArrayList<Passenger> objects)
            throws IOException {

        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(system.passengersFilePath));

        for (Passenger outputPassenger : objects) {
            output.writeObject(outputPassenger);
        }
        output.close();
    }

}