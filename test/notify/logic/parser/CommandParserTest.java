package notify.logic.parser;

import java.util.Calendar;

import notify.DateRange;
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
	public void test() {
		           
		String input = "add meeting with joe by 01 oct at 5pm #meeting";
		AddCommand command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals("meeting with joe", command.getTaskName());
		org.junit.Assert.assertEquals(TaskType.DEADLINE, command.getTaskType());
		org.junit.Assert.assertEquals("meeting", command.getCategory());
		
		DateRange dateRange = command.getDateRange();
		org.junit.Assert.assertEquals(null, dateRange.getStartDate());
		org.junit.Assert.assertEquals(null, dateRange.getStartTime());
		
		org.junit.Assert.assertEquals(1, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add ps5 submission by 05 oct at 2am";
		command = (AddCommand) this.parser.parse(input);
		System.out.println(command.getTaskName());
		
		input = "add meeting with joe on 02 oct at 5pm #meeting";
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(command.getCategory(), "meeting");
		
		dateRange = command.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		

		input = "add meeting with joe on 02 oct at 5pm #meeting";
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(command.getCategory(), "meeting");
		
		dateRange = command.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		org.junit.Assert.assertEquals(17, dateRange.getEndTime().get(Calendar.HOUR_OF_DAY));
		org.junit.Assert.assertEquals(0, dateRange.getEndTime().get(Calendar.MINUTE));
		
		input = "add meeting with joe on 02 oct #meeting";
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joe");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(command.getCategory(), "meeting");
		
		dateRange = command.getDateRange();
		org.junit.Assert.assertEquals(dateRange.getStartDate(), null);
		org.junit.Assert.assertEquals(dateRange.getStartTime(), null);
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		org.junit.Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR), dateRange.getEndDate().get(Calendar.YEAR));
		
		input = "add meeting with joshua on 02 oct from 5pm to 0630pm #fail";
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(command.getCategory(), "fail");
		
		dateRange = command.getDateRange();
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
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.DEADLINE);
		org.junit.Assert.assertEquals(command.getCategory(), "fail");
		
		dateRange = command.getDateRange();
		org.junit.Assert.assertEquals(null, dateRange.getStartDate());
		org.junit.Assert.assertEquals(null, dateRange.getStartTime());
		
		org.junit.Assert.assertEquals(2, dateRange.getEndDate().get(Calendar.DAY_OF_MONTH));
		org.junit.Assert.assertEquals(Calendar.OCTOBER, dateRange.getEndDate().get(Calendar.MONTH));
		
		
		input = "add meeting with joshua from 01 10 at 5pm to 02 oct at 6pm #ok";
		command = (AddCommand) this.parser.parse(input);
		org.junit.Assert.assertEquals(command.getTaskName(), "meeting with joshua");
		org.junit.Assert.assertEquals(command.getTaskType(), TaskType.RANGE);
		org.junit.Assert.assertEquals(command.getCategory(), "ok");
		
		dateRange = command.getDateRange();
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
