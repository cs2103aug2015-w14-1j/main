package notify.logic.command;

import static org.junit.Assert.*;
import notify.logic.Logic;
import notify.logic.parser.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class AddCommandTest {
	
	private Logic logic;
	private CommandParser parser;
	private AddCommand addCommand;
	
	@Before
	public void setUp(){
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
	}

	@Test
	public void test() {
		
		//testing for adding deadline task
		String testString1 = "add meeting with mark on 05 nov at 5pm";
		AddCommand addCommand = (AddCommand) this.parser.parse(testString1);
		Result result = addCommand.execute();
		assertTrue(result.getActionPerformed().equals(Action.ADD));
		assertTrue(result.getResults().size() == 1);
		assertTrue(result.getResults().get(0).getTaskName().equals("meeting with mark"));
		
		//testing for adding floating task
		String testString2 = "add meeting with mark";
		
	}

}
