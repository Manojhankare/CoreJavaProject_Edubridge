package com.mainapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.Train.PassengerDaoImpl;
import com.Train.TrainDao;
import com.Train.TrainDaoImpl;
import com.railways.Passenger;
import com.railways.Train;

public class MyApp {
	int pid = 0;
	Operations op = new Operations();

	Scanner sc = new Scanner(System.in);
	boolean isLoggedIn = false;

	public static void main(String[] args) throws Exception {
		System.out.println(
				"**************************************************************************************************************");
		System.out.println(
				"                                      RAILWAY RESERVATION SYSTEM                                              ");
		System.out.println(
				"**************************************************************************************************************");

		MyApp app = new MyApp();
		app.mainmenu();
	}

	public void mainmenu() throws Exception {
		while (true) {
			System.out.println(" \n------------Railway Reservation System----------------");
			System.out.println("\nHOME Login Menu:");
			System.out.println("1. View Trains");
			System.out.println("2. Admin Login");
			System.out.println("3. Passenger Login");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				op.viewtrains();
				break;
			case 2:
				adminpass();
				break;
			case 3:
				if (!isLoggedIn) {
					passengerlog();
				} else {
					passengerMenu(); // Directly go to the passenger menu if already logged in
				}
				break;
			case 4:
				System.exit(0);
				System.out.println("Exited from System Successfully...Happy Journey!!!");
			default:
				System.out.println("Invalid input. Please Insert the right choice!!");
			}
		}
	}

	void adminpass() throws Exception {
		System.out.print("Enter password: ");
		String ps = sc.next();
		if (ps.equals("admin")) {
			adminMenu();
		} else {
			System.out.println("Wrong password. Try again!!!!!!");
		}
	}

	void passengerlog() throws Exception {
		while (true) {
			System.out.println("  ");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~WELCOME TO Passenger Login MENU~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("\n  1. Login\n  2. Sign Up\n  3. Return to the main menu\n  4. Exit");
			System.out.println("\n Please Choose from above options : ");
			int choice = sc.nextInt();
			

			switch (choice) {
			case 1:
				int login = passengerLogin();
				if (login == 1) {
					passengerMenu();
				}
				break;
			case 2:
				passengersignup();
				break;
			case 3:
				return;
			case 4:
				System.exit(0);
			default:
				System.out.println("Invalid input. Please Insert the right choice!!");
			}
		}
	}

	public static boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() == 10) {
			for (char c : phoneNumber.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false; // Contains non-digit characters
				}
			}
			return true; // Contains exactly 10 digits
		} else {
			return false; // Not 10 characters long
		}
	}

	private void passengersignup() {
		System.out.println("-------------------Passenger Registration-------------");
		System.out.print("Enter First Name: ");
		String firstName = sc.next();
		System.out.print("Enter Last Name: ");
		String lastName = sc.next();
		System.out.print("Enter Email: ");
		String email = sc.next();

		String phoneNumber = "";
		while (true) {
			System.out.print("Enter Phone Number (10 digits): ");
			phoneNumber = sc.next();
			if (isValidPhoneNumber(phoneNumber)) {
				break;
			} else {
				System.out.println("Invalid phone number format. Please enter 10 digits.");
			}
		}

		System.out.print("Enter Age: ");
		int age;
		while (true) {
			if (sc.hasNextInt()) {
				age = sc.nextInt();
				break; // Valid integer input
			} else {
				System.out.println("Invalid age format. Please enter a valid integer.");
				sc.next(); // Consume invalid input
			}
		}

		String username = "";
		while (true) {
			System.out.print("Enter Username (at least 6 characters): ");
			username = sc.next();
			if (username.length() >= 6) {
				break; // Valid username
			} else {
				System.out.println("Username must be at least 6 characters long.");
			}
		}

		String password = "";
		while (true) {
			System.out.print("Enter Password (at least 6 characters): ");
			password = sc.next();
			if (password.length() >= 6) {
				break; // Valid password
			} else {
				System.out.println("Password must be at least 6 characters long.");
			}
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "admin");

			// Check if the username already exists in the database
			PreparedStatement checkUsername = c.prepareStatement("SELECT username FROM passenger WHERE username = ?");
			checkUsername.setString(1, username);
			ResultSet resultSet = checkUsername.executeQuery();

			if (resultSet.next()) {
				System.out.println("Username already exists.");
			} else {
				// Insert user data into the database
				PreparedStatement insertUser = c.prepareStatement(
						"INSERT INTO passenger (firstName, lastName, email, phoneNumber, age, username, password) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
				insertUser.setString(1, firstName);
				insertUser.setString(2, lastName);
				insertUser.setString(3, email);
				insertUser.setString(4, phoneNumber);
				insertUser.setInt(5, age);
				insertUser.setString(6, username);
				insertUser.setString(7, password);

				int rowsAffected = insertUser.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("User registered successfully. Please Log in to Continue!!!");
				} else {
					System.out.println("User registration failed. Try Again");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public int passengerLogin() throws Exception {
		String username;
		String password;
		System.out.println(" \n------------Railway Reservation System----------------");
		System.out.println("\n-----------Passenger LOGIN-------------");

		System.out.print("\nEnter Username    : ");
		username = sc.next();
		System.out.print("Please Enter Your Password : ");
		password = sc.next();

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = DataConn.getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT username, password FROM passenger");
		ResultSet r = ps.executeQuery();

		boolean found = false;

		while (r.next()) {
			if (username.equals(r.getString(1)) && password.equals(r.getString(2))) {
				found = true;
				pid=op.getPassengerIdByUsername(username);
				break;
			}
		}
		if (found) {
			System.out.println("Logged in successfully!!!");
			return 1; // Authentication successful
		} else {
			System.out.println("Invalid username or password. Please try again.");
			return 0; // Authentication failed
		}
	}
	

	public void passengerMenu() throws SQLException {
		while (true) {
			try {
				// Scanner sc = new Scanner(System.in);

				System.out.println(" \n--------------------Railway Reservation System------------------");
				System.out.println("\n-----------Passenger Menu-------------");

				System.out.println("1. View Trains");
				System.out.println("2. Book Ticket");
				System.out.println("3. View Reservations");
				System.out.println("4. Logout");
				System.out.print("Enter your choice: ");
				int passengerChoice = sc.nextInt();
			
				switch (passengerChoice) {
				case 1:
					op.viewtrains();
					break;
				case 2:
					
					op.bookTicket();
					break;
				case 3:
					op.viewReservations(pid);
					break;
				case 4:
					isLoggedIn = false; // Logout the user
					return; // Return to the main menu
				default:
					System.out.println("Invalid choice. Please enter a valid option.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	 		

	public  void adminMenu() throws SQLException {
		 while (true) {
		        System.out.println(" \n--------------------Admin Menu------------------");
		        System.out.println("1. View Trains");
		        System.out.println("2. Add Train");
		        System.out.println("3. Update Train");
		        System.out.println("4. Delete Train");
		        System.out.println("5. View Passengers");
		        System.out.println("6. View Reservations");
		        System.out.println("7. Back to Main Menu");
		        System.out.print("Enter your choice: ");
		        int adminChoice = sc.nextInt();
		        TrainDao trainDao = new TrainDaoImpl();

		        switch (adminChoice) {
		            case 1:
		                //List<Train> trains = trainDao.getAllTrains();
		                System.out.println("List of Trains:");
		                op.viewtrains();
		                 
		                break;
		            case 2:
		            	Train newTrain = createNewTrain(); // Implement createNewTrain() to take input from admin
		            	trainDao.addTrain(newTrain);
		            	System.out.println("Train added successfully.");
		            	
		                break;
		            case 3:
		                
		                 Train updatedTrain = createUpdatedTrain(); // Implement createUpdatedTrain() to take input from admin
		                 trainDao.updateTrain(updatedTrain);
		                break;
		            case 4:
		                System.out.print("Enter Train ID to delete: ");
		                int trainId = sc.nextInt();
		                
		                System.out.print("Are you sure you want to delete this train? (y/n): ");
		                char confirmation = sc.next().charAt(0);

		                if (confirmation == 'y' || confirmation == 'Y') {
		                    trainDao.deleteTrain(trainId);
		                    System.out.println("Train with ID " + trainId+ " deleted successfully.");
		                } else if (confirmation == 'n' || confirmation == 'N') {
//		                     return;
		                } else {
		                    System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
		                }
		                break; 
		                
		            case 5:
		            	 System.out.println("List of Passengers/Users:");
			              op.viewPassengers();
		              
		                break;
		            case 6:
		                System.out.println("List of Reservations done!!!");
		                op.viewallReservations();
		                break;
		            case 7:
		                return; // Return to the main menu
		            default:
		                System.out.println("Invalid choice. Please enter a valid option.");
		        }
		    }
		}
	
	private Train createUpdatedTrain() {
	    Scanner sc = new Scanner(System.in);

	    // Prompt for the Train ID
	    System.out.print("Enter Train ID to update: ");
	    int trainId;
	    if (sc.hasNextInt()) {
	        trainId = sc.nextInt();
	    } else {
	        System.out.println("Invalid input. Please enter a valid integer for Train ID.");
	        return null;
	    }

	    // Check if the train with the provided Train ID exists in the database
	    TrainDao trainDao = new TrainDaoImpl();
	    try {
	        Train existingTrain = trainDao.getTrainById(trainId);
	        if (existingTrain == null) {
	            System.out.println("Train with ID " + trainId + " does not exist in the database.");
	            return null;
	        }
	    } catch (SQLException e) {
	        System.out.println("An error occurred while checking for the existing train: " + e.getMessage());
	        return null;
	    }

	    // Create a new Train object for updating
	    Train updatedTrain = new Train();
	    
	    System.out.print("Enter Train Name: ");
	    updatedTrain.setTrainName(sc.next());

	    System.out.print("Enter Source Station: ");
	    updatedTrain.setSourceStation(sc.next());

	    System.out.print("Enter Destination Station: ");
	    updatedTrain.setDestinationStation(sc.next());

	    System.out.print("Enter Journey Date (yyyy-MM-dd): ");
	    String journeyDateStr = sc.next();
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date journeyDate = sdf.parse(journeyDateStr);
	        updatedTrain.setDate(journeyDate);
	    } catch (ParseException e) {
	        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
	        return null;
	    }

	    System.out.print("Enter Total Seats: ");
	    if (sc.hasNextInt()) {
	        updatedTrain.setTotalSeats(sc.nextInt());
	    } else {
	        System.out.println("Invalid input. Please enter a valid integer for total seats.");
	        return null;
	    }

	    // Set the Train ID for updating the existing train
	    updatedTrain.setTrainId(trainId);

	    return updatedTrain;
	}


	private Train createNewTrain() {
	    Scanner sc = new Scanner(System.in);
	    
	    
	    System.out.print("Enter Train Name: ");
	    String trainName = sc.nextLine();
	    
	    System.out.print("Enter Source Station: ");
	    String sourceStation = sc.nextLine();
	    
	    System.out.print("Enter Destination Station: ");
	    String destinationStation = sc.nextLine();
	    
	    System.out.print("Enter Journey Date (yyyy-MM-dd): ");
	    String dateStr = sc.nextLine();
	    Date date = null;
	    
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        date = dateFormat.parse(dateStr);
	    } catch (ParseException e) {
	        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
	    }
	    
	    System.out.print("Enter Total Seats: ");
	    int totalSeats = sc.nextInt();
	    
	    return new Train( trainName, sourceStation, destinationStation, date, totalSeats);
	}

}
