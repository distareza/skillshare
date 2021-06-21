package com.jdbc.operation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class BasicDataBaseOperation03 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			//String query = "insert into users values('Noami', 'Miller', 'noami_miller@live.com', '555-991-8021')";
			String query = 
					"insert into users (first_name, last_name, email) " +
					"	values('Julio', 'Chavez', 'chavesj317@gmail.com')," +
					"		('Zack', 'Harken', 'zack_harken@hotmail.com') ";

			int result = stmt.executeUpdate(query);
			
			System.out.println("Execute Query is successfully executed is : " + result);
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
