package com.jdbc.advance;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.jdbc.util.MySqlJdbcUtil;

public class BlobExample2 {

	@SuppressWarnings("resource")
	public static void getPuppyInfo() throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()){
			
			CachedRowSet cachedRs = RowSetProvider.newFactory().createCachedRowSet();
			cachedRs.setCommand("SELECT * FROM PuppyInfo");

			cachedRs.execute(con);
			cachedRs.beforeFirst();
			
			while (cachedRs.next()) {
				byte byteArray[] = cachedRs.getBytes("puppy_photo");
				FileOutputStream output = new FileOutputStream("target/" + cachedRs.getInt("puppy_id") + ".jpg");
				output.write(byteArray);
				System.out.println("Image of " + cachedRs.getString("puppy_breed") + " saved to " + cachedRs.getInt("puppy_id") + ".jpg");
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws Exception {
		getPuppyInfo();
	}

}
