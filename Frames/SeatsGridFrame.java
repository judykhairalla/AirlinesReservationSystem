package AirlinesReservationSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class SeatsGridFrame extends JFrame implements ActionListener {

    //Global variables to allow access from the ActionListener
    String ChosenSeat = "";
    JTextArea guide;
    Flight ChosenFlight; //variable to store the chosen Flight from the user and pass it to the next Frame
    Passenger CurrentPassenger; //variable to store the logged in Passenger

    public SeatsGridFrame(Flight chosenFlight, Passenger currentPassenger) {
        ChosenFlight = chosenFlight;
        CurrentPassenger = currentPassenger;
        
        //-------------------------- SETTING FRAME ----------------------------//
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("MIU Airlines ");
        setLocationRelativeTo(null);

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
        stepsBar.setFont(system.font);
        progressPanel.add(stepsBar);
        
        //--------------------------- CENTER PANEL ----------------------------//
        // Seats Plan
        JPanel seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(7, 7, 5, 5));
        seatsPanel.setMaximumSize(new Dimension(200, 200));

       //Start of Seats Grid drawing
        int SeatRows = chosenFlight.getNumberOfSeats() / 6; //6 Coloumns A to F
        JButton btn;
        char coloumnLetter = (char) 65;
        JLabel coloumn;

        //Loop to print the upper half 
        for (int i = 0; i < 3 * SeatRows; i++) {
            if (i % SeatRows == 0) {
                coloumn = new JLabel(Character.toString(coloumnLetter));
                seatsPanel.add(coloumn);
                coloumnLetter++;
            }

            //if condition to seperate the first class
            if (i == 3 || i == SeatRows + 3 || i == 2 * SeatRows + 3) {
                seatsPanel.add(new JSeparator(SwingConstants.VERTICAL));
            }

            //if condition to seperate the Business class
            if (i == 10 || i == SeatRows + 10 || i == 2 * SeatRows + 10) {
                seatsPanel.add(new JSeparator(SwingConstants.VERTICAL));
            }

            //condition to colour classes
            if (chosenFlight.getSeats().get(i).getIsEmpty() == false) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setEnabled(false);
            } else if (i < 3 || i >= SeatRows && i < SeatRows + 3 || i >= 2 * SeatRows && i < 2 * SeatRows + 3) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.BLACK);
            } else if (i >= 3 && i < 10 || i >= SeatRows + 3 && i < SeatRows + 10 || i >= 2 * SeatRows + 3 && i < 2 * SeatRows + 10) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.YELLOW);
                btn.setForeground(Color.YELLOW);
            } else {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.WHITE);
            }

            btn.setFont(system.font);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setFocusable(false);
            seatsPanel.add(btn);
            btn.addActionListener(this);
        }
        JLabel whiteSpace = new JLabel("     ");
        JLabel whiteSpace1 = new JLabel("    ");
        JLabel whiteSpace2 = new JLabel("   ");
        seatsPanel.add(whiteSpace);
        JLabel rowNum;

        // Loop to make an aisle 
        for (int i = 0; i <= 2; i++) {
            rowNum = new JLabel(Integer.toString(i));
            seatsPanel.add(rowNum);
        }
        seatsPanel.add(whiteSpace1);
        for (int i = 3; i <= 9; i++) {
            rowNum = new JLabel(Integer.toString(i));
            seatsPanel.add(rowNum);
        }
        seatsPanel.add(whiteSpace2);
        for (int i = 10; i <= SeatRows - 1; i++) {
            rowNum = new JLabel(Integer.toString(i));
            seatsPanel.add(rowNum);
        }
        // Loop to print the lower half
        for (int i = 3 * SeatRows; i < 6 * SeatRows; i++) {

            if (i % SeatRows == 0) {
                coloumn = new JLabel(Character.toString(coloumnLetter));
                seatsPanel.add(coloumn);
                coloumnLetter++;
            }

            // if condition to spereate first class
            if (i == 3 + 3 * SeatRows || i == SeatRows * 4 + 3 || i == 5 * SeatRows + 3) {
                seatsPanel.add(new JSeparator(SwingConstants.VERTICAL));
            }

            // if condition to seperate the Business class
            if (i == 10 + 3 * SeatRows || i == SeatRows * 4 + 10 || i == 5 * SeatRows + 10) {
                seatsPanel.add(new JSeparator(SwingConstants.VERTICAL));
            }
            if (chosenFlight.getSeats().get(i).getIsEmpty() == false) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setEnabled(false);
            } else if (i < 3 + 3 * SeatRows || i >= SeatRows * 4 && i < SeatRows * 4 + 3 || i >= SeatRows * 5 && i < 5 * SeatRows + 3) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.BLACK);

            } else if (i >= 3 * SeatRows && i < 3 * SeatRows + 10 || i >= SeatRows * 4 + 3 && i < SeatRows * 4 + 10 || i >= 5 * SeatRows + 3 && i < 5 * SeatRows + 10) {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.YELLOW);
                btn.setForeground(Color.YELLOW);
            } else {
                btn = new JButton(chosenFlight.getSeats().get(i).getSeatNumber());
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.WHITE);
            }
            btn.setFont(system.font);
            btn.setMargin(new Insets(0, 0, 0, 0));
            btn.setFocusable(false);
            seatsPanel.add(btn);
            btn.addActionListener(this);

        }
        //end of Seats Grid drawing

        //--------------------------- GUIDE PANEL ----------------------------//
        JPanel keyPanel = new JPanel();
        keyPanel.setSize(100, 150);
        keyPanel.setMaximumSize(new Dimension(150, 150));

        guide = new JTextArea();
        guide.setSize(300, 500);
        guide.setFont(system.font);
        guide.setBackground(null);
        guide.setEditable(false);
        guide.setText("Seat:      " + "\nClass:         ");
        keyPanel.add(guide);

        //------------------------ NAVIGATION PANEL ---------------------------//
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BorderLayout());
        JButton back = new JButton("Back");
        JButton next = new JButton("Next");
        back.addActionListener(this);
        next.addActionListener(this);
        back.setFont(system.font);
        next.setFont(system.font);
        back.setFocusable(false);
        next.setFocusable(false);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(back);
        buttonsPanel.add(next);
        
        navPanel.add(buttonsPanel, BorderLayout.CENTER);
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
        JButton tempButton;

        //------------------------ BACK BUTTON -------------------------//
        if ("Back".equals(e.getActionCommand())) {
            dispose();
            new AvailableFlightsFrame(CurrentPassenger);
        }

        //------------------------ NEXT BUTTON -------------------------//
        if ("Next".equals(e.getActionCommand()) && "".equals(ChosenSeat)) {
            guide.setText("Please choose a seat to proceed");
        } else if ("Next".equals(e.getActionCommand())) {
            dispose();
            PaymentMethodFrame p = new PaymentMethodFrame(ChosenFlight, ChosenFlight.getSeat(ChosenSeat), CurrentPassenger);
        } else {
            //checks if what's being pressed is already pressed to invert its state
           if (ChosenSeat.equals(e.getActionCommand())) {
                tempButton = (JButton) e.getSource();
                //Conditions to revert back to the correct colour according to the class
                if ("First".equals(ChosenFlight.getSeat(ChosenSeat).getTypeOfSeat())) {
                    tempButton.setBackground(Color.BLACK);
                } else if ("Business".equals(ChosenFlight.getSeat(ChosenSeat).getTypeOfSeat())) {
                    tempButton.setBackground(Color.YELLOW);
                } else {
                    tempButton.setBackground(Color.WHITE);
                }
                ChosenSeat = "";
                guide.setText("Seat:   " + "\nClass:         ");

            } else {
                //don't allow the user to choose more than 1 seat
                if (!"".equals(ChosenSeat)) {
                    JOptionPane.showMessageDialog(this, "You can only choose one seat, unselect a seat by clicking on it again", "", ERROR_MESSAGE);
                } else {
                    ChosenSeat = e.getActionCommand();
                    tempButton = (JButton) e.getSource();
                    tempButton.setBackground(Color.GREEN);
                    e.setSource(tempButton);
                    guide.setText("Seat: " + ChosenSeat + "\nClass: " + ChosenFlight.getSeat(ChosenSeat).getTypeOfSeat());
                }
            }
        }

    }
}