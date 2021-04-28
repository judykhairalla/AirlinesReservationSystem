package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame implements ActionListener {

    Passenger currentPassenger = new Passenger();

    private final JPanel jPanel;
    private final JScrollPane jScrollPane;

    private JLabel jLabel;
    private JButton showFlightsButton;
    private JButton pastReservationsButton;
    private JButton contactUsButton;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu logoutMenu;
    private final JMenu contactUsMenu;

    public WelcomeFrame(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("MIU Airlines ");

        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setPreferredSize(new Dimension(600, 550));
        jScrollPane = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(jScrollPane);
        jScrollPane.setPreferredSize(new Dimension(600, 420));

        //---------------------------- MENU BAR ------------------------------//
        menuBar = new JMenuBar();

        logoutMenu = new JMenu("Sign out");
        backMenu = new JMenu("Back");
        
        if (currentPassenger.getLoggedIn()) {
            logoutMenu.setIcon(Main.logoutIcon);
            logoutMenu.addMouseListener(new MouseEventer());
            menuBar.add(logoutMenu);
        } else {
            backMenu.setIcon(Main.backIcon);
            backMenu.addMouseListener(new MouseEventer());
            menuBar.add(backMenu);
        }

        menuBar.add(Box.createHorizontalGlue());

        contactUsMenu = new JMenu("Contact us");
        contactUsMenu.setIcon(Main.contactUsIcon);
        contactUsMenu.addMouseListener(new MouseEventer());
        menuBar.add(contactUsMenu);

        
        setJMenuBar(menuBar);
        //-------------------------- WELCOME LABEL ----------------------------//
        if (currentPassenger.getLoggedIn()) {
            jLabel = new JLabel("Welcome " + currentPassenger.getFullName(), JLabel.CENTER);
        } else {
            jLabel = new JLabel("Welcome Guest", JLabel.CENTER);
        }
        jLabel.setFont(Main.titleFont);
        jPanel.add(jLabel);
        jLabel.setBounds(200, 30, 400, 30);

        //-------------------------- ICON BUTTONS ----------------------------//
        ImageIcon imageIcon1 = new ImageIcon("airplane.gif");
        showFlightsButton = new JButton("Show Avaliable Flights", imageIcon1);
        showFlightsButton.setFont(Main.font);
        showFlightsButton.setFocusable(false);
        jPanel.add(showFlightsButton);
        showFlightsButton.setBounds(200, 70, 400, 150);
        showFlightsButton.addActionListener(this);

        ImageIcon imageIcon2 = new ImageIcon("ticket.gif");
        pastReservationsButton = new JButton("   Past Reservations   ", imageIcon2);
        pastReservationsButton.setFont(Main.font);
        pastReservationsButton.setFocusable(false);
        jPanel.add(pastReservationsButton);
        pastReservationsButton.setBounds(200, 230, 400, 150);
        pastReservationsButton.addActionListener(this);

        ImageIcon imageIcon3 = new ImageIcon("email.gif");
        contactUsButton = new JButton("      Contact Us       ", imageIcon3);
        contactUsButton.setFont(Main.font);
        contactUsButton.setFocusable(false);
        jPanel.add(contactUsButton);
        contactUsButton.setBounds(200, 390, 400, 150);
        contactUsButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //----------------------- SHOWFLIGHTS BUTTON -------------------------//
        if (e.getSource() == showFlightsButton) {
            dispose();
            new AvailableFlightsFrame(currentPassenger);
        }

        //------------------------ RESERVATIONS BUTTON ------------------------//
        if (e.getSource() == pastReservationsButton) {
            // Make sure that the user is already logged in
            if (currentPassenger.getLoggedIn()) {
                dispose();
                new ShowReservationsFrame(currentPassenger);
            } else {
                Object[] options = {"Ok", "Sign In now"};
                int choice = JOptionPane.showOptionDialog(this, "You have to be logged in to view past reservations.", "", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (choice == 1) {
                    dispose();
                    new PassengerLogInFrame();
                }
            }
        }

        //------------------------- CONTACTUS BUTTON -------------------------//
        if (e.getSource() == contactUsButton) {
            dispose();
            new ContactUsFrame(currentPassenger);
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

            if (e.getSource().equals(backMenu)) {
                dispose();
                new PassengerLogInFrame();
            }

            if (e.getSource().equals(contactUsMenu)) {
                dispose();
                new ContactUsFrame(currentPassenger);
            }
        }
    }
}
