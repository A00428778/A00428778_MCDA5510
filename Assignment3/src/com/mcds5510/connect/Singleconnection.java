
package com.mcds5510.connect;

import java.sql.Connection;
import java.sql.DriverManager;

public class Singleconnection { 
	private static Connection connection;
	private Singleconnection () {}
	public static Connection getConnection() throws Exception {
		connection=null;
		if (connection==null) {
			try {
				// This will load the MySQL driver, each DB has its own driver
				// Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Setup the connection with the DB
				connection = DriverManager.getConnection("jdbc:mysql://localhost/transactions?" // DTP I spelled
						// transactions wrong
						// oops
						+ "user=root&password=1023" // Creds
						+ "&useSSL=false&allowPublicKeyRetrieval=true" // b/c localhost
						+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"); // timezone
			}catch (Exception e) {
				throw e;
			}
		}
		return connection;	
	}

}
