package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class RemoveOperation {

	public static void main(String[] args) throws Exception {
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee_data");

			while (rs.next()) {
				if (rs.getString("designation").equalsIgnoreCase("Java Developer")) {
					displayEmpData("remove : ", rs);
					rs.deleteRow();
					
				} else {
					displayEmpData("no changes : ", rs);
				}				
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
