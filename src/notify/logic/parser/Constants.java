/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import java.util.Calendar;

/**
 * This Constants class stores all the shared variables required in the various
 * components of the parser package
 */
public class Constants {
	
	/** Shared Constant Variables - String **/
	public static final String STRING_EMPTY = "";
	public static final String STRING_SPACE = " ";
	public static final String STRING_ESCAPE = "/";
	
	
	/** Category Parser Constants **/
	public static final int HASHTAG_START_INDEX = 0;
	public static final int HASHTAG_END_INDEX = 1;
	public static final String KEYWORD_HASHTAG = "#";
	
	
	/** Command Parser Constants **/
	public static final int OPTION_KEYWORD_FRONT = 0;
	public static final int OPTION_KEYWORD_BACK = 1;
	public static final int OPTION_KEYWORD_BOTH = 2;
	public static final String FORMAT_KEYWORD_FRONT = " %1$s";
	public static final String FORMAT_KEYWORD_BACK = "%1$s ";
	public static final String FORMAT_KEYWORD_BOTH = " %1$s ";
	
	public static final String COMMAND_SEPERATOR = " ";
	public static final int COMMAND_LOCATION_INDEX = 0;
	
	public static final int KEYWORD_NOT_FOUND = -1;
	
	public static final int PARAM_RESULT_SIZE = 2;
	public static final int PARAM_RESULT_NAME = 0;
	public static final int PARAM_RESULT_DATE = 1;
	
	public static final int PARAM_FIRST_INDEX = 0;
	public static final int PARAM_SECOND_INDEX = 1;
	public static final int PARAM_THIRD_INDEX = 2;
	
	
	/** Date Time Parser Constants **/
	public static final String[] DATETIME_PROMPT_KEYWORDS = { "BY", "ON", "FROM" };
	public static final String[] DATETIME_KEYWORDS = { "AT", "FROM", "TO" };
	public static final String KEYWORD_BY = DATETIME_PROMPT_KEYWORDS[0];
	public static final String KEYWORD_ON = DATETIME_PROMPT_KEYWORDS[1];
	public static final String KEYWORD_FROM = DATETIME_PROMPT_KEYWORDS[2];
	public static final String KEYWORD_AT = DATETIME_KEYWORDS[0];
	public static final String KEYWORD_TO = DATETIME_KEYWORDS[2];
	public static final int KEYWORD_PROMPT_INDEX = 0;
	
	public static final String[] KEYWORD_TODAY = { "TODAY", "TDY", "LATER" };
	public static final String[] KEYWORD_TOMORROW = { "TOMORROW", "TMR", "TMRW" };
	public static final String[] KEYWORD_NEXT_WEEK = { "NEXT WEEK" };
	public static final String[] KEYWORD_NEXT_MONTH = { "NEXT MONTH" };
	public static final String[] KEYWORD_NEXT_YEAR = { "NEXT YEAR" };
	
	public static final int PARAM_DATE = 0;
	public static final int PARAM_TIME = 1;
	public static final int PARAM_FROM = 0;
	public static final int PARAM_TO = 1;
	
	public static final String DATE_SEPERATOR_SLASH = "\\/";
	public static final String DATE_SEPERATOR_SPACE = " ";
	public static final int DATE_SEPERATOR_MIN = 2;
	public static final int DATE_SEPERATOR_MAX = 3;
	
	public static final int DATE_DAY_MIN = 1;
	public static final int DATE_DAY_MAX = 31;
	public static final int DATE_MONTH_MIN = Calendar.JANUARY;
	public static final int DATE_MONTH_MAX = Calendar.DECEMBER;
	public static final int DATE_YEAR_MIN = 1900;
	public static final int DATE_YEAR_MAX = 2100;
	
	public static final int TIME_HOUR_MIN = 0;
	public static final int TIME_HOUR_MAX = 23;
	public static final int TIME_MINUTE_MIN = 0;
	public static final int TIME_MINUTE_MAX = 59;
	public static final int TIME_DIFFERENCE = 12;
	
	public static final int OFFSET_DAY = 1;
	public static final int OFFSET_WEEK = 7;
	public static final int OFFSET_MONTH = 1;
	public static final int OFFSET_YEAR = 1;
	
	public static final int DEFAULT_YEAR = 0;
	public static final int DEFAULT_MONTH = 0;
	public static final int DEFAULT_DAY = 1;
	public static final int DEFAULT_HOUR = 0;
	public static final int DEFAULT_MINUTE = 0;
	public static final int DEFAULT_SECOND = 0;
	
	public static final int TIME_FORMAT_ONE = 1; //eg. 9pm, 8am
	public static final int TIME_FORMAT_TWO = 2; //eg. 12am, 12pm
	public static final int TIME_FORMAT_THREE = 3; //eg. 130am 
	public static final int TIME_FORMAT_FOUR = 4; //eg. 0130pm 2359
	
	public static final int PARAM_DAY = 0;
	public static final int PARAM_MONTH = 1;
	public static final int PARAM_YEAR = 2;
	
	public static final int INVALID_YEAR = -1;
	
	
	/** Parser Error Messages **/
	public static final String ERROR_INVALID_COMMAND = "Unable to parse command. Invalid command provided.";
	public static final String ERROR_INVALID_PARAMS = "Unable to recognize parameter(s) entered.";
	public static final String ERROR_INVALID_DATE = "You have entered an invalid date.";
	
}
