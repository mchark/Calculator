package com.calculator.beans;

import java.util.ArrayList;

/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Wrapper class for Collaborator used as a list of collaborators, helps to implement futur functionalities																*
 * 																																										*
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 *  _____________________________________________________________________________________________________________________________________________________________________
 * getCollaboratorIndexByName(String name, String firstname, String cin)       | gets the wanted collaborator reference to update its HR number  						|
 *__________________________________|__________________________________________|________________________________________________________________________________________|
 * display 																	   | displays all the colaborators, for debug usage											|
 * ____________________________________________________________________________|________________________________________________________________________________________|
 */

public class Collaborators extends ArrayList<Collaborator>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getCollaboratorIndexByname(String name, String firstName, String cin){
		int result = -1;
		for(int i = 0; i < this.size(); i++){
			if( (this.get(i).getName().equals(name) && this.get(i).getFirstName().equals(firstName)) || this.get(i).getCin().equals(cin)){
				result = i;
				break;
			}
		}
		return result;
	}
	
	public void display(){
		for(Collaborator it : this)
			System.out.println(" Nom: " + it.getName() + " prénom: " + it.getFirstName() + " matricule RH : " + it.getHRnum() );
	}
}
