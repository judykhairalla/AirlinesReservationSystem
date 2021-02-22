package AirlinesReservationSystem;

import java.io.Serializable;

public class Seat implements Serializable {

    private String typeOfSeat;
    private String seatNumber;
    private Boolean isEmpty = true;
    private Reservation Reservation;

    public Seat() {
    }

    public Seat(String typeOfSeat, String seatNumber) {
        this.typeOfSeat = typeOfSeat;
        this.seatNumber = seatNumber;
    }

    public void setTypeOfSeat(String typeOfSeat) {
        this.typeOfSeat = typeOfSeat;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setReservation(Reservation r) {
        this.Reservation = r;
    }

    public String getTypeOfSeat() {
        return typeOfSeat;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }
    
    public Reservation getReservation(){
        return Reservation;
    }

    @Override
    public String toString() {
        return ("Seat Number: " + seatNumber + "\nType Of Seat: " + typeOfSeat + "\nIs Empty? " + isEmpty);
    }
}