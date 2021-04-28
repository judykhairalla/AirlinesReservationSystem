package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
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

    private JButton reserveButton;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    public AvailableFlightsFrame(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
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
        if (currentPassenger.getLoggedIn()) {
            logoutMenu.setIcon(Main.logoutIcon);
            logoutMenu.addMouseListener(new MouseEventer());
            menuBar.add(logoutMenu);
        }
        setJMenuBar(menuBar);

        //--------------------------- NORTH PANEL ----------------------------//
        // 1. Progress Bar
        JPanel progressPanel = new JPanel();
        JProgressBar stepsBar = new JProgressBar();
        stepsBar.setValue(25);
        stepsBar.setStringPainted(true);
        stepsBar.setString("Step 1/4: Choose your flight");
        stepsBar.setPreferredSize(new Dimension(700, 30));
        stepsBar.setFont(Main.font);
        progressPanel.add(stepsBar);

        // 2. Filters Panel
        JPanel descriptionPanel = new JPanel();

        fromList = new JComboBox(Main.cities);
        toList = new JComboBox(Main.cities);
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
        flights = Admin.getFlights();
        fillTable();
        setTableSettings();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        emptyTableChecker();
        //------------------------- SOUTH PANEL --------------------------//
        // Action Panel
        JPanel actionsPanel = new JPanel();
        reserveButton = new JButton("Reserve");
        reserveButton.setFont(Main.font);
        reserveButton.addActionListener(new ButtonListener());
        actionsPanel.add(reserveButton);
        //------------------- ADDING PANELS TO FRAME ---------------------//
        setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);
        setVisible(true);

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

            //------------------------- FILTER BUTTON --------------------------//
            if (buttonPressed.equals(applyFilterButton)) {
                flights = Admin.getFlights(
                        fromList.getItemAt(fromList.getSelectedIndex()).toString(),
                        toList.getItemAt(toList.getSelectedIndex()).toString());
                fillTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setDataVector(data, columnNames);
                emptyTableChecker();
            }

            //----------------------- SHOWALL BUTTON --------------------------//
            if (buttonPressed.equals(showAllButton)) {
                flights = Admin.getFlights(); //                }
                fillTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setDataVector(data, columnNames);
                emptyTableChecker();

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
