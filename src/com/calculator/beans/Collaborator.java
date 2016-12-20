package com.calculator.beans;

import java.util.ArrayList;

/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Attendance class which will hold information about attendance for a given person in a given date																		*
 * 																																										*
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 _______________________________________________________________________________________________________________________________________________________________________
 * ID          | Mysql database auto incremented ID																														|
 *_____________|________________________________________________________________________________________________________________________________________________________|
 * personId    | Collaborators Id in IProtect Database																													|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * cin         | collaborators CIN																																		|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * HRnum       | Collaborators Human ressoureces number																													|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * name        | Collaborators name																																		|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * firstname   | Collaborators firstname																																|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * Attendances | Collaborators attendances during the previous week of the program execution																			|
 --____________|________________________________________________________________________________________________________________________________________________________|
 * Transactions| Collaborators attendances during the previous week of the program execution																			|
 * ____________|________________________________________________________________________________________________________________________________________________________|
 * */

public class Collaborator {

	private int id;
	private Long personId;
	private String cin;
	private String HRnum;
	private String name;
	private String firstName;
	private Attendances attendances;
	private Transactions transactions;

	public Collaborator() {
		super();
	}

	public Collaborator(int id, Long personId, String cin, String hRnum,
			String name, String firstName, Attendances attendances,
			Transactions transactions) {
		super();
		this.id = id;
		this.personId = personId;
		this.cin = cin;
		this.HRnum = hRnum;
		this.name = name;
		this.firstName = firstName;
		this.attendances = attendances;
		this.transactions = transactions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getHRnum() {
		return HRnum;
	}

	public void setHRnum(String hRnum) {
		HRnum = hRnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Attendances getAttendances() {
		return attendances;
	}

	public void setAttendances(Attendances attendances) {
		this.attendances = attendances;
	}

	public Transactions getTransactions() {
		return transactions;
	}

	public void setTransactions(Transactions transactions) {
		this.transactions = transactions;
	}

	public ArrayList<Transaction> getTransactionsByDate(String date) {
		ArrayList<Transaction> result = new ArrayList<Transaction>();
		for (Transaction it : this.transactions)
			if (it.getDate().equals(date) && it.getPersonId() == this.personId)
				result.add(it);
		return result;
	}

	public ArrayList<String> getTransactionsDates() {
		ArrayList<String> result = new ArrayList<String>();
		Boolean exist;
		for (Transaction it : this.transactions) {
			exist = false;
			for (String its : result)
				if (it.getDate().equals(its)) {
					exist = true;
					break;
				}
			if (!exist)
				result.add(it.getDate());
		}
		return result;
	}

}
