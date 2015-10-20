package notify;

import java.util.Calendar;

import notify.logic.parser.DateTimeParser;

public class DateRange {

	private static final int startDateIndex = 0;
	private static final int startTimeIndex = 1;
	private static final int endDateIndex = 0;
	private static final int endTimeIndex = 1;

	private Calendar startDate = null;
	private Calendar startTime = null;
	private Calendar endDate = null;
	private Calendar endTime = null;

	public DateRange(String startDate, String endDate) {
		this(startDate, null, endDate, null);
	}
	
	public DateRange(Calendar startDate, Calendar endDate) {
		this(startDate, null, endDate, null);
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
			Calendar[] startDateInfo = DateTimeParser.parseDate(startDate);
			this.startDate = startDateInfo[startDateIndex];
			this.startTime = startDateInfo[startTimeIndex];
		}
	}
	
	public Calendar getStartDate() { 
		return this.startDate;
	}
	
	public void setStartTime(String startTime) {
		if(startTime != null) {
			this.startTime = DateTimeParser.parseTime(startTime);
		}
	}
	
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	public void setEndDate(String endDate) {
		
		if(endDate != null) {
			Calendar[] startDateInfo = DateTimeParser.parseDate(endDate);
			this.endDate = startDateInfo[endDateIndex];
			this.endTime = startDateInfo[endTimeIndex];
		}
	}
	
	public Calendar getEndDate() { 
		return this.endDate;
	}
	
	public void setEndTime(String endTime) {
		if(endTime != null) {
			this.endTime = DateTimeParser.parseTime(endTime);
		}
	}
	
	public Calendar getEndTime() { 
		return this.endTime;
	}
}