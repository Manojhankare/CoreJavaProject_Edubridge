# CoreJavaProject_Edubridge
# Railway Reservation System

The Railway Reservation System is a simple Java console application that allows users to view trains, make reservations, and manage the railway system. It consists of three main entities: passengers, trains, and reservations.

## Features

- **Passenger Management**: Users can log in as passengers, sign up for new accounts, and manage their reservations.

- **Train Management**: Admin users can view trains, add new trains, update existing train details, and delete trains.

- **Reservation Management**: Passengers can make reservations, view their reservations, and cancel reservations.
 
## Usage
When you run the application, you will be presented with a menu where you can choose to view trains, log in as a passenger, or exit the program.

Admin users can log in with the password "admin" to access admin-specific features.

Passengers can sign up for new accounts and then log in to make reservations.

## Getting Started

1. Clone the repository to your local machine.
2. Open the project in your Java IDE.
3. Configure the database connection in DataConn.java. Update the database URL, username, and password.
4. Create Database Schema as Shown in below;
5. Run the MyApp.java file to start the application.

## Database Schema
The project uses a MySQL database with three tables: passenger, train, and reservation. Here are the schemas for each table:

1) Passenger Table :-    
       passengerId (int) primarykey, mul
       firstName (varchar)
       lastName (varchar)
       email (varchar)
       phoneNumber (varchar)
       age (int)
       username (varchar, unique)
       password (varchar)

2) Train Table :- 
       trainId (int) primarykey, mul
       trainName (varchar)
       sourceStation (varchar)
       destinationStation (varchar)
       date (date) 
       totalSeats (int)
   
3) Reservation Table :- 
      reservationId (int) primarykey
      passengerId (int, foreign key)
      trainId (int, foreign key)
      reservationDate (date)
      departureDate (date)
      seatNumber (int)
      status (varchar)

  ## Contact
    If you have any questions or suggestions, please feel free to contact me manojhankare2@gmail.com or https://www.linkedin.com/in/manojhankare/
