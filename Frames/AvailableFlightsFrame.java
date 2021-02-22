package AirlinesReservationSystem;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.*;


public class AvailableFlightsFrame extends JFrame {

    Passenger currentPassenger = new Passenger();

    private JComboBox fromList, toList;
    private JButton applyFilterButton;
    private JButton showAllButton;

    private JTable table;
    private ArrayList<Flight> flights;
    Object[][] data;
    Object[] columnNames;
    DefaultTableModel tableModel;

    private JButton backButton;
    private JButton reserveButton;

    public AvailableFlightsFrame(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);

        //--------------------------- NORTH PANEL ----------------------------//
        // 1. Progress Bar
        JPanel progressPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(25);
        stepsBar.setStringPainted(true);
        stepsBar.setString("Step 1/4: Choose your flight");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(system.font);
        progressPanel.add(stepsBar);

        // 2. Filters Panel
        JPanel descriptionPanel = new JPanel();

        fromList = new JComboBox(system.cities);
        toList = new JComboBox(system.cities);
        applyFilterButton = new JButton("Apply Filter");
        showAllButton = new JButton("Show All");

        descriptionPanel.add(new JLabel("From"));
        descriptionPanel.add(fromList);
        descriptionPanel.add(new JLabel("To"));
        descriptionPanel.add(toList);
        descriptionPanel.add(applyFilterButton);
        applyFilterButton.addActionListener(new ButtonListener());
        descriptionPanel.add(showAllButton);
        showAllButton.addActionListener(new ButtonListener());

        // 3. Setting The north panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(progressPanel, BorderLayout.NORTH);
        northPanel.add(descriptionPanel, BorderLayout.SOUTH);

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
            reserveButton = new JButton("Reserve");
            reserveButton.setFont(system.font);
            reserveButton.addActionListener(new ButtonListener());
            actionsPanel.add(backButton);
            actionsPanel.add(reserveButton);

            //------------------- ADDING PANELS TO FRAME ---------------------//
            setLayout(new BorderLayout());
            add(northPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(actionsPanel, BorderLayout.SOUTH);
            setVisible(true);

        } catch (IOException | ClassNotFoundException | InputMismatchException | IndexOutOfBoundsException e) {
           JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                          , "ERROR", ERROR_MESSAGE);
        }
       

    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            Object buttonPressed = event.getSource();

            //------------------------ RESERVE BUTTON -------------------------//
            if (buttonPressed.equals(reserveButton)) {
                if (table.getSelectedRow() != -1) {
                    if (currentPassenger.getLoggedIn()) {
                        dispose();
                        new SeatsGridFrame(flights.get(table.getSelectedRow()), currentPassenger);
                    } else {
                        Object[] options = {"Ok", "Sign In now"};
                        int choice = JOptionPane.showOptionDialog(null, "You have to be logged in to make a reservation.", "", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if (choice == 1) {
                            dispose();
                            new PassengerLogInFrame();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a flight");
                }
            }

            //------------------------- BACK BUTTON --------------------------//
            if (buttonPressed.equals(backButton)) {
                dispose();
                new WelcomeFrame(currentPassenger);
            }

            //------------------------- FILTER BUTTON --------------------------//
            if (buttonPressed.equals(applyFilterButton)) {
                try {
                    flights = Admin.getFlights(
                            fromList.getItemAt(fromList.getSelectedIndex()).toString(),
                            toList.getItemAt(toList.getSelectedIndex()).toString());

                    fillTable();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setDataVector(data, columnNames);

                    emptyTableChecker();

                } catch (IOException | ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);
                }
            }

            //----------------------- SHOWALL BUTTON --------------------------//
            if (buttonPressed.equals(showAllButton)) {
                try {
                    flights = Admin.getFlights();
                    fillTable();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setDataVector(data, columnNames);

                    emptyTableChecker();

                } catch (IOException | ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);//                }
            }

          }
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
            JOptionPane.showMessageDialog(null, "No available flights, "
                    + "Try adjusting your filter or try again later");
        }

    }
}