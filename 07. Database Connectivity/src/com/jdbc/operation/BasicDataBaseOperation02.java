package com.jdbc.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class BasicDataBaseOperation02 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			String query = "SELECT first_name, last_name from users";
			
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Query is successfully executed");
			System.out.println();
			System.out.println(String.format("%-30s\t%-30s", "first_name", "rs_last_name"));
			System.out.println("==========================================================");
			while (rs.next()) {
				System.out.println(String.format("%-30s\t%-30s", rs.getString("first_name"), rs.getString("last_name")));
			}
			System.out.println("==========================================================");
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
