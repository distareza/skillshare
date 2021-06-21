package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class QueryOperation {

	public static void main(String[] args) throws Exception {
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			DatabaseMetaData metadata = con.getMetaData();
			
			System.out.println("--- ResultSet Properties ---");
			
			// Does this Database support Iterate type on Forward Only Direction (That does not support Backward directory) 
			System.out.println("Support TYPE_FORWARD_ONLY : " + metadata.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
			// Does this Database support navigate both forward and backward directions to jump position, head to specific point in ResultSet		
			System.out.println("Support TYPE_SCROLL_INSENSITIVE: " + metadata.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
			// Does this Database support navigate that sensitive to any changes in underlaying data
			System.out.println("Support TYPE_SCROLL_SENSITIVE: " + metadata.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
			
			System.out.println("--- ResultSet Concurreny ---");
			//does it allowed to perform update operations on Result Set ?
			System.out.println("Support CONCUR_READ_ONLY : " + metadata.supportsResultSetType(ResultSet.CONCUR_READ_ONLY));			
			System.out.println("Support CONCUR_UPDATEABLE : " + metadata.supportsResultSetType(ResultSet.CONCUR_UPDATABLE));
			
			
			System.out.println("--- Result Cursor Holdability ---");
			int holdability = metadata.getResultSetHoldability();
			if (holdability == ResultSet.CLOSE_CURSORS_AT_COMMIT) {
				System.out.println("Default : CLOSE_CURSORS_AT_COMMIT");
			} else if (holdability == ResultSet.HOLD_CURSORS_OVER_COMMIT) {
				System.out.println("Default : HOLD_CURSORS_AT_COMMIT");
			}
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee_data");
			
			System.out.println();
			rs.first();
			displayEmpData("first()", rs);
			
			rs.relative(3);
			displayEmpData("relative(3)", rs);
			
			rs.previous();
			displayEmpData("previous()", rs);
			
			rs.absolute(6);
			displayEmpData("absolute(6)", rs);
			
			rs.last();
			displayEmpData("last()", rs);
			
			rs.relative(-5);
			displayEmpData("relatives(-5)", rs);
			
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
