//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateTimeParserTest {

	private String date; 
	private Calendar expected;
	private Calendar actual;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	
	}
	
	@Test
	public void testParseDate_ValidKeywords() {
		
		//Test Case #1; Keyword Today
		this.date = "today";
		this.expected = Calendar.getInstance();
		
		this.actual = DateTimeParser.parseDate(date);
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #2; Keyword Tdy
		this.date = "tdy";
		this.expected = Calendar.getInstance();
		
		this.actual = DateTimeParser.parseDate(date);
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #3; Keyword Later
		this.date = "later";
		this.expected = Calendar.getInstance();
			
		this.actual = DateTimeParser.parseDate(date);
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #4; Keyword Tomorrow
		this.date = "tomorrow";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 1);
				
		this.actual = DateTimeParser.parseDate(date);
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #4; Keyword Tmr
		this.date = "tmr";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, Constants.OFFSET_DAY); 
						
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #5; Keyword Tmrw
		this.date = "tmrw";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, Constants.OFFSET_DAY);
						
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #6; Keyword Next Week
		this.date = "next week";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 7);
						
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #6; Keyword Next Month
		this.date = "next month";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.MONTH, 1);
								
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #7; Keyword Next Year
		this.date = "next year";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.YEAR, 1);
								
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
	}
	
	@Test
	public void testParseDate_ValidSplashDateSeperator() {
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Slash Date Seperator (dd/mm)
		this.date = "01/05";
		this.expected = DateTimeParser.getInstance();
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, Calendar.MAY);
		this.expected.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #2; Slash Date Seperator (dd/mm/yyyy)
		this.date = "01/05/2013";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, Calendar.MAY);
		this.expected.set(Calendar.YEAR, 2013);
												
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
	}
	
	@Test
	public void testParseDate_ValidSpaceDateSeperator() {
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Space Date Seperator (dd mm)
		this.date = "01 05";
		this.expected = DateTimeParser.getInstance();
		this.expected.set(Calendar.DAY_OF_MONTH, 1);
		this.expected.set(Calendar.MONTH, Calendar.MAY);
		this.expected.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
										
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #2; Space Date Seperator (dd mm yyyy)
		this.date = "01 05 2013";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, Calendar.MAY);
		this.expected.set(Calendar.YEAR, 2013);
												
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #3; Space Date Seperator (dd MMM)
		this.date = "01 oct";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
												
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #4; Space Date Seperator (dd MMM)
		this.date = "05 jun";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 5);
		this.expected.set(Calendar.MONTH, Calendar.JUNE);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
												
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #5; Space Date Seperator (dd MMM yyyy)
		this.date = "05 jul 2000";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 5);
		this.expected.set(Calendar.MONTH, Calendar.JULY);
		this.expected.set(Calendar.YEAR, 2000);
														
		this.actual = DateTimeParser.parseDate(date);	
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

	}
	
	@Test
	//test valid date format
	public void testParseTime_ValidMeridiemFormat() {
				
		//Test Case #1: Ante Meridiem
		this.date = "0759am";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #2: Post Meridiem
		this.date = "0759pm";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #3: Post Meridiem
		this.date = "5PM";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseTime_InvalidMeridiemHour() {
		
		this.date = "5559am";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}

	@Test(expected=IllegalArgumentException.class)
	//test invalid date format
	public void testParseTime_InvalidMeridiemMinute() {
		
		this.date = "0779pm";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}
	
	@Test
	//test valid date format
	public void testParseTime_ValidHourFormat() {
		
		//Test Case #1: Ante Meridiem
		this.date = "0759";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #2: Post Meridiem
		this.date = "1959";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	
		//Test Case #3: Three Digits
		this.date = "959";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 9);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseTime_InvalidHourHour() {
		
		this.date = "5559";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}

	@Test(expected=IllegalArgumentException.class)
	//test invalid date format
	public void testParseTime_InvalidHourMinute() {
		
		this.date = "0779";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	//test invalid date format
	public void testParseTime_InvalidZero() {
		
		this.date = "";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(date); 
		assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
	}
	
}
