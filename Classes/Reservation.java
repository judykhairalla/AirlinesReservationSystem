package AirlinesReservationSystem;

import java.io.Serializable;

public class Reservation implements Serializable {

    private Flight Flight;
    private String userName;
    private Seat seat;
    private String reservationID;

    public static enum Status {
        Confirmed, Cancelled, Pending
    }
    Status status;

    public Reservation() {
        super();
    }

    public Reservation(String userName, Flight Flight, Seat seat, String reservationID,  Status status) {
        this.Flight = Flight;
        this.userName = userName;
        this.seat= seat;
        this.reservationID = reservationID;
        this.status = status;
    }

    public void setFlight(Flight f) {
        this.Flight = f;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSeatID(String seatID) {
        this.seat = seat;
    }

    public void setReservation(String reservationID) {
        this.reservationID = reservationID;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Seat getSeat() {
        return seat;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getUserName() {
        return userName;
    }

    public Flight getFlight() {
        return Flight;
    }

    public Status getStatus() {
        return status;
    }

}