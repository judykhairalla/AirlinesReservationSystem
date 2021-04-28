package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.*;

class PaymentMethodFrame extends JFrame implements ActionListener {

    JLabel cashMessage;
    JFrame f;
    public ArrayList<Seat> chosenSeats;
    public Flight chosenFlight;
    public Passenger currentPassenger;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    public PaymentMethodFrame(Flight chosenFlight, ArrayList<Seat> chosenSeats, Passenger currentPassenger) {
        this.chosenSeats = chosenSeats;
        this.chosenFlight = chosenFlight;
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(800, 600);
        f.setTitle("MIU Airlines ");
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        f.setJMenuBar(menuBar);

        //--------------------------- NORTH PANEL ----------------------------//
        // Progress Bar
        JPanel northPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(75);
        stepsBar.setStringPainted(true);
        stepsBar.setString("Step 3/4: Choose a payment method");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(Main.font);
        northPanel.add(stepsBar);

        //--------------------------- CENTER PANEL ----------------------------//
        // Icon Buttons
        Icon CashIcon = new ImageIcon("CashIcon.png");
        Icon CardIcon = new ImageIcon("CardIcon.png");

        JButton cashButton = new JButton(CashIcon);
        cashButton.setFont(Main.font);
        cashButton.setText("Cash");
        cashButton.setFocusable(false);
        cashButton.addActionListener(this);

        JButton cardButton = new JButton(CardIcon);
        cardButton.setFont(Main.font);
        cardButton.setText("Debit/Credit");
        cardButton.setFocusable(false);
        cardButton.addActionListener(this);

        cashMessage = new JLabel();
        cashMessage.setFont(Main.font);
        f.add(cashMessage);

        JPanel panel = new JPanel();
        panel.add(cashButton);
        panel.add(cardButton);
        panel.add(cashMessage);

        //--------------------- ADDING PANELS TO FRAME -----------------------//
        f.add(northPanel, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //--------------------------  BUTTONS --------------------------------//
        if ("Cash".equals(e.getActionCommand())) {

            try {
                //Make a reservation
                makeReservations();

                JOptionPane.showMessageDialog(this, "Payment Pending! \nPlease visit an MIU Airlines branch to pay and confirm the reservation.", "", INFORMATION_MESSAGE);
                f.dispose();
                new WelcomeFrame(currentPassenger);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                        "ERROR", ERROR_MESSAGE);
                System.exit(1);
            }

            //---------------------- CARD BUTTON ------------------------------//
        } else {
            f.dispose();
            CardFrame p = new CardFrame(chosenFlight, chosenSeats, currentPassenger);
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(logoutMenu)) {
                f.dispose();
                currentPassenger.setLoggedIn(false);
                new PassengerLogInFrame();
            }

            if (e.getSource().equals(homeMenu)) {
                f.dispose();
                new WelcomeFrame(currentPassenger);
            }

            if (e.getSource().equals(contactUsMenu)) {
                f.dispose();
                new ContactUsFrame(currentPassenger);
            }

            if (e.getSource().equals(backMenu)) {
                f.dispose();
                new SeatsGridFrame(chosenFlight, currentPassenger);
            }
        }
    }

    void makeReservations() throws IOException, ClassNotFoundException {
        for (Seat currentSeat : chosenSeats) {
            String reservationID = chosenFlight.getNumberOfSeats() - chosenFlight.getSeatsAvaliable() + currentSeat.getSeatNumber();
            Reservation currentReservation = new Reservation(currentPassenger.getUserName(), chosenFlight, currentSeat, reservationID, Reservation.Status.Pending);
            currentPassenger.bookFlight(currentReservation);
        }
    }
}
