package com.calculator.dbconnections;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
/****************************************************************************************************************************************************************
 * @author Mounir CHARKAOUI																																		*
 * Concrete class of creating Mysql database connection and return a singelton refernce to it																*
 * Singelton design pattern																																		*
 * **************************************************************************************************************************************************************
 * **************************************************************************************************************************************************************
 * ************************************************************************************************************************************************************** */

public class Mysql implements Database{

	public Connection conn;
	private static Mysql db;

	private Mysql() {
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "Test";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String pwd = "";
		try{
			Class.forName(driver).newInstance();
			this.conn = (Connection) DriverManager.getConnection(url+dbName, userName, pwd);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public  static synchronized Mysql getMysqlDatabase() {
		if (db == null) {
			db = new Mysql();
		}
		return db;
	}
	
	public Mysql getDatabase(){
		return getMysqlDatabase();
	}

}
