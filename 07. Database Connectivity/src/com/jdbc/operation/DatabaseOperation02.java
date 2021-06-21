package com.jdbc.operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.util.MySqlJdbcUtil;

public class DatabaseOperation02 {

	public static void main(String[] args) throws Exception {
		
		Object[][] data = new Object[][] {
									{103, "Raj", "Tech Lead", 110000}, 
									{104, "Maria", "Product Manager", 118000}
							};

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			String query = 
					"insert into employee_data (emp_id, emp_name, designation, salary) values (?, ?, ?, ?)";
			
			PreparedStatement ps = con.prepareStatement(query);

			int updateRow = 0;
			for (int i=0; i<data.length; i++) {
				ps.setInt(1, Integer.parseInt( data[i][0].toString() ));
				ps.setString(2, data[i][1].toString());
				ps.setString(3, data[i][2].toString());
				ps.setDouble(4, Double.parseDouble( data[i][3].toString() ));
				
				updateRow+= ps.executeUpdate();
				
			}
			System.out.println("Execute Query is successfully executed is : " + updateRow);

			query = "SELECT emp_id, emp_name, designation, salary from employee_data";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
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
