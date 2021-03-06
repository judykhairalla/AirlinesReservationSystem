package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import javax.swing.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;

public class PassengerLogInFrame extends JFrame implements ActionListener {

    Passenger currentPassenger = new Passenger();

    private final JLabel usernameLabel;
    private final JTextField usernameField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JCheckBox showPassswordBox;
    private final JButton signInButton;
    private final JButton guestButton;
    private final JButton signUpButton;
    private final JLabel haveAccLabel;

    private final JMenuBar menuBar;
    private final JMenu backMenu;

    public PassengerLogInFrame() {
        //-------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setVisible(true);
        
        //---------------------------- MENU BAR ------------------------------//
        menuBar = new JMenuBar();

        backMenu = new JMenu("Back");
        backMenu.setIcon(Main.backIcon);
        backMenu.addMouseListener(new MouseEventer());
        menuBar.add(backMenu);

        setJMenuBar(menuBar);

        //------------------------- USERNAME/PASSWORD -------------------------//
        usernameLabel = new JLabel("User Name");
        usernameLabel.setFont(Main.font);
        getContentPane().add(usernameLabel);
        usernameLabel.setBounds(260, 105, 200, 25);

        usernameField = new JTextField("");
        usernameField.setFont(Main.font);
        getContentPane().add(usernameField);
        usernameField.setBounds(260, 130, 300, 30);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(Main.font);
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(260, 185, 200, 25);

        passwordField = new JPasswordField("");
        passwordField.setFont(Main.font);
        getContentPane().add(passwordField);
        passwordField.setBounds(260, 210, 300, 30);

        showPassswordBox = new JCheckBox("Show Password");
        showPassswordBox.setFont(Main.subFont);
        getContentPane().add(showPassswordBox);
        showPassswordBox.setBounds(260, 245, 200, 25);
        showPassswordBox.addActionListener(this);

        signInButton = new JButton("Sign In");
        signInButton.setFont(Main.font);
        signInButton.setFocusable(false);
        getContentPane().add(signInButton);
        signInButton.setBounds(260, 285, 100, 25);
        signInButton.addActionListener(this);

        //-------------------------- SIGNUP/GUEST ----------------------------// 
        haveAccLabel = new JLabel("Don't have an account?");
        haveAccLabel.setFont(Main.font);
        getContentPane().add(haveAccLabel);
        haveAccLabel.setBounds(260, 355, 200, 25);

        signUpButton = new JButton("Sign Up");

        signUpButton.setFocusable(false);
        signUpButton.setFont(Main.font);
        getContentPane().add(signUpButton);
        signUpButton.setBounds(260, 390, 100, 25);
        signUpButton.addActionListener(this);

        guestButton = new JButton("Continue as a Guest");
        guestButton.setFont(Main.font);
        guestButton.setFocusable(false);
        getContentPane().add(guestButton);
        guestButton.setBounds(370, 390, 200, 25);
        guestButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            if (Passenger.logIn(username, password) == false) {
                usernameField.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(this, "Either username or password is incorrect.", "", ERROR_MESSAGE);
            } else {
                currentPassenger = Passenger.getPassenger(username);
                dispose();
                new WelcomeFrame(currentPassenger);
            }
        }

        //-------------------------- GUEST BUTTON ----------------------------//
        if (e.getSource() == guestButton) {
            dispose();
            new WelcomeFrame(currentPassenger);
        }

        //-------------------------- SIGNUP BUTTON ----------------------------//
        if (e.getSource() == signUpButton) {
            dispose();
            new RegisterFrame(currentPassenger);
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(backMenu)) {
                dispose();
                new FirstFrame(false);
            }
        }
    }
}
