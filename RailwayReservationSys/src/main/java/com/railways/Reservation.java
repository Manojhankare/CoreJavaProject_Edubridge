package com.railways;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private Date reservationDate;
    private Date departureDate;
    private int seatNumber;
    private String status;  
    
    private Train train;  
    private Passenger passenger;
    
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation(int reservationId, Date reservationDate, Date departureDate, int seatNumber, String status,
			Train train, Passenger passenger) {
		super();
		this.reservationId = reservationId;
		this.reservationDate = reservationDate;
		this.departureDate = departureDate;
		this.seatNumber = seatNumber;
		this.status = status;
		this.train = train;
		this.passenger = passenger;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	} 

}
