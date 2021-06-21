package com.jdbc.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class DatabaseOperation01 {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement();
			String query = 
					"insert into employee_data (emp_id, emp_name, designation, salary) " +
					"	values(101, 'Alan', 'Java Developer', 87000)," +
					"		(102, 'Claudia', 'Senior Software Engineer', 102000) ";

			int result = stmt.executeUpdate(query);
			
			System.out.println("Execute Query is successfully executed is : " + result);

			query = "SELECT emp_id, emp_name, designation, salary from employee_data";
			
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Query is successfully executed");
			System.out.println();
			System.out.println(String.format("%-30s\t%-30s\t%-30s\t%-30s", "emp_id", "emp_name", "designation", "salary"));
			System.out.println("==========================================================");
			while (rs.next()) {
				System.out.println(String.format("%-30s\t%-30s\t%-30s\t%.0f", rs.getString("emp_id"), rs.getString("emp_name"), rs.getString("designation"), rs.getDouble("salary")));
			}
			System.out.println("==========================================================");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
