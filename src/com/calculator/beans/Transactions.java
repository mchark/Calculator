package com.calculator.beans;

import java.util.ArrayList;


/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Wrapper class for Transaction used as a list of transactions, helps to implement futur functionalities																*
 * 																																										*
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * ______________________________________________________________________________________________________________________________________________________________________
 * getTrabsactionDates       | gets the dates of transaction made by a given collaborator														  						|
 *___________________________|__________________________________________________________________________________________________________________________________________|
*/
public class Transactions extends ArrayList<Transaction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayList<String> getTransactionsDates() {
		ArrayList<String> result = new ArrayList<String>();
		Boolean exist;
		for (Transaction it : this){
			exist = false;
			for(String its : result)
				if(it.getDate().equals( its)){
					exist = true;
					break;
				}
			if (!exist)
				result.add(it.getDate());
		}
		return result;
	}

}
