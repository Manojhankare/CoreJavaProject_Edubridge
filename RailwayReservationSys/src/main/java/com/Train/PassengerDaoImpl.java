package com.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mainapp.*;
import com.railways.Passenger;

public class PassengerDaoImpl implements PassengerDao { 

	    @Override
	    public List<Passenger> getAllPassengers() throws SQLException {
	        List<Passenger> passengers = new ArrayList<>();
	        
	        try (Connection connection = DataConn.getConnection();
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery("SELECT * FROM passenger")) {
	            
	            while (resultSet.next()) {
	                Passenger passenger = new Passenger();
	                passenger.setPassengerId(resultSet.getInt("passengerId"));
	                passenger.setFirstName(resultSet.getString("firstName"));
	                passenger.setLastName(resultSet.getString("lastName"));
	                passenger.setEmail(resultSet.getString("email"));
	                passenger.setPhoneNumber(resultSet.getString("phoneNumber"));
	                passenger.setAge(resultSet.getInt("Age"));
	                passenger.setUsername(resultSet.getString("username"));
	                passenger.setPassword(resultSet.getString("password"));
	                passengers.add(passenger);
	            }
	        }
	        
	        return passengers;
	    }

	    @Override
	    public void addPassenger(Passenger passenger) throws SQLException {
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "INSERT INTO passenger (firstName, lastName, email, phoneNumber, age, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
	            
	            preparedStatement.setString(1, passenger.getFirstName());
	            preparedStatement.setString(2, passenger.getLastName());
	            preparedStatement.setString(3, passenger.getEmail());
	            preparedStatement.setString(4, passenger.getPhoneNumber());
	            preparedStatement.setInt(5, passenger.getAge());
	            preparedStatement.setString(6, passenger.getUsername());
	            preparedStatement.setString(7, passenger.getPassword());
	            
	            preparedStatement.executeUpdate();
	        }
	    }

	    @Override
	    public void updatePassenger(Passenger passenger) throws SQLException {
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement ps = connection.prepareStatement(
	                     "UPDATE passenger SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, age = ?, username = ?, password = ? WHERE passengerId = ?")) {
	            
	            ps.setString(1, passenger.getFirstName());
	            ps.setString(2, passenger.getLastName());
	            ps.setString(3, passenger.getEmail());
	            ps.setString(4, passenger.getPhoneNumber());
	            ps.setInt(5, passenger.getAge());
	            ps.setString(6, passenger.getUsername());
	            ps.setString(7, passenger.getPassword());
	            ps.setInt(8, passenger.getPassengerId());
	            
	            ps.executeUpdate();
	        }
	    }

	    @Override
	    public void deletePassenger(int passengerId) throws SQLException {
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement ps = connection.prepareStatement(
	                     "DELETE FROM passenger WHERE passengerId = ?")) {
	            
	            ps.setInt(1, passengerId);
	            
	            ps.executeUpdate();
	        }
	    }

	    @Override
	    public Passenger getPassengerById(int passengerId) throws SQLException {
	        Passenger passenger = null;
	        
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement ps = connection.prepareStatement("SELECT * FROM passenger WHERE passengerId = ?")) {
	            
	            ps.setInt(1, passengerId);
	            
	            try (ResultSet resultSet = ps.executeQuery()) {
	                if (resultSet.next()) {
	                    passenger = new Passenger();
	                    passenger.setPassengerId(resultSet.getInt("passengerId"));
	                    passenger.setFirstName(resultSet.getString("firstName"));
	                    passenger.setLastName(resultSet.getString("lastName"));
	                    passenger.setEmail(resultSet.getString("email"));
	                    passenger.setPhoneNumber(resultSet.getString("phoneNumber"));
	                    passenger.setAge(resultSet.getInt("age"));
	                    passenger.setUsername(resultSet.getString("username"));
	                    passenger.setPassword(resultSet.getString("password"));
	                }
	            }
	        }
	        
	        return passenger;
	    }

	    @Override
	    public Passenger getPassengerByUsername(String username) throws SQLException {
	        Passenger passenger = null;
	        
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement ps = connection.prepareStatement("SELECT * FROM passenger WHERE username = ?")) {
	            
	            ps.setString(1, username);
	            
	            try (ResultSet resultSet = ps.executeQuery()) {
	                if (resultSet.next()) {
	                    passenger = new Passenger();
	                    passenger.setPassengerId(resultSet.getInt("passengerId"));
	                    passenger.setFirstName(resultSet.getString("firstName"));
	                    passenger.setLastName(resultSet.getString("lastName"));
	                    passenger.setEmail(resultSet.getString("email"));
	                    passenger.setPhoneNumber(resultSet.getString("phoneNumber"));
	                    passenger.setAge(resultSet.getInt("age"));
	                    passenger.setUsername(resultSet.getString("username"));
	                    passenger.setPassword(resultSet.getString("password"));
	                }
	            }
	        }
	        
	        return passenger;
	    }

	    @Override
	    public boolean isUsernameTaken(String username) throws SQLException {
	        try (Connection connection = DataConn.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM passenger WHERE username = ?")) {
	            
	            preparedStatement.setString(1, username);
	            
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count > 0; // If count > 0, the username is taken
	                }
	            }
	        }
	        
	        return false; // Username is not taken
	    }
	    
	   
}
