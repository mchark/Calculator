package com.calculator.beans;

/************************************************************************************************************************************************************************
 * 																																										*
 * @author Mounir CHARKAOUI																																				*
 * Time class representing time																																			*
 * 																																										*
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 * **********************************************************************************************************************************************************************
 ___________________________________________________________________________________________________________________________________________________________________________
 * seconds    | seconds																																					    |
 *____________|_____________________________________________________________________________________________________________________________________________________________|
 * minutes    | minutes																																					    |
 * ___________|_____________________________________________________________________________________________________________________________________________________________|
 * hours      | hours																																						|
 * ___________|_____________________________________________________________________________________________________________________________________________________________|
 * addTime    | adds time to the current time value	    																													|
 * ___________|_____________________________________________________________________________________________________________________________________________________________|
 */
public class Time {
	
	private int seconds;
	private int minutes;
	private int hours;

	public Time(int seconds, int minutes, int hours) {
		super();
		this.seconds = seconds;
		this.minutes = minutes;
		this.hours = hours;
	}

	public Time() {
		super();
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void add(Time value) {

		long milliV = value.getSeconds() * 1000 + value.getMinutes() * 60
				* 1000 + value.getHours() * 60 * 60 * 1000;
		long milliT = this.getSeconds() * 1000 + this.getMinutes() * 60 * 1000
				+ this.getHours() * 60 * 60 * 1000;
		long res = milliV + milliT;
		this.seconds = (int) (res / 1000) % 60;
		this.minutes = (int) ((res / (1000 * 60)) % 60);
		this.hours = (int) ((res / (1000 * 60 * 60)) % 24);
	}

	public String toString() {
		 String h = ""+this.hours;
		 String m = ""+this.minutes ;
		 String s = ""+this.seconds;
		if ( this.hours < 10)
			h = "0"+this.hours;
		if ( this.minutes < 10)
			m = "0"+this.minutes;
		if ( this.seconds < 10)
			s = "0"+this.seconds;
		return   "" + h + ":" + m + ":" + s;
	}

}
