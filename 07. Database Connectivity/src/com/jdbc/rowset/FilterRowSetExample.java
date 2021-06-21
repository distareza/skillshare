package com.jdbc.rowset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

public class FilterRowSetExample {
	
	private class ProductPriceFilter implements Predicate {

		private double lowPrice;
		private double highPrice;
		private String colName = null;
		private int colNumber = -1;
		
		public ProductPriceFilter(double lo, double hi, int colNumber) {
			this.lowPrice = lo;
			this.highPrice = hi;
			this.colNumber = colNumber;
		}

		@SuppressWarnings("unused")
		public ProductPriceFilter(double lo, double hi, String colName) {
			this.lowPrice = lo;
			this.highPrice = hi;
			this.colName = colName;
		}

		@Override
		public boolean evaluate(RowSet rs) {
			if (rs == null) return false;
			FilteredRowSet filterRs = (FilteredRowSet) rs;
			try {
				double columnValue = filterRs.getDouble(this.colNumber);				
				if ((columnValue >= this.lowPrice) && (columnValue <= highPrice))
					return true;					
			} catch (Exception ex) {
			}
			return false;
		}

		@Override
		public boolean evaluate(Object value, int column) throws SQLException {
			boolean evaluation = true;
			if (column == this.colNumber) {
				double columnValue = ((Double) value).doubleValue();
				if (columnValue >= this.lowPrice && columnValue <=this.highPrice) {
					evaluation = true;
				} else {
					evaluation = false;
				}
			}
			return evaluation;
		}

		@Override
		public boolean evaluate(Object value, String columnName) throws SQLException {
			boolean evaluation = true;
			if (columnName.equalsIgnoreCase(this.colName)) {
				double columnValue = ((Double) value).doubleValue();
				if (columnValue >= this.lowPrice && columnValue <=this.highPrice) {
					evaluation = true;
				} else {
					evaluation = false;
				}
			}
			return evaluation;
		}
		
	}
	
	public void queryFilteredRowSet() {

		System.out.println();
		System.out.println("==== Query Filter RowSet ====");
		FilteredRowSet filterRs = null;
		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			filterRs = RowSetProvider.newFactory().createFilteredRowSet();
			
			filterRs.setCommand("SELECT * FROM Products");
			
			filterRs.execute(con);
			
			filterRs.setFilter(new ProductPriceFilter(0.0, 20.0, 3));
			
			filterRs.beforeFirst();
			while (filterRs.next()) {
				displayProductData(filterRs);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void navigateAndupdateFilteredRowSet() {
		System.out.println();
		System.out.println("==== Navigate and Insert on Filter RowSet ====");
		FilteredRowSet filterRs = null;
		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			con.setAutoCommit(false);
			filterRs = RowSetProvider.newFactory().createFilteredRowSet();
			
			filterRs.setCommand("SELECT * FROM Products");
			
			filterRs.execute(con);
			
			filterRs.setFilter(new ProductPriceFilter(0.0, 20.0, 3));
			
			filterRs.moveToInsertRow();
			filterRs.updateInt("product_id", 109);
			filterRs.updateString("product_name", "Mouse Pad");
			filterRs.updateDouble("price", 7);
			
			filterRs.insertRow();
			filterRs.moveToCurrentRow();

			filterRs.beforeFirst();
			while (filterRs.next()) {
				displayProductData(filterRs);
			}
			
			filterRs.acceptChanges(con); // <-- Required because FilterRowSet is implemented from CachedRowSet
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public void navigateAndRemoveFilteredRowSet() {
		System.out.println();
		System.out.println("==== Navigate and Insert on Filter RowSet ====");
		FilteredRowSet filterRs = null;
		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			con.setAutoCommit(false);
			filterRs = RowSetProvider.newFactory().createFilteredRowSet();
			
			filterRs.setCommand("SELECT * FROM Products");
			
			filterRs.execute(con);
			
			filterRs.setFilter(new ProductPriceFilter(0.0, 20.0, 3));
			
			filterRs.beforeFirst();
			while (filterRs.next()) {
				if (filterRs.getString("product_name").endsWith("Cable")){
					displayProductData("deleted: " , filterRs);
					filterRs.deleteRow();
				} else 
					displayProductData("current", filterRs);
			}
			
			filterRs.acceptChanges(con); // <-- Required because FilterRowSet is implemented from CachedRowSet
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}


	public static void main(String[] args) throws Exception {
		initdata();
		
		(new FilterRowSetExample()).queryFilteredRowSet();
		(new FilterRowSetExample()).navigateAndupdateFilteredRowSet();
		(new FilterRowSetExample()).navigateAndRemoveFilteredRowSet();
	}

	private static void initdata() throws Exception {
		Object productData[][] = new Object[][] { 
				{101, "Mother Board", 79}, 
				{102, "Mouse", 15}, 
				{103, "HDMI Cable", 5}, 
				{104, "Keyboard", 12},
				{105, "USB Cable", 3}, 
				{106, "VGA Cable", 3}, 
				{107, "LCD Monitor", 159},
				{108, "LCD Curve Monitor", 350}
		};
		

		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("TRUNCATE Products");
			
			PreparedStatement pstmt = con.prepareStatement("Insert into Products values(?,?,?)");
			for (Object[] data : productData) {
				if (data == null) continue;
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
}
