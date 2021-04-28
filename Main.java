/** *****************************************************************************
 * A complete reservation system for an Airline company containing two modules,
 * one for regular users and another for administrators. The users can sign up,
 * login and manage their reservations. The admins can add and cancel flights.
 * All updates made by the admins to the flights automatically affect the passengers’
 * reservations.
 *
 * Admin Credentials:
 * Username: basma123 , Password: 4321@Basma
 * Username: rana123 , Password: 4321@Rana
 * Username: steven123 , Password: 4321@Steven
 * Username: judy123 , Password: 4321@Judy
 * User Credentials: Kindly, Register a new user
 *
 * Sample Run:
 * 1- Make sure that all Icons and Files are in the default path
 * 2- Run Main.java file
 * All text Fields have special constraints (password: number of characters and
 * symbols, phone number, emails, new username for registration, etc...)
 *
 * Prepared by: Rana Raafat Kamal Al-Attar, Basma Mohamed Dessouky,
 * Judy Wadgy Fahmy Khairalla, Steven Albert Farahat
 *
 *
 * CSC240 Fall 2020
 ***************************************************************************** */
package AirlinesReservationSystem;

import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class Main {

    public static HashMap<String, Flight> flights;
    public static HashMap<String, Passenger> passengers;
    public static HashMap<String, Admin> admins;

    public static final String flightsFilePath = "Flights.bin";
    public static final String passengersFilePath = "Passengers.bin";
    public static final String adminsFilePath = "Admins.bin";

    public static final ImageIcon logoutIcon = new ImageIcon("logout.png");
    public static final ImageIcon homeIcon = new ImageIcon("home.png");
    public static final ImageIcon backIcon = new ImageIcon("back.png");
    public static final ImageIcon contactUsIcon = new ImageIcon("contactus.png");

    public static final ImageIcon blockedSeat = new ImageIcon("blocked.png");
    public static final ImageIcon selectedSeat = new ImageIcon("selected.png");
    public static final ImageIcon firstClassSeat = new ImageIcon("first.png");
    public static final ImageIcon businessClassSeat = new ImageIcon("business.png");
    public static final ImageIcon economyClassSeat = new ImageIcon("economy.png");

    public static final Font titleFont = new Font(null, Font.BOLD, 21);
    public static final Font font = new Font(null, Font.PLAIN, 17);
    public static final Font subFont = new Font(null, Font.PLAIN, 15);
    public static Font creditsFont = new Font(null, Font.PLAIN, 12);

    public static final String[] cities = {"AUH, Abu Dhabi", "AUS, USA",
        "BOS, USA", "DTW, USA", "JFK, USA", "ORD, USA",
        "CAI, Egypt", "HRG, Egypt", "LXR, Egypt",
        "CDG, France", "ORY, France", "TLS, France",
        "MAD, Spain", "BCN, Spain",
        "BER, Germany", "FRA, Germany",
        "FLR, Italy", "MIL, Italy", "ROM, Italy", "VCE, Italy",
        "BOM, India", "BLR, India", "DEL, India",
        "BJS, China", "CAN, China", "HKG, China", "SHA, China"};

    public static void main(String[] args) {
        flights = new HashMap<>();
        passengers = new HashMap<>();
        admins = new HashMap<>();
        readFiles();
        addAdmins();
        new FirstFrame(true);
    }

    public static void addAdmins() {
        Admin newAdmin1 = new Admin("basma123", "4321@Basma", "Basma", "Dessouky", 20, 'F', "0198924872", "basma@gmail.com");

        Admin newAdmin2 = new Admin("judy123", "4321@Judy", "Judy", "Khairalla", 19, 'F', "0198924872", "judy@gmail.com");

        Admin newAdmin3 = new Admin("rana123", "4321@Rana", "Rana", "Raafat", 19, 'F', "0198924872", "rana@gmail.com");

        Admin newAdmin4 = new Admin("steven123", "4321@Steven", "Steven", "Albert", 19, 'M', "0198924872", "steven@gmail.com");

        Admin.addAdmin(newAdmin1);
        Admin.addAdmin(newAdmin2);
        Admin.addAdmin(newAdmin3);
        Admin.addAdmin(newAdmin4);

    }

    public static boolean isReadable(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        return true;
    }

    public static void saveFiles() {
        try {
            // Write flights file ----------------------------------------------
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream(Main.flightsFilePath));
            output.writeObject(flights);
            output.close();

            // Write passengers file -------------------------------------------
            output = new ObjectOutputStream(
                    new FileOutputStream(Main.passengersFilePath));
            output.writeObject(passengers);
            output.close();

            // Write passengers file -------------------------------------------
            output = new ObjectOutputStream(
                    new FileOutputStream(Main.adminsFilePath));
            output.writeObject(admins);
            output.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                    "ERROR1", ERROR_MESSAGE);
            System.exit(1);
        }

    }

    public static void readFiles() {
        try {
            File file = new File(Main.flightsFilePath);
            if (file.length() == 0) {
                return;
            }

            // Read flights file -----------------------------------------------
            FileInputStream fileInput = new FileInputStream(Main.flightsFilePath);
            ObjectInputStream input = new ObjectInputStream(fileInput);

            flights = (HashMap<String, Flight>) input.readObject();

            fileInput.close();
            input.close();

            // Read passengers file --------------------------------------------
            file = new File(Main.passengersFilePath);
            if (file.length() == 0) {
                return;
            }

            fileInput = new FileInputStream(Main.passengersFilePath);
            input = new ObjectInputStream(fileInput);

            passengers = (HashMap<String, Passenger>) input.readObject();

            fileInput.close();
            input.close();

            // Read admins file ------------------------------------------------
            file = new File(Main.adminsFilePath);
            if (file.length() == 0) {
                return;
            }

            fileInput = new FileInputStream(Main.adminsFilePath);
            input = new ObjectInputStream(fileInput);

            admins = (HashMap<String, Admin>) input.readObject();

            fileInput.close();
            input.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                    "ERROR1", ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
