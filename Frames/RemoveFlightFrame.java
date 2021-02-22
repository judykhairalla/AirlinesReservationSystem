package AirlinesReservationSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class RemoveFlightFrame extends JFrame {

    private JTable table;
    private ArrayList<Flight> flights;
    Object[][] data;
    Object[] columnNames;
    DefaultTableModel tableModel;

    private JButton backButton;
    private JButton cancelButton;

    private Admin currentAdmin;

    public RemoveFlightFrame(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;

        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        //--------------------------- NORTH PANEL ----------------------------//
        // 1. Title Panel
        JPanel northPanel = new JPanel();
        JLabel titleLabel = new JLabel("Select a Flight");
        titleLabel.setFont(system.titleFont);
        northPanel.add(titleLabel);

        //------------------------- CENTER PANEL --------------------------//
        // Flights Table
        try {
            flights = Admin.getFlights();
            fillTable();
            setTableSettings();
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            emptyTableChecker();

            //------------------------- SOUTH PANEL --------------------------//
            // Action Panel
            JPanel actionsPanel = new JPanel();

            backButton = new JButton("Back");
            backButton.setFont(system.font);
            backButton.addActionListener(new ButtonListener());
            actionsPanel.add(backButton);

            cancelButton = new JButton("Cancel Flight");
            cancelButton.setFont(system.font);
            cancelButton.addActionListener(new ButtonListener());
            actionsPanel.add(cancelButton);

            //------------------- ADDING PANELS TO FRAME ---------------------//
            setLayout(new BorderLayout());
            add(northPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(actionsPanel, BorderLayout.SOUTH);
            setVisible(true);

        } catch (InputMismatchException | IndexOutOfBoundsException | IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                     "ERROR", ERROR_MESSAGE);
        }

    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            Object buttonPressed = event.getSource();

            //------------------------ RESERVE BUTTON -------------------------//
            if (buttonPressed.equals(cancelButton)) {
                int choice;
                if (table.getSelectedRow() != -1) {
                    choice = JOptionPane.showConfirmDialog(null, "Do you want really want to cancel?",
                            "Confirmation Message", JOptionPane.YES_NO_OPTION);

                    // Confirming cancellation
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            Flight f = Admin.getFlight(flights.get(table.getSelectedRow()).getFlightNumber());
                            Passenger currentP = new Passenger();

                            ArrayList<Seat> flightSeats = f.getSeats();
                            for (int i = 0; i < flightSeats.size(); i++) {
                                if (flightSeats.get(i).getIsEmpty() == false) {
                                    Reservation r = flightSeats.get(i).getReservation();
                                    currentP = currentP.getPassenger(r.getUserName());
                                    r.setStatus(Reservation.Status.Cancelled);
                                    currentP.updateReservation(r.getReservationID(), r);
                                }
                            }

                            currentAdmin.removeObject(flights.get(table.getSelectedRow()).getFlightNumber());
                            flights = Admin.getFlights();
                            fillTable();
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.setDataVector(data, columnNames);

                            emptyTableChecker();
                        } catch (IOException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg",
                                     "ERROR", ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a flight");
                }
            }

            //------------------------- BACK BUTTON --------------------------//
            dispose();
            new AddOrRemoveFlightsFrame(currentAdmin);
        }

    }

    private void fillTable() {
        columnNames = new Object[5];
        columnNames[0] = "Flight ID";
        columnNames[1] = "From";
        columnNames[2] = "To";
        columnNames[3] = "Departure Date";
        columnNames[4] = "Arrival Date";

        data = new Object[flights.size()][6];
        int i = 0;
        for (Flight currentFlight : flights) {

            data[i][0] = currentFlight.getFlightNumber();
            data[i][1] = currentFlight.getFrom();
            data[i][2] = currentFlight.getTo();
            data[i][3] = currentFlight.getDepartureDate();
            data[i][4] = currentFlight.getArrivalDate();

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

        tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(tableModel);
    }

    void emptyTableChecker() {
        if (table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No available flights");
        }

    }
}
