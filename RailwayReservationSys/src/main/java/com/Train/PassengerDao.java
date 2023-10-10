package com.Train;

import java.sql.SQLException;
import java.util.List;

import com.railways.Passenger;

public interface PassengerDao {
	
	List<Passenger> getAllPassengers() throws SQLException;

	void addPassenger(Passenger passenger) throws SQLException;

	void updatePassenger(Passenger passenger) throws SQLException;

	void deletePassenger(int passengerId) throws SQLException;

	Passenger getPassengerById(int passengerId) throws SQLException;

	Passenger getPassengerByUsername(String username) throws SQLException;

	boolean isUsernameTaken(String username) throws SQLException;
	
//	 public void viewPassengers() throws SQLException;
}
