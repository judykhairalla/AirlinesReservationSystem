package AirlinesReservationSystem;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JTextField;

public class AddFlightFrame extends JFrame implements ActionListener {

    Admin currentAdmin = new Admin();

    private Container c;
    private JLabel title;
    private JTextField tTitle;
    private JLabel flightNumber;
    private JTextField tFlightNumber;
    private JLabel from;
    private JComboBox tFrom;
    private JLabel to;
    private JComboBox tTo;
    private JLabel departureDate;
    private JLabel arrivalDate;
    private JLabel day;
    private JComboBox tDepDay;
    private JLabel month;
    private JComboBox tDepMonth;
    private JComboBox tArDay;
    private JComboBox tArMonth;
    private JLabel time;
    private JLabel hour;
    private JLabel min;
    private JComboBox tHourDep;
    private JComboBox tMinDep;
    private JComboBox tHourAr;
    private JComboBox tMinAr;
    private JLabel price;
    private JTextField tPrice;
    private JButton add;
    private JButton back;
    private int numberOfSeats;

    private String dates[]
            = {"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25",
                "26", "27", "28", "29", "30",
                "31"};
    private String months[]
            = {"Jan", "feb", "Mar", "Apr",
                "May", "Jun", "July", "Aug",
                "Sup", "Oct", "Nov", "Dec"};

    private String hours[]
            = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23"};

    private String mins[]
            = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59"};


    public AddFlightFrame(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;

        //-------------------------- SETTING FRAME ----------------------------//
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("MIU Airlines ");

        c = getContentPane();
        c.setLayout(null);
        
        //----------------------------- TITLE ---------------------------------//
        title = new JLabel("Flight Details:");
        title.setFont(system.titleFont);
        title.setSize(300, 30);
        title.setLocation(320, 30);
        c.add(title);
        
        //------------------------- FLIGHT DETAILS ----------------------------//
        flightNumber = new JLabel("Flight ID: ");
        flightNumber.setFont(system.font);
        flightNumber.setSize(100, 20);
        flightNumber.setLocation(100, 100);
        c.add(flightNumber);

        tFlightNumber = new JTextField("");
        tFlightNumber.setFont(system.font);
        tFlightNumber.setSize(190, 30);
        tFlightNumber.setLocation(200, 100);
        c.add(tFlightNumber);

        from = new JLabel("From: ");
        from.setFont(system.font);
        from.setSize(100, 20);
        from.setLocation(100, 150);
        c.add(from);

        tFrom = new JComboBox(system.cities);
        tFrom.setFont(system.font);
        tFrom.setSize(150, 25);
        tFrom.setLocation(200, 150);
        c.add(tFrom);

        to = new JLabel("To: ");
        to.setFont(system.font);
        to.setSize(100, 20);
        to.setLocation(500, 150);
        c.add(to);

        tTo = new JComboBox(system.cities);
        tTo.setFont(system.font);
        tTo.setSize(150, 25);
        tTo.setLocation(600, 150);
        c.add(tTo);

        departureDate = new JLabel("Departure Date: ");
        departureDate.setFont(system.font);
        departureDate.setSize(140, 20);
        departureDate.setLocation(100, 210);
        c.add(departureDate);

        day = new JLabel("Day ");
        day.setFont(system.font);
        day.setSize(70, 20);
        day.setLocation(280, 210);
        c.add(day);

        tDepDay = new JComboBox(dates);
        tDepDay.setFont(system.font);
        tDepDay.setSize(70, 25);
        tDepDay.setLocation(350, 210);
        c.add(tDepDay);

        month = new JLabel("Month ");
        month.setFont(system.font);
        month.setSize(70, 20);
        month.setLocation(500, 210);
        c.add(month);

        tDepMonth = new JComboBox(months);
        tDepMonth.setFont(system.font);
        tDepMonth.setSize(80, 25);
        tDepMonth.setLocation(600, 210);
        c.add(tDepMonth);

        time = new JLabel("Departure Time: ");
        time.setFont(system.font);
        time.setSize(140, 20);
        time.setLocation(100, 250);
        c.add(time);

        hour = new JLabel("Hour ");
        hour.setFont(system.font);
        hour.setSize(70, 20);
        hour.setLocation(280, 250);
        c.add(hour);

        tHourDep = new JComboBox(hours);
        tHourDep.setFont(system.font);
        tHourDep.setSize(80, 25);
        tHourDep.setLocation(350, 250);
        c.add(tHourDep);

        min = new JLabel("Minutes ");
        min.setFont(system.font);
        min.setSize(100, 20);
        min.setLocation(500, 250);
        c.add(min);

        tMinDep = new JComboBox(mins);
        tMinDep.setFont(system.font);
        tMinDep.setSize(80, 25);
        tMinDep.setLocation(600, 250);
        c.add(tMinDep);

        arrivalDate = new JLabel("Arrival Date: ");
        arrivalDate.setFont(system.font);
        arrivalDate.setSize(140, 20);
        arrivalDate.setLocation(100, 310);
        c.add(arrivalDate);

        day = new JLabel("Day ");
        day.setFont(system.font);
        day.setSize(70, 20);
        day.setLocation(280, 310);
        c.add(day);

        tArDay = new JComboBox(dates);
        tArDay.setFont(system.font);
        tArDay.setSize(70, 25);
        tArDay.setLocation(350, 310);
        c.add(tArDay);

        month = new JLabel("Month ");
        month.setFont(system.font);
        month.setSize(70, 20);
        month.setLocation(500, 310);
        c.add(month);

        tArMonth = new JComboBox(months);
        tArMonth.setFont(system.font);
        tArMonth.setSize(80, 25);
        tArMonth.setLocation(600, 310);
        c.add(tArMonth);

        time = new JLabel("Arrival Time: ");
        time.setFont(system.font);
        time.setSize(140, 20);
        time.setLocation(100, 350);
        c.add(time);

        hour = new JLabel("Hour ");
        hour.setFont(system.font);
        hour.setSize(70, 20);
        hour.setLocation(280, 350);
        c.add(hour);

        tHourAr = new JComboBox(hours);
        tHourAr.setFont(system.font);
        tHourAr.setSize(80, 25);
        tHourAr.setLocation(350, 350);
        c.add(tHourAr);

        min = new JLabel("Minutes ");
        min.setFont(system.font);
        min.setSize(100, 20);
        min.setLocation(500, 350);
        c.add(min);

        tMinAr = new JComboBox(mins);
        tMinAr.setFont(system.font);
        tMinAr.setSize(80, 25);
        tMinAr.setLocation(600, 350);
        c.add(tMinAr);

        price = new JLabel("Ticket Price: ");
        price.setFont(system.font);
        price.setSize(140, 25);
        price.setLocation(100, 400);
        c.add(price);

        tPrice = new JTextField("");
        tPrice.setFont(system.font);
        tPrice.setSize(100, 30);
        tPrice.setLocation(280, 400);
        c.add(tPrice);

        //---------------------------- BUTTONS --------------------------------//
        add = new JButton("Add");
        add.setSize(80, 40);
        add.setLocation(405, 480);
        add.setFont(system.font);
        c.add(add);
        add.addActionListener(this);

        back = new JButton("Back");
        back.setSize(80, 40);
        back.setLocation(315, 480);
        back.setFont(system.font);
        c.add(back);
        back.addActionListener(this);

        setVisible(true);
    }

    Flight f = new Flight();

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //-------------------------- ADD BUTTON ------------------------------//
        if (e.getSource() == add) {
            try {
                String flightIDInfo = ((JTextField) tFlightNumber).getText();
                String fromInfo = tFrom.getSelectedItem().toString();
                String toInfo = tTo.getSelectedItem().toString();
                int depDayInfo = tDepDay.getSelectedIndex() + 1;
                int depMonthInfo = tDepMonth.getSelectedIndex() + 1;
                int arDayInfo = tArDay.getSelectedIndex() + 1;
                int arMonthInfo = tArMonth.getSelectedIndex() + 1;
                int depHourInfo = tHourDep.getSelectedIndex();
                int depMinInfo = tMinDep.getSelectedIndex();
                int arHourInfo = tHourAr.getSelectedIndex();
                int arMinInfo = tMinAr.getSelectedIndex();
                String priceDetails = tPrice.getText();
                Double priceInfo = 0.0;
                boolean proceed = true;

                if (flightIDInfo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the flight number.", "", ERROR_MESSAGE);

                    proceed = false;
                } else {

                    f.setFlightNumber(flightIDInfo);
                }

                if (fromInfo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please choose an airport.", "", ERROR_MESSAGE);
                    proceed = false;
                } else {
                    f.setFrom(fromInfo);
                }

                if (toInfo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please choose an airport.", "", ERROR_MESSAGE);
                    proceed = false;
                } else {
                    f.setTo(toInfo);

                }

                f.setDepartureDate(depDayInfo, depMonthInfo, 2021, depHourInfo, depMinInfo);
                f.setArrivalDate(arDayInfo, arMonthInfo, 2021, arHourInfo, arMinInfo);

                if (priceDetails.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the price of the ticket.", "", ERROR_MESSAGE);
                    proceed = false;
                } else {
                    priceInfo = Double.parseDouble(tPrice.getText());
                    f.setPrice(priceInfo);

                }

                try {
                    f.setPrice(priceInfo);
                } catch (NumberFormatException ev) {
                    tPrice.setText(" ");
                    JOptionPane.showMessageDialog(this, "Ticket price must be in digits.", "", ERROR_MESSAGE);
                    proceed = false;
                }
                if (proceed == true) {
                    f.setNumberOfSeats(150);
                    String SeatNo;
                    for (int coloumn = 65; coloumn <= 70; coloumn++) {
                        for (int row = 0; row < 25; row++) {
                            char ch = (char) coloumn;
                            SeatNo = Character.toString(ch);
                            SeatNo = SeatNo + Integer.toString(row);
                            if (row < 3) {
                                f.setSeat("First", SeatNo);
                            } else if (row >= 3 && row < 10) {
                                f.setSeat("Business", SeatNo);
                            } else if (row >= 10) {
                                f.setSeat("Economy", SeatNo);
                            }
                        }
                    }

                    currentAdmin.addObject(f);
                    JOptionPane.showMessageDialog(this, "Flight Added successfully!", "", INFORMATION_MESSAGE);
                    dispose();
                    new AddOrRemoveFlightsFrame(currentAdmin);
                }
            } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Sorry, it seems like there has been an error. \n\nPlease contact us on miuAirlinesSupport@miuegypt.edu.eg"
                                      , "IOERROR", ERROR_MESSAGE);
            }
        }

        //------------------------- BACK BUTTON --------------------------//
        if (e.getSource() == back) {
            dispose();
            new AddOrRemoveFlightsFrame(currentAdmin);
        }
    }

}