package AirlinesReservationSystem;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class AdminLogInFrame extends JFrame implements ActionListener {

    Admin currentAdmin = new Admin();

    private final JLabel usernameLabel;
    private final JTextField usernameField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JCheckBox showPassswordBox;
    private final JButton signInButton;
    private final JButton backButton;

    public AdminLogInFrame() {
        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setVisible(true);

        //------------------------- USERNAME/PASSWORD -------------------------//
        usernameLabel = new JLabel("User Name");
        usernameLabel.setFont(system.font);
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(260, 100, 300, 30);

        usernameField = new JTextField("");
        usernameField.setFont(system.font);
        getContentPane().add(usernameField);
        usernameField.setBounds(260, 130, 300, 30);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(system.font);
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(260, 190, 300, 30);

        passwordField = new JPasswordField("");
        passwordField.setFont(system.font);
        getContentPane().add(passwordField);
        passwordField.setBounds(260, 220, 300, 30);

        showPassswordBox = new JCheckBox("Show Password");
        showPassswordBox.setFont(system.subFont);
        getContentPane().add(showPassswordBox);
        showPassswordBox.setBounds(260, 250, 300, 30);
        showPassswordBox.addActionListener(this);

        signInButton = new JButton("Sign In");
        signInButton.setFont(system.font);
        getContentPane().add(signInButton);
        signInButton.setBounds(260, 310, 135, 30);
        signInButton.addActionListener(this);

        //--------------------------- BACK BUTTON ----------------------------//
        ImageIcon imageIcon = new ImageIcon("back.png");
        backButton = new JButton(imageIcon);
        getContentPane().add(backButton);
        backButton.setBounds(0, 0, 30, 30);
        backButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        //--------------------------- BACK BUTTON ----------------------------//
        if (e.getSource() == backButton) {
            dispose();
            new FirstFrame(false);
        }

        //----------------------- SHOWPASSWORD BUTTON --------------------------//
        if (e.getSource() == showPassswordBox) {
            if (showPassswordBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        }

        //-------------------------- SIGNIN BUTTON ----------------------------//
        if (e.getSource() == signInButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                if (currentAdmin.logIn(username, password) == false) {
                    usernameField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(this, "Either username or password is incorrect.", "", ERROR_MESSAGE);
                } else {
                    currentAdmin = currentAdmin.getAdmin(username);
                    dispose();

                    new AddOrRemoveFlightsFrame(currentAdmin);
                }
            } catch (IOException | ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "ERROR", ERROR_MESSAGE);//     
        }
        }

    }
}