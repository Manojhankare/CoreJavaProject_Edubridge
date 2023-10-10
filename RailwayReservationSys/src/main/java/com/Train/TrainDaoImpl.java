package com.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mainapp.*;
import com.railways.Train;

public class TrainDaoImpl implements TrainDao {
    //private Connection connection;
	
	public List<Train> availableTrains() throws SQLException {
        List<Train> availableTrains = new ArrayList<>();

        try (Connection connection = DataConn.getConnection()) {
            // Implement your SQL query to retrieve available trains
            String sql = "SELECT * FROM train WHERE totalSeats > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Train train = new Train();
                train.setTrainId(resultSet.getInt("trainId"));
                train.setTrainName(resultSet.getString("train_nName"));
                train.setSourceStation(resultSet.getString("sourceStation"));
                train.setDestinationStation(resultSet.getString("destinationStation"));
                train.setDate(resultSet.getDate("date"));
                train.setTotalSeats(resultSet.getInt("totalSeats"));
             

                availableTrains.add(train);
            }
        }

        return availableTrains;
    }
	
	public void viewTrains() throws SQLException {
	    try {
	        TrainDaoImpl trainDao = new TrainDaoImpl();
	        List<Train>availableTrains = trainDao.getAllTrains(); // You need to implement this method in TrainDaoImpl
	        if (availableTrains().isEmpty()) {
	            System.out.println("No trains available at the moment.");
	        } else {
	            System.out.println("Available Trains:");
	            for (Train train : availableTrains) {
	                System.out.println(train); // Assuming you have a suitable toString method in the Train class
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    @Override
    public List<Train> getAllTrains() throws SQLException {
        List<Train> trains = new ArrayList<>();

        try (Connection connection = DataConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM train")) {

            // Print header
            System.out.println("**************************************************************************************************************");
            System.out.println("Train Number   Train Name      From       Destination     Journey date   Total Seats");
            System.out.println("**************************************************************************************************************");

            while (resultSet.next()) {
                int trainId = resultSet.getInt("trainId");
                String trainName = resultSet.getString("trainName");
                String sourceStation = resultSet.getString("sourceStation");
                String destinationStation = resultSet.getString("destinationStation");
                Date date = resultSet.getDate("date");
                int totalSeats = resultSet.getInt("totalSeats");

                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                // Print train information
                System.out.printf("%-5d %-13s %-11s %-13s %-15s %-6d%n", trainId, trainName,
                        sourceStation, destinationStation, formattedDate, totalSeats);

                Train train = new Train();
                train.setTrainId(trainId);
                train.setTrainName(trainName);
                train.setSourceStation(sourceStation);
                train.setDestinationStation(destinationStation);
                train.setDate(date);
                train.setTotalSeats(totalSeats);

                trains.add(train);
            }
        }

       return trains;
    }

    @Override
    public void addTrain(Train train) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO train (trainName, sourceStation, destinationStation, date, totalSeats) VALUES (?, ?, ?, ?, ?)")) {
            
            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getSourceStation());
            preparedStatement.setString(3, train.getDestinationStation());
            preparedStatement.setDate(4, new java.sql.Date(train.getDate().getTime()));
            preparedStatement.setInt(5, train.getTotalSeats());
            
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateTrain(Train train) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE train SET trainName = ?, sourceStation = ?, destinationStation = ?, date = ?, totalSeats = ? WHERE trainId = ?")) {
            
            preparedStatement.setString(1, train.getTrainName());
            preparedStatement.setString(2, train.getSourceStation());
            preparedStatement.setString(3, train.getDestinationStation());
            preparedStatement.setDate(4, new java.sql.Date(train.getDate().getTime()));
            preparedStatement.setInt(5, train.getTotalSeats());
            preparedStatement.setInt(6, train.getTrainId());
            
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteTrain(int trainId) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM train WHERE trainId = ?")) {
            
            preparedStatement.setInt(1, trainId);
            
            preparedStatement.executeUpdate();
        }
        
    }
    //@Override
    public Train getTrainById(int trainId) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM train WHERE trainId = ?")) {

            preparedStatement.setInt(1, trainId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Train train = new Train();
                    train.setTrainId(resultSet.getInt("trainId"));
                    train.setTrainName(resultSet.getString("trainName"));
                    train.setSourceStation(resultSet.getString("sourceStation"));
                    train.setDestinationStation(resultSet.getString("destinationStation"));
                    train.setDate(resultSet.getDate("date"));
                    train.setTotalSeats(resultSet.getInt("totalSeats"));
                    return train;
                }
            }
        }

        // Train not found
        return null;
    }}
