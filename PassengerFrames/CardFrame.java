package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.*;
import javax.swing.text.*;

//Limits characters to max 3 for CVV
class JTextFieldLimit extends PlainDocument {

    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            try {
                super.insertString(offset, str, attr);
            } catch (BadLocationException ex) {
                JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                         "ERROR", ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}

class CardFrame extends JFrame implements ActionListener {

    // Components of the Form 
    private Container c;
    private JLabel titleLabel;

    private JLabel cardNumberLabel;
    private JTextField cardNumberField;

    private JLabel CVVLabel;
    private JPasswordField CVVField;

    private JLabel Label;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;

    private JLabel flightPriceLabel;
    private JTextArea tadd;
    private JCheckBox termsCheckBox;

    private JButton payButton;
    private JTextArea ticketTextArea;

    private JLabel feedbackLabel;

    private String months[]
            = {"Jan", "feb", "Mar", "Apr",
                "May", "Jun", "July", "Aug",
                "Sup", "Oct", "Nov", "Dec"};
    private String years[]
            = {"2021", "2022", "2023", "2024", "2025", "2026"};

    Boolean firstChar = true;
    Flight chosenFlight;
    ArrayList<Seat> chosenSeats;
    Passenger currentPassenger;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    public CardFrame(Flight chosenFlight, ArrayList<Seat> chosenSeats, Passenger currentPassenger) {
        this.chosenFlight = chosenFlight;
        this.chosenSeats = chosenSeats;
        this.currentPassenger = currentPassenger;
        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        c = getContentPane();
        c.setLayout(null);

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

        //--------------------------- NORTH PANEL ----------------------------//
        // Progress Bar
        JPanel progressPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(100);
        stepsBar.setStringPainted(true);
        stepsBar.setForeground(Color.green);
        stepsBar.setBackground(Color.green);
        stepsBar.setString("Step 4/4: Confirm Payment");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(Main.font);
        stepsBar.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        progressPanel.add(stepsBar);
        progressPanel.setSize(700, 40);
        progressPanel.setLocation(50, 10);
        c.add(progressPanel);
        //---------------------------- TITLES ---------------------------------//
        titleLabel = new JLabel("Payment");
        titleLabel.setFont(Main.titleFont);
        titleLabel.setSize(300, 30);
        titleLabel.setLocation(50, 70);
        c.add(titleLabel);
        titleLabel = new JLabel("Reservation Details");
        titleLabel.setFont(Main.titleFont);
        titleLabel.setSize(300, 30);
        titleLabel.setLocation(450, 70);
        c.add(titleLabel);
        //------------------------- CARD DETAILS -----------------------------//
        cardNumberLabel = new JLabel("Card Number");
        cardNumberLabel.setFont(Main.font);
        cardNumberLabel.setSize(150, 20);
        cardNumberLabel.setLocation(50, 120);
        c.add(cardNumberLabel);
        Icon VisaIconFaded = new ImageIcon("Visa Logo Faded.png");
        Icon MasterCardIconFaded = new ImageIcon("MasterCard Logo Faded.png");
        Icon VisaIcon = new ImageIcon("Visa Logo.png");
        Icon MasterCardIcon = new ImageIcon("MasterCard Logo.png");
        JLabel visaLabel = new JLabel(VisaIconFaded);
        visaLabel.setSize(30, 22);
        visaLabel.setLocation(200, 150);
        c.add(visaLabel);
        JLabel masterLabel = new JLabel(MasterCardIconFaded);
        masterLabel.setSize(30, 22);
        masterLabel.setLocation(235, 150);
        c.add(masterLabel);
        cardNumberField = new JTextField();
        cardNumberField.setFont(Main.font);
        cardNumberField.setSize(190, 25);
        cardNumberField.setLocation(200, 120);
        cardNumberField.setDocument(new JTextFieldLimit(16));
        cardNumberField.addKeyListener(new KeyAdapter() {
            @Override

            public void keyPressed(KeyEvent e) {
                String check = cardNumberField.getText();

                if (check.isEmpty()) {
                    visaLabel.setIcon(VisaIconFaded);
                    masterLabel.setIcon(MasterCardIconFaded);
                    firstChar = true;
                }

                if (e.getKeyChar() == '4' && firstChar == true) {
                    visaLabel.setIcon(VisaIcon);

                } else if (e.getKeyChar() == '5' && firstChar == true) {
                    masterLabel.setIcon(MasterCardIcon);
                }

                firstChar = false;
            }

        });
        c.add(cardNumberField);

        CVVLabel = new JLabel("CVV");
        CVVLabel.setFont(Main.font);
        CVVLabel.setSize(100, 20);
        CVVLabel.setLocation(50, 180);
        c.add(CVVLabel);

        CVVField = new JPasswordField();
        CVVField.setFont(Main.font);
        CVVField.setSize(150, 25);
        CVVField.setLocation(200, 180);
        CVVField.setDocument(new JTextFieldLimit(3));
        c.add(CVVField);

        Label = new JLabel("Expiry");
        Label.setFont(Main.font);
        Label.setSize(150, 20);
        Label.setLocation(50, 230);
        c.add(Label);

        monthComboBox = new JComboBox(months);
        monthComboBox.setFont(Main.font);
        monthComboBox.setSize(70, 20);
        monthComboBox.setLocation(200, 230);
        c.add(monthComboBox);

        yearComboBox = new JComboBox(years);
        yearComboBox.setFont(Main.font);
        yearComboBox.setSize(80, 20);
        yearComboBox.setLocation(280, 230);
        c.add(yearComboBox);
        //------------------------------ TICKET -------------------------------//
        flightPriceLabel = new JLabel("Your Total: " + getTotalPrice());
        flightPriceLabel.setFont(Main.font);
        flightPriceLabel.setSize(200, 20);
        flightPriceLabel.setLocation(50, 320);
        c.add(flightPriceLabel);
        //------------------------------ BUTTONS -------------------------------//
        termsCheckBox = new JCheckBox("I accept the terms and conditions.");
        termsCheckBox.setFont(Main.subFont);
        termsCheckBox.setSize(320, 30);
        termsCheckBox.setLocation(50, 350);
        c.add(termsCheckBox);

        payButton = new JButton("Pay");
        payButton.setFont(Main.font);
        payButton.setSize(90, 30);
        payButton.setLocation(355, 460);
        payButton.addActionListener(this);
        c.add(payButton);

        ticketTextArea = new JTextArea();
        ticketTextArea.setFont(Main.font);
        ticketTextArea.setBackground(null);
        ticketTextArea.setSize(500, 700);
        ticketTextArea.setLocation(450, 120);
        ticketTextArea.setEditable(false);
        ticketTextArea.setText("Flight No: " + this.chosenFlight.getFlightNumber() + "\nFrom: " + this.chosenFlight.getFrom() + "\nTo: " + this.chosenFlight.getTo() + "\nDeparture: "
                + this.chosenFlight.getDepartureDate() + "\nArrival: " + this.chosenFlight.getArrivalDate() + "\n-----------------------------------\nSeats: \n" + seatsInfo());
        c.add(ticketTextArea);
        
        //--------------------------- FEEDBACK ---------------------------------//
        feedbackLabel = new JLabel("");
        feedbackLabel.setFont(Main.font);
        feedbackLabel.setSize(500, 25);
        feedbackLabel.setLocation(100, 420);
        c.add(feedbackLabel);
        setVisible(true);
    }

    // method actionPerformed() 
    // to get the action performed 
    // by the user and act accordingly 
    public void actionPerformed(ActionEvent e) {

        //--------------------------- PAY BUTTON ------------------------------//
        if (e.getSource() == payButton) {
            String CCNumber = ((JTextField) cardNumberField).getText();
            //Visa and Mastercard begin with either 4 or 5
            if (!CCNumber.isEmpty()) {
                if ((CCNumber.charAt(0) == '4' || CCNumber.charAt(0) == '5') && CCNumber.length() == 16) {

                    String CVV = ((JTextField) CVVField).getText();
                    if (CVV.length() == 3) // If the passenger has already accepted the terms and conditions
                    {
                        if (termsCheckBox.isSelected()) {
                            JOptionPane.showMessageDialog(this, "Payment Successful! \nThanks for choosing MIU Airlines", "", INFORMATION_MESSAGE);
                            dispose();
                            new WelcomeFrame(currentPassenger);
                            try {
                                //Make a reservation
                                makeReservations();

                            } catch (IOException | ClassNotFoundException ex) {
                                JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                                         "ERROR", ERROR_MESSAGE);
                                System.exit(1);
                            }

                        } else {
                            feedbackLabel.setText("Please accept the"
                                    + " terms & conditions..");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid CC Number", "", ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "", ERROR_MESSAGE);
            }
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //----------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(logoutMenu)) {
                dispose();
                currentPassenger.setLoggedIn(false);
                new PassengerLogInFrame();
            }

            //-------------------------- HOME BUTTON --------------------------//
            if (e.getSource().equals(homeMenu)) {
                dispose();
                new WelcomeFrame(currentPassenger);
            }

            //----------------------- CONTACT BUTTON --------------------------//
            if (e.getSource().equals(contactUsMenu)) {
                dispose();
                new ContactUsFrame(currentPassenger);
            }

            //----------------------- BACK BUTTON --------------------------//
            if (e.getSource().equals(backMenu)) {
                dispose();
                new PaymentMethodFrame(chosenFlight, chosenSeats, currentPassenger);
            }
        }
    }

    void makeReservations() throws IOException, ClassNotFoundException {
        for (Seat currentSeat : chosenSeats) {
            String reservationID = chosenFlight.getNumberOfSeats() - chosenFlight.getSeatsAvaliable() + currentSeat.getSeatNumber();
            Reservation currentReservation = new Reservation(currentPassenger.getUserName(), chosenFlight, currentSeat, reservationID, Reservation.Status.Confirmed);
            currentPassenger.bookFlight(currentReservation);
        }
    }
    
    String seatsInfo ()
    {
        String str = "";
        for (Seat currentSeat : chosenSeats) {
            str += currentSeat.getSeatNumber()+ "/" + currentSeat.getTypeOfSeat() + "\n" ;
        }
        return str;
    }
    
    double getTotalPrice()
    {
        double total = 0;
        for (Seat currentSeat : chosenSeats) {
            total += Admin.getFlightPrice(chosenFlight.getFlightNumber(), currentSeat) ;
        }
        return total;
    }
}
