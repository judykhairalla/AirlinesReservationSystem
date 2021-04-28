package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;
import AirlinesReservationSystem.Main;
import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;

public class ContactUsFrame extends JFrame implements ActionListener {

    Passenger currentPassenger = new Passenger();

    private final JLabel fromLabel;
    private final JTextField fromField;
    private final JLabel toLabel;
    private final JTextField toField;
    private final JTextArea jTextArea;
    private final JButton sendButton;

    private final JMenuBar menuBar;
    private final JMenu backMenu;
    private final JMenu homeMenu;
    private final JMenu logoutMenu;

    public ContactUsFrame(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;

        //------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

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
        if (currentPassenger.getLoggedIn()) {
            logoutMenu.setIcon(Main.logoutIcon);
            logoutMenu.addMouseListener(new MouseEventer());
            menuBar.add(logoutMenu);
        }

        setJMenuBar(menuBar);

        //---------------------------- FROM/TO  -------------------------------//
        fromLabel = new JLabel("From");
        fromLabel.setFont(Main.font);
        getContentPane().add(fromLabel);
        fromLabel.setBounds(150, 75, 100, 25);

        if (currentPassenger.getLoggedIn()) {
            fromField = new JTextField(currentPassenger.getEmail());
            fromField.setEditable(false);
        } else {
            fromField = new JTextField("");
        }

        fromField.setFont(Main.font);
        getContentPane().add(fromField);
        fromField.setBounds(250, 75, 400, 25);

        toLabel = new JLabel("To");
        toLabel.setFont(Main.font);
        getContentPane().add(toLabel);
        toLabel.setBounds(150, 130, 100, 25);

        toField = new JTextField("miuAirLinesSupport@miuegypt.edu.eg");
        toField.setFont(Main.font);
        toField.setEditable(false);
        getContentPane().add(toField);
        toField.setBounds(250, 130, 400, 25);

        //-------------------------- EMAIL TEXT  ------------------------------//
        jTextArea = new JTextArea();
        jTextArea.setFont(Main.font);
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 185, 520, 250);

        //---------------------------- SEND BUTTON -------------------------------//
        sendButton = new JButton("Send");
        sendButton.setFont(Main.font);
        getContentPane().add(sendButton);
        sendButton.setBounds(335, 463, 100, 25);
        sendButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //-------------------------- SEND BUTTON -----------------------------//
        if (e.getSource() == sendButton) {
            String attempt = fromField.getText();

            if (currentPassenger.isValidEmailAddress(attempt)) {
                JOptionPane.showMessageDialog(this, "Your message was sent successfully, you'll hear \n from our support team soon.", "", INFORMATION_MESSAGE);
                dispose();
                new WelcomeFrame(currentPassenger);
            } else {
                JOptionPane.showMessageDialog(this, "Your email must be in form email@example.com.", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON -----------------------//
            if (e.getSource().equals(logoutMenu)) {
                dispose();
                currentPassenger.setLoggedIn(false);
                new PassengerLogInFrame();
            }

            if (e.getSource().equals(homeMenu)) {
                dispose();
                new WelcomeFrame(currentPassenger);
            }

            if (e.getSource().equals(backMenu)) {
                dispose();
                new WelcomeFrame(currentPassenger);
            }
        }
    }
}
