package com.jdbc.advance;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.jdbc.util.MySqlJdbcUtil;

public class ClobExample {
	
	@SuppressWarnings("rawtypes")
	public static void iterateAndUpdateClob() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> dogMap = new HashMap() {
			private static final long serialVersionUID = 1L;
		{
			put("Amercan Eskimo", "american eskimo.txt");
			put("Pug", "pug.txt");
			put("Labrador", "labrador.txt");
		}};
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String query = "SELECT * FROM PuppyInfo";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String dogBreed = rs.getString("puppy_breed");
				String desc = dogMap.get(dogBreed);
				FileReader file = new FileReader("text/" + desc);
			
				rs.updateClob("puppy_desc", file);
				rs.updateRow();
				
				System.out.println("added description for " + dogBreed + " from " + desc);
				
				file.close();
				
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void retrieveClob() throws Exception {

		Map<String, String> dogMap = new HashMap() {
			private static final long serialVersionUID = 1L;
		{
			put("Amercan Eskimo", "american eskimo.txt");
			put("Pug", "pug.txt");
			put("Labrador", "labrador.txt");
		}};
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String query = "SELECT * FROM PuppyInfo";
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String dogBreed = rs.getString("puppy_breed");
				String desc = dogMap.get(dogBreed);
				
				Clob clob = rs.getClob("puppy_desc");
				Reader r = clob.getCharacterStream();
				FileWriter writer = new FileWriter("target/" + desc);
				
				int i;
				while ((i=r.read()) != -1)
					writer.write(i);
				writer.close();
				r.close();
				
				System.out.println("save description in " + desc);
				
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}	
	public static void main(String[] args) throws Exception {
		iterateAndUpdateClob();
		retrieveClob();
	}

}
