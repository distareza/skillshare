package com.jdbc.rowset;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class CachedRowSetExample {

	public static void queryCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test Query Row");
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			
			cachedRs.setUrl(MySqlJdbcUtil.getUrl());
			cachedRs.setUsername(MySqlJdbcUtil.getUser());
			cachedRs.setPassword(MySqlJdbcUtil.getPassword());
			
			cachedRs.execute();
			
			while(cachedRs.next()) {
				displayProductData("", cachedRs);
			}

			cachedRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void navigateCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test Navigate Cached Row");
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			
			cachedRs.setUrl(MySqlJdbcUtil.getUrl());
			cachedRs.setUsername(MySqlJdbcUtil.getUser());
			cachedRs.setPassword(MySqlJdbcUtil.getPassword());
			
			cachedRs.execute();

			cachedRs.first();
			displayProductData("first()", cachedRs);

			cachedRs.relative(3);
			displayProductData("relative(3)", cachedRs);

			cachedRs.previous();
			displayProductData("previous()", cachedRs);

			cachedRs.absolute(6);
			displayProductData("absolute(6)", cachedRs);

			cachedRs.last();
			displayProductData("last()", cachedRs);

			cachedRs.relative(-5);
			displayProductData("relative(-5)", cachedRs);
			
			cachedRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void navigateAndRefreshedCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test Navigate and Check Refreshed Row");

			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			
			cachedRs.setUrl(MySqlJdbcUtil.getUrl());
			cachedRs.setUsername(MySqlJdbcUtil.getUser());
			cachedRs.setPassword(MySqlJdbcUtil.getPassword());
			
			cachedRs.execute();

			cachedRs.first();
			displayProductData("first()", cachedRs);

			System.out.println("\tSleep for 5 second...");
			Thread.sleep(5000);
			
			cachedRs.last();
			cachedRs.refreshRow(); // <-- to refreshed changes, however because it is a cached RowSet / Disconnected RowSet, the data are not getting the updated data from table, 
			displayProductData("last()", cachedRs);

			cachedRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void navigateAndUpdateCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test and Update the Cached Row Set");

			Connection con = MySqlJdbcUtil.getConnection();
			con.setAutoCommit(false);
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			cachedRs.execute(con); // <-- required if need to set autocommit to false

			while (cachedRs.next()) {
				if (cachedRs.getString("product_name").endsWith("Cable")) {
					cachedRs.updateDouble("price", cachedRs.getDouble("price") + 1);
					cachedRs.updateRow();
					
					displayProductData("updated", cachedRs);
				} else {
					displayProductData("old", cachedRs);
				}
			}

			cachedRs.acceptChanges(); // <-- To let cachedRowSet performing update/delete/update operation into database , same like con.commit
			cachedRs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

	public static void navigateAndRemoveCachedRowSet() {
		try {
			System.out.println();
			System.out.println("Test and Remove the Cached Row Set");

			Connection con = MySqlJdbcUtil.getConnection();
			con.setAutoCommit(false);
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			cachedRs.execute(con); // <-- required if need to set autocommit to false

			while (cachedRs.next()) {
				if (cachedRs.getString("product_name").endsWith("Cable")) {
					cachedRs.deleteRow();
					
					displayProductData("deleted", cachedRs);
				} else {
					displayProductData("old", cachedRs);
				}
			}

			cachedRs.acceptChanges(); // <-- To let cachedRowSet performing update/delete/update operation into database , same like con.commit
			cachedRs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

	public static void navigateAndInsertCachedRowSet() {
		try {
			Object productData[][] = new Object[][] { 
				{"HDMI Cable", 5}, 
				{"USB Cable", 3}, 
				{"VGA Cable", 3} 
			};			
			
			System.out.println();
			System.out.println("Test and Insert the Cached Row Set");

			Connection con = MySqlJdbcUtil.getConnection();
			con.setAutoCommit(false);
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM Products");
			cachedRs.execute(con); // <-- required if need to set autocommit to false

			int productCount = -1;
			while (cachedRs.next()) {
				productCount = Math.max(cachedRs.getInt("product_id"), productCount);
			}
			
			productCount++;
			for (Object[] data : productData) {
				cachedRs.moveToInsertRow();
				cachedRs.updateInt("product_id", productCount++);
				cachedRs.updateString("product_name", data[0].toString());
				cachedRs.updateDouble("price", Double.parseDouble(data[1].toString()));
				
				cachedRs.insertRow();				
				cachedRs.moveToCurrentRow();
				
				displayProductData("added: ", cachedRs);
			}

			cachedRs.acceptChanges(); // <-- To let cachedRowSet performing update/delete/update operation into database , same like con.commit
			cachedRs.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
	public static void main(String[] args) throws Exception {
		
		initdata();
		
		queryCachedRowSet();
		navigateCachedRowSet();
		
		backgroundJobUpdateLastRow();
		navigateAndRefreshedCachedRowSet();

		navigateAndUpdateCachedRowSet();
		queryCachedRowSet();
		
		navigateAndRemoveCachedRowSet();
		queryCachedRowSet();
		
		navigateAndInsertCachedRowSet();
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
