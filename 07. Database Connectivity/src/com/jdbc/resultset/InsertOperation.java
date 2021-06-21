package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class InsertOperation {

	public static void main(String[] args) throws Exception {
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee_data");

			int javaDevCount = 0;
			int empCount = 0;
			while (rs.next()) {
				empCount = Math.max(rs.getInt("emp_id"),  empCount);
				if (rs.getString("designation").equalsIgnoreCase("Java Developer"))
					javaDevCount++;
			}
			
			if (javaDevCount == 0) {
				rs.moveToInsertRow();
				
				rs.updateInt("emp_id", ++empCount);
				rs.updateString("emp_name", "Alan");
				rs.updateString("designation", "Java Developer");
				rs.updateString("salary", "9500");
				
				rs.insertRow();
				rs.last();
				
				displayEmpData("added : ", rs);
			}
			
			con.commit();
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
