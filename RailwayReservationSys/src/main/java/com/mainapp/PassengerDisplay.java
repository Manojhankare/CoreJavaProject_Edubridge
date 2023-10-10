package com.mainapp;

import java.util.Scanner;

public class PassengerDisplay {
	
	public void passengerMenu() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean passengerMenuActive = true;

		while (passengerMenuActive) {
			System.out.println("\nPassenger Menu:");
			System.out.println("1. View Trains");
			System.out.println("2. Book Ticket");
			System.out.println("3. View Reservations");
			System.out.println("4. Logout");
			System.out.print("Enter your choice: ");
			int passengerChoice = sc.nextInt();

			switch (passengerChoice) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				 
				break;
			case 4:
			 
				passengerMenuActive = false;
				System.out.println("Logged out of passenger account.");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}

		sc.close();
	}

}
