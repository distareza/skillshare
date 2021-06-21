package com.jdbc.rowset;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;

import com.jdbc.util.MySqlJdbcUtil;

/**
 * Demonstrate WebRowSet, is maintaining the operation into xml file
 * 
 *
 */
public class WebRowSetExample {

	
	public static void queryWebRowSet() {
		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			con.setAutoCommit(false);
			
			WebRowSet webRs = RowSetProvider.newFactory().createWebRowSet();
			webRs.setCommand("SELECT * FROM Products");
			webRs.execute(con);
			
			String path = "target/ProductsXML.xml";
			
			File myFile = new File(path);
			FileWriter writer = new FileWriter(new File(path));
			System.out.println("writing the product table data to an XML File" + myFile.getAbsolutePath());
			webRs.writeXml(writer);

			writer.flush();
			writer.close();
			webRs.close();
			
		} catch (Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void navigateAndUpdateWebRowSet() {
		try {
			Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			con.setAutoCommit(false);
			
			WebRowSet webRs = RowSetProvider.newFactory().createWebRowSet();
			webRs.setCommand("SELECT * FROM Products");
			webRs.execute(con);
			
			while (webRs.next()) {
				if (webRs.getString("product_name").endsWith("Cable")) {
					webRs.deleteRow();
				}
				if (webRs.getString("product_name").contains("Mouse")) {
					webRs.updateDouble("price", webRs.getDouble("price") + 2);
					webRs.updateRow();
				}
			}
			
			webRs.moveToInsertRow();
			webRs.updateInt("product_id", 108);	
			webRs.updateString("product_name", "Curved LCD Monitor");
			webRs.updateDouble("price", 199);
			
			webRs.insertRow();
			webRs.moveToCurrentRow();
			
			String path = "target/ProductsXML.xml";
			
			File myFile = new File(path);
			FileWriter writer = new FileWriter(new File(path));
			System.out.println("writing the product table data to an XML File" + myFile.getAbsolutePath());
			webRs.writeXml(writer); // <-- will write all properties (Select Query Command, rowset-type, table-name, etc) , metadata (column name, type, column-definition, etc), 
			// operation (insert, update and deleted) status and (current, updated and old) data into xml file

			writer.flush();
			writer.close();

			webRs.acceptChanges(); // <-- write into database
			webRs.close();
			
		} catch (Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		initdata();

		queryWebRowSet();
		navigateAndUpdateWebRowSet();
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
}
