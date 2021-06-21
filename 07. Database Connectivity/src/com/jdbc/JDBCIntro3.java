package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * https://dev.mysql.com/downloads/
 *
 */
public class JDBCIntro3 {

	public static void main(String[] args) throws Exception {
		
		String dbUrl = "jdbc:mysql://localhost:3306";

		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "password");
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, prop);
			
			if (con!=null)
				System.out.println("The connection has been successfully establish");
		} catch (SQLException ex) {
			System.err.println("A Connection error has occured :");
			ex.printStackTrace();
		}
		
	}

}
