package com.mainapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.InputMismatchException;

import com.Train.PassengerDao;
import com.Train.PassengerDaoImpl;
import com.Train.TrainDaoImpl;
import com.railways.Passenger;
import com.railways.Train;

public class Operations  {

    

	int trainId;
    PassengerDao pdao = new PassengerDaoImpl();
    Scanner scanner = new Scanner(System.in);

    public int checkTrainExistence(int trainId) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM train WHERE trainID = ?")) {

            ps.setInt(1, trainId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                System.out.println("Train ID: " + resultSet.getInt("trainId"));
                System.out.println("Train Name: " + resultSet.getString("trainName"));
                System.out.println("Source Station: " + resultSet.getString("sourceStation"));
                System.out.println("Destination Station: " + resultSet.getString("destinationStation"));
                System.out.println("Date: " + resultSet.getDate("date"));
                System.out.println("Total Seats: " + resultSet.getInt("totalSeats"));
                return 1; // Return 1 if the train exists
            } else {
                System.out.println("Train with ID " + trainId + " not found.");
                return 0; // Return 0 if not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for error handling
        }
    }

    public Date getDepartureDate(int trainId) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT date FROM train WHERE trainID = ?")) {

            ps.setInt(1, trainId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDate("date");
            } else {
                System.out.println("Train with ID " + trainId + " not found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void bookTicket() {
       System.out.println("Enter the Train Number to Reserve ticket :  ");
       
       try {
           trainId = scanner.nextInt();
       } catch (InputMismatchException e) {
           System.out.println("Invalid input. Please enter a valid train number.");
           scanner.nextLine(); // Consume the invalid input
           bookTicket(); // Call the method recursively to try again
           return;
       }
        
        try {
            if (checkTrainExistence(trainId) == 0) {
                System.out.println("Train number doesn't exist");
                return;
            }

            Date departureDate = getDepartureDate(trainId);
            if (departureDate == null) {
                System.out.println("Booking failed. Train not found.");
                return;
            }

            System.out.print("Enter username to confirm ticket: ");
            String passengerUsername = scanner.next();

            Passenger passenger = pdao.getPassengerByUsername(passengerUsername);

            if (passenger != null) {
                int passengerId = passenger.getPassengerId();
                int seatNumber = calculateNextAvailableSeat(trainId);

                if (seatNumber == -1) {
                    System.out.println("Booking failed. All seats are reserved.");
                    return;
                }

                String insertReservationQuery = "INSERT INTO reservation (passengerId, trainId, reservationDate, departureDate, seatNumber, status) VALUES (?, ?, NOW(), ?, ?, ?)";

                try (Connection connection = DataConn.getConnection();
                     PreparedStatement insertReservationStmt = connection.prepareStatement(insertReservationQuery, Statement.RETURN_GENERATED_KEYS)) {

                    insertReservationStmt.setInt(1, passengerId);
                    insertReservationStmt.setInt(2, trainId);
                    insertReservationStmt.setDate(3, new java.sql.Date(departureDate.getTime()));
                    insertReservationStmt.setInt(4, seatNumber);
                    insertReservationStmt.setString(5, "Booked");

                    int rowsAffected = insertReservationStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = insertReservationStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int reservationId = generatedKeys.getInt(1);
                            System.out.println("Booking confirmed your seat no is  " + seatNumber + " on train " + trainId);
                            System.out.println("Reservation ID: " + reservationId);
                            
                         // After successfully booking a ticket, decrement available seats in the train table
                            String updateTrainQuery = "UPDATE train SET totalSeats = totalSeats - 1 WHERE trainId = ?";

                            try (PreparedStatement updateTrainStmt = connection.prepareStatement(updateTrainQuery)) {
                                updateTrainStmt.setInt(1, trainId);
                                updateTrainStmt.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.out.println("Failed to update available seats in the train table.");
                            }

                        } else {
                            System.out.println("Failed to retrieve reservation ID.");
                        }
                    } else {
                        System.out.println("Failed to book a seat.");
                    }
                }
            } else {
                System.out.println("Invalid username. Please enter a valid username.");
            }
        }catch (InputMismatchException e) {
            // Handle the input mismatch exception here
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Consume the invalid input
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while booking the ticket.");
        }
    }


    private int calculateNextAvailableSeat(int trainId) throws SQLException {
        try (Connection connection = DataConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT seatNumber FROM reservation WHERE trainId = ?")) {

            preparedStatement.setInt(1, trainId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Set<Integer> reservedSeatNumbers = new HashSet<>();

            while (resultSet.next()) {
                reservedSeatNumbers.add(resultSet.getInt("seatNumber"));
            }

            int nextAvailableSeat = 1;
            while (reservedSeatNumbers.contains(nextAvailableSeat)) {
                nextAvailableSeat++;
            }

            return nextAvailableSeat;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
   
    void viewtrains() {
		//String ch;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "admin");
			System.out.println(
					"**************************************************************************************************************");
			System.out.println("Train Number   Train Name      From       Destination     Journey date   Total Seats");
			System.out.println(
					"**************************************************************************************************************");
			Statement st = c.createStatement();

			ResultSet r = st.executeQuery("SELECT * FROM train");
			while (r.next()) {
				System.out.println(r.getInt(1) + "\t" + r.getString(2) + "\t" + r.getString(3) + "\t" + r.getString(4)
						+ "\t" + r.getDate(5) + "\t" + r.getInt(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void viewReservations(int passengerId) {		 
	    try (Connection connection = DataConn.getConnection();
	         PreparedStatement ps = connection.prepareStatement("SELECT * FROM reservation WHERE passengerId = ?")) {

	        ps.setInt(1, passengerId);
	        ResultSet resultSet = ps.executeQuery();

	        if (!resultSet.isBeforeFirst()) {
	            System.out.println("No reservations found for the passenger.");
	        } else {
	            System.out.println("Reservations for Passenger ID " + passengerId + ":");
	            while (resultSet.next()) {
	                int reservationId = resultSet.getInt("reservationId");
	                int trainId = resultSet.getInt("trainId");
	                Date reservationDate = resultSet.getDate("reservationDate");
	                Date departureDate = resultSet.getDate("departureDate");
	                int seatNumber = resultSet.getInt("seatNumber");
	                String status = resultSet.getString("status");

	                System.out.println("Reservation ID			: " + reservationId);
	                System.out.println("Train ID					: " + trainId);
	                System.out.println("Reservation Date		: " + reservationDate);
	                System.out.println("Departure Date		: " + departureDate);
	                System.out.println("Seat Number			: " + seatNumber);
	                System.out.println("Status						: " + status);
	                System.out.println();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("An error occurred while retrieving reservations.");
	    }
	}
    
    public int getPassengerIdByUsername(String username) throws SQLException {
	    int passengerId = -1; // Initialize to -1 as a default value

	    try (Connection connection = DataConn.getConnection();
	         PreparedStatement ps = connection.prepareStatement("SELECT passengerId FROM passenger WHERE username = ?")) {

	        ps.setString(1, username);
	        ResultSet resultSet = ps.executeQuery();

	        if (resultSet.next()) {
	            passengerId = resultSet.getInt("passengerId");
	        }
	    }

	    return passengerId;
	}

    public void viewPassengers() throws SQLException {
        try (Connection connection = DataConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT passengerId, firstName, lastName, email, username FROM passenger")) {

            // Print header
            System.out.println("****************************************************************************************************");
            System.out.println("Passenger ID   First Name   Last Name   Email              Username");
            System.out.println("****************************************************************************************************");

            while (resultSet.next()) {
                int passengerId = resultSet.getInt("passengerId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");

                // Print passenger information
                System.out.printf("%-14d %-12s %-11s %-18s %-11s%n", passengerId, firstName, lastName, email, username);
            }
        }
    }
    public void viewallReservations() throws SQLException {
        try (Connection connection = DataConn.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT passengerId, trainId, reservationDate, departureDate, seatNumber, status FROM reservation")) {

            // Print header
            System.out.println("*********************************************************************************************************************************");
            System.out.println("Passenger ID   Train ID   Reservation Date   Departure Date   Seat Number   Status");
            System.out.println("*********************************************************************************************************************************");

            while (resultSet.next()) {
                int passengerId = resultSet.getInt("passengerId");
                int trainId = resultSet.getInt("trainId");
                Date reservationDate = resultSet.getDate("reservationDate");
                Date departureDate = resultSet.getDate("departureDate");
                int seatNumber = resultSet.getInt("seatNumber");
                String status = resultSet.getString("status");

                // Print reservation information
                System.out.printf("%-14d %-9d %-17s %-15s %-13d %-7s%n", passengerId, trainId, reservationDate.toString(),
                        departureDate.toString(), seatNumber, status);
            }
        }
    }



}
