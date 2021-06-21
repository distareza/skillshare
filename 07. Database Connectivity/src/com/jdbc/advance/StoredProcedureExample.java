package com.jdbc.advance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.jdbc.util.MySqlJdbcUtil;

public class StoredProcedureExample {
	
	public static void executeSP() throws Exception {
		
		Object[][] datas = new Object[][] {
			{101, "Alan Alford", 10020, "alan.alford@hotmail.com"},
			{102, "Claudia Sand", 10015, "claudia.sand@hotmail.com"},
			{103, "Harriet Lipsey", 10020, "harriet.lipsey@hotmail.com"},
		};
		
		
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			CallableStatement cs = con.prepareCall("{call Create_Tables(?, ?, ?, ?)}");
			
			for (Object[] data: datas) {
				cs.setInt(1, Integer.parseInt(data[0].toString()));
				cs.setString(2,  data[1].toString());
				cs.setInt(3,  Integer.parseInt(data[2].toString()));
				cs.setString(4, data[3].toString());
				
				cs.execute();
			}
			
			cs.close();
			
			System.out.println("Stored Procedure Called Successfully");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void createAndRunSP() throws Exception {
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			Statement stmt = con.createStatement();
			String dropQuery = "DROP PROCEDURE IF EXISTS Select_Student";
			String selectQuery = 
						"	CREATE PROCEDURE Select_Student (IN student_id INT, OUT student_name VARCHAR(255), OUT sdept_id INT) " +
						"	BEGIN " +
						" 		SELECT 									" +
						"			s.stud_name, sd.dept_id into student_name, sdept_id " +
						"		FROM Student s, StudentDepartment sd 	" +
						" 		WHERE s.stud_id = student_id 			" +
						"		AND sd.stud_id = student_id; 			" +
						"	END ";
			
			stmt.execute(dropQuery);
			stmt.execute(selectQuery);
			
			stmt.close();
			System.out.println();
			System.out.println("Stored Procedure #1 create successfully ");
			
			System.out.println();
			CallableStatement cs = con.prepareCall("{call Select_Student(?, ?, ?)}");
			cs.setInt(1, 102);
			
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.INTEGER);
			cs.execute();
			

			System.out.println("Student Name = " + cs.getString(2));
			System.out.println("Student Departement_ID = " + cs.getInt(3));
			cs.close();
		}
	}
	
	public static void createAndRunSP2() throws Exception {
		try (Connection con = MySqlJdbcUtil.getDataSource().getConnection()) {
			Statement stmt = con.createStatement();
			String dropQuery = "DROP PROCEDURE IF EXISTS Update_Student";
			String selectQuery = 
						"	CREATE PROCEDURE Update_Student (IN student_id INT, INOUT student_email VARCHAR(255)) " +
						"	BEGIN " +
						" 		DECLARE temp_email VARCHAR(255);										" +
						" 		SELECT email into temp_email from Student where stud_id = student_id;	" +
						" 		UPDATE Student set email = student_email where stud_id = student_id;	" +
						"		SET student_email = temp_email; 										" +
						"	END ";
			
			stmt.execute(dropQuery);
			stmt.execute(selectQuery);
			
			stmt.close();
			System.out.println();
			System.out.println("Stored Procedure #2 create successfully ");
			
			System.out.println();
			CallableStatement cs = con.prepareCall("{call Update_Student(?, ?)}");
			cs.setInt(1, 102);
			cs.setString(2, "claudia.sand@loonycorn.com");
			cs.registerOutParameter(2, Types.VARCHAR);
			
			cs.execute();
			System.out.println("The Old Email Id = " + cs.getString(2));
			
			cs.close();
		}
	}

	
	public static void main(String[] args) throws Exception {
		initData();
		executeSP();
		
		createAndRunSP();
		createAndRunSP2();
	}

	public static void initData() throws Exception {
		Connection con = MySqlJdbcUtil.getDataSource().getConnection();
			
		Statement stmt = con.createStatement();
		stmt.executeUpdate("TRUNCATE StudentDepartment");
		stmt.executeUpdate("DELETE FROM Student");
		
		System.out.println("data cleared or initiate");
	}
}
