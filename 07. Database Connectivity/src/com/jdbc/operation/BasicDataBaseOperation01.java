package com.jdbc.operation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class BasicDataBaseOperation01 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			String query = 
							"	create table users ( 			" + 
							"		first_name varchar(50), 	" + 
							"		last_name varchar(50), 		" +
							" 		email varchar(50), 			" + 
							" 		phone_number varchar(50) 	" +
							" 	)	";
			
			stmt.execute(query);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
