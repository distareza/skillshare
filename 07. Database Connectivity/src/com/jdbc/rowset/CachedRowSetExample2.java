package com.jdbc.rowset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

/**
 * CachedRowSet = Disconnected Row Set
 * 
 *
 */
public class CachedRowSetExample2 {

	public static void queryCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test Query Cached RowSet populate from ResultSet");
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			Connection con = MySqlJdbcUtil.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
			cachedRs.populate(rs);
			con.close();
			
			while(cachedRs.next()) {
				displayProductData("", cachedRs);
			}

			cachedRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public static void main(String[] args) throws Exception {
		
		initdata();
		
		queryCachedRowSet();
	}
	
	private static void initdata() throws Exception {
		Object productData[][] = new Object[][] { 
				{101, "Mother Board", 79}, 
				{102, "Mouse", 15}, 
				{103, "HDMI Cable", 5}, 
				{104, "Keyboard", 12},
				{105, "USB Cable", 3}, 
				{106, "VGA Cable", 3}, 
				{107, "LCD Monitor", 159}
		};
		try {
			Statement stmt = MySqlJdbcUtil.getDataSource().getConnection().createStatement();
			stmt.executeUpdate("TRUNCATE Products");
			
			PreparedStatement pstmt = MySqlJdbcUtil.getDataSource().getConnection().prepareStatement("Insert into Products values(?,?,?)");
			for (Object[] data : productData) {
				pstmt.setInt(1, Integer.parseInt( data[0].toString() ));
				pstmt.setString(2, data[1].toString());
				pstmt.setDouble(3, Double.parseDouble(data[2].toString()));
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			System.out.println("Data initilized");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private static void displayProductData(String label, RowSet rs) throws SQLException {
		int id = rs.getInt("product_id");
		String name = rs.getString("product_name");
		double price = rs.getDouble("price");

		if (label == null || label.isEmpty())
			System.out.format("\t%d|\t%-30s|\t%,.2f\n", id, name, price);
		else
			System.out.format("\t%-20s|\t%d|\t%-30s|\t%,.2f\n", label, id, name, price);
	}
}
