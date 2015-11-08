//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import notify.DateRange;

/**
 * This DateTimeParser class handles both date and time parsing from raw inputs
 */
public class DateTimeParser {
	
	/**
	 * Returns an defaulted instance of the Calendar object
	 * Values are set to the defaults indicated in the Constants class
	 * 
	 * @return Calendar defaulted values are returned
	 * 
	 */
	public static Calendar getInstance() {
		
		Calendar result = Calendar.getInstance();
		
		result.set(Constants.DEFAULT_YEAR, Constants.DEFAULT_MONTH, Constants.DEFAULT_DAY, 
				Constants.DEFAULT_HOUR, Constants.DEFAULT_MINUTE, Constants.DEFAULT_SECOND);
		
		return result;
	}
	
	/**
	 * Handles the analyzing of date range from its raw input
	 * Information is parsed and a DateRange object with the values is populated
	 * 
	 * @param rawDateTime unprocessed string of information to be parsed
	 * 
	 * @returns DateRange values are populated into the DateRange
	 * 					values within the DateRange will be null if not provided
	 * 
	 */
	public static DateRange parseDateRange(String rawDateTime) {
		
		rawDateTime = rawDateTime.toUpperCase().trim();
		
		DateRange dateRange = new DateRange();
		
		 String byKeyword = CommandParser.formatKeyword(Constants.KEYWORD_BY, Constants.OPTION_KEYWORD_BACK);
	     String onKeyword = CommandParser.formatKeyword(Constants.KEYWORD_ON, Constants.OPTION_KEYWORD_BACK);
	     String fromKeyword = CommandParser.formatKeyword(Constants.KEYWORD_FROM, Constants.OPTION_KEYWORD_BACK);
	     String toKeyword =  CommandParser.formatKeyword(Constants.KEYWORD_TO, Constants.OPTION_KEYWORD_BOTH);
	     String atKeyword =  CommandParser.formatKeyword(Constants.KEYWORD_AT, Constants.OPTION_KEYWORD_BOTH);
	     
		int byIndex = rawDateTime.indexOf(byKeyword);
		int onIndex = rawDateTime.indexOf(onKeyword);
		int fromIndex = rawDateTime.indexOf(fromKeyword);;
		int toIndex = rawDateTime.indexOf(toKeyword);
	
		//case one: contains 'by' or 'on', check for at (start/time date = today, end/time date = given)
		if(byIndex == Constants.KEYWORD_PROMPT_INDEX || onIndex == Constants.KEYWORD_PROMPT_INDEX) {
			rawDateTime = rawDateTime.substring(Constants.KEYWORD_BY.length(), rawDateTime.length());
			
			toKeyword = CommandParser.formatKeyword(Constants.KEYWORD_TO, Constants.OPTION_KEYWORD_BOTH);
			fromKeyword = CommandParser.formatKeyword(Constants.KEYWORD_FROM, Constants.OPTION_KEYWORD_BOTH);
			
			int atIndex = rawDateTime.indexOf(atKeyword);
			toIndex = rawDateTime.indexOf(toKeyword);
			fromIndex = rawDateTime.indexOf(fromKeyword); 
						
			if(atIndex != Constants.KEYWORD_NOT_FOUND) {
				
				String[] split = rawDateTime.split(Constants.KEYWORD_AT);
				String date = split[Constants.PARAM_DATE].trim();
				String time = split[Constants.PARAM_TIME].trim();
						
				dateRange.setEndDate(date);
				dateRange.setEndTime(time);
			
			//case two: contains 'on', check for from and to 
			} else if (fromIndex != Constants.KEYWORD_NOT_FOUND && toIndex != Constants.KEYWORD_NOT_FOUND) {
				
				if(toIndex <= fromIndex) { 
				
					throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
				
				}
				
				String[] split = rawDateTime.split(Constants.KEYWORD_FROM);
				String date = split[Constants.PARAM_DATE];
				dateRange.setStartDate(date);
				dateRange.setEndDate(date);
				
				split = split[Constants.PARAM_TIME].split(Constants.KEYWORD_TO);
				String fromTime = split[Constants.PARAM_FROM];
				String toTime = split[Constants.PARAM_TO];
				
				dateRange.setStartTime(fromTime);
				dateRange.setEndTime(toTime);	
			
			} else {
				
				dateRange.setEndDate(rawDateTime);
				
			}
			
		//case three: from and to (search for at in within)	
		} else if(fromIndex == Constants.KEYWORD_PROMPT_INDEX && toIndex != Constants.KEYWORD_NOT_FOUND) {
			
			if(toIndex <= fromIndex) { 
				
				throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
				
			}

			rawDateTime = rawDateTime.substring(Constants.KEYWORD_FROM.length(), rawDateTime.length());
			toKeyword = CommandParser.formatKeyword(Constants.KEYWORD_TO, Constants.OPTION_KEYWORD_BOTH);
			
			String[] split = rawDateTime.split(toKeyword);
			String startDateTime = split[Constants.PARAM_FROM];
			String endDateTime = split[Constants.PARAM_TO];	
			
			int atIndex = startDateTime.indexOf(Constants.KEYWORD_AT);
			
			if(atIndex != Constants.KEYWORD_NOT_FOUND) {
			
				split = startDateTime.split(Constants.KEYWORD_AT);
				dateRange.setStartDate(split[Constants.PARAM_DATE]);
				dateRange.setStartTime(split[Constants.PARAM_TIME]);
			
			} else {
			
				dateRange.setStartDate(startDateTime);
			
			}

			atIndex = endDateTime.indexOf(Constants.KEYWORD_AT);
			
			if(atIndex != Constants.KEYWORD_NOT_FOUND) {
			
				split = endDateTime.split(Constants.KEYWORD_AT);
				dateRange.setEndDate(split[Constants.PARAM_DATE]);
				dateRange.setEndTime(split[Constants.PARAM_TIME]);
			
			} else {
			
				dateRange.setEndDate(endDateTime);
			
			}
			
		}
	
		return dateRange;
	
	}
	
	/**
	 * Handles the analyzing of date from its raw input
	 * Information is parsed and a Calendar object with the values is populated
	 * 
	 * @param rawDate unprocessed string of information to be parsed
	 * 
	 * @returns Calendar values are populated into the Calendar
	 * 				Fields within the Calendar object that is not specified will be defaulted
	 * 
	 */
	public static Calendar parseDate(String rawDate) {
		
		assert rawDate != null;
		
		Calendar result = null;
		
		// check if raw date contains time, parse it accordingly
		boolean dateFound = false;
		
		// search for all possible keywords of today
		for(int i = 0; i < Constants.KEYWORD_TODAY.length; i++) {
		
			if(rawDate.equalsIgnoreCase(Constants.KEYWORD_TODAY[i])) {
			
				result = Calendar.getInstance();
				dateFound = true;
		
			}
		
		}
		
		
		// search for all possible keywords of tomorrow
		for(int i = 0; i < Constants.KEYWORD_TOMORROW.length; i++) {
			
			if(rawDate.equalsIgnoreCase(Constants.KEYWORD_TOMORROW[i])) {
			
				result = Calendar.getInstance();
				result.add(Calendar.DATE, Constants.OFFSET_DAY);
				dateFound = true;
			
			}
		
		}
		
		//search for all possible keywords of next week
		for(int i = 0; i < Constants.KEYWORD_NEXT_WEEK.length; i++) {
		
			if(rawDate.equalsIgnoreCase(Constants.KEYWORD_NEXT_WEEK[i])) {
			
				result = Calendar.getInstance();
				result.add(Calendar.DATE, Constants.OFFSET_WEEK);
				dateFound = true;
			
			}
		
		}
		
		//search for all possible keywords of next month
		for(int i = 0; i < Constants.KEYWORD_NEXT_MONTH.length; i++) {
		
			if(rawDate.equalsIgnoreCase(Constants.KEYWORD_NEXT_MONTH[i])) {
			
				result = Calendar.getInstance();
				result.add(Calendar.MONTH, Constants.OFFSET_MONTH);
				dateFound = true;
			
			}
		
		}
		
		//search for all possible keywords of next year
		for(int i = 0; i < Constants.KEYWORD_NEXT_YEAR.length; i++) {
		
			if(rawDate.equalsIgnoreCase(Constants.KEYWORD_NEXT_YEAR[i])) {
			
				result = Calendar.getInstance();
				result.add(Calendar.YEAR, Constants.OFFSET_YEAR);
				dateFound = true;
			
			}
		
		}
		
		//check for days keyword
		if(dateFound == false) {
	
			result = retrieveNext(rawDate);
			
			if(result != null) {
				
				dateFound = true;

			}

		}
		
		//if date entered is not a keyword, check if date is separated by slashes
		if(dateFound == false) {
		
			Calendar today = Calendar.getInstance();
			String[] split = rawDate.split(Constants.DATE_SEPERATOR_SLASH);
	
			if(split.length >= Constants.DATE_SEPERATOR_MIN) {
				
				String dayInput = split[Constants.PARAM_DAY];
				String monthInput = split[Constants.PARAM_MONTH];
				
				int day = retrieveDay(dayInput);
				int month = retrieveMonth(monthInput);
				int year = retrieveYear(null);
			
				if(split.length == Constants.DATE_SEPERATOR_MAX) {
					
					String yearInput = split[Constants.PARAM_YEAR];
					year = retrieveYear(yearInput);
				
				}
				
				result = Calendar.getInstance();
				result.set(Calendar.DATE, day);
				result.set(Calendar.MONTH, month);
				result.set(Calendar.YEAR, year);
				
				//indicates that year is not added
				if(split.length < Constants.DATE_SEPERATOR_MAX) {
					
					if(result.before(today)) {
						
						result.set(Calendar.YEAR, year + Constants.OFFSET_YEAR);
						
					}
					
				}
				
				dateFound = true;
			
			}
		
		}
		
		//check if date is separated by spaces
		if(dateFound == false) {
		
			Calendar today = Calendar.getInstance();
			String[] split = rawDate.split(Constants.DATE_SEPERATOR_SPACE);
	
			if(split.length >= Constants.DATE_SEPERATOR_MIN) {
				
				String dayInput = split[Constants.PARAM_DAY];
				String monthInput = split[Constants.PARAM_MONTH];
				
				int day = retrieveDay(dayInput);
				int month = retrieveMonth(monthInput);
				int year = retrieveYear(null);
				
				if(split.length == Constants.DATE_SEPERATOR_MAX) {
					
					String yearInput = split[Constants.PARAM_YEAR];
					year = retrieveYear(yearInput);
				
				}
				
				result = Calendar.getInstance();
				result.set(Calendar.DATE, day);
				result.set(Calendar.MONTH, month);
				result.set(Calendar.YEAR, year);
				
				//indicates that year is not added
				if(split.length < Constants.DATE_SEPERATOR_MAX) {
					
					if(result.before(today)) {
						
						result.set(Calendar.YEAR, year + Constants.OFFSET_YEAR);
						
					}
					
				}
				
				dateFound = true;
				
			}
			
		}
		
		if(dateFound == false) { 
			
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		return result;
	
	}
	
	/**
	 * Validates and return the day if is in correct format
	 */
	private static int retrieveDay(String day) {
		
		boolean isNumeric = StringUtils.isNumeric(day);
		
		if(isNumeric == false) { 
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		int result = Integer.parseInt(day);
		
		if(result < Constants.DATE_DAY_MIN || result > Constants.DATE_DAY_MAX) {
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		return result;		
	}
	
	/**
	 * Validates and return the month if is in correct format
	 */
	private static int retrieveMonth(String month) {
		
		int result = -1;
		
		boolean isNumeric = StringUtils.isNumeric(month);
		
		if(isNumeric == true) {
		
			result = Integer.parseInt(month) - Constants.OFFSET_MONTH;
		
		} else {
		
			result = Month.retrieve(month).getValue();
			
			if(result < Constants.DATE_MONTH_MIN || result > Constants.DATE_MONTH_MAX) {
			
				throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
			
			}
		
		}
		
		return result;
	
	}
	
	/**
	 * Validates and return the year if is in correct format
	 */
	private static int retrieveYear(String year) {
		
		int result = Constants.INVALID_YEAR;
		
		if(year == null) { 
		
			result = Calendar.getInstance().get(Calendar.YEAR);
			year = String.valueOf(result);
		
		}

		boolean isNumeric = StringUtils.isNumeric(year);
		
		if(isNumeric == true) {
		
			result = Integer.parseInt(year);
		
		} else { 
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		if(result < Constants.DATE_YEAR_MIN || result > Constants.DATE_YEAR_MAX) {
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		return result;
	
	}

	/**
	 * Search for the corresponding Calendar's Day with the keyword provided
	 */
	private static int retrieveDayIndex(String day) { 
		
		int dayOfWeek = Constants.INT_ZERO;
		day = day.toUpperCase();
		
		for(int i = 0; i < Constants.KEYWORD_MONDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_MONDAY[i])) {
			
				dayOfWeek = Calendar.MONDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_TUESDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_TUESDAY[i])) {
			
				dayOfWeek = Calendar.TUESDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_WEDNESDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_WEDNESDAY[i])) {
			
				dayOfWeek = Calendar.WEDNESDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_THURSDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_THURSDAY[i])) {
			
				dayOfWeek = Calendar.THURSDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_FRIDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_FRIDAY[i])) {
			
				dayOfWeek = Calendar.FRIDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_SATURDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_SATURDAY[i])) {
			
				dayOfWeek = Calendar.SATURDAY;
				
			}
			
		}
		
		for(int i = 0; i < Constants.KEYWORD_SUNDAY.length; i++) {
			
			if(day.contains(Constants.KEYWORD_SUNDAY[i])) {
			
				dayOfWeek = Calendar.SUNDAY;
				
			}
			
		}
		
		return dayOfWeek;
		
	}
	
	/**
	 * Find the following corresponding day or the day on the next week
	 * Information is parsed and a Calendar object with the values is populated
	 * 
	 * @param day the closest day that matches request
	 * 
	 * @returns Calendar values are populated into the Calendar
	 * 				Fields within the Calendar object that is not specified will be defaulted
	 * 
	 */
	private static Calendar retrieveNext(String day) {
		
		Calendar results = null;
		Calendar today = Calendar.getInstance();
		int dayIndex = retrieveDayIndex(day);
		boolean found = false;
		
		day = day.toUpperCase();
		
		int offset;
		int current = today.get(Calendar.DAY_OF_WEEK);
		
		for(offset = 0; offset < Constants.OFFSET_WEEK; offset++) { 
			
			if(current == dayIndex) {
				
				found = true;
				break;
				
			}
			
			current++;
			
		}
		
		if(day.contains(Constants.KEYWORD_NEXT) == true) {
			
			offset += Constants.OFFSET_WEEK;
			
		}
		
		offset = today.get(Calendar.DAY_OF_YEAR) + offset;
		today.set(Calendar.DAY_OF_YEAR, offset);
		
		if(found == true) { 
			
			results = today;
			
		}
		
		return results;
		
	}
	
	/**
	 * Handles the analyzing of time range from its raw input
	 * Information is parsed and a Calendar object with only start and end time
	 * 
	 * @param rawRange unprocessed string of information to be parsed
	 * 
	 * @returns DateRange values are populated into the Calendar
	 * 				Fields within the Calendar object that is not specified will be defaulted
	 * 
	 */
	public static DateRange parseTimeRange(String rawRange) {
		
		assert rawRange != null;

		rawRange = rawRange.trim();
		rawRange = rawRange.toUpperCase();
		rawRange = rawRange.replaceAll(Constants.KEYWORD_FROM, Constants.STRING_EMPTY);
		
		String[] split = rawRange.split(Constants.KEYWORD_TO);
		
		DateRange dateRange = null;
		
		if(split.length == Constants.PARAM_RESULT_SIZE) { 
			
			String startTime = split[Constants.PARAM_FROM];
			String endTime = split[Constants.PARAM_TO];
			
			if(startTime.length() <= Constants.TIME_FORMAT_MAX 
					&& endTime.length() <= Constants.TIME_FORMAT_MAX) {
				
				dateRange = new DateRange(null, startTime, null, endTime);
				
			}
		}
		
		return dateRange;

	}

	/**
	 * Handles the analyzing of time from its raw input
	 * Information is parsed and a Calendar object with the values is populated
	 * 
	 * @param rawTime unprocessed string of information to be parsed
	 * 
	 * @returns Calendar values are populated into the Calendar
	 * 				Fields within the Calendar object that is not specified will be defaulted
	 * 
	 */
	public static Calendar parseTime(String rawTime) {
		
		assert rawTime != null;

		rawTime = rawTime.trim();
		
		int hour = 0; 
		int minute = 0;
		boolean isPostMeridiem = false;
		Calendar calendar = getInstance();
		
		if(rawTime.length() >= Constants.TIME_FORMAT_THREE) {
			
			String am = Meridiem.AM.toString();
			String pm = Meridiem.PM.toString();
			
			String meridiem = rawTime.substring(rawTime.length() - am.length(), rawTime.length());
			
			if(meridiem.equalsIgnoreCase(am) == true || meridiem.equalsIgnoreCase(pm) == true) {
			
				isPostMeridiem = meridiem.equalsIgnoreCase(Meridiem.PM.toString());
				rawTime = rawTime.substring(0, rawTime.length() - am.length());
			
			}
			
		}
		
		boolean isNumeric = StringUtils.isNumeric(rawTime);
		
		if(isNumeric == false) {
			
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}

		// Used to handle time format such as 9am, 12pm
		if(rawTime.length() == Constants.TIME_FORMAT_ONE || rawTime.length() == Constants.TIME_FORMAT_TWO) {
		
			hour = Integer.parseInt(rawTime);
			minute = Constants.DEFAULT_MINUTE;
		
		// Used to handle time format such as 930am
		} else if(rawTime.length() == Constants.TIME_FORMAT_THREE) {
		
			hour = Integer.parseInt(rawTime.substring(Constants.PARAM_FIRST_INDEX, Constants.PARAM_SECOND_INDEX));
			minute = Integer.parseInt(rawTime.substring(Constants.PARAM_SECOND_INDEX, rawTime.length()));
	
		// Used to handle time format such as 1030pm, 2359
		} else if(rawTime.length() == Constants.TIME_FORMAT_FOUR) {
		
			hour = Integer.parseInt(rawTime.substring(Constants.PARAM_FIRST_INDEX, Constants.PARAM_THIRD_INDEX));
			minute = Integer.parseInt(rawTime.substring(Constants.PARAM_THIRD_INDEX, rawTime.length()));
		
		// If it does not fall within any of the category, an exception is thrown
		} else {
			
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		// handle the meridiem offset, from meridiem format to 24 hours time format
		if(isPostMeridiem == true) {
			
			if(hour != Constants.TIME_DIFFERENCE) {
				
				hour = hour + Constants.TIME_DIFFERENCE;
				
			}
		}
		
		if(hour < Constants.TIME_HOUR_MIN || hour > Constants.TIME_HOUR_MAX) { 
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		if(minute < Constants.TIME_MINUTE_MIN || minute > Constants.TIME_MINUTE_MAX) {
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		
		return calendar;
	
	}
}
