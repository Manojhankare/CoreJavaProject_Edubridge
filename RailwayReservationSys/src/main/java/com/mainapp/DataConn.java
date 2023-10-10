package com.mainapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConn {
	private static String url = "jdbc:mysql://localhost:3306/railway";
	private static String us = "root";
	private static String pass = "admin";
	private static Connection connection;

	public static Connection getConnection() {
		 
		    try{
		        Class.forName("com.mysql.cj.jdbc.Driver");
		         return DriverManager.getConnection(url, us,pass);
		    }catch(Exception ex){
		        System.out.println(ex.getMessage());
		        System.out.println("couldn't connect!");
		        throw new RuntimeException(ex);
		    }
		}
//	public static void closeConnection() {
//		if (connection != null) {
//			try {
//				connection.close();
//				System.out.println("Connection closed.");
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
