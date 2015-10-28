package notify.logic.parser;

import java.util.Calendar;

import notify.logic.parser.DateTimeParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateTimeParserTest {

	private String time; 
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
		this.time = "today";
		this.expected = Calendar.getInstance();
		
		this.actual = DateTimeParser.parseDate(time);
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #2; Keyword Tdy
		this.time = "tdy";
		this.expected = Calendar.getInstance();
		
		this.actual = DateTimeParser.parseDate(time);
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #3; Keyword Later
		this.time = "later";
		this.expected = Calendar.getInstance();
			
		this.actual = DateTimeParser.parseDate(time);
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #4; Keyword Tomorrow
		this.time = "tomorrow";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 1);
				
		this.actual = DateTimeParser.parseDate(time);
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #4; Keyword Tmr
		this.time = "tmr";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 1); 
						
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #5; Keyword Tmrw
		this.time = "tmrw";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 1);
						
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #6; Keyword Next Week
		this.time = "next week";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.DATE, 7);
						
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #6; Keyword Next Month
		this.time = "next month";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.MONTH, 1);
								
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
		//Test Case #7; Keyword Next Year
		this.time = "next year";
		this.expected = Calendar.getInstance();
		this.expected.add(Calendar.YEAR, 1);
								
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	}
	
	@Test
	public void testParseDate_ValidSplashDateSeperator() {
		
		//Test Case #1; Slash Date Seperator (dd/mm)
		this.time = "01/05";
		this.expected = DateTimeParser.getInstance();
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, 5);
		this.expected.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
										
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #2; Slash Date Seperator (dd/mm/yyyy)
		this.time = "01/05/2013";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, 5);
		this.expected.set(Calendar.YEAR, 2013);
												
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	}
	
	@Test
	public void testParseDate_ValidSpaceDateSeperator() {
		
		//Test Case #1; Space Date Seperator (dd mm)
		this.time = "01 05";
		this.expected = DateTimeParser.getInstance();
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, 5);
		this.expected.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
										
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #2; Space Date Seperator (dd mm yyyy)
		this.time = "01 05 2013";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, 5);
		this.expected.set(Calendar.YEAR, 2013);
												
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #3; Space Date Seperator (dd MMM)
		this.time = "01 oct";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 1);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		this.expected.set(Calendar.YEAR, 2015);
												
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #4; Space Date Seperator (dd MMM)
		this.time = "05 jun";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 5);
		this.expected.set(Calendar.MONTH, Calendar.JUNE);
		this.expected.set(Calendar.YEAR, 2015);
												
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

		//Test Case #5; Space Date Seperator (dd MMM yyyy)
		this.time = "05 jul 2000";
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 5);
		this.expected.set(Calendar.MONTH, Calendar.JULY);
		this.expected.set(Calendar.YEAR, 2000);
														
		this.actual = DateTimeParser.parseDate(time);	
		org.junit.Assert.assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		org.junit.Assert.assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));

	}
	
	@Test
	//test valid time format
	public void testParseTime_ValidMeridiemFormat() {
				
		//Test Case #1: Ante Meridiem
		this.time = "0759am";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #2: Post Meridiem
		this.time = "0759pm";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #3: Post Meridiem
		this.time = "5PM";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseTime_InvalidMeridiemHour() {
		
		this.time = "5559am";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}

	@Test(expected=IllegalArgumentException.class)
	//test invalid time format
	public void testParseTime_InvalidMeridiemMinute() {
		
		this.time = "0779pm";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}
	
	@Test
	//test valid time format
	public void testParseTime_ValidHourFormat() {
		
		//Test Case #1: Ante Meridiem
		this.time = "0759";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
		
		//Test Case #2: Post Meridiem
		this.time = "1959";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	
		//Test Case #3: Three Digits
		this.time = "959";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 9);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseTime_InvalidHourHour() {
		
		this.time = "5559";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 7);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}

	@Test(expected=IllegalArgumentException.class)
	//test invalid time format
	public void testParseTime_InvalidHourMinute() {
		
		this.time = "0779";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}
	
	@Test(expected=IllegalArgumentException.class)
	//test invalid time format
	public void testParseTime_InvalidZero() {
		
		this.time = "";
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.HOUR_OF_DAY, 19);
		this.expected.set(Calendar.MINUTE, 59);

		this.actual = DateTimeParser.parseTime(time); 
		org.junit.Assert.assertEquals(actual.get(Calendar.HOUR_OF_DAY), expected.get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(actual.get(Calendar.MINUTE), expected.get(Calendar.MINUTE));
	}
	
}
