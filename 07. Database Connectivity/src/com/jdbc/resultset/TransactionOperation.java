package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jdbc.util.MySqlJdbcUtil;

public class TransactionOperation {

	public static void main(String[] args) throws Exception {

		
		Object[][] datas = new Object[][] {
			{101, "Mother Board", 79},
			{102, "Mouse", 15},
			{103, "HDMI Cable", 5},
			{104, "Keyboard", 12},
			{105, "USB Cable", 3},
			{106, "VGA Cable", 3},
			{102, "Touch Pad", 20},
		};
		
		Connection con = MySqlJdbcUtil.getDataSource().getConnection();
		try {
			
			con.setAutoCommit(false);
			
			PreparedStatement pstmt = con.prepareStatement("insert into Products values (?, ?, ?)");
			
			for (int i : new int[] {3, 1} ) {
				Object[] data = datas[i];
				pstmt.setInt(1, Integer.parseInt(data[0].toString()));
				pstmt.setString(2, data[1].toString());
				pstmt.setDouble(3, Double.parseDouble(data[2].toString()));
				pstmt.executeUpdate();
			}
				
			System.out.println("Successfully update data");
			con.commit();
		} catch (SQLException ex) {
			System.err.println("SQL State : " + ex.getSQLState());
			System.err.println("Message : " + ex.getMessage());
			System.err.println("Vendor: " + ex.getErrorCode());
			ex.printStackTrace();
			
			con.rollback();
			System.err.println("Transaction rollback");
		} finally {
			con.close();
		}

	}

}
