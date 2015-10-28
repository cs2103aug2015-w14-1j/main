package notify.logic.parser;

import java.util.Calendar;

import notify.DateRange;

import org.apache.commons.lang3.StringUtils;

public class DateTimeParser {

	private static final String[] KEYWORD_TODAY = { "TODAY", "TDY", "LATER" };
	private static final String[] KEYWORD_TOMORROW = { "TOMORROW", "TMR", "TMRW" };
	private static final String[] KEYWORD_NEXT_WEEK = { "NEXT WEEK" };
	private static final String[] KEYWORD_NEXT_MONTH = { "NEXT MONTH" };
	private static final String[] KEYWORD_NEXT_YEAR = { "NEXT YEAR" };
	
	private static final int OFFSET_DAY = 1;
	private static final int OFFSET_WEEK = 7;
	private static final int OFFSET_MONTH = 1;
	private static final int OFFSET_YEAR = 1;
	
	private static final int TIME_MERIDIEM_LENGTH = 2;
	
	private static final int DATE_DEFAULT_YEAR = 0;
	private static final int DATE_DEFAULT_MONTH = 0;
	private static final int DATE_DEFAULT_DAY = 1;
	private static final int DATE_DEFAULT_HOUR = 0;
	private static final int DATE_DEFAULT_MINUTE = 0;
	private static final int DATE_DEFAULT_SECOND = 0;
	
	private static final int TIME_HOUR_FIRST_INDEX = 0;
	private static final int TIME_HOUR_SECOND_INDEX = 1;
	private static final int TIME_HOUR_THIRD_INDEX = 2;
	
	private static final int TIME_HOUR_MIN = 0;
	private static final int TIME_HOUR_MAX = 23;
	private static final int TIME_MINUTE_MIN = 0;
	private static final int TIME_MINUTE_MAX = 59;
	private static final int TIME_DIFFERENCE = 12;
	
	private static final int DATE_DAY_MIN = 1;
	private static final int DATE_DAY_MAX = 31;
	private static final int DATE_MONTH_MIN = 1;
	private static final int DATE_MONTH_MAX = 12;
	private static final int DATE_YEAR_MIN = 1900;
	private static final int DATE_YEAR_MAX = 2100;
	
	private static final int SEPERATOR_DAY_INDEX = 0;
	private static final int SEPERATOR_MONTH_INDEX = 1;
	private static final int SEPERATOR_YEAR_INDEX = 2;
	
	private static final int TIME_HOUR = 1;
	private static final int TIME_HOUR_MINUTE = 3;
	private static final int TIME_FULL = 4;

	private static final String DATE_SEPERATOR_SLASH = "\\/";
	private static final String DATE_SEPERATOR_SPACE = " ";
	private static final int DATE_SEPERATOR_MIN = 2;
	private static final int DATE_SEPERATOR_MAX = 3;
	
	public static final String[] DATETIME_PROMPT_KEYWORDS = { "BY", "ON", "FROM" };
	public static final String[] DATETIME_KEYWORDS = { "AT", "FROM", "TO" };
	public static final String KEYWORD_BY = DATETIME_PROMPT_KEYWORDS[0];
	public static final String KEYWORD_ON = DATETIME_PROMPT_KEYWORDS[1];
	public static final String KEYWORD_FROM = DATETIME_PROMPT_KEYWORDS[2];
	public static final String KEYWORD_AT = DATETIME_KEYWORDS[0];
	public static final String KEYWORD_TO = DATETIME_KEYWORDS[2];
	public static final int KEYWORD_PROMPT_INDEX = 0; 
	
	public static final int DATE_PARAM_INDEX = 0;
	public static final int TIME_PARAM_INDEX = 1;
	public static final int FROM_PARAM_INDEX = 0;
	public static final int TO_PARAM_INDEX = 1;
	public static final int KEYWORD_NOT_FOUND_INDEX = -1;
	public static final String ESCAPE_KEYWORD = "/";
	
	private static final int INVALID_YEAR = -1;
	private static final String ERROR_INVALID_DATE = "You have entered an invalid date.";
	
	public static Calendar getInstance() {
		
		Calendar result = Calendar.getInstance();
		result.set(DATE_DEFAULT_YEAR, DATE_DEFAULT_MONTH, DATE_DEFAULT_DAY, DATE_DEFAULT_HOUR, DATE_DEFAULT_MINUTE, DATE_DEFAULT_SECOND);
		return result;
	}
	
	public static DateRange parseDateRange(String rawDateTime) {
		
		rawDateTime = rawDateTime.toUpperCase();
		
		DateRange dateRange = new DateRange();

		//case one: contains 'by' or 'on', check for at (start/time date = today, end/time date = given)
		int byIndex = rawDateTime.indexOf(KEYWORD_BY);
		int onIndex = rawDateTime.indexOf(KEYWORD_ON);
		int fromIndex = rawDateTime.indexOf(KEYWORD_FROM);
		int toIndex = rawDateTime.indexOf(KEYWORD_TO);
	
		if(byIndex == KEYWORD_PROMPT_INDEX || onIndex == KEYWORD_PROMPT_INDEX) {
			rawDateTime = rawDateTime.substring(KEYWORD_BY.length(), rawDateTime.length());
			
			int atIndex = rawDateTime.indexOf(KEYWORD_AT);
			toIndex = rawDateTime.indexOf(KEYWORD_TO);
			fromIndex = rawDateTime.indexOf(KEYWORD_FROM); 
						
			if(atIndex != KEYWORD_NOT_FOUND_INDEX) {
				String[] split = rawDateTime.split(KEYWORD_AT);
				
				dateRange.setEndDate(split[DATE_PARAM_INDEX].trim());
				dateRange.setEndTime(split[TIME_PARAM_INDEX].trim());
			
			//case two: contains 'on', check for from and to 
			} else if (fromIndex != KEYWORD_NOT_FOUND_INDEX && toIndex != KEYWORD_NOT_FOUND_INDEX) {
				if(toIndex <= fromIndex) { 
					throw new IllegalArgumentException(ERROR_INVALID_DATE);
				}
				
				String[] split = rawDateTime.split(KEYWORD_FROM);
				dateRange.setStartDate(split[DATE_PARAM_INDEX]);
				dateRange.setEndDate(split[DATE_PARAM_INDEX]);
				
				split = split[TIME_PARAM_INDEX].split(KEYWORD_TO);				
				dateRange.setStartTime(split[FROM_PARAM_INDEX]);
				dateRange.setEndTime(split[TO_PARAM_INDEX]);	
			
			} else {
				dateRange.setEndDate(rawDateTime);
			}
			
		//case three: from and to (search for at in within)	
		} else if(fromIndex == KEYWORD_PROMPT_INDEX && toIndex != KEYWORD_NOT_FOUND_INDEX) {
			if(toIndex <= fromIndex) { 
				throw new IllegalArgumentException(ERROR_INVALID_DATE);
			}
			
			rawDateTime = rawDateTime.substring(KEYWORD_FROM.length(), rawDateTime.length());
			String[] split = rawDateTime.split(KEYWORD_TO);
			
			String startDateTime = split[FROM_PARAM_INDEX];
			String endDateTime = split[TO_PARAM_INDEX];	
			
			int atIndex = startDateTime.indexOf(KEYWORD_AT);
			if(atIndex != KEYWORD_NOT_FOUND_INDEX) {
				split = startDateTime.split(KEYWORD_AT);
				dateRange.setStartDate(split[DATE_PARAM_INDEX]);
				dateRange.setStartTime(split[TIME_PARAM_INDEX]);
			} else {
				dateRange.setStartDate(startDateTime);
			}

			atIndex = endDateTime.indexOf(KEYWORD_AT);
			if(atIndex != KEYWORD_NOT_FOUND_INDEX) {
				split = endDateTime.split(KEYWORD_AT);
				dateRange.setEndDate(split[DATE_PARAM_INDEX]);
				dateRange.setEndTime(split[TIME_PARAM_INDEX]);
			} else {
				dateRange.setEndDate(endDateTime);
			}
		}
		
		return dateRange;
	}
	
	/**
	 * Returns an array of Calendar object. The first object will contain the
	 * date and the second the time. Object will be null if it does not exist in
	 * the rawDate entered
	 * 
	 * @return Calendar[] The first object will contain the date and the second
	 *         the time.
	 */
	public static Calendar parseDate(String rawDate) {
		
		assert rawDate != null;
		
		Calendar result = null;
		
		//check if raw date contains time, parse it accordingly
		boolean dateFound = false;
		for(int i = 0; i < KEYWORD_TODAY.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_TODAY[i])) {
				result = Calendar.getInstance();
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_TOMORROW.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_TOMORROW[i])) {
				result = Calendar.getInstance();
				result.add(Calendar.DATE, OFFSET_DAY);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_WEEK.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_WEEK[i])) {
				result = Calendar.getInstance();
				result.add(Calendar.DATE, OFFSET_WEEK);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_MONTH.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_MONTH[i])) {
				result = Calendar.getInstance();
				result.add(Calendar.MONTH, OFFSET_MONTH);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_YEAR.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_YEAR[i])) {
				result = Calendar.getInstance();
				result.add(Calendar.YEAR, OFFSET_YEAR);
				dateFound = true;
			}
		}
		
		//if date entered is not a keyword, check if date is seperated by slashes
		if(dateFound == false) {
			String[] split = rawDate.split(DATE_SEPERATOR_SLASH);
	
			if(split.length >= DATE_SEPERATOR_MIN) {
				
				int day = retrieveDay(split[SEPERATOR_DAY_INDEX]);
				int month = retrieveMonth(split[SEPERATOR_MONTH_INDEX]);
				int year = retrieveYear(null);
				if(split.length == DATE_SEPERATOR_MAX) {
					year = retrieveYear(split[SEPERATOR_YEAR_INDEX]);
				}
				
				result = getInstance();
				result.set(Calendar.DATE, day);
				result.set(Calendar.MONTH, month);
				result.set(Calendar.YEAR, year);
				
				dateFound = true;
			}
		}
		
		//check if date is seperated by spaces
		if(dateFound == false) {
			String[] split = rawDate.split(DATE_SEPERATOR_SPACE);
	
			if(split.length >= DATE_SEPERATOR_MIN) {
				
				int day = retrieveDay(split[SEPERATOR_DAY_INDEX]);
				int month = retrieveMonth(split[SEPERATOR_MONTH_INDEX]);
				int year = retrieveYear(null);
				if(split.length == DATE_SEPERATOR_MAX) {
					year = retrieveYear(split[SEPERATOR_YEAR_INDEX]);
				}
				
				result = getInstance();
				result.set(Calendar.DATE, day);
				result.set(Calendar.MONTH, month);
				result.set(Calendar.YEAR, year);
				
				dateFound = true;
			}
		}
		
		return result;
	}
	
	private static int retrieveDay(String day) {
		
		boolean isNumeric = StringUtils.isNumeric(day);
		if(isNumeric == false) { 
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		int result = Integer.parseInt(day);
		if(result < DATE_DAY_MIN || result > DATE_DAY_MAX) {
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		return result;		
	}
	
	private static int retrieveMonth(String month) {
		
		int result = -1;
		
		boolean isNumeric = StringUtils.isNumeric(month);
		if(isNumeric == true) {
			result = Integer.parseInt(month) - OFFSET_MONTH;
		} else {
			result = Month.retrieve(month).getValue();
			if(result < DATE_MONTH_MIN || result > DATE_MONTH_MAX) {
				throw new IllegalArgumentException(ERROR_INVALID_DATE);
			}
		}
		
		return result;
	}
	
	private static int retrieveYear(String year) {
		
		int result = INVALID_YEAR;
		
		if(year == null) { 
			result = Calendar.getInstance().get(Calendar.YEAR);
			year = String.valueOf(result);
		}

		boolean isNumeric = StringUtils.isNumeric(year);
		if(isNumeric == true) {
			result = Integer.parseInt(year);
		} else { 
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		if(result < DATE_YEAR_MIN || result > DATE_YEAR_MAX) {
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		return result;
	}

	public static Calendar parseTime(String rawTime) {
		
		assert rawTime != null;

		Calendar calendar = getInstance();
		boolean isPostMeridiem = false;
		int hour = 0; 
		int minute = 0;
		
		if(rawTime.length() >= TIME_HOUR_MINUTE) {
			String meridiem = rawTime.substring(rawTime.length() - TIME_MERIDIEM_LENGTH, rawTime.length());
			if(meridiem.equalsIgnoreCase(Meridiem.AM.toString()) == true || meridiem.equalsIgnoreCase(Meridiem.PM.toString()) == true) {
				isPostMeridiem = meridiem.equalsIgnoreCase(Meridiem.PM.toString());
				rawTime = rawTime.substring(0, rawTime.length() - TIME_MERIDIEM_LENGTH);
			}
		}
		
		boolean isNumeric = StringUtils.isNumeric(rawTime);
		if(isNumeric == false) {
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		if(rawTime.length() == TIME_HOUR) {
			hour = Integer.parseInt(rawTime);
			minute = DATE_DEFAULT_MINUTE;
		} else if(rawTime.length() == TIME_HOUR_MINUTE) {
			hour = Integer.parseInt(rawTime.substring(TIME_HOUR_FIRST_INDEX, TIME_HOUR_SECOND_INDEX));
			minute = Integer.parseInt(rawTime.substring(TIME_HOUR_SECOND_INDEX, rawTime.length()));
		} else if(rawTime.length() == TIME_FULL) {
			hour = Integer.parseInt(rawTime.substring(TIME_HOUR_FIRST_INDEX, TIME_HOUR_THIRD_INDEX));
			minute = Integer.parseInt(rawTime.substring(TIME_HOUR_THIRD_INDEX, rawTime.length()));
		} else {
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		hour = (isPostMeridiem) ? hour + TIME_DIFFERENCE : hour;
		if(hour < TIME_HOUR_MIN || hour > TIME_HOUR_MAX) { 
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		if(minute < TIME_MINUTE_MIN || minute > TIME_MINUTE_MAX) {
			throw new IllegalArgumentException(ERROR_INVALID_DATE);
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		
		return calendar;
	}
}
