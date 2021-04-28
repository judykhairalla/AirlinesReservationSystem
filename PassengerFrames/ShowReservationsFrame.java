package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.*;

public class ShowReservationsFrame extends JFrame {

    private JButton cancelButton;
    private JTable table;
    private ArrayList<Reservation> reservations;
    private Passenger currentPassenger;

    Object[][] data;
    Object[] columnNames;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    public ShowReservationsFrame(Passenger passenger) {
        currentPassenger = passenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("MIU Airlines ");
        setLayout(new BorderLayout());

        try {
            try {
                fillTable();
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                        "ERROR", ERROR_MESSAGE);
                System.exit(1);
            }
            setTableSettings();

        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                    "ERROR", ERROR_MESSAGE);
            System.exit(1);
        }

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

        //------------------------- NORTH PANEL -------------------------------//
        // Title
        JPanel descriptionPanel = new JPanel();
        JLabel jLabel1 = new JLabel("My Reservations:");
        jLabel1.setFont(Main.titleFont);
        descriptionPanel.add(jLabel1);

        //------------------------- SOUTH PANEL -------------------------------//
        JPanel actionsPanel = new JPanel();

        // Cancel reservation button
        cancelButton = new JButton("Cancel Reservation");
        cancelButton.setFont(Main.font);
        cancelButton.addActionListener(new ButtonListener());
        actionsPanel.add(cancelButton);

        add(descriptionPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            Object buttonPressed = event.getSource();

            //------------------------ CANCEL BUTTON -------------------------//
            if (buttonPressed.equals(cancelButton)) {

                int choice;
                // Making sure a reservation is selected
                if (table.getSelectedRow() != -1) {
                    choice = JOptionPane.showConfirmDialog(null, "Do you want really want to cancel?",
                            "Confirmation Message", JOptionPane.YES_NO_OPTION);

                    // Confirming cancellation
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            String SeatID = currentPassenger.getReservation(table.getSelectedRow()).getSeat().getSeatNumber();
                            String FlightID = currentPassenger.getReservation(table.getSelectedRow()).getFlight().getFlightNumber();
                            Flight f = Admin.getFlight(FlightID);
                            if (f != null) {
                                Seat s = f.getSeat(SeatID);
                                f.updateSeatRes(SeatID, true);
                                Admin a = new Admin();
                                a.updateFlight(FlightID, f);
                            }
                            currentPassenger.removeObject(String.valueOf(table.getSelectedRow()));
                            fillTable();
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.setDataVector(data, columnNames);

                        } catch (IOException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                                    "ERROR", ERROR_MESSAGE);
                            System.exit(1);
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a reservation");
                }

            }

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
                new WelcomeFrame(currentPassenger);
            }
        }
    }

    void fillTable() throws IOException, FileNotFoundException, ClassNotFoundException {
        reservations = currentPassenger.getReservations();
        columnNames = new Object[8];
        columnNames[0] = "Reservation ID";
        columnNames[1] = "Flight ID";
        columnNames[2] = "Seat ID";
        columnNames[3] = "From";
        columnNames[4] = "To";
        columnNames[5] = "Departure Date";
        columnNames[6] = "Arrival Date";
        columnNames[7] = "Status";
        data = new Object[reservations.size()][8];

        int i = 0;
        for (Reservation currentRes : reservations) {

            data[i][0] = currentRes.getReservationID();
            data[i][1] = currentRes.getFlight().getFlightNumber();
            data[i][2] = currentRes.getSeat().getSeatNumber();
            data[i][3] = currentRes.getFlight().getFrom();
            data[i][4] = currentRes.getFlight().getTo();
            data[i][5] = currentRes.getFlight().getDepartureDate();
            data[i][6] = currentRes.getFlight().getArrivalDate();
            data[i][7] = currentRes.getStatus();
            i++;
        }

    }

    private void setTableSettings() {
        table = new JTable(data, columnNames) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(238, 246, 255);
                Color whiteColor = Color.WHITE;
                if (!comp.getBackground().equals(getSelectionBackground())) {
                    Color c = (row % 2 == 1 ? alternateColor : whiteColor);
                    if (column == 7) {
                        if (data[row][7].toString().equalsIgnoreCase("Confirmed")) {
                            c = Color.GREEN;
                        } else if (data[row][7].toString().equalsIgnoreCase("Pending") || data[row][7].toString().equalsIgnoreCase("Changed")) {
                            c = Color.YELLOW;
                        } else if (data[row][7].toString().equalsIgnoreCase("Cancelled")) {
                            c = Color.RED;
                        }
                    }
                    comp.setBackground(c);
                    c = null;
                }
                return comp;
            }
        };
        table.setFillsViewportHeight(true);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(tableModel);
    }
}
