package com.jdbc.operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BasicDataBaseOperation06 {

	public static void main(String[] args) throws Exception {

		String dbUrl = "jdbc:mysql://localhost:3306/";
		String user = "root";
		String password = "password";
		
		try (Connection con = DriverManager.getConnection(dbUrl, user, password)) {
			
			Statement stmt = con.createStatement();
			String query = "drop database SampleDB";

			boolean result = stmt.execute(query);
			
			System.out.println("Execute Query is successfully executed is : " + result);
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
