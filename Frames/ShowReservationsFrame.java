package AirlinesReservationSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.*;

public class ShowReservationsFrame extends JFrame {

    private JButton backButton;
    private JButton cancelButton;
    private JTable table;
    private ArrayList<Reservation> reservations;
    private Passenger currentPassenger;

    Object[][] data;
    Object[] columnNames;

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
                JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);

            }
            setTableSettings();

        } catch ( InputMismatchException | IndexOutOfBoundsException e) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);
        }

        //------------------------- NORTH PANEL -------------------------------//
        // Title
        JPanel descriptionPanel = new JPanel();
        JLabel jLabel1 = new JLabel("My Reservations:");
        jLabel1.setFont(system.titleFont);
        descriptionPanel.add(jLabel1);

        //------------------------- SOUTH PANEL -------------------------------//
        JPanel actionsPanel = new JPanel();
        // Back button
        backButton = new JButton("Back");
        backButton.setFont(system.font);
        backButton.addActionListener(new ButtonListener());
        actionsPanel.add(backButton);

        // Cancel reservation button
        cancelButton = new JButton("Cancel Reservation");
        cancelButton.setFont(system.font);
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
                            Seat s = f.getSeat(SeatID);                
                            f.updateSeatRes(SeatID, true);
                            Admin a = new Admin();
                            a.updateFlight(FlightID, f);                           
                            currentPassenger.removeObject(String.valueOf(table.getSelectedRow()));
                            
                            fillTable();
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.setDataVector(data, columnNames);

                        } catch (IOException | ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a reservation");
                }

            }

            //------------------------- BACK BUTTON --------------------------//
            if (buttonPressed.equals(backButton)) {
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
                        } else if (data[row][7].toString().equalsIgnoreCase("Pending")) {
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