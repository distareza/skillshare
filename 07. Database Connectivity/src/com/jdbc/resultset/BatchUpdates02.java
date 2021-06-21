package com.jdbc.resultset;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.jdbc.util.MySqlJdbcUtil;

public class BatchUpdates02 {

	public static void main(String[] args) throws Exception {

		
		Object[][] datas = new Object[][] {
			{101, "Mother Board", 79},
			{102, "Mouse", 15},
			{103, "HDMI Cable", 5},
			{104, "Keyboard", 12},
			{105, "USB Cable", 3},
			{106, "VGA Cable", 3},
			{107, "CPU", 300}
		};
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			PreparedStatement pstmt = con.prepareStatement("insert into Products values (?, ?, ?)");
			con.setAutoCommit(false);
			
			for (Object[] data : datas) {
				pstmt.setInt(1, Integer.parseInt(data[0].toString()));
				pstmt.setString(2, data[1].toString());
				pstmt.setDouble(3, Double.parseDouble(data[2].toString()));
				pstmt.addBatch();
			}
			
			int[] updateCounts = pstmt.executeBatch();
			System.out.println(Arrays.toString(updateCounts));

			con.commit();
		} catch (BatchUpdateException ex) {
			System.err.println("SQL State : " + ex.getSQLState());
			System.err.println("Message : " + ex.getMessage());
			System.err.println("Vendor: " + ex.getErrorCode());
			System.err.println("Update Counts : " + Arrays.toString(ex.getUpdateCounts()));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
