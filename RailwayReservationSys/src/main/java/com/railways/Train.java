package com.railways;

import java.util.Date;

public class Train {
    private int trainId;
    private String trainName;
    private String sourceStation;
    private String destinationStation;
    private Date date; // Use java.util.Date for date fields
    private int totalSeats;
    
	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Train(int trainId, String trainName, String sourceStation, String destinationStation, Date date, int totalSeats) {
		super();
		this.trainId = trainId;
		this.trainName = trainName;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.date = date;
		this.totalSeats = totalSeats;
	}
	public Train( String trainName, String sourceStation, String destinationStation, Date date, int totalSeats) {
		super();
		//this.trainId = trainId;
		this.trainName = trainName;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.date = date;
		this.totalSeats = totalSeats;
	}
	public int getTrainId() {
		return trainId;
	}
	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getSourceStation() {
		return sourceStation;
	}
	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	@Override
	public String toString() {
		return "Train [trainId=" + trainId + ", trainName=" + trainName + ", sourceStation=" + sourceStation
				+ ", destinationStation=" + destinationStation + ", date=" + date + ", totalSeats=" + totalSeats + "]";
	}

     
}
