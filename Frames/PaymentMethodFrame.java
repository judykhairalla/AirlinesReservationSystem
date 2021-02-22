package AirlinesReservationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

class PaymentMethodFrame extends JFrame implements ActionListener {

    JLabel cashMessage;
    JFrame f;
    public Seat ChosenSeat;
    public Flight ChosenFlight;
    public Passenger CurrentPassenger;

    public PaymentMethodFrame(Flight chosenFlight, Seat chosenSeat, Passenger currentPassenger) {
        ChosenSeat = chosenSeat;
        ChosenFlight = chosenFlight;
        CurrentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        f = new JFrame();
        f.setLayout(new BorderLayout());
        f.setSize(800, 600);
        f.setTitle("MIU Airlines ");
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //--------------------------- NORTH PANEL ----------------------------//
        // Progress Bar
        JPanel northPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(75);
        stepsBar.setStringPainted(true);
        stepsBar.setString("Step 3/4: Choose a payment method");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(system.font);
        northPanel.add(stepsBar);

        //--------------------------- CENTER PANEL ----------------------------//
        // Icon Buttons
        Icon CashIcon = new ImageIcon("CashIcon.png");
        Icon CardIcon = new ImageIcon("CardIcon.png");

        JButton cashButton = new JButton(CashIcon);
        cashButton.setFont(system.font);
        cashButton.setText("Cash");
        cashButton.setFocusable(false);
        cashButton.addActionListener(this);

        JButton cardButton = new JButton(CardIcon);
        cardButton.setFont(system.font);
        cardButton.setText("Debit/Credit");
        cardButton.setFocusable(false);
        cardButton.addActionListener(this);

        cashMessage = new JLabel();
        cashMessage.setFont(system.font);
        f.add(cashMessage);

        JPanel panel = new JPanel();
        panel.add(cashButton);
        panel.add(cardButton);
        panel.add(cashMessage);

        //--------------------------- SOUTH PANEL ----------------------------//
        JButton backButton = new JButton("Back");
        backButton.setFont(system.font);
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        JPanel navPanel = new JPanel();
        navPanel.add(backButton);

        //--------------------- ADDING PANELS TO FRAME -----------------------//
        f.add(northPanel, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);
        f.add(navPanel, BorderLayout.SOUTH);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //-------------------------- BACK BUTTON -----------------------------//
        if ("Back".equals(e.getActionCommand())) {
            f.dispose();
            SeatsGridFrame S = new SeatsGridFrame(ChosenFlight, CurrentPassenger);
            //---------------------- CASH BUTTON ------------------------------//
        } else if ("Cash".equals(e.getActionCommand())) {
   
            try {
                //Make a reservation
                String reservationID = ChosenFlight.getNumberOfSeats() - ChosenFlight.getSeatsAvaliable() + ChosenSeat.getSeatNumber();
                Reservation currentReservation = new Reservation(CurrentPassenger.getUserName(), ChosenFlight, ChosenSeat, reservationID, Reservation.Status.Pending);
                CurrentPassenger.bookFlight(currentReservation);

                JOptionPane.showMessageDialog(this, "Payment Pending! \nPlease visit an MIU Airlines branch to pay and confirm the reservation.", "", INFORMATION_MESSAGE);
                dispose();
                new WelcomeFrame(CurrentPassenger);
            } catch (IOException | ClassNotFoundException ex) {
              JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);            }

            //---------------------- CARD BUTTON ------------------------------//
        } else {
            f.dispose();

            CardFrame p = new CardFrame(ChosenFlight, ChosenSeat, CurrentPassenger);

        }
    }
}