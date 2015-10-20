package notify.logic.parser;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public class DateTimeParser {

	private static final String KEYWORD_AT = "AT";
	private static final String[] KEYWORD_TODAY = { "TODAY", "TDY", "LATER" };
	private static final String[] KEYWORD_TOMORROW = { "TOMORROW", "TMR", "TMRW" };
	private static final String[] KEYWORD_NEXT_WEEK = { "NEXT WEEK" };
	private static final String[] KEYWORD_NEXT_MONTH = { "NEXT MONTH" };
	private static final String[] KEYWORD_NEXT_YEAR = { "NEXT YEAR" };
	
	private static final int OFFSET_DAY = 1;
	private static final int OFFSET_WEEK = 7;
	private static final int OFFSET_MONTH = 1;
	private static final int OFFSET_YEAR = 1;
	
	private static final int DATE_PARAM_INDEX = 0;
	private static final int TIME_PARAM_INDEX = 1;
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

	private static final int DATE_RESULT_SIZE = 2;
	private static final String DATE_SEPERATOR_SLASH = "\\/";
	private static final String DATE_SEPERATOR_SPACE = " ";
	private static final int DATE_SEPERATOR_MIN = 2;
	private static final int DATE_SEPERATOR_MAX = 3;
	
	private static final int INVALID_YEAR = -1;
	private static final String ERROR_INVALID_DATE = "You have entered an invalid date.";
	
	public static Calendar getInstance() {
		
		Calendar result = Calendar.getInstance();
		result.set(DATE_DEFAULT_YEAR, DATE_DEFAULT_MONTH, DATE_DEFAULT_DAY, DATE_DEFAULT_HOUR, DATE_DEFAULT_MINUTE, DATE_DEFAULT_SECOND);
		return result;
	}
	
	/**
	 * Returns an array of Calendar object. The first object will contain the
	 * date and the second the time. Object will be null if it does not exist in
	 * the rawDate entered
	 * 
	 * @return Calendar[] The first object will contain the date and the second
	 *         the time.
	 */
	public static Calendar[] parseDate(String rawDate) {
		
		assert rawDate != null;
		
		Calendar[] result = new Calendar[DATE_RESULT_SIZE];
		
		//check if raw date contains time, parse it accordingly
		String[] dateInfo = rawDate.split(KEYWORD_AT);
		if (dateInfo.length > TIME_PARAM_INDEX) {
			result[TIME_PARAM_INDEX] = parseTime(dateInfo[TIME_PARAM_INDEX]);
			rawDate = dateInfo[DATE_PARAM_INDEX];
		}
		
		boolean dateFound = false;
		for(int i = 0; i < KEYWORD_TODAY.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_TODAY[i])) {
				result[DATE_PARAM_INDEX] = Calendar.getInstance();
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_TOMORROW.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_TOMORROW[i])) {
				result[DATE_PARAM_INDEX] = Calendar.getInstance();
				result[DATE_PARAM_INDEX].add(Calendar.DATE, OFFSET_DAY);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_WEEK.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_WEEK[i])) {
				result[DATE_PARAM_INDEX] = Calendar.getInstance();
				result[DATE_PARAM_INDEX].add(Calendar.DATE, OFFSET_WEEK);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_MONTH.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_MONTH[i])) {
				result[DATE_PARAM_INDEX] = Calendar.getInstance();
				result[DATE_PARAM_INDEX].add(Calendar.MONTH, OFFSET_MONTH);
				dateFound = true;
			}
		}
		
		for(int i = 0; i < KEYWORD_NEXT_YEAR.length; i++) {
			if(rawDate.equalsIgnoreCase(KEYWORD_NEXT_YEAR[i])) {
				result[DATE_PARAM_INDEX] = Calendar.getInstance();
				result[DATE_PARAM_INDEX].add(Calendar.YEAR, OFFSET_YEAR);
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
				
				result[DATE_PARAM_INDEX] = getInstance();
				result[DATE_PARAM_INDEX].set(Calendar.DATE, day);
				result[DATE_PARAM_INDEX].set(Calendar.MONTH, month);
				result[DATE_PARAM_INDEX].set(Calendar.YEAR, year);
				
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
				
				result[DATE_PARAM_INDEX] = getInstance();
				result[DATE_PARAM_INDEX].set(Calendar.DATE, day);
				result[DATE_PARAM_INDEX].set(Calendar.MONTH, month);
				result[DATE_PARAM_INDEX].set(Calendar.YEAR, year);
				
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
			result = Integer.parseInt(month);
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
