package com.jdbc.advance;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jdbc.util.MySqlJdbcUtil;

public class BlobExample {

	public static void insertPuppyInfo() throws Exception {
		PreparedStatement pstmt = null;
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()){
			
			pstmt = con.prepareStatement("INSERT INTO PuppyInfo (puppy_id, puppy_breed, puppy_photo) values (?, ?, ?)");
			pstmt.setInt(1, 101);
			pstmt.setString(2, "Amercan Eskimo");
			pstmt.setBlob(3, new FileInputStream("images/american escimo.jpg"));
			
			pstmt.executeUpdate();			
			System.out.println("Images inserted successfully");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static void updatePuppyInfo() throws Exception {
		PreparedStatement pstmt = null;
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()){
			
			pstmt = con.prepareStatement("UPDATE PuppyInfo SET puppy_photo=? WHERE puppy_Breed = ?");
			pstmt.setBlob(1, new FileInputStream("images/pug.jpg"));
			pstmt.setString(2, "Pug");
			
			pstmt.executeUpdate();
			
			pstmt.setBlob(1, new FileInputStream("images/labrador.jpg"));
			pstmt.setString(2, "Labrador");
			pstmt.executeUpdate();
			
			System.out.println("Images uploaded successfully");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws Exception {

		updatePuppyInfo();
		insertPuppyInfo();
		
	}

}
