/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import java.util.Calendar;

/**
 * This Day enumeration indicates the list of days the system supports
 */
public enum Day {

	MONDAY		("MONDAY", "MON"), 
	TUESDAY		("TUESDAY", "TUE", "TUES"), 
	WEDNESDAY	("WEDNESDAY", "WED"), 
	THURSDAY	("THURSDAY", "THU", "THURS"), 
	FRIDAY		("FRIDAY", "FRI"), 
	SATURDAY	("SATURDAY", "SAT"), 
	SUNDAY		("SUNDAY", "SUN"), 
	INVALID		();

	// These are the variables required to store the required for each day
	String[] alias = null;

	private Day(String... alias) {

		this.alias = alias;

	}

	public Calendar getDate(Day day) {
		
		Calendar today = DateTimeParser.getInstance();
		
		int count = 0;
		for (int i = 0; i < Day.values().length; i++) {
			
			count++;

			if (day.equals(Day.values()[i]) == true) {
				
				break;
			
			}
			
		}

		today.set(Calendar.DATE, today.get(Calendar.DATE) + count);

		return today;

	}

	public Calendar getDay(String alias) {

		alias = alias.trim();

		Day day = Day.INVALID;
		
		for (int i = 0; i < Day.values().length; i++) {

			String[] aliases = Day.values()[i].getAlias();

			for (int x = 0; x < aliases.length; x++) {

				if (aliases[x].equalsIgnoreCase(alias)) {

					day = Day.values()[i];
					break;

				}

			}

		}

		return getDate(day);

	}

	/**
	 * This method returns the list of alias associated with the day
	 */
	private String[] getAlias() {

		return this.alias;

	}
}
