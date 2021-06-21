package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.jdbc.util.MySqlJdbcUtil;

/**
 * https://dev.mysql.com/downloads/
 *
 */
public class JDBCIntro4 {

	public static void main(String[] args) throws Exception {
		
		Connection con = null;
		try {
			con = MySqlJdbcUtil.getConnection();
			
			if (con!=null)
				System.out.println("The connection has been successfully establish");
		} catch (SQLException ex) {
			System.err.println("A Connection error has occured :");
			ex.printStackTrace();
		} finally {
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}

}
