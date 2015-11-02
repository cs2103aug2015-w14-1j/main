package notify.logic.parser;

public enum Month {
	
	JAN (0), FEB (1), MAR (2), APR (3), 
	MAY (4), JUN (5), JUL (6), AUG (7),
	SEP (8), OCT (9), NOV (10), DEC (11),
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
