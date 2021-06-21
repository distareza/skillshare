package com.jdbc.operation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class BasicDataBaseOperation04 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			//String query = " update users set phone_number = '555-923-5271' where last_name = 'Harken' ";
			String query = " update users set phone_number = '555-101-7777' where email like '%hotmail.com' ";
			//String query = " delete from users where phone_number is NULL ";
			
			int result = stmt.executeUpdate(query);
			
			System.out.println("Execute Query is successfully executed is : " + result);
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
