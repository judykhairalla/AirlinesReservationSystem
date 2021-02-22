package AirlinesReservationSystem;

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
    private final JButton backButton;
    
        public ContactUsFrame(Passenger currentPassenger){ 
        this.currentPassenger = currentPassenger;

        //------------------------- SETTING FRAME ----------------------------//
        setTitle("MIU Airlines ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        
        //---------------------------- FROM/TO  -------------------------------//
        fromLabel = new JLabel("From");
        fromLabel.setFont(system.font);
        getContentPane().add(fromLabel);
        fromLabel.setBounds(150, 75, 100, 25);
    
        if(currentPassenger.getLoggedIn()){
            fromField =  new JTextField(currentPassenger.getEmail());
            fromField.setEditable(false);
        }
        else
            fromField =  new JTextField("");
        
        fromField.setFont(system.font);
        getContentPane().add(fromField);
        fromField.setBounds(250, 75, 400, 25);
        
        toLabel = new JLabel("To");
        toLabel.setFont(system.font);
        getContentPane().add(toLabel);
        toLabel.setBounds(150, 130, 100, 25);
        
        toField = new JTextField("miuAirLinesSupport@miuegypt.edu.eg");
        toField.setFont(system.font);
        toField.setEditable(false);
        getContentPane().add(toField);
        toField.setBounds(250, 130, 400, 25);
        
        //-------------------------- EMAIL TEXT  ------------------------------//
        jTextArea = new JTextArea();
        jTextArea.setFont(system.font);
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 185, 520, 250);
        
        //---------------------------- SEND BUTTON -------------------------------//
        sendButton = new JButton("Send"); 
        sendButton.setFont(system.font);
        getContentPane().add(sendButton);
        sendButton.setBounds(335, 463, 100, 25);
        sendButton.addActionListener(this);
       
        //--------------------------- BACK BUTTON ----------------------------//
        ImageIcon imageIcon = new ImageIcon("back.png");        
        backButton = new JButton(imageIcon);
        backButton.setFocusable(false);
        getContentPane().add(backButton);
        backButton.setBounds(0, 0, 30, 30);
        backButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //--------------------------- BACK BUTTON ----------------------------//
        if (e.getSource() == backButton)
        {
            dispose();
            new WelcomeFrame(currentPassenger);
        }
        
        //-------------------------- SEND BUTTON -----------------------------//
        if (e.getSource() == sendButton ) {  
            String attempt = fromField.getText();

            if(currentPassenger.isValidEmailAddress(attempt)){
                JOptionPane.showMessageDialog(this, "Your message was sent successfully, you'll hear \n from our support team soon.", "", INFORMATION_MESSAGE);
                dispose();
                new WelcomeFrame(currentPassenger);
            }
            else
                JOptionPane.showMessageDialog(this, "Your email must be in form email@example.com.", "", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}