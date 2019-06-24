package com.donald.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	private static String url;
	private static String user;
	private static String password;
	private static final String PROPERTIES_FILE = "database.properties";
	private static ConnectionFactory cf;

	public static synchronized Connection getConnection() {

		if (cf == null) {
			cf = new ConnectionFactory();
		}

		return cf.createConnection();

	}

	private ConnectionFactory() {

		Properties prop = new Properties();
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		try (InputStream fis = loader.getResourceAsStream(PROPERTIES_FILE);) {

			prop.load(fis);
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LoggingUtil.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LoggingUtil.error(e.getMessage());
		}
	}

	private Connection createConnection() {

		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, user, password);
			LoggingUtil.debug("successfully connected to DB");
			System.out.println("successfully connected to DB");

		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			LoggingUtil.error("Failed to make DB connection");
			System.out.println("Failed to make DB connection");

		}

		return conn;

	}

}
