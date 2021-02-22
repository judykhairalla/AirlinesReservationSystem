package AirlinesReservationSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Flight implements Serializable {

    private String flightNumber;
    private String from;
    private String to;
    private String departureDate;
    private String arrivalDate;
    private double price;
    private int numberOfSeats;
    private int seatsAvaliable;
    private ArrayList<Seat> seats;
    private Boolean hasDepartured = false;

    public Flight() {
        this.flightNumber = " ";
        this.from = " ";
        this.to = " ";
        this.departureDate = " ";
        this.arrivalDate = " ";
        this.price = 0;
        setNumberOfSeats(0);
    }

    public Flight(String flightNumber, String from, String to, int day, int month, int year, int depHour, int depMin, int arHour, int arMin, double price, int numberOfSeats) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        setDepartureDate(day, month, year, depHour, depMin);
        setArrivalDate(day, month, year, arHour, arMin);
        this.price = price;
        setNumberOfSeats(numberOfSeats);
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDepartureDate(int day, int month, int year, int hour, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm ");     //Defines the date format needed
        Calendar departure = new GregorianCalendar(year, month - 1, day, hour, minute);   //creates a date from Calendar class
        this.departureDate = sdf.format(departure.getTime());
    }

    public void setArrivalDate(int day, int month, int year, int hour, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
        Calendar arrival = new GregorianCalendar(year, month - 1, day, hour, minute);
        this.arrivalDate = sdf.format(arrival.getTime());
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        seats = new ArrayList<>(numberOfSeats);
        this.seatsAvaliable = this.numberOfSeats;
    }

    public void setSeatsAvaliable(int seatsAvaliable) {
        this.seatsAvaliable = seatsAvaliable;
    }

    public void setSeat(String typeOfSeat, String seatNumber) {
        Seat s = new Seat(typeOfSeat, seatNumber);
        seats.add(s);
    }

    public void updateSeat(String SeatID, Seat s) {
        for (int i = 0; i < seats.size(); i++) {
            if (SeatID.equals(seats.get(i).getSeatNumber())) {
                seats.set(i, s);
                break;
            }
        }
    }

    public void updateSeatRes(String SeatID, Boolean isEmpty) {

        for (int i = 0; i < seats.size(); i++) {
            if (SeatID.equals(seats.get(i).getSeatNumber())) {
                seats.get(i).setIsEmpty(isEmpty);
                this.seatsAvaliable++;
                break;
            }
        }
    }

    public Seat getSeat(String seatNumber) {
        Seat S = new Seat();

        S = null;

        for (int i = 0; i < seats.size(); i++) {

            if (seatNumber.equals(seats.get(i).getSeatNumber())) {

                S = seats.get(i);
            }
        }
        return S;
    }

    public void setHasDeparted(Boolean hasDepartured) {
        this.hasDepartured = hasDepartured;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public double getPrice() {
        return price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getSeatsAvaliable() {
        return seatsAvaliable;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public Boolean getHasDeparted() {
        return hasDepartured;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + "\nFrom: " + from + "\nTo: " + to + "\nDeparture Date: " + departureDate + "\nArrival Date: " + arrivalDate + "\nPrice: " + price + " £" + "\nSeats Avaliable: " + seatsAvaliable;
    }
}