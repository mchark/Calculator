package com.calculator.agent;

import com.calculator.beans.Collaborators;
import com.calculator.writers.Persister;
import com.calculator.writers.Writer;


/****************************************************************************************************************************************************************
 * @author Mounir CHARKAOUI																																		*
 * Main class																																					*
 * 1. gets ATSM collaborators																																	*
 * 2. gets their transactions																																	*
 * 3. calculates their presence																																	*
 * 4. persists thei attendance in Mysql database																												*
 * 5. writes their transactions in csv file for kelio to import																									*
 * **************************************************************************************************************************************************************
 * **************************************************************************************************************************************************************
 * ************************************************************************************************************************************************************** */
public class Main {

	public static void main(String[] args) {

			Persister ps = new Persister();
			Collaborators cols = ps.getCollaborators("ATSM");
			ps.getTransactions(cols);
			ps.calculateAttendance(cols);
			ps.persistAttendances(cols);
			ps.persistCollaborators(cols);
			Writer wr = new Writer();
			wr.writeTransactions(cols);
			System.out.println("Done executing");

	}

}
