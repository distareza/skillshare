package com.jdbc.rowset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

/**
 * Demonstrate JDBC Rowset = Connected Rowset
 * 
 *
 */
public class JdbcRowSetExample {

	public static void queryJdbcRowSet() {
		try {
			
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.execute();
			
			System.out.println();
			System.out.println("==== Query Jdbc Rowset ====");
			System.out.format("%s |\t%-30s|\t%s\n", "product id", "product name", "price");
			while(jdbcRs.next()) {
				displayProductData(jdbcRs);
			}
			
			jdbcRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void navigateJdbcRowSet() {
		try {
			System.out.println();
			System.out.println("==== NAvigaete Jdbc Row Set ====");
			
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.execute();
			
			jdbcRs.first();
			displayProductData("first()", jdbcRs);

			jdbcRs.relative(3);
			displayProductData("relative(3)", jdbcRs);

			jdbcRs.previous();
			displayProductData("previous()", jdbcRs);

			jdbcRs.absolute(6);
			displayProductData("absolute(6)", jdbcRs);

			jdbcRs.last();
			displayProductData("last()", jdbcRs);

			jdbcRs.relative(-5);
			displayProductData("relative(-5)", jdbcRs);

			jdbcRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void navigateAndUpdateJdbcRowSet() {
		try {
			System.out.println();
			System.out.println("==== Navigate and check for updated Record ====");
			
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.execute();
			
			jdbcRs.first();
			displayProductData("first()", jdbcRs);

			System.out.println("\tWait 5 second...");
			Thread.sleep(5000);
			
			jdbcRs.last();
			jdbcRs.refreshRow();
			displayProductData("last()", jdbcRs);

			jdbcRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		initdata();
		
		queryJdbcRowSet();
		navigateJdbcRowSet();
		
		backgroundJobUpdateLastRow(); // Preaparing update the row after 2 second run, let jdbcrowset is iterating
		navigateAndUpdateJdbcRowSet();
		
		queryJdbcRowSet();
		System.out.println("Done");
	}

	private static void displayProductData(RowSet rs) throws SQLException {
		int id = rs.getInt("product_id");
		String name = rs.getString("product_name");
		double price = rs.getDouble("price");
		
		System.out.format("\t%d|\t%-30s|\t%,.2f\n", id, name, price);
	}
	
	private static void displayProductData(String label, RowSet rs) throws SQLException {
		int id = rs.getInt("product_id");
		String name = rs.getString("product_name");
		double price = rs.getDouble("price");
		
		System.out.format("\t%-20s|\t%d|\t%-30s|\t%,.2f\n", label, id, name, price);
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
			System.out.println("!!! Data initilized !!!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}	
	
	private static void backgroundJobUpdateLastRow() {
		Runnable myThread = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("\tUpdating last row");
					PreparedStatement pstmt = MySqlJdbcUtil.getDataSource().getConnection().prepareStatement("Update Products SET product_name=?, price=? WHERE product_id=?");
					pstmt.setInt(3, 107);
					pstmt.setString(1, "LCD Curve Monitor");
					pstmt.setDouble(2, 350);

					int rowAdded = pstmt.executeUpdate();
					System.out.println(String.format("\t%d row updated", rowAdded));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		
		(new Thread(myThread)).start();
	}	
}
