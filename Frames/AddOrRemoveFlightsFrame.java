package AirlinesReservationSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddOrRemoveFlightsFrame extends JFrame implements ActionListener {

    Admin currentAdmin = new Admin();

    private final JPanel jPanel;

    private final JLabel jLabel;
    private final JButton addFlightButton;
    private final JButton removeFlightButton;
    private final JButton logOutButton;

    public AddOrRemoveFlightsFrame(Admin currentAdmin){
        this.currentAdmin = currentAdmin;

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
        add(jPanel);
        //-------------------------- WELCOME LABEL ----------------------------//
        jLabel = new JLabel("Welcome " + currentAdmin.getFullName(), JLabel.CENTER);

        jLabel.setFont(system.titleFont);
        jPanel.add(jLabel);
        jLabel.setBounds(200, 30, 400, 30);

        //-------------------------- ICON BUTTONS ----------------------------//
        ImageIcon imageIcon1 = new ImageIcon("add.png");
        addFlightButton = new JButton("            Add a flight ", imageIcon1);
        addFlightButton.setFont(system.font);
        addFlightButton.setFocusable(false);
        jPanel.add(addFlightButton);
        addFlightButton.setBounds(200, 120, 400, 150);
        addFlightButton.addActionListener(this);

        ImageIcon imageIcon2 = new ImageIcon("remove.png");
        removeFlightButton = new JButton("         Cancel a flight ", imageIcon2);
        removeFlightButton.setFont(system.font);
        removeFlightButton.setFocusable(false);
        jPanel.add(removeFlightButton);
        removeFlightButton.setBounds(200, 280, 400, 150);
        removeFlightButton.addActionListener(this);

        //-------------------------- LOG OUT/BACK BUTTON --------------------------//
        ImageIcon imageIcon4 = new ImageIcon("Log-Out.png");
        logOutButton = new JButton(imageIcon4);
        logOutButton.setFocusable(false);
        jPanel.add(logOutButton);
        logOutButton.setBounds(0, 0, 30, 30);
        logOutButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //----------------------- ADD FLIGHT BUTTON -------------------------//
        if (e.getSource() == addFlightButton) {
            dispose();
            new AddFlightFrame(currentAdmin);
        }

        //----------------------- CANCEL FLIGHT BUTTON ------------------------//
        if (e.getSource() == removeFlightButton) {
            dispose();
            new RemoveFlightFrame(currentAdmin);
        }

        //-------------------------- LOG OUT BUTTON --------------------------//
        if (e.getSource() == logOutButton) {
            currentAdmin = new Admin();
            dispose();
            new AdminLogInFrame();
        }

    }
}