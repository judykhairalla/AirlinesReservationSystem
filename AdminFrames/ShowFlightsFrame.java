package AirlinesReservationSystem.AdminFrames;

import AirlinesReservationSystem.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import javax.swing.table.*;

public class ShowFlightsFrame extends JFrame {

    private JTable table;
    private ArrayList<Flight> flights;
    Object[][] data;
    Object[] columnNames;
    DefaultTableModel tableModel;

    private JButton cancelButton;
    private JButton editButton;
    private JButton viewButton;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;

    private Admin currentAdmin;

    public ShowFlightsFrame(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;

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

        logoutMenu = new JMenu("Sign out");
        logoutMenu.setIcon(Main.logoutIcon);
        logoutMenu.addMouseListener(new MouseEventer());
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);
        //--------------------------- NORTH PANEL ----------------------------//
        // 1. Title Panel
        JPanel northPanel = new JPanel();
        JLabel titleLabel = new JLabel("Select a Flight");
        titleLabel.setFont(Main.titleFont);
        northPanel.add(titleLabel);

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

        cancelButton = new JButton("Cancel ");
        cancelButton.setFont(Main.font);
        cancelButton.addActionListener(new ButtonListener());
        actionsPanel.add(cancelButton);

        editButton = new JButton("Edit ");
        editButton.setFont(Main.font);
        editButton.addActionListener(new ButtonListener());
        actionsPanel.add(editButton);

        viewButton = new JButton("View ");
        viewButton.setFont(Main.font);
        viewButton.addActionListener(new ButtonListener());
        actionsPanel.add(viewButton);
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
            int choice;
            //------------------------ View BUTTON -------------------------//

            if (buttonPressed.equals(viewButton)) {
                if (table.getSelectedRow() != -1) {
                    Flight f = Admin.getFlight(flights.get(table.getSelectedRow()).getFlightNumber());
                    new ViewFlightFrame(f, currentAdmin);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a flight");
                }
            }

            //------------------------ Edit BUTTON -------------------------//
            if (buttonPressed.equals(editButton)) {
                if (table.getSelectedRow() != -1) {
                    Flight f = Admin.getFlight(flights.get(table.getSelectedRow()).getFlightNumber());
                    new EditFlightFrame(currentAdmin, f);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a flight");
                }
            }

            //------------------------ Cancel BUTTON -------------------------//
            if (buttonPressed.equals(cancelButton)) {
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
                            System.exit(1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a flight");
                    }
                }
            }
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON -----------------------//
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
                new AddOrRemoveFlightsFrame(currentAdmin);
            }
        }
    }
    
    private void fillTable() {
        columnNames = new Object[6];
        columnNames[0] = "Flight ID";
        columnNames[1] = "From";
        columnNames[2] = "To";
        columnNames[3] = "Departure Date";
        columnNames[4] = "Arrival Date";
        columnNames[5] = "Booked";
        data = new Object[flights.size()][6];
        int i = 0;
        for (Flight currentFlight : flights) {

            data[i][0] = currentFlight.getFlightNumber();
            data[i][1] = currentFlight.getFrom();
            data[i][2] = currentFlight.getTo();
            data[i][3] = currentFlight.getDepartureDate();
            data[i][4] = currentFlight.getArrivalDate();
            data[i][5] = 150 - currentFlight.getSeatsAvaliable() + "/150";
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
