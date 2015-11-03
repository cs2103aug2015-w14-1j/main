package notify;

import java.util.Calendar;

import notify.logic.parser.DateTimeParser;

public class DateRange {
	
	private Calendar startDate = null;
	private Calendar startTime = null;
	private Calendar endDate = null;
	private Calendar endTime = null;
	
	private static final String ERROR_DATE_RANGE = "You have entered an invalid date range.";
	
	public DateRange() { 
		
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

	public Calendar getNowTime() {
		return Calendar.getInstance();
	}
	
	public void setStartDate(String startDate) {
		
		if(startDate != null) {
			startDate = startDate.trim();
			System.out.println(startDate);
			this.startDate = DateTimeParser.parseDate(startDate);
		}
	}
	
	public Calendar getStartDate() { 
		return this.startDate;
	}
	
	public void setStartTime(String startTime) {
	
		if(startTime != null) {
			startTime = startTime.trim();
			this.startTime = DateTimeParser.parseTime(startTime);
			
			if(isSameDay() == true && this.startTime.after(this.endTime)) {
				throw new IllegalArgumentException(ERROR_DATE_RANGE);
			}
		}
	}
	
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	public void setEndDate(String endDate) {
		
		if(endDate != null) {
			endDate = endDate.trim();
			this.endDate = DateTimeParser.parseDate(endDate.trim());
			
			if(this.startDate != null) {
				if(this.endDate.equals(this.startDate) == false && this.endDate.before(this.startDate)) {
					throw new IllegalArgumentException(ERROR_DATE_RANGE);
				}
			}
		}
	}
	
	public Calendar getEndDate() { 
		return this.endDate;
	}
	
	public void setEndTime(String endTime) {
		if(endTime != null) {
			endTime = endTime.trim();
			this.endTime = DateTimeParser.parseTime(endTime);
			
			if(isSameDay() == true && this.endTime.before(this.startTime)) {
				throw new IllegalArgumentException(ERROR_DATE_RANGE);
			}
		}
	}
	
	public Calendar getEndTime() { 
		return this.endTime;
	}
	
	public boolean isSameDay() {
		boolean result = true;
		
		if(this.getStartDate() != null && this.getEndDate() != null) {
			
			if(this.getStartDate().get(Calendar.DATE) != this.getEndDate().get(Calendar.DATE)) { 
				result = false; 
			}
			
			if(this.getStartDate().get(Calendar.MONTH) != this.getEndDate().get(Calendar.MONTH)) { 
				result = false; 
			}
			
			if(this.getStartDate().get(Calendar.YEAR) != this.getEndDate().get(Calendar.YEAR)) { 
				result = false; 
			}
			
		}
			
		return result;
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