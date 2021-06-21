package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * https://dev.mysql.com/downloads/
 *
 */
public class JDBCIntro2 {

	public static void main(String[] args) throws Exception {
		
		String dbUrl = "jdbc:mysql://localhost:3306?user=root&password=password";
		Connection con = null;
		
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver"); <!-- only need for earlier java version, jdbc already included from java 8 on word
			con = DriverManager.getConnection(dbUrl);
			
			if (con!=null)
				System.out.println("The connection has been successfully establish");
		} catch (SQLException ex) {
			System.err.println("A Connection error has occured :");
			ex.printStackTrace();
		}
		
	}

}
