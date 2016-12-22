package com.calculator.beans;

/************************************************************************************************************************************************************************
 * *
 * 
 * @author Mounir CHARKAOUI * Transaction class which will hold information
 *         about transaction, object representation of Iprotect transaction * *
 *         *********************************************************************
 *         *********************************************************************
 *         ****************************
 *         *****************************************
 *         *****************************
 *         *****************************************
 *         *******************************************************
 *         **************
 *         ********************************************************
 *         **************
 *         ********************************************************
 *         **************************
 *         _______________________________________________________________________________________________________________________________________________________________________
 *         ID | Mysql database auto incremented ID | __________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         | transID | transaction ID in iprotect Database | _________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         | personID | personid of the person generating the current
 *         transaction | _________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         | date | date when the transaction took place | _________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         | time | time when the transaction took placer | _________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         | type | 1 entry 0 exit | _________|
 *         ___________________________________________________________________________________________________________________________________________________________
 *         |
 */

public class Transaction {

	private int id;
	private Long transId;
	private Long personId;
	private long att_id;
	private String date;
	private String time;
	private int type;
	private String text;

	public Transaction() {
		super();
	}

	public Transaction(int id, Long transId, Long personId, Long att_id,
			String date, String time, int type, String text) {
		super();
		this.id = id;
		this.transId = transId;
		this.personId = personId;
		this.att_id = att_id;
		this.date = date;
		this.time = time;
		this.type = type;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public long getAtt_id() {
		return att_id;
	}

	public void setAtt_id(long att_id) {
		this.att_id = att_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
