package notify.logic.parser;

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
	public void test() {
		           
		String input = "add meeting with joe by 01 oct at 5pm #meeting";
		AddCommand addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals("meeting with joe", addCommand.getTaskName());
		org.junit.Assert.assertEquals(TaskType.DEADLINE, addCommand.getTaskType());
		org.junit.Assert.assertEquals("meeting", addCommand.getCategory());
		
		DateRange dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(null, dateRange.getStartDate());
		org.junit.Assert.assertEquals(null, dateRange.getStartTime());
		
		org.junit.Assert.assertEquals(1, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add ps5 submission by 05 oct at 2am";
		addCommand = (AddCommand) this.parser.parse(input);
		
		input = "add meeting with joe on 02 oct at 5pm #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		

		input = "add meeting with joe on 02 oct at 5pm #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add meeting with joe on 02 oct #meeting";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "meeting");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		
		input = "add meeting with joshua on 02 oct from 5pm to 0630pm #fail";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "fail");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(2, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(30, dateRange.getEndTime().get(Calendar.MINUTE));
		
		
		input = "add meeting with joshua on 02 oct #fail";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "fail");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(null, dateRange.getStartDate());
		org.junit.Assert.assertEquals(null, dateRange.getStartTime());
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		
		
		input = "add meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		addCommand = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(addCommand.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(addCommand.getTaskType(), TaskType.RANGE);
		org.junit.Assert.assertEquals(addCommand.getCategory(), "ok");
		
		dateRange = addCommand.getDateRange();
		org.junit.Assert.assertEquals(1, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));	
		
		
		input = "edit 1 meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		EditCommand editCommand = (EditCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(editCommand.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(editCommand.getTaskType(), TaskType.RANGE);
		org.junit.Assert.assertEquals(editCommand.getCategory(), "ok");
		
		dateRange = editCommand.getDateRange();
		org.junit.Assert.assertEquals(1, dateRange.getStartDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getStartDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getStartTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getStartTime().get(Calendar.MINUTE));

		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(18, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));	

	}

}
