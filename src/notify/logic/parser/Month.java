package notify.logic.parser;

public enum Month {
	
	JAN (1), FEB (2), MAR (3), APR (4), 
	MAY (5), JUN (6), JUL (7), AUG (8),
	SEP (9), OCT (10), NOV (11), DEC (12),
	INVALID (-1);
	
	private int value; 
	
	private Month(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
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
