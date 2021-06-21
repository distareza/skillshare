package com.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MySqlJdbcUtil {
	
	private static Properties properties;
	
	public static String getUrl() {
		if (properties == null) {
			properties = getMySQLProperty();
		}
		return properties.getProperty("url");
	}

	public static String getUser() {
		if (properties == null) {
			properties = getMySQLProperty();
		}
		return properties.getProperty("user");
	}

	public static String getPassword() {
		if (properties == null) {
			properties = getMySQLProperty();
		}
		return properties.getProperty("password");
	}

	
	public static Properties getMySQLProperty() {
		try (InputStream in = MySqlJdbcUtil.class.getResourceAsStream("/mysqldb.properties")){
			Properties prop = new Properties();
			prop.load(in);
			
			return prop; 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection con = null;
		try (InputStream in = MySqlJdbcUtil.class.getResourceAsStream("/mysqldb.properties")){
			
			Properties prop = new Properties();
			prop.load(in);
			
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			
			con = DriverManager.getConnection(url, user, password);		
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return con;
	}
	
	public static DataSource getDataSource() throws Exception {
		MysqlDataSource mysqlDS = null;
		try (InputStream in = MySqlJdbcUtil.class.getResourceAsStream("/mysqldb.properties")) {
			Properties prop = new Properties();
			prop.load(in);

			mysqlDS = new MysqlDataSource();
			mysqlDS.setUrl(prop.getProperty("url"));
			mysqlDS.setUser(prop.getProperty("user"));
			mysqlDS.setPassword(prop.getProperty("password"));
		} catch (Exception ex) {
			throw ex;
		}
		
		return mysqlDS;
	}
	
}
