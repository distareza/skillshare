package com.jdbc.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdbc.util.MySqlJdbcUtil;

public class DatabaseOperation03 {

	public static void main(String[] args) throws Exception {
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			String query = "SELECT emp_id, emp_name, designation, salary from employee_data where emp_id = ? ";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,  103);
			
			ResultSet rs = ps.executeQuery();
			System.out.println("Query is successfully executed");
			System.out.println();
			System.out.println(String.format("%-30s\t%-30s\t%-30s\t%-30s", "emp_id", "emp_name", "designation", "salary"));
			System.out.println("===============================================================================");
			while (rs.next()) {
				System.out.println(String.format("%-30s\t%-30s\t%-30s\t%.0f", rs.getString("emp_id"), rs.getString("emp_name"), rs.getString("designation"), rs.getDouble("salary")));
			}
			System.out.println("===============================================================================");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

}
