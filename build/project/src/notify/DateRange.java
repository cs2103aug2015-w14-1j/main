//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify;

import java.util.Calendar;

import notify.logic.parser.DateTimeParser;

public class DateRange {
	
	// These are the variables required to store the individual start and end range
	private Calendar startDate;
	private Calendar startTime;
	private Calendar endDate;
	private Calendar endTime;
	
	// These are the variables required to store the individual start and end range
	private static final String ERROR_DATE_RANGE = "You have entered an invalid date range.";
	
	public DateRange() { 
		
		this.startDate = null;
		this.startTime = null;
		this.endDate = null;
		this.endTime = null;
		
	}
	
	public DateRange(Calendar startDate, Calendar startTime, Calendar endDate, Calendar endTime) {
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	
	}
	
	public DateRange(String startDate, String startTime, String endDate, String endTime) {
	
		setStartDate(startDate);
		setEndDate(endDate);
		setStartTime(startTime);
		setEndTime(endTime);
	
	}
	
	/**
	 * Returns the start date indicated in the DateRange
	 * 
	 *  @return Calendar start date is instantiated with default time values in the date range
	 *  			only the date variables are useful. Returns null if end date do not exists
	 * 
	 */
	public Calendar getStartDate() { 
	
		return this.startDate;
	
	}
	
	/**
	 * Returns the start time indicated in the DateRange
	 * 
	 *  @return Calendar start time is instantiated with default date values in the date range
	 *  			only the time variables are useful. Returns null if end time do not exists
	 * 
	 */
	public Calendar getStartTime() {
		
		return this.startTime;
	
	}
	
	/**
	 * Returns the end date indicated in the DateRange
	 * 
	 *  @return Calendar end date is instantiated with default time values in the date range
	 *  			only the date variables are useful. Returns null if end date do not exists
	 * 
	 */
	public Calendar getEndDate() { 
		
		return this.endDate;
	
	}
	
	/**
	 * Returns the end time indicated in the DateRange
	 * 
	 *  @return Calendar end time is instantiated with default date values in the date range
	 *  			only the time variables are useful. Returns null if end time do not exists
	 * 
	 */
	public Calendar getEndTime() { 
		
		return this.endTime;
	
	}
	
	/**
	 * Sets the start date to be stored in the DateRange
	 * 
	 *  @param startDate is processed into a Calendar with the date values instantiated
	 *  			the time variables are instantiated to default
	 * 
	 */
	public void setStartDate(String startDate) {
		
		if(startDate != null) {
	
			startDate = startDate.trim();
			this.startDate = DateTimeParser.parseDate(startDate);
		
		}
	
	}
	
	/**
	 * Sets the start time to be stored in the DateRange
	 * 
	 *  @param startTime is processed into a Calendar with the time values instantiated
	 *  			the date variables are instantiated to default
	 * 
	 */
	public void setStartTime(String startTime) {
	
		if(startTime != null) {
	
			startTime = startTime.trim();
			this.startTime = DateTimeParser.parseTime(startTime);
			
			if(isSameDay() == true) { 
				
				if(this.startTime.after(this.endTime) || this.startTime.equals(this.endTime)) {
					
					throw new IllegalArgumentException(ERROR_DATE_RANGE);
					
				}
			
			}
		
		}
	
	}
	
	/**
	 * Sets the end date to be stored in the DateRange
	 * 
	 *  @param endDate is processed into a Calendar with the date values instantiated
	 *  			the time variables are instantiated to default
	 * 
	 */
	public void setEndDate(String endDate) {
		
		if(endDate != null) {
	
			endDate = endDate.trim();
			this.endDate = DateTimeParser.parseDate(endDate.trim());
			
			if(isSameDay() == true) { 
			
				if(this.endDate.before(this.startDate)) {
				
					throw new IllegalArgumentException(ERROR_DATE_RANGE);
				
				}
			
			}
		
		}
	
	}
	

	/**
	 * Sets the end time to be stored in the DateRange
	 * 
	 *  @param endTime is processed into a Calendar with the time values instantiated
	 *  			the date variables are instantiated to default
	 * 
	 */
	public void setEndTime(String endTime) {
	
		if(endTime != null) {
			
			endTime = endTime.trim();
			this.endTime = DateTimeParser.parseTime(endTime);
			
			if(isSameDay() == true) { 
				
				if(this.endTime.before(this.startTime)) {
			
					throw new IllegalArgumentException(ERROR_DATE_RANGE);
				
				}
			
			}
				
		}
	
	}
	
	/**
	 * Returns if the start and end dates are the same
	 * 
	 *  @param true if both days have the same date or either date is null
	 * 
	 */
	public boolean isSameDay() {
	
		boolean isSameDay = true;
		
		if(this.getStartDate() != null && this.getEndDate() != null) {
			
			if(this.getStartDate().get(Calendar.DATE) != this.getEndDate().get(Calendar.DATE)) { 
	
				isSameDay = false; 
			
			}
			
			if(this.getStartDate().get(Calendar.MONTH) != this.getEndDate().get(Calendar.MONTH)) { 
			
				isSameDay = false; 
			
			}
			
			if(this.getStartDate().get(Calendar.YEAR) != this.getEndDate().get(Calendar.YEAR)) { 
			
				isSameDay = false; 
			
			}
			
		}
			
		return isSameDay;

	}
	
	public String toString() {
	
		String output = "";
		
		if(startDate != null) { 
			output += "start: " + startDate.get(Calendar.DATE) + "-" + startDate.get(Calendar.MONTH) + "-" + startDate.get(Calendar.YEAR);
			if(startTime != null) { output += " " + startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE); }
		}
		
		if(endDate != null) { 
			output += " to " + endDate.get(Calendar.DATE) + "-" + endDate.get(Calendar.MONTH) + "-" + endDate.get(Calendar.YEAR);
			if(endTime != null) { output += " " + endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE); }
		}
		
		return output;
	}
	
}