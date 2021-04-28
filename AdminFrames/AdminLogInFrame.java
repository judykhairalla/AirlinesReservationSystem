package AirlinesReservationSystem.AdminFrames;

import AirlinesReservationSystem.*;
import javax.swing.*;
import java.awt.event.*;
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

    private final JMenuBar menuBar;
    private final JMenu backMenu;

    public AdminLogInFrame() {
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
        usernameLabel.setBounds(260, 100, 300, 30);

        usernameField = new JTextField("");
        usernameField.setFont(Main.font);
        getContentPane().add(usernameField);
        usernameField.setBounds(260, 130, 300, 30);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(Main.font);
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(260, 190, 300, 30);

        passwordField = new JPasswordField("");
        passwordField.setFont(Main.font);
        getContentPane().add(passwordField);
        passwordField.setBounds(260, 220, 300, 30);

        showPassswordBox = new JCheckBox("Show Password");
        showPassswordBox.setFont(Main.subFont);
        getContentPane().add(showPassswordBox);
        showPassswordBox.setBounds(260, 250, 300, 30);
        showPassswordBox.addActionListener(this);

        signInButton = new JButton("Sign In");
        signInButton.setFont(Main.font);
        getContentPane().add(signInButton);
        signInButton.setBounds(260, 310, 135, 30);
        signInButton.addActionListener(this);

    }

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
            if (Admin.logIn(username, password) == false) {
                usernameField.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(this, "Either username or password is incorrect.", "", ERROR_MESSAGE);
            } else {
                currentAdmin = Admin.getAdmin(username);
                dispose();

                new AddOrRemoveFlightsFrame(currentAdmin);
            }
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
