package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class QueryOperation02 {

	public static void main(String[] args) throws Exception {
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee_data");

			rs.next();
			displayEmpData("next()", rs);
			
			System.out.println("wait for 12 second for you to update data in mysql workbench");
			Thread.sleep(5000);
			// try to update the last record and see the result
			
			rs.last();
			rs.refreshRow();
			displayEmpData("last()", rs);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
	
	private static void displayEmpData(String label, ResultSet rs) throws SQLException {
		int id = rs.getInt("emp_id");
		String name = rs.getString("emp_name");
		String designation = rs.getString("designation");
		double salary = rs.getDouble("salary");
		
		System.out.format("%-20s:\t%d|\t%-30s|\t%-30s|\t%,.2f\n", label, id, name, designation, salary);
	}

}
