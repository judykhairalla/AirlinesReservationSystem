package AirlinesReservationSystem.AdminFrames;

import AirlinesReservationSystem.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class ViewFlightFrame extends JFrame implements ActionListener {

    //Global variables to allow access from the ActionListener
    String chosenSeatID = "";
    JTextArea guide;
    JButton blockButton;
    JButton unblockButton;
    Flight chosenFlight; //variable to store the chosen Flight from the user and pass it to the next Frame
    Admin currentAdmin;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;

    JPanel seatsPanel;
    int seatsInRow;
    char columnLetter;

    private ArrayList<JCheckBox> seatsList;

    public ViewFlightFrame(Flight chosenFlight, Admin currentAdmin) {
        this.chosenFlight = chosenFlight;
        this.currentAdmin = currentAdmin;

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

        logoutMenu = new JMenu("Sign out");
        logoutMenu.setIcon(Main.logoutIcon);
        logoutMenu.addMouseListener(new MouseEventer());
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        //--------------------------- NORTH PANE5L -----------------------------//
        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.PAGE_AXIS));

        //--------------------------- CENTER PANEL ----------------------------//
        seatsList = new ArrayList<>();

        // Seats Plan
        seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(7, 7, 5, 5));
        seatsPanel.setMaximumSize(new Dimension(200, 200));

        //Start of Seats Grid drawing
        seatsInRow = chosenFlight.getNumberOfSeats() / 6; //6 Coloumns A to F
        columnLetter = (char) 65;

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

        blockButton = new JButton("Block Seats");
        blockButton.addActionListener(this);
        blockButton.setFont(Main.font);

        unblockButton = new JButton("Unblock Seats");
        unblockButton.addActionListener(this);
        unblockButton.setFont(Main.font);
        unblockButton.setFocusable(false);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(blockButton);
        buttonsPanel.add(unblockButton);

        navPanel.add(buttonsPanel, BorderLayout.EAST);
        navPanel.add(keyPanel, BorderLayout.WEST);

        //--------------------- ADDING PANELS TO FRAME ------------------------//
        add(seatsPanel, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);
        setResizable(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton tempButton;

        //------------------------ Block BUTTON -------------------------//
        if (e.getSource().equals(blockButton) && noneSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a seat to proceed", "", INFORMATION_MESSAGE);
        } else if (e.getSource().equals(blockButton)) {
            blockSeats();
            Admin.updateFlight(chosenFlight.getFlightNumber(), chosenFlight);
            JOptionPane.showMessageDialog(this, "Seats Blocked", "", INFORMATION_MESSAGE);
            dispose();
            new ShowFlightsFrame(currentAdmin);
         //----------------------- Unblock BUTTON -------------------------//
        } else if (e.getSource().equals(unblockButton)) {
            unblockSeats();
            Admin.updateFlight(chosenFlight.getFlightNumber(), chosenFlight);
            JOptionPane.showMessageDialog(this, "Seats Unlocked", "", INFORMATION_MESSAGE);
            dispose();
            new ShowFlightsFrame(currentAdmin);
        } else {
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
                currentAdmin.setLoggedIn(false);
                new AdminLogInFrame();
            }

            if (e.getSource().equals(homeMenu)) {
                dispose();
                new AddOrRemoveFlightsFrame(currentAdmin);
            }

            if (e.getSource().equals(backMenu)) {
                dispose();
                new ShowFlightsFrame(currentAdmin);
            }
        }
    }

    void addRow() {
        for (int i = 0; i < seatsInRow; i++) {
            if (i == 0) {
                JLabel coloumn = new JLabel(Character.toString(columnLetter));
                seatsPanel.add(coloumn);
                columnLetter++;
            }

            JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
            sep.setBackground(new Color(0, 0, 0, 0));
            sep.setForeground(new Color(0, 0, 0, 0));

            //if condition to seperate the first class or business class
            if (i == 3 || i == 10) {
                seatsPanel.add(sep);
            }

            JCheckBox btn = new JCheckBox();
            btn.setToolTipText("Available");
            btn.setActionCommand(chosenFlight.getSeats().get(seatsList.size()).getSeatNumber());

            int currentSeat = seatsList.size();

            //condition to colour classes
            if (chosenFlight.getSeats().get(currentSeat).getBlock() == true) {
                btn.setEnabled(false);
                btn.setToolTipText("Blocked");
                btn.setIcon(Main.blockedSeat);
            } else if (chosenFlight.getSeats().get(currentSeat).getIsEmpty() == false) {
                btn.setToolTipText("<html>" + "Passenger: " + Passenger.getPassenger(chosenFlight.getSeats().get(currentSeat).getReservation().getUserName()).getFullName() + "<br>"
                        + "Reservation ID: " + chosenFlight.getSeats().get(currentSeat).getReservation().getReservationID() + "<br>"
                        + "Status: " + chosenFlight.getSeats().get(currentSeat).getReservation().getStatus() + "</html>");
                btn.setEnabled(false);
                btn.setIcon(Main.blockedSeat);
            } else if (i < 3) {
                btn.setIcon(Main.firstClassSeat);
            } else if (i < 10) {
                btn.setIcon(Main.businessClassSeat);
            } else {
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

    void blockSeats() {
        for (JCheckBox seat : seatsList) {
            if (seat.isSelected()) {
                Seat s = chosenFlight.getSeat(seat.getActionCommand());
                s.setBlock(true);
                chosenFlight.updateSeat(seat.getActionCommand(), s);
            }
        }
    }

    void unblockSeats() {
        for (JCheckBox seat : seatsList) {
            Seat s = chosenFlight.getSeat(seat.getActionCommand());
            if (s.getBlock()) {
                s.setBlock(false);
                chosenFlight.updateSeat(seat.getActionCommand(), s);
            }
        }
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
