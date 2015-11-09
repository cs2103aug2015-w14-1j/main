//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import notify.DateRange;
import notify.TaskType;
import notify.logic.Logic;
import notify.logic.command.AddCommand;
import notify.logic.command.EditCommand;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
	
	private Logic logic;
	private CommandParser parser;
	
	private String date; 
	private Calendar expected;
	private Calendar actual;
	
	@Before
	public void setUp() throws Exception {
		
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
		
	}

	@After
	public void tearDown() throws Exception {
		
		this.logic = null;
		this.parser = null;
		
	}
	
	
	@Test
	public void testAdd_FloatingTask() {
		
		//Test Case #1; Add Default Floating Task
		String input = "add meeting";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.FLOATING, addCommand.getTaskType());
		
		//Test Case #2; Add Default Floating Task with Category
		input = "add meeting #category";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.FLOATING, addCommand.getTaskType());
		assertEquals("category", addCommand.getCategory());
	
	}
	
	@Test
	public void testAdd_DeadlineTask() {
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Add Deadline Task with 'on' keyword with date
		String input = "add meeting on 03 oct";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));		
		
		//Test Case #2; Add Deadline Task with 'by' keyword with date
		input = "add meeting by 03 oct";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #3; Add Deadline Task with 'by' keyword with date with category
		input = "add meeting by 03 oct #ok";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals("ok", addCommand.getCategory());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
	}
	
	@Test
	public void testAdd_DeadlineTask_WithTime() {
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Add Deadline Task with 'on' keyword with date and time
		String input = "add meeting on 03 oct at 0330pm";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));		
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 15);
		this.expected.set(Calendar.MINUTE, 30);
		
		this.actual = addCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
		
		//Test Case #2; Add Deadline Task with 'by' keyword with date and time
		input = "add meeting by 03 oct at 1530";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 15);
		this.expected.set(Calendar.MINUTE, 30);
		
		this.actual = addCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
		
		//Test Case #3; Add Deadline Task with 'by' keyword with date and time with category
		input = "add meeting by 03 oct at 1530 #leggo";
		addCommand = (AddCommand) this.parser.parse(input);
	
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
				
		if(expected.before(today)) { 
					
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
					
		}
				
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals("leggo", addCommand.getCategory());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
				
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 15);
		this.expected.set(Calendar.MINUTE, 30);
			
		this.actual = addCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
	
	}
	
	@Test
	public void testAdd_RangeTask() {
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Add Deadline Task with 'from' and 'to' keyword with date
		String input = "add meeting from 03 oct to 04 oct";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getStartDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 4);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
	
		//Test Case #2; Add Deadline Task with 'from' and 'to' keyword with date and time
		input = "add meeting from 03 oct at 0330pm to 04 oct at 5pm";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
				
		if(expected.before(today)) { 
					
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
					
		}
				
		this.actual = addCommand.getDateRange().getStartDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
				
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 4);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
				
		if(expected.before(today)) { 
					
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
					
		}
				
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 15);
		this.expected.set(Calendar.MINUTE, 30);
							
		this.actual = addCommand.getDateRange().getStartTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));	
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);
		
		this.actual = addCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));	
		
		//Test Case #3; Add Deadline Task with 'from' and 'to' keyword with date and time with category
		input = "add meeting from 03 oct at 0330pm to 04 oct at 5pm #lala";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 3);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
				
		if(expected.before(today)) { 
					
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
					
		}
				
		this.actual = addCommand.getDateRange().getStartDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals("lala", addCommand.getCategory());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
				
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.DATE, 4);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
				
		if(expected.before(today)) { 
					
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
					
		}
				
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.RANGE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 15);
		this.expected.set(Calendar.MINUTE, 30);
							
		this.actual = addCommand.getDateRange().getStartTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));	
		
		this.expected = DateTimeParser.getInstance();	
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);
		
		this.actual = addCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));	
		
	}
	
	@Test
	public void testAdd_Shorthands() {
		
		//Test Case #1; Add Shorthand Task with Today
		String input = "add meeting on today";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);
		
		this.expected = Calendar.getInstance();
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	

		//Test Case #2; Add Shorthand Task with Today
		input = "add meeting today";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = Calendar.getInstance();
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));	
		
		//Test Case #3; Add Shorthand Task with Tmr
		input = "add meeting tmr";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = Calendar.getInstance();
		int date = this.expected.get(Calendar.DAY_OF_MONTH) + Constants.OFFSET_DAY;
		this.expected.set(Calendar.DAY_OF_MONTH,  date);
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #3; Add Shorthand Task with Tmr and category
		input = "add meeting tmr #okok";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = Calendar.getInstance();
		date = this.expected.get(Calendar.DAY_OF_MONTH) + Constants.OFFSET_DAY;
		this.expected.set(Calendar.DAY_OF_MONTH,  date);
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals("okok", addCommand.getCategory());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #4; Add Shorthand Task with next week and category
		input = "add meeting next week #help";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = Calendar.getInstance();
		date = this.expected.get(Calendar.DAY_OF_MONTH) + Constants.OFFSET_WEEK;
		this.expected.set(Calendar.DAY_OF_MONTH, date);
		
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals("help", addCommand.getCategory());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		//Test Case #5; Add Shorthand Task with later and category
		input = "add meeting later #help";
		addCommand = (AddCommand) this.parser.parse(input);

		this.expected = Calendar.getInstance();
		this.actual = addCommand.getDateRange().getEndDate();
		assertEquals("meeting", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals("help", addCommand.getCategory());
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
	
	}
	
	@Test
	public void testAdd_WithCornerCases() {
		       
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		//Test Case #1; Add Task in Full (with keywords in name)
		String input = "add ps5 submission by 01 oct at 5pm #meeting";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);
		assertEquals("ps5 submission", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals("meeting", addCommand.getCategory());
		
		DateRange dateRange = addCommand.getDateRange();
		assertEquals(null, dateRange.getStartDate());
		assertEquals(null, dateRange.getStartTime());
		
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.DAY_OF_MONTH, 1);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		this.actual = dateRange.getEndDate();
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);
		
		this.actual = dateRange.getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));

	}
	
	@Test
	public void testEdit_RangeTask() {
	
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		
		String input = "edit 1 meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		EditCommand editCommand = (EditCommand) this.parser.parse(input);
		assertEquals(editCommand.getTaskName(), "meeting with joshua");
		assertEquals(editCommand.getTaskType(), TaskType.RANGE);
		assertEquals(editCommand.getCategory(), "ok");
		
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.DAY_OF_MONTH, 1);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		this.expected.set(Calendar.HOUR_OF_DAY, 17);
		this.expected.set(Calendar.MINUTE, 0);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		actual = editCommand.getDateRange().getStartDate();
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		actual = editCommand.getDateRange().getStartTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
	
		this.expected = Calendar.getInstance();
		this.expected.set(Calendar.DAY_OF_MONTH, 2);
		this.expected.set(Calendar.MONTH, Calendar.OCTOBER);
		this.expected.set(Calendar.HOUR_OF_DAY, 18);
		this.expected.set(Calendar.MINUTE, 0);
		
		if(expected.before(today)) { 
			
			year = today.get(Calendar.YEAR) + Constants.OFFSET_YEAR;
			this.expected.set(Calendar.YEAR, year);
			
		}
		
		actual = editCommand.getDateRange().getEndDate();
		assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
		assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
		assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
		
		actual = editCommand.getDateRange().getEndTime();
		assertEquals(expected.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
		assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
	
	}

}
