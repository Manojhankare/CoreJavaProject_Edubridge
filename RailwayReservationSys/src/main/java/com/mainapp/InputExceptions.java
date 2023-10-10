package com.mainapp;

import java.util.Scanner;
 
public class InputExceptions extends InputMismatchException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Scanner scanner;

    public InputExceptions() {
        super("Input mismatch. Please enter a valid input.");
        scanner = new Scanner(System.in);
    }

    public int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public String getStringInput(String message) {
        System.out.print(message);
        return scanner.next();
    }

    public void close() {
        scanner.close();
    }
}
