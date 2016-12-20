package com.calculator.dbconnections;

/****************************************************************************************************************************************************************
 * @author Mounir CHARKAOUI																																		*
 * factory class responsible of creating the wanted type of database connection																					*
 * factory design pattern																																		*
 * **************************************************************************************************************************************************************
 * **************************************************************************************************************************************************************
 * ************************************************************************************************************************************************************** */

public class DbFactory {
	
	public Database getDatabaseConnection(String type){
		Database dbM = Mysql.getMysqlDatabase();
		Database dbI = Iprotect.getIprotectDatabase();
		Database db = null;
		if(type.equals("Mysql"))
			db =  dbM;
		if(type.equals("Iprotect"))
			db =  dbI;
		return db;
	}

}
