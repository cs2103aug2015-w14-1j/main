/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import java.util.Calendar;

/**
 * This Month enumeration indicates the list of months the system supports
 */
public enum Month {
	
	JAN			(Calendar.JANUARY), 
	FEB 		(Calendar.FEBRUARY), 
	MAR 		(Calendar.MARCH),
	APR 		(Calendar.APRIL), 
	MAY 		(Calendar.MAY),
	JUN 		(Calendar.JUNE), 
	JUL 		(Calendar.JULY),
	AUG 		(Calendar.AUGUST),
	SEP			(Calendar.SEPTEMBER),
	OCT 		(Calendar.OCTOBER), 
	NOV 		(Calendar.NOVEMBER),
	DEC 		(Calendar.DECEMBER),
	INVALID 	(-1);
	
	// These are the variables required to store corresponding for each month
	private int value; 
	
	private Month(int value) {
		
		this.value = value;
	
	}
	
	/**
	 * This method returns value associated with the month object
	 * 
	 */
	public int getValue() {
		
		return this.value;

	}
	
	/**
	 * This method reads the string input and converts it into
	 * its corresponding month enum
	 * 
	 * @param month
	 *           name of month to be converted
	 * 
	 * @return Month
	 *           the corresponding month based on input string
	 *           invalid month is returned if invalid string is given
	 *           
	 */
	public static Month retrieve(String month) {
		
		Month selected =  Month.INVALID;
		
		if(month.equalsIgnoreCase(Month.JAN.toString())) {
			
			selected = Month.JAN;
		
		} else if(month.equalsIgnoreCase(Month.FEB.toString())) {
		
			selected = Month.FEB;
		
		} else if(month.equalsIgnoreCase(Month.MAR.toString())) {
		
			selected = Month.MAR;
		
		} else if(month.equalsIgnoreCase(Month.APR.toString())) {
		
			selected = Month.APR;
		
		} else if(month.equalsIgnoreCase(Month.MAY.toString())) {
		
			selected = Month.MAY;
		
		} else if(month.equalsIgnoreCase(Month.JUN.toString())) {
		
			selected = Month.JUN;
		
		} else if(month.equalsIgnoreCase(Month.JUL.toString())) {
		
			selected = Month.JUL;
		
		} else if(month.equalsIgnoreCase(Month.AUG.toString())) {
		
			selected = Month.AUG;
		
		} else if(month.equalsIgnoreCase(Month.SEP.toString())) {
		
			selected = Month.SEP;
		
		} else if(month.equalsIgnoreCase(Month.OCT.toString())) {
		
			selected = Month.OCT;
		
		} else if(month.equalsIgnoreCase(Month.NOV.toString())) {
		
			selected = Month.NOV;
		
		} else if(month.equalsIgnoreCase(Month.DEC.toString())) {
		
			selected = Month.DEC;
		
		}
		
		return selected;
		
	}
}
