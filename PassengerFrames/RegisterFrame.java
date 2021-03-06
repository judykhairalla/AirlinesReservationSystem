package AirlinesReservationSystem.PassengerFrames;

import AirlinesReservationSystem.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;

public class RegisterFrame extends JFrame implements ActionListener {

    Passenger currentPassenger = new Passenger();

    private final JPanel jPanel;
    private final JScrollPane jScrollPane1;

    private final JLabel usernamelabel;
    private final JTextField usernameField;
    private final JLabel passwordLabel;
    private final JPasswordField passwordField;
    private final JCheckBox showPasswordBox;
    private final JLabel firstNameLabel;
    private final JTextField firstNameField;
    private final JLabel lastNameLabel;
    private final JTextField lastNameField;
    private final JLabel ageLabel;
    private final JTextField ageField;
    private final JLabel phoneLabel;
    private final JTextField phoneField;
    private final JLabel emailLabel;
    private final JTextField emailField;
    private final JLabel genderLabel;
    private final JRadioButton femaleRadioButton;
    private final JRadioButton maleRadioButton;
    private final ButtonGroup genderButtonGroup;
    private final JButton registerButton;

    private final JMenuBar menuBar;
    private final JMenu backMenu;

    public RegisterFrame(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;

        //-------------------------- SETTING FRAME ----------------------------//
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("MIU Airlines ");

        // Scroll Panel
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setPreferredSize(new Dimension(600, 500));
        jScrollPane1 = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(jScrollPane1);
        jScrollPane1.setPreferredSize(new Dimension(600, 420));

        //---------------------------- MENU BAR ------------------------------//
        menuBar = new JMenuBar();

        backMenu = new JMenu("Back");
        backMenu.setIcon(Main.backIcon);
        backMenu.addMouseListener(new MouseEventer());
        menuBar.add(backMenu);

        setJMenuBar(menuBar);

        //---------------------------- USER INFO ------------------------------//
        usernamelabel = new JLabel("User Name");
        usernamelabel.setFont(Main.font);
        jPanel.add(usernamelabel);
        usernamelabel.setBounds(210, 55, 200, 25);

        usernameField = new JTextField("");
        usernameField.setFont(Main.font);
        jPanel.add(usernameField);
        usernameField.setBounds(330, 55, 200, 25);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(Main.font);
        jPanel.add(passwordLabel);
        passwordLabel.setBounds(210, 105, 200, 25);

        passwordField = new JPasswordField("");
        passwordField.setFont(Main.font);
        jPanel.add(passwordField);
        passwordField.setBounds(330, 105, 200, 25);

        showPasswordBox = new JCheckBox("Show Password");
        showPasswordBox.setFont(Main.subFont);
        jPanel.add(showPasswordBox);
        showPasswordBox.setBounds(550, 105, 200, 25);
        showPasswordBox.addActionListener(this);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(Main.font);
        jPanel.add(firstNameLabel);
        firstNameLabel.setBounds(210, 155, 200, 25);

        firstNameField = new JTextField("");
        firstNameField.setFont(Main.font);
        jPanel.add(firstNameField);
        firstNameField.setBounds(330, 155, 200, 25);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(Main.font);
        jPanel.add(lastNameLabel);
        lastNameLabel.setBounds(210, 205, 200, 25);

        lastNameField = new JTextField("");
        lastNameField.setFont(Main.font);
        jPanel.add(lastNameField);
        lastNameField.setBounds(330, 205, 200, 25);

        ageLabel = new JLabel("Age");
        ageLabel.setFont(Main.font);
        jPanel.add(ageLabel);
        ageLabel.setBounds(210, 255, 200, 25);

        ageField = new JTextField("");
        ageField.setFont(Main.font);
        jPanel.add(ageField);
        ageField.setBounds(330, 255, 200, 25);

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(Main.font);
        jPanel.add(phoneLabel);
        phoneLabel.setBounds(210, 305, 200, 25);

        phoneField = new JTextField("");
        phoneField.setFont(Main.font);
        jPanel.add(phoneField);
        phoneField.setBounds(330, 305, 200, 25);

        emailLabel = new JLabel("Email Adress");
        emailLabel.setFont(Main.font);
        jPanel.add(emailLabel);
        emailLabel.setBounds(210, 355, 200, 25);

        emailField = new JTextField("");
        emailField.setFont(Main.font);
        jPanel.add(emailField);
        emailField.setBounds(330, 355, 200, 25);

        genderLabel = new JLabel("Gender");
        genderLabel.setFont(Main.font);
        jPanel.add(genderLabel);
        genderLabel.setBounds(210, 405, 200, 25);

        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setFont(Main.font);
        jPanel.add(femaleRadioButton);
        femaleRadioButton.setBounds(330, 405, 100, 25);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setFont(Main.font);
        jPanel.add(maleRadioButton);
        maleRadioButton.setBounds(430, 405, 100, 25);

        genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(femaleRadioButton);
        genderButtonGroup.add(maleRadioButton);

        registerButton = new JButton("Register");
        registerButton.setFont(Main.font);
        jPanel.add(registerButton);
        registerButton.setBounds(350, 470, 100, 25);
        registerButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //---------------------- SHOWPASSWORD BUTTON --------------------------//
        if (e.getSource() == showPasswordBox) {
            if (showPasswordBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        }

        //------------------------- REGISTER BUTTON ---------------------------//
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String age = ageField.getText();
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            char gender = 'Z';
            if (femaleRadioButton.isSelected()) {
                gender = 'F';
            } else if (maleRadioButton.isSelected()) {
                gender = 'M';
            }
            Boolean proceed = true;
            // Approving and rejecting data fields
            if (Passenger.AuthenticateUserName(username) == false || username.equals("") == true) {
                usernameField.setText("");
                JOptionPane.showMessageDialog(this, "User Name is taken or invalid.", "", ERROR_MESSAGE);
                proceed = false;
            } else {
                currentPassenger.setUserName(username);
            }

            if (currentPassenger.setPassword(password) == false) {
                passwordField.setText("");
                JOptionPane.showMessageDialog(this, "Password must be minimum of 8 chars, contains at least 1 digit,"
                        + "\n1 upper alpha char, a special char, and doesn't contain any space.", "", ERROR_MESSAGE);
                proceed = false;
            } else {
                currentPassenger.setPassword(password);
            }

            if (firstName.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter your first name.", "", ERROR_MESSAGE);
                proceed = false;
            } else {
                currentPassenger.setFIrstName(firstName);
            }

            if (lastName.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter your last name.", "", ERROR_MESSAGE);
                proceed = false;
            } else {
                currentPassenger.setLastName(lastName);
            }

            try {
                currentPassenger.setAge(Integer.parseInt(age));
            } catch (NumberFormatException ev) {
                ageField.setText("");
                JOptionPane.showMessageDialog(this, "Age must be in digits.", "", ERROR_MESSAGE);
                proceed = false;
            }

            if (currentPassenger.setPhoneNumber(phoneNumber) == false) {
                phoneField.setText("");
                JOptionPane.showMessageDialog(this, "Phone Number is invalid.", "", ERROR_MESSAGE);
                proceed = false;
            }

            if (currentPassenger.setEmail(email) == false) {
                emailField.setText("");
                JOptionPane.showMessageDialog(this, "Your email must be in form email@example.com", "", ERROR_MESSAGE);
                proceed = false;
            }

            if (gender == 'Z') {
                JOptionPane.showMessageDialog(this, "Please select gender.", "", ERROR_MESSAGE);
                proceed = false;
            } else {
                currentPassenger.setGender(gender);
            }

            if (proceed == true) {
                currentPassenger.addObject(currentPassenger);
                currentPassenger = currentPassenger.getPassenger(username);

                dispose();
                new PassengerLogInFrame();
            }
        }
    }

    private class MouseEventer extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            //-------------------------- LOG OUT BUTTON --------------------------//
            if (e.getSource().equals(backMenu)) {
                dispose();
                new PassengerLogInFrame();
            }
        }

    }

}
