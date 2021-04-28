package AirlinesReservationSystem.AdminFrames;

import AirlinesReservationSystem.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddOrRemoveFlightsFrame extends JFrame implements ActionListener {

    Admin currentAdmin = new Admin();

    private final JPanel jPanel;

    private final JLabel jLabel;
    private final JButton addFlightButton;
    private final JButton editFlightsButton;


    private final JMenuBar menuBar;

    private final JMenu logoutMenu;

    public AddOrRemoveFlightsFrame(Admin currentAdmin) {
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

        //---------------------------- MENU BAR ------------------------------//
        menuBar = new JMenuBar();

        logoutMenu = new JMenu("Sign out");

        logoutMenu.setIcon(Main.logoutIcon);
        logoutMenu.addMouseListener(new MouseEventer());
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        //-------------------------- WELCOME LABEL ----------------------------//
        jLabel = new JLabel("Welcome " + currentAdmin.getFullName(), JLabel.CENTER);

        jLabel.setFont(Main.titleFont);
        jPanel.add(jLabel);
        jLabel.setBounds(200, 30, 400, 30);

        //-------------------------- ICON BUTTONS ----------------------------//
        ImageIcon imageIcon1 = new ImageIcon("add.gif");
        addFlightButton = new JButton("            Add Flight ", imageIcon1);
        addFlightButton.setFont(Main.font);
        addFlightButton.setFocusable(false);
        jPanel.add(addFlightButton);
        addFlightButton.setBounds(200, 120, 400, 150);
        addFlightButton.addActionListener(this);

        ImageIcon imageIcon2 = new ImageIcon("edit.gif");
        editFlightsButton = new JButton("         Edit Flights ", imageIcon2);
        editFlightsButton.setFont(Main.font);
        editFlightsButton.setFocusable(false);
        jPanel.add(editFlightsButton);
        editFlightsButton.setBounds(200, 280, 400, 150);
        editFlightsButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //----------------------- ADD FLIGHT BUTTON -------------------------//
        if (e.getSource() == addFlightButton) {
            dispose();
            new AddFlightFrame(currentAdmin);
        }

        //----------------------- CANCEL FLIGHT BUTTON ------------------------//
        if (e.getSource() == editFlightsButton) {
            dispose();
            new ShowFlightsFrame(currentAdmin);
        }
    }
    
    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(logoutMenu)) {
                dispose();
                currentAdmin.setLoggedIn(false);
                new AdminLogInFrame();
            }
        }
    }
}
