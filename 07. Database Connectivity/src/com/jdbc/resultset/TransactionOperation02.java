package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.jdbc.util.MySqlJdbcUtil;

public class TransactionOperation02 {

	public static void main(String[] args) throws Exception {

		
		Connection con = MySqlJdbcUtil.getDataSource().getConnection();
		
		Savepoint savePoint = null;
		try {
			
			con.setAutoCommit(false);
			
			PreparedStatement pstmt = con.prepareStatement("insert into Products values (?, ?, ?)");


			saveProducts(pstmt, 101, "Mother Board", 79);
			saveProducts(pstmt, 102, "Mouse", 15);
			saveProducts(pstmt, 103, "HDMI Cable", 12);
			savePoint = con.setSavepoint("Save Point Records");

			saveProducts(pstmt, 104, "Keyboard", 12);
			saveProducts(pstmt, 105, "USB Cable", 3);
			saveProducts(pstmt, 106, "VGA Cable", 3);
			saveProducts(pstmt, 102, "Touch Pad", 20);

			System.out.println("Successfully update data");
			con.commit();
		} catch (SQLException ex) {
			System.err.println("SQL State : " + ex.getSQLState());
			System.err.println("Message : " + ex.getMessage());
			System.err.println("Vendor: " + ex.getErrorCode());
			ex.printStackTrace();
			
			if (savePoint != null) {
				System.err.println("Error Detected. roll back to save point...");
				con.rollback(savePoint); 
				con.commit(); // Only Save data before save point is commit
			} else {
				System.err.println("Error detected, rollback everything...");
				con.rollback();
			}
			con.rollback();
			System.err.println("Transaction rollback");
		} finally {
			con.close();
		}

	}
	
	private static void saveProducts(PreparedStatement pstmt, int productId, String productName, double price) throws Exception {
		pstmt.setInt(1, productId);
		pstmt.setString(2, productName);
		pstmt.setDouble(3, price);
		pstmt.executeUpdate();		
	}

}
