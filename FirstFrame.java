package AirlinesReservationSystem;

import AirlinesReservationSystem.PassengerFrames.PassengerLogInFrame;
import AirlinesReservationSystem.AdminFrames.AdminLogInFrame;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class FirstFrame extends JFrame implements ActionListener {

    private JLabel gifLabel;
    private Timer timer;
    private final JLabel chooseLabel;
    private final JButton adminButton;
    private final JButton passengerButton;

    public FirstFrame(Boolean prerun) {
        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setVisible(true);

        //---------------------------- GIF AT STARTUP -------------------------//
        if (prerun == true) {
            ImageIcon imageIcon1 = new ImageIcon("prerun.gif");
            gifLabel = new JLabel(imageIcon1);
            getContentPane().add(gifLabel);
            gifLabel.setBounds(0, 0, 800, 600);

            timer = new Timer(4800, this);
            timer.start();
        }

        //---------------------------- ADMIN OR PASSENGER -------------------------//
        chooseLabel = new JLabel("Pick Your Role");
        chooseLabel.setFont(Main.titleFont);
        getContentPane().add(chooseLabel);
        chooseLabel.setBounds(333, 50, 200, 20);

        ImageIcon imageIcon2 = new ImageIcon("admin.png");
        adminButton = new JButton("     Admin", imageIcon2);
        adminButton.setFont(Main.font);
        adminButton.setFocusable(false);
        getContentPane().add(adminButton);
        adminButton.setBounds(90, 100, 300, 200);
        adminButton.addActionListener(this);

        ImageIcon imageIcon3 = new ImageIcon("passenger.png");
        passengerButton = new JButton("  Passenger", imageIcon3);
        passengerButton.setFont(Main.font);
        passengerButton.setFocusable(false);
        getContentPane().add(passengerButton);
        passengerButton.setBounds(410, 100, 300, 200);
        passengerButton.addActionListener(this);

        if (prerun == true) {
            chooseLabel.setVisible(false);
            adminButton.setVisible(false);
            passengerButton.setVisible(false);
        }

        JLabel creditsLabel = new JLabel();
        creditsLabel.setFont(Main.creditsFont);
        creditsLabel.setForeground(new Color(153, 153, 153));
        creditsLabel.setText("<html><div style='text-align: center;'>" + "Prepared by: Basma Mohamed Dessouky, Judy Wadgy Fahmy Khairalla, Rana Raafat Kamal Al-Attar, Steven Albert Farahat"
                + "<br/> CSC240 Fall 2020" + "</html>");
        getContentPane().add(creditsLabel);
        creditsLabel.setBounds(175, 300, 450, 400);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == timer) {
            gifLabel.hide();
            timer.stop();
            chooseLabel.setVisible(true);
            adminButton.setVisible(true);
            passengerButton.setVisible(true);
            return;
        }

        if (e.getSource() == adminButton) {
            dispose();
            new AdminLogInFrame();
        }

        if (e.getSource() == passengerButton) {
            dispose();
            new PassengerLogInFrame();
        }
    }
}
