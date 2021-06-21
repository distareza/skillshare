package com.jdbc.rowset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

public class JoinRowSetExample {

	/**
	 * query Inner Join
	 */
	public static void queryJoinRowSet() {
		try {
			System.out.println();
			System.out.println("=== Join Inner Join 2 Table ===");
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			CachedRowSet cachedProducts = RowSetProvider.newFactory().createCachedRowSet();
			CachedRowSet cachedBrand = RowSetProvider.newFactory().createCachedRowSet();
			
			cachedProducts.setCommand("SELECT * FROM Products");
			cachedBrand.setCommand("SELECT * FROM ProductBrand");
			
			cachedProducts.execute(con);
			cachedBrand.execute(con);
			
			JoinRowSet joinRs = RowSetProvider.newFactory().createJoinRowSet();
			
			joinRs.addRowSet(cachedProducts, "product_id");
			joinRs.addRowSet(cachedBrand, "product_id");
		
			while(joinRs.next()) {
				displayProductBrandData(joinRs);
			}
			
			joinRs.close();
			cachedProducts.close();
			cachedBrand.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void queryJoinRowSet2() {
		try {
			System.out.println();
			System.out.println("=== Join Inner Join 3 Table ===");
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			CachedRowSet cachedProducts = RowSetProvider.newFactory().createCachedRowSet();
			CachedRowSet cachedBrand = RowSetProvider.newFactory().createCachedRowSet();
			CachedRowSet cachedSupplier = RowSetProvider.newFactory().createCachedRowSet();
			
			cachedProducts.setCommand("SELECT * FROM Products");
			cachedBrand.setCommand("SELECT * FROM ProductBrand");
			cachedSupplier.setCommand("SELECT * FROM ProductSupplier");
			
			cachedProducts.execute(con);
			cachedBrand.execute(con);
			cachedSupplier.execute(con);
			
			JoinRowSet joinRs = RowSetProvider.newFactory().createJoinRowSet();
			
			joinRs.addRowSet(cachedProducts, "product_id");
			joinRs.addRowSet(cachedBrand, "product_id");
			joinRs.addRowSet(cachedSupplier, "product_id");
		
//			System.out.println(" joinRs.supportsCrossJoin() " + joinRs.supportsCrossJoin());
//			System.out.println(" joinRs.supportsFullJoin() " + joinRs.supportsFullJoin());
//			System.out.println(" joinRs.supportsInnerJoin() " + joinRs.supportsInnerJoin());
//			System.out.println(" joinRs.supportsLeftOuterJoin() " + joinRs.supportsLeftOuterJoin());
//			System.out.println(" joinRs.supportsRightOuterJoin() " + joinRs.supportsRightOuterJoin());
			
			while(joinRs.next()) {
				displayProductBrandSupplierData(joinRs);
			}
			
			joinRs.close();
			cachedProducts.close();
			cachedBrand.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void queryLeftOutterJoinRowSet() {
		try {
			System.out.println();
			System.out.println("=== Left Outter Join Join 2 Table ===");
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			CachedRowSet cachedProducts = RowSetProvider.newFactory().createCachedRowSet();
			CachedRowSet cachedBrand = RowSetProvider.newFactory().createCachedRowSet();
			
			cachedProducts.setCommand("SELECT * FROM Products");
			cachedBrand.setCommand("SELECT * FROM ProductBrand");
			
			cachedProducts.execute(con);
			cachedBrand.execute(con);
			
			JoinRowSet joinRs = RowSetProvider.newFactory().createJoinRowSet();

			if (joinRs.supportsRightOuterJoin())
				joinRs.setJoinType(JoinRowSet.RIGHT_OUTER_JOIN);
			//joinRs.setJoinType(JoinRowSet.LEFT_OUTER_JOIN);
			
			
			joinRs.addRowSet(cachedProducts, "product_id");
			joinRs.addRowSet(cachedBrand, "product_id");
		
			while(joinRs.next()) {
				displayProductBrandData(joinRs);
			}
			
			joinRs.close();
			cachedProducts.close();
			cachedBrand.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	public static void main(String[] args) throws Exception {
		initdata();

		queryJoinRowSet();
		queryJoinRowSet2();
		queryLeftOutterJoinRowSet();
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
		
		Object productBrand[][] = new Object[][] {
			{ 101, "Qenel" },
			{ 102, "Sonical" },
			{ 103, "Diallonic" },
			{ 104, "Sonical" },
			{ 105, "Diallonic" },
			{ 106, "Diallonic" },
			{ 107, "Qenel" }
		};
		
		Object supplier[][] = new Object[][] {
			{ 101, "Brocadero" },
			{ 102, "Brocadero" },
			{ 103, "Phlogistix" },
			{ 104, "Brocadero" },
			{ 105, "Phlogistix" },
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
			
			stmt.executeUpdate("TRUNCATE ProductBrand");
			pstmt = con.prepareStatement("Insert into ProductBrand values(?,?)");
			for (Object[] data : productBrand) {
				if (data == null) continue;
				pstmt.setInt(1, Integer.parseInt(data[0].toString()));
				pstmt.setString(2, data[1].toString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			stmt.executeUpdate("TRUNCATE ProductSupplier");
			pstmt = con.prepareStatement("Insert into ProductSupplier values(?,?)");
			for (Object[] data : supplier) {
				if (data == null) continue;
				pstmt.setInt(1, Integer.parseInt(data[0].toString()));
				pstmt.setString(2, data[1].toString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			System.out.println("!!! Data initilized !!!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}	

	private static void displayProductBrandData(RowSet rs) throws SQLException {
		displayProductBrandData("", rs);
	}

	private static void displayProductBrandData(String label, RowSet rs) throws SQLException {
		int id = rs.getInt("product_id");
		String name = rs.getString("product_name");
		String brand = rs.getString("brand_name");
		double price = rs.getDouble("price");
		
		
		if (label == null || label.isEmpty())
			System.out.format("\t%d|\t%-30s|\t%-20s|\t%,.2f\n", id, name, brand, price);
		else
			System.out.format("\t%-20s|\t%d|\t%-30s|\t%-20s|\t%,.2f\n", label, id, name, brand, price);
		
	}
	
	private static void displayProductBrandSupplierData(RowSet rs) throws SQLException {
		displayProductBrandSupplierData("", rs);
	}

	private static void displayProductBrandSupplierData(String label, RowSet rs) throws SQLException {
		int id = rs.getInt("product_id");
		String name = rs.getString("product_name");
		String brand = rs.getString("brand_name");
		String supplier = rs.getString("supplier_name");		
		double price = rs.getDouble("price");
		
		
		if (label == null || label.isEmpty())
			System.out.format("\t%d|\t%-30s|\t%-20s|\t%-20s|\t%,.2f\n", id, name, brand, supplier, price);
		else
			System.out.format("\t%-20s|\t%d|\t%-30s|\t%-20s|\t%-20s|\t%,.2f\n", label, id, name, brand, supplier, price);
		
	}
	
}
