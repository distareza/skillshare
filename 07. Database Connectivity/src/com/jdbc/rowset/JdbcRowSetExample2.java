package com.jdbc.rowset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

/**
 * To demonstrate data can be modified wile iterating JdbcRowSet by executing updateRow
 * set autocommit will initiate is the transactional state, for testing purpose
 *
 */
public class JdbcRowSetExample2 {

	public static void updateOperation() {
		try {
			
			System.out.println();
			System.out.println("--- Update Operation ---");
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.setAutoCommit(false);
			
			jdbcRs.execute();
			
			while (jdbcRs.next()) {
				if (jdbcRs.getString("product_name").endsWith("Cable")) {
					jdbcRs.updateDouble("price", jdbcRs.getDouble("price") + 1);
					jdbcRs.updateRow();
					
					displayProductData("updated : " , jdbcRs);
				} else {
					displayProductData("current data: ", jdbcRs);
				}
			}
			
			jdbcRs.close();
			
			//jdbcRs.commit(); --> not commit as it only for testing
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void deleteOperation() {
		try {
			
			System.out.println();
			System.out.println("--- Delete Operation ---");
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.setAutoCommit(false);
			
			jdbcRs.execute();
			
			while (jdbcRs.next()) {
				if (jdbcRs.getString("product_name").endsWith("Cable")) {
					displayProductData("deleting : " , jdbcRs);
					jdbcRs.deleteRow();					
				} else {
					displayProductData("current data: ", jdbcRs);
				}
			}
			
			jdbcRs.close();
			
			//jdbcRs.commit(); --> not commit as it only for testing
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static void insertOperation() {
		try {
			
			System.out.println();
			System.out.println("--- Insert Operation ---");
			JdbcRowSet jdbcRs = RowSetProvider.newFactory().createJdbcRowSet();
			
			jdbcRs.setCommand("SELECT * FROM Products");
			jdbcRs.setUrl(MySqlJdbcUtil.getUrl());
			jdbcRs.setUsername(MySqlJdbcUtil.getUser());
			jdbcRs.setPassword(MySqlJdbcUtil.getPassword());
			
			jdbcRs.setAutoCommit(false);
			
			jdbcRs.execute();

			// get the max product id
			int lastProductId = -1;
			jdbcRs.first();
			while (jdbcRs.next()) {
				lastProductId = Math.max(jdbcRs.getInt("product_id"), lastProductId);
			}
			lastProductId++;
						
			jdbcRs.moveToInsertRow();
			jdbcRs.updateInt("product_id", lastProductId);
			jdbcRs.updateString("product_name", "Curve LCD Monitor");
			jdbcRs.updateDouble("price", 350);
			jdbcRs.insertRow();

			jdbcRs.first();
			while (jdbcRs.next()) {
				if (jdbcRs.getInt("product_id") == lastProductId) 
					displayProductData("new : ", jdbcRs);
				else
					displayProductData("current data: ", jdbcRs);
			}
			
			jdbcRs.close();
			
			//jdbcRs.commit(); --> not commit as it only for testing
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		initdata();
		
		updateOperation();		
		deleteOperation();
		insertOperation();
		System.out.println("Done");
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
}
