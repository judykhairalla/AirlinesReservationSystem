package AirlinesReservationSystem;

import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class system {

    static Flight chosenFlight;

    static String flightsFilePath = "Flights.bin";
    static String passengersFilePath = "Passengers.bin";
    static String adminsFilePath = "Admins.bin";

    static Font titleFont = new Font(null, Font.BOLD, 21);
    static Font font = new Font(null, Font.PLAIN, 17);
    static Font subFont = new Font(null, Font.PLAIN, 15);

    static final String[] cities = {"AUH, Abu Dhabi", "AUS, USA",
        "BOS, USA", "DTW, USA", "JFK, USA", "ORD, USA",
        "CAI, Egypt", "HRG, Egypt", "LXR, Egypt",
        "CDG, France", "ORY, France", "TLS, France",
        "MAD, Spain", "BCN, Spain",
        "BER, Germany", "FRA, Germany",
        "FLR, Italy", "MIL, Italy", "ROM, Italy", "VCE, Italy",
        "BOM, India", "BLR, India", "DEL, India",
        "BJS, China", "CAN, China", "HKG, China", "SHA, China"};

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        addAdmins();
        new FirstFrame(true);
    }

    public static void addAdmins() throws IOException {
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
        if (!file.exists() || file.length() == 0 || !file.canRead()) {
            return false;
        }
        return true;
    }
}
