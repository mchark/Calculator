package com.calculator.writers;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.calculator.beans.Attendance;
import com.calculator.beans.Attendances;
import com.calculator.beans.Collaborator;
import com.calculator.beans.Collaborators;
import com.calculator.beans.Time;
import com.calculator.beans.Transaction;
import com.calculator.beans.Transactions;
import com.calculator.dbconnections.Database;
import com.calculator.dbconnections.DbFactory;
import com.calculator.dbconnections.Iprotect;
import com.calculator.dbconnections.Mysql;
import com.ie.jdbc.ResultSet;



/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Persister class responsible of getting, processing and saving data in Mysql database    																				*
 *                                                                         																							    *
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * _____________________________________________________________________________________________________________________________________________________________________
 * getCollaborators(String entity)          | gets collaborators for a given entity(ATSM, ASM, ...)																		|
 *__________________________________________|___________________________________________________________________________________________________________________________|
 * getTransactions(Collaborators cols)      | gets transactions of collaborators given by the function getCollaborators(String entity)									|
 * 			   							    |and saves them to their collaborators reference.																			|
 * _________________________________________|___________________________________________________________________________________________________________________________|
 * calculateAttendance(Collaborators cols)  | calculates attendances for collaborators given by the function getCollaborators(String entity)							|
 * 		       								| presence = timeBetween(in, out)																							|
 *            								| pause = timeBetween(in_level1, in_level2) || timeBetween(out_level2, out_level1) || timeBetween(out_level2, in_level1)	|
 *            								| and saves them to their collaborators reference																			|
 * _________________________________________|___________________________________________________________________________________________________________________________|
 * persistAttendance(Collaborators cols)    | loops over the list of collaborators and saves their attendances data in Mysql Database									|
 * _________________________________________|___________________________________________________________________________________________________________________________|
 * persistCollaborators(Collaborators cols) | loops over the list of collaborators and saves their infos in Mysql Database												|
 * _________________________________________|___________________________________________________________________________________________________________________________|
 * timeDifference(String t1, String t2)     | calculates difference between two dates written as Strings and returns a Time instance									|
 * _________________________________________|___________________________________________________________________________________________________________________________|
 */
public class Persister {

	
	private Database dbM = new DbFactory().getDatabaseConnection("Mysql");
	private Database dbI = new DbFactory().getDatabaseConnection("Iprotect");
	private Mysql conM = (Mysql) dbM.getDatabase();
	private Iprotect conI = (Iprotect) dbI.getDatabase();
	private int nxt_index = 0;
	
	
	public Time convertToTime(long value) {
		Time t = new Time((int) (value / 1000) % 60,
				(int) ((value / (1000 * 60)) % 60),
				(int) ((value / (1000 * 60 * 60)) % 24));
		
		return t;

	}
	
	public Time timeDifference(String t1, String t2) {

		Time result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date d1 = null;
		Date d2 = null;

		try {
			result = new Time();
			d1 = format.parse(t1);
			d2 = format.parse(t2);
			long diff = d2.getTime() - d1.getTime();
			result = convertToTime(diff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public Collaborators getCollaborators(String entity){
		Collaborators result = new Collaborators();
		try{
			ResultSet rs_col = (com.ie.jdbc.ResultSet) conI.conn.prepareStatement("SELECT NAME, FIRSTNAME, PERSONID, FREETEXT1 FROM PERSON WHERE FREETEXT2 = '"+ entity + "'").executeQuery();
//			ResultSet rs_col = (com.ie.jdbc.ResultSet) conI.conn.prepareStatement("SELECT NAME, FIRSTNAME, PERSONID, FREETEXT1 FROM PERSON WHERE personid = 2918").executeQuery();
			while (rs_col.next()) {
				Collaborator it_col = new Collaborator();
				it_col.setPersonId(rs_col.getLong("PERSONID"));
				it_col.setCin(rs_col.getString("FREETEXT1"));
				it_col.setFirstName(rs_col.getString("FIRSTNAME"));
				it_col.setName(rs_col.getString("NAME"));
				result.add(it_col);
			}
			rs_col.close();
		}catch(Exception e){
			System.err.println("Exception when getting collaborators: "+ e.getMessage());
		}
		return result;
	}
	
	public void getTransactions(Collaborators collaborators){
		
//		get current date and substract 7 days of it. When the program is executed it will get the transactions of the past week.
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");		
		String end = format.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String begin = format.format(cal.getTime());
// *******************************************************************************************************************************************************************************
		try{
			for(int i = 0 ; i < collaborators.size(); i++){
				ResultSet rs_trans = (com.ie.jdbc.ResultSet) conI.conn.prepareStatement("SELECT TIME, TRANSID, PRIMKEY FROM TRANSTABLE INNER JOIN TRANSACTION ON TRANSACTION.TRANSID = TRANSTABLE.TRANSID WHERE TIME >= #"
				+ begin
//				+ "2016-12-1"
				+ " 00:00:00# and TIME <= #"
				+ end 
//				+ "2016-12-21"
				+ " 00:00:00# AND TRANSACTION.TRANSTYPE = 2 AND  TABLEID = 19 AND PRIMKEY = " + collaborators.get(i).getPersonId()).executeQuery();
				Transactions col_transactions = new Transactions();
				while (rs_trans.next()) {
					ResultSet rs_text = (com.ie.jdbc.ResultSet) conI.conn.prepareStatement("SELECT TIME, TEXT, TRANSID FROM TRANSVIEW WHERE transid = " + rs_trans.getLong("TRANSID")).executeQuery();
					Transaction trans = new Transaction();
					while (rs_text.next()) {
						String date = rs_text.getTimestamp("TIME").toString();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
						Calendar c = Calendar.getInstance();
						c.setTime(sdf.parse(date));
						c.add(Calendar.HOUR, -1);
						date = sdf.format(c.getTime());
						trans.setDate(date.toString().split(" ")[0]);
						trans.setTime(date.toString().split(" ")[1]);
						trans.setTransId(rs_text.getLong("TRANSID"));
						trans.setPersonId(collaborators.get(i).getPersonId());
						if(rs_text.getString("TEXT").contains("Sortie"))
							trans.setType(0);
						else 
							trans.setType(1);
						trans.setText(rs_text.getString("TEXT"));
						trans.setAtt_id(nxt_index);
					}
					col_transactions.add(trans);
				}
				collaborators.get(i).setTransactions(col_transactions);
			}
			
		}catch(Exception e){
			System.err.println("Exception when getting collaborators transactions: "+ e.getMessage());
		}
	}
	
	public void calculateAttendance(Collaborators collaborators){
		for(int i = 0; i < collaborators.size(); i++){
			Attendances attendances = new Attendances();
			ArrayList<String> dates = collaborators.get(i).getTransactionsDates();
			
			for(int k = 0 ; k < dates.size(); k++){
//				System.out.println("attendance for : " + collaborators.get(i).getFirstName() + " Date : "+ dates.get(k));
				Transactions trs = new Transactions();
				trs = collaborators.get(i).getTransactionsByDate(dates.get(k));
				Time presence = new Time();
				Time pause = new Time();
				Attendance att = new Attendance();
				for(int j = 1; j < trs.size(); j++){
					att.setDate(dates.get(k));
//					if the current Transaction is in or out set its type to 1 if in and 0 if out.
					if(att.getEntry() == null && trs.get(j-1).getType() == 1){
						att.setEntry( trs.get(j-1).getTime() );
					}
					if(trs.get(j).getType() == 0 )
						att.setExit( trs.get(j).getTime() );
	
					if(trs.get(j).getType() == 0 && trs.get(j - 1).getType() == 1)
						presence.add(timeDifference(trs.get(j - 1).getDate() + " " + trs.get(j - 1).getTime(), trs.get(j).getDate() + " " + trs.get(j).getTime()));
					if(trs.get(j).getType() == 1 && trs.get(j - 1).getType() == 0)
						pause.add(timeDifference(trs.get(j - 1).getDate() + " " + trs.get(j - 1).getTime(), trs.get(j).getDate() + " " + trs.get(j).getTime()));
					if(trs.get(j).getType() == 0 && trs.get(j - 1).getType() == 0)
						pause.add(timeDifference(trs.get(j - 1).getDate() + " " + trs.get(j - 1).getTime(), trs.get(j).getDate() + " " + trs.get(j).getTime()));
					if(trs.get(j).getType() == 1 && trs.get(j - 1).getType() == 1)
						pause.add(timeDifference(trs.get(j - 1).getDate() + " " + trs.get(j - 1).getTime(), trs.get(j).getDate() + " " + trs.get(j).getTime()));
					
				}
				att.setTransactions(trs);
				att.setPause(pause.toString());
				att.setPresence(presence.toString());
				attendances.add(att);
				att = new Attendance();
			}
			collaborators.get(i).setAttendances(attendances);
		}
	}
	public void persistCollaborators(Collaborators collaborators){
		Statement st;
		try {
			st = conM.conn.createStatement();
			st.execute("DELETE FROM `collaborator` WHERE 1");
		} catch (Exception e) {
			System.out.println("Error deleting collaborators for update!"+e.getLocalizedMessage());
		}
		for(Collaborator it_col : collaborators){
			try {
				st = conM.conn.createStatement();
				st.execute("INSERT INTO `collaborator`(`PERSONID`, `NAME`, `FIRSTNAME`) VALUES ("
						+ it_col.getPersonId()
						+ ",'"
						+ it_col.getName()
						+ "','"
						+ it_col.getFirstName().replace("'", "") + "')");
				st.close();
				
			} catch (SQLException e) {
				System.out.println("Persisting collaborators data failed! "
						+ e.getMessage());
				System.out.println("INSERT INTO `collaborator`(`PERSONID`, `NAME`, `FIRSTNAME`) VALUES ("
						+ it_col.getPersonId()
						+ ",'"
						+ it_col.getName()
						+ "','"
						+ it_col.getFirstName().replace("'", "") + "')");
			}
			
		}
	}
	
	public void persistAttendanceData(Collaborators collaborators){
		Statement st;

		for(Collaborator it_col : collaborators){
			for(Attendance it_att : it_col.getAttendances()){
				try {

					st = conM.conn.createStatement();
					st.execute("INSERT INTO `attendance`(`PERSONID`, `DATE`, `ENTRY`, `EXIT`, `PRESENCE`, `PAUSE`) VALUES ("
							+ it_col.getPersonId()
							+ ",'"
							+ it_att.getDate()
							+ "','"
							+ it_att.getEntry()
							+ "','"
							+ it_att.getExit()
							+ "','"
							+ it_att.getPresence() + "','" + it_att.getPause() + "')");
					java.sql.ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID() AS lAST_ID FROM ATTENDANCE");
					rs.next();
					nxt_index = rs.getInt("LAST_ID");
					for(Transaction it_trans : it_att.getTransactions()){
						st.execute("INSERT INTO `transaction`(`TRANSID`, `PERSONID`, `ATTENDID`, `DATE`, `TIME`, `TYPE`, `TEXT`) "
									+" VALUES (" + it_trans.getTransId() + "," + it_trans.getPersonId()+", "+ nxt_index +", '"+ it_trans.getDate()+ "', '"+ it_trans.getTime()+"',"
											+ it_trans.getType()+", '"+it_trans.getText().replace("'", "")+"')");
					}
					st.close();

				} catch (SQLException e) {
					System.out.println("Persisting attendance data failed! "
							+ e.getLocalizedMessage());
					System.out.println("INSERT INTO `attendance`(`PERSONID`, `DATE`, `ENTRY`, `EXIT`, `PRESENCE`, `PAUSE`) VALUES ("
							+ it_col.getPersonId()
							+ ",'"
							+ it_att.getDate()
							+ "','"
							+ it_att.getEntry()
							+ "','"
							+ it_att.getExit()
							+ "','"
							+ it_att.getPresence() + "','" + it_att.getPause() + "')");
				}
			}
		}
	}		

}
