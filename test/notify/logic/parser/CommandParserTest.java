package notify.logic.parser;

import static org.junit.Assert.assertEquals;
import notify.TaskType;
import notify.logic.Logic;
import notify.logic.command.AddCommand;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
	
	private Logic logic;
	private CommandParser parser;
	
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
	public void testFloatingTask() {
		
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
	
	
	
	/*
	@Test
	public void test() {
		           
		String input = "add meeting with joe by 01 oct at 5pm #meeting";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);
		assertEquals("meeting with joe", addCommand.getTaskName());
		assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		assertEquals("meeting", addCommand.getCategory());
		
		DateRange dateRange = addCommand.getDateRange();
		assertEquals(null, dateRange.getStartDate());
		assertEquals(null, dateRange.getStartTime());
		
		assertEquals(1, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add ps5 submission by 05 oct at 2am";
		addCommand = (AddCommand) this.parser.parse(input);
		
		input = "add meeting with joe on 02 oct at 5pm #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joe");
		assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		assertEquals(dateRange.getStartDate(), null);
		assertEquals(dateRange.getStartTime(), null);
		
		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		

		input = "add meeting with joe on 02 oct at 5pm #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joe");
		assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		assertEquals(dateRange.getStartDate(), null);
		assertEquals(dateRange.getStartTime(), null);
		
		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add meeting with joe on 02 oct #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joe");
		assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		assertEquals(dateRange.getStartDate(), null);
		assertEquals(dateRange.getStartTime(), null);
		
		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		
		input = "add meeting with joshua on 02 oct from 5pm to 0630pm #fail";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joshua");
		assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		assertEquals(addCommand.getCategory(), "fail");
		
		dateRange = addCommand.getDateRange();
		assertEquals(2, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(30, dateRange.getEndTime().get(Calendar.MINUTE));
		
		
		input = "add meeting with joshua on 02 oct #fail";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joshua");
		assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		assertEquals(addCommand.getCategory(), "fail");
		
		dateRange = addCommand.getDateRange();
		assertEquals(null, dateRange.getStartDate());
		assertEquals(null, dateRange.getStartTime());
		
		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		
		
		input = "add meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		addCommand = (AddCommand) this.parser.parse(input);
		assertEquals(addCommand.getTaskName(), "meeting with joshua");
		assertEquals(addCommand.getTaskType(), TaskType.RANGE);
		assertEquals(addCommand.getCategory(), "ok");
		
		dateRange = addCommand.getDateRange();
		assertEquals(1, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));	
		
		
		input = "edit 1 meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		EditCommand editCommand = (EditCommand) this.parser.parse(input);
		assertEquals(editCommand.getTaskName(), "meeting with joshua");
		assertEquals(editCommand.getTaskType(), TaskType.RANGE);
		assertEquals(editCommand.getCategory(), "ok");
		
		dateRange = editCommand.getDateRange();
		assertEquals(1, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, dateRange.getEndDate().get(Calendar.YEAR));
		assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));	

	}*/

}
