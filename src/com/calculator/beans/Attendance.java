package com.calculator.beans;

/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Attendance class which will hold information about attendance for a given person in a given date																		*
 * 																																										*
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 _______________________________________________________________________________________________________________________________________________________________________
 * ID       | Mysql database auto incremented ID																														|
 *__________|___________________________________________________________________________________________________________________________________________________________|
 * presence | presence time for which the collaborator was inside ATSM workplace																						|
 * _________|___________________________________________________________________________________________________________________________________________________________|
 * pause    | break time for which the collaborator was outside ATSM workplace																							|
 * _________|___________________________________________________________________________________________________________________________________________________________|
 * exit     | when the collaborator left ATSM workplace for the day																										|
 * _________|___________________________________________________________________________________________________________________________________________________________|
 * entry    | when the collaborator got inside ATSM collaborator																										|
 * _________|___________________________________________________________________________________________________________________________________________________________|
 * date		| the date during which the above infos happened																											|
 * _________|___________________________________________________________________________________________________________________________________________________________|
 */

public class Attendance {

	private int id;
	private String presence;
	private String pause;
	private String entry;
	private String exit;
	private String date;
	private Transactions transactions;

	public Attendance() {
		super();
	}

	public Attendance(int id, String presence, String pause, String entry,
			String exit, String date) {
		super();
		this.id = id;
		this.presence = presence;
		this.pause = pause;
		this.entry = entry;
		this.exit = exit;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPresence() {
		return presence;
	}

	public void setPresence(String presence) {
		this.presence = presence;
	}

	public String getPause() {
		return pause;
	}

	public void setPause(String pause) {
		this.pause = pause;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Transactions getTransactions() {
		return transactions;
	}

	public void setTransactions(Transactions transactions) {
		this.transactions = transactions;
	}
	
	

}
