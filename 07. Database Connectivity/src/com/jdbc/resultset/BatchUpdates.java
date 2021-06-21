package com.jdbc.resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.jdbc.util.MySqlJdbcUtil;

public class BatchUpdates {

	public static void main(String[] args) throws Exception {

		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.addBatch("insert into Products values(101, 'Mother Board', 79)");
			stmt.addBatch("insert into Products values(102, 'Mouse', 15)");
			stmt.addBatch("insert into Products values(103, 'HDMI Cable', 5)");
			stmt.addBatch("insert into Products values(104, 'Keyboard', 12)");
			stmt.addBatch("insert into Products values(105, 'USB Cable', 3)");
			stmt.addBatch("insert into Products values(106, 'VGA Cable', 3)");
			
			int[] updateCounts = stmt.executeBatch();
			System.out.println(Arrays.toString(updateCounts));

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
