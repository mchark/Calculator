package com.calculator.dbconnections;

import java.sql.Connection;
import java.util.Properties;
import com.ie.jdbc.Driver;

/****************************************************************************************************************************************************************
 * @author Mounir CHARKAOUI																																		*
 * Concrete class of creating IProtect database connection and return a singelton refernce to it																*
 * Singelton design pattern																																		*
 * **************************************************************************************************************************************************************
 * **************************************************************************************************************************************************************
 * ************************************************************************************************************************************************************** */

public class Iprotect implements Database{
	
	public Connection conn;
	private static Iprotect db;

	private Iprotect() {

		String conString = "jdbc:iprotect://10.110.29.130:20000";
		Properties info = new Properties();
		info.setProperty("protocol", "jdbc:iprotect:");
		info.setProperty("ip", "10.110.29.130");
		info.setProperty("port", "20000");
		info.setProperty("db", "demo");
		info.setProperty("user", "root");
		info.setProperty("password", "AxA123456");

		try {
			Driver driver = (Driver) Class.forName("com.ie.jdbc.Driver")
					.newInstance();
			conn = driver.connect(conString, info);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public  static synchronized Iprotect getIprotectDatabase() {
		if (db == null) {
			db = new Iprotect();
		}
		return db;
	}
	
	public Iprotect getDatabase(){
		return getIprotectDatabase();
	}
	
	

}
