package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JOptionPane.*;

public class SeatsGridFrame extends JFrame implements ActionListener {

    //Global variables to allow access from the ActionListener
    String chosenSeatID = "";
    JTextArea guide;
    Flight chosenFlight; //variable to store the chosen Flight from the user and pass it to the next Frame
    Passenger currentPassenger; //variable to store the logged in Passenger
    JButton nextButton;
    ArrayList<JCheckBox> seatsList;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    int seatsInRow;
    char columnLetter;
    JPanel seatsPanel;

    public SeatsGridFrame(Flight chosenFlight, Passenger currentPassenger) {
        this.chosenFlight = chosenFlight;
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("MIU Airlines ");
        setLocationRelativeTo(null);

        //---------------------------- MENU BAR ------------------------------//
        menuBar = new JMenuBar();

        backMenu = new JMenu("Back");
        backMenu.setIcon(Main.backIcon);
        backMenu.addMouseListener(new MouseEventer());
        menuBar.add(backMenu);

        homeMenu = new JMenu("Home");
        homeMenu.setIcon(Main.homeIcon);
        homeMenu.addMouseListener(new MouseEventer());
        menuBar.add(homeMenu);

        menuBar.add(Box.createHorizontalGlue());

        contactUsMenu = new JMenu("Contact us");
        contactUsMenu.setIcon(Main.contactUsIcon);
        contactUsMenu.addMouseListener(new MouseEventer());
        menuBar.add(contactUsMenu);

        logoutMenu = new JMenu("Sign out");
        logoutMenu.setIcon(Main.logoutIcon);
        logoutMenu.addMouseListener(new MouseEventer());
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        //--------------------------- NORTH PANEL -----------------------------//
        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));

        // Progress Bar
        JPanel progressPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(50);
        stepsBar.setStringPainted(true);
        stepsBar.setString("Step 2/4: Choose your seat ");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(Main.font);
        progressPanel.add(stepsBar);

        //--------------------------- CENTER PANEL ----------------------------//
        // Seats Plan
        seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(7, 7, 0, 0));
        seatsPanel.setMaximumSize(new Dimension(200, 200));

        //Start of Seats Grid drawing
        seatsInRow = chosenFlight.getNumberOfSeats() / 6; //6 Coloumns A to F

        columnLetter = (char) 65;
        seatsList = new ArrayList<>();

        //Loop to print the upper half -----------------------------------------
        for (int i = 0; i < 3; i++) {
            addRow();
        }

        // Draw the horizontal aisle
        seatsPanel.add(new JLabel("     "));
        JLabel rowNum;

        for (int i = 0; i <= seatsInRow - 1; i++) {
            rowNum = new JLabel(Integer.toString(i));
            seatsPanel.add(rowNum);

            if (i == 2) {
                seatsPanel.add(new JLabel("    "));
            } else if (i == 9) {
                seatsPanel.add(new JLabel("     "));
            }
        }
        
        // Loop to print the lower half ----------------------------------------
        for (int i = 0; i < 3; i++) {
            addRow();
        }
        //end of Seats Grid drawing

        //--------------------------- GUIDE PANEL ----------------------------//
        JPanel keyPanel = new JPanel();
        keyPanel.setSize(100, 150);
        keyPanel.setMaximumSize(new Dimension(150, 150));

        guide = new JTextArea();
        guide.setSize(300, 500);
        guide.setFont(Main.font);
        guide.setBackground(null);
        guide.setEditable(false);
        guide.setText("Seat:      " + "\nClass:         ");
        keyPanel.add(guide);

        //------------------------ NAVIGATION PANEL ---------------------------//
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setFont(Main.font);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nextButton);

        navPanel.add(buttonsPanel, BorderLayout.EAST);
        navPanel.add(keyPanel, BorderLayout.WEST);

        //--------------------- ADDING PANELS TO FRAME ------------------------//
        add(progressPanel, BorderLayout.NORTH);
        add(seatsPanel, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);
        setResizable(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nextButton) && noneSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a seat to proceed", "", INFORMATION_MESSAGE);
            guide.setText("Seat: " + "\nClass: ");
        } else if (e.getSource().equals(nextButton)) {
            dispose();
            PaymentMethodFrame p = new PaymentMethodFrame(chosenFlight, getSelectedSeats(), currentPassenger);
        } else {
            int count = getSelectedSeats().size();
            if (count == 5) {
                disableUnselectedSeats();
                guide.setText("You can only select up to 5 seats.\nDeselect a seat to change it.");
                return;
            } else {
                enableSeats();
            }

            JCheckBox selectedButton = (JCheckBox) e.getSource();
            chosenSeatID = selectedButton.getActionCommand();
            guide.setText("Seat: " + chosenSeatID + "\nClass: " + chosenFlight.getSeat(chosenSeatID).getTypeOfSeat());

        }

    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(logoutMenu)) {
                dispose();
                currentPassenger.setLoggedIn(false);
                new PassengerLogInFrame();
            }

            if (e.getSource().equals(homeMenu)) {
                dispose();
                new WelcomeFrame(currentPassenger);
            }

            if (e.getSource().equals(contactUsMenu)) {
                dispose();
                new ContactUsFrame(currentPassenger);
            }

            if (e.getSource().equals(backMenu)) {
                dispose();
                new AvailableFlightsFrame(currentPassenger);
            }

        }
    }

    void addRow() {

        for (int i = 0; i < seatsInRow; i++) {
            if (i == 0) {
                JLabel coloumnPanel = new JLabel(Character.toString(columnLetter));
                seatsPanel.add(coloumnPanel);
                columnLetter++;
            }

            JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
            sep.setBackground(new Color(0, 0, 0, 0));
            sep.setForeground(new Color(0, 0, 0, 0));

            //if condition to seperate the first class and business class
            if (i == 3 || i == 10) {
                seatsPanel.add(sep);
            }

            JCheckBox btn = new JCheckBox();
            btn.setToolTipText("Available");
            btn.setActionCommand(chosenFlight.getSeats().get(seatsList.size()).getSeatNumber());
            //condition to colour classes
            if (chosenFlight.getSeats().get(seatsList.size()).getBlock() == true) {
                btn.setEnabled(false);
                btn.setToolTipText("Reserved");
                btn.setIcon(Main.blockedSeat);
            } else if (chosenFlight.getSeats().get(seatsList.size()).getIsEmpty() == false) {
                btn.setIcon(Main.blockedSeat);
                btn.setEnabled(false);
                btn.setToolTipText("Reserved");
            } else if (i < 3) {
                // Set default icon for checkbox
                btn.setIcon(Main.firstClassSeat);
            } else if (i < 10) {
                // Set default icon for checkbox
                btn.setIcon(Main.businessClassSeat);
            } else {
                // Set default icon for checkbox
                btn.setIcon(Main.economyClassSeat);
            }

            btn.setFocusable(false);
            seatsPanel.add(btn);
            btn.addActionListener(this);
            seatsList.add(btn);

            // Set selected icon when checkbox state is selected
            btn.setSelectedIcon(Main.selectedSeat);
        }
    }

    void disableUnselectedSeats() {
        for (JCheckBox seat : seatsList) {
            if (!seat.isSelected()) {
                seat.setEnabled(false);
            }
        }
    }

    void enableSeats() {
        for (JCheckBox seat : seatsList) {
            if (!seat.getToolTipText().equals("Reserved") && !seat.getToolTipText().equals("Blocked")) {
                seat.setEnabled(true);
            }
        }
    }

    ArrayList<Seat> getSelectedSeats() {
        ArrayList<Seat> selectedSeats = new ArrayList<>();
        for (JCheckBox seat : seatsList) {
            if (seat.isSelected()) {
                selectedSeats.add(chosenFlight.getSeat(seat.getActionCommand()));
            }
        }
        return selectedSeats;
    }

    Boolean noneSelected() {
        for (JCheckBox seat : seatsList) {
            if (seat.isSelected()) {
                return false;
            }
        }
        return true;
    }
}
