package com.jdbc.operation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class BasicDataBaseOperation05 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			String query = "drop table users ";

			boolean result = stmt.execute(query);
			
			System.out.println("Execute Query is successfully executed is : " + result);
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
