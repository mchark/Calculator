package com.calculator.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.calculator.beans.Collaborator;
import com.calculator.beans.Collaborators;
import com.calculator.beans.Transaction;



/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Writer class : Reads ATSM's PDB database (EXCEL File c:\hades\cols_ATSM.xlsx) to get  collaborators HR numbers														*
 * then Writes collaborators transaction in a csv file (C:\hades\kelio.csv) for kelio to import later.																	*
 * PDB columns order, to respect at all costs : Nom - Prénom - CIN - Service - Supérieur Hiérarchique - Supérieur fonctionnel - Matricule RH    						*														*
 *                                                                         																							    *
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * _____________________________________________________________________________________________________________________________________________________________________
 * writeTransaction(Collaborators cols)     | reads PDB excel file to get HR number for every collaborator and save its transaction data in C:\Hades\kelio.csv			|															|
 *__________________________________________|___________________________________________________________________________________________________________________________|
 * */

public class Writer {
	
	public void writeTransactions(Collaborators cols){
		try{
			
			FileInputStream pdb = new FileInputStream(new File("C:\\Hades\\cols_ATSM.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(pdb);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()){
				String name = new String();
				String firstName = new String();
				String hRnum = new String();
				String cin = new String();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				for(int i = 0; cellIterator.hasNext(); i++){
					Cell cell = cellIterator.next();
					switch(i){
//					0 : Nom - 1 : Prénom - 2 : CIN - 6 : Matricule RH
						case 0: name = cell.getStringCellValue();
						case 1: firstName = cell.getStringCellValue();
						case 2: cin = cell.getStringCellValue();
						case 6: hRnum = cell.getStringCellValue();
					}
//					Get collaborator reference by its name, firstname and CIN to save its HR number
					int index = cols.getCollaboratorIndexByname(name, firstName, cin);
					if(index!= -1)
						cols.get(index).setHRnum(hRnum);

				}				
			}
			pdb.close();		
		}catch(Exception e){
			System.out.println("Error reading excel file / updating collaborators : "+ e.getStackTrace());
		}
		
//		Writing collaborators with their transactions
		try{
			BufferedWriter file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Hades\\kelio.csv"), "utf-8"));
			
			for(Collaborator it_col : cols){
				for(Transaction it_trans : it_col.getTransactions()){
					String output = String.format(it_col.getHRnum() + ";" + format(it_trans.getDate(), "date") + ";" + format(it_trans.getTime(), "time"));
					file.write(output);
					file.newLine();
				}				
			}
			file.close();
		}catch(Exception e){
			System.out.println("Error writing kelio import file : " + e.getStackTrace());
		}
	}
	
	public String format(String input, String type){
		String result = new String();
		if(type.equals("time"))
			result = input.split(":")[0] + ":" + input.split(":")[1];
		if(type.equals("date"))
			result = input.split("-")[2] + "/" + input.split("-")[1] + "/" + input.split("-")[0];
		return result;
	}

}
