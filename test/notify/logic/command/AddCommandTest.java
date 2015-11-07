package notify.logic.command;

import static org.junit.Assert.*;

import java.util.Stack;

import notify.TaskType;
import notify.logic.Logic;
import notify.logic.parser.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class AddCommandTest {
	
	private Logic logic;
	private CommandParser parser;
	private AddCommand addCommand;
	private Stack<ReversibleCommand> history;
	
	@Before
	public void setUp(){
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
		this.history = logic.getHistory();
	}

	@Test
	public void test() {
		
		//testing for adding deadline task
		String testString1 = "add meeting with mark on 05 nov at 5pm";
		AddCommand addCommand = (AddCommand) this.parser.parse(testString1);
		Result result1 = addCommand.execute();
		assertTrue(result1.getActionPerformed().equals(Action.ADD));
		assertTrue(result1.getResults().size() == 1);
		assertTrue(result1.getResults().get(0).getTaskName().equals("meeting with mark"));
		assertTrue(result1.getResults().get(0).getTaskType().equals(TaskType.DEADLINE));
		assertTrue(history.size() == 1); //the command has been pushed to the stack
		
		//testing for adding floating task
		String testString2 = "add meeting with mark";
		AddCommand addCommand2 = (AddCommand) this.parser.parse(testString2);
		Result result2 = addCommand2.execute();
		assertTrue(result2.getActionPerformed().equals(Action.ADD));
		assertTrue(result2.getResults().size() == 1);
		assertTrue(result2.getResults().get(0).getTaskName().equals("meeting with mark"));
		assertTrue(result2.getResults().get(0).getTaskType().equals(TaskType.FLOATING));
		assertTrue(history.size() == 2); //the command has been pushed to the stack
		

		//testing for adding range task
		String testString3 = "add meeting with mark from 05 nov at 2pm to 06 nov at 1am";
		AddCommand addCommand3 = (AddCommand) this.parser.parse(testString3);
		Result result3 = addCommand3.execute();
		assertTrue(result3.getActionPerformed().equals(Action.ADD));
		assertTrue(result3.getResults().get(0).getTaskName().equals("meeting with mark"));
		assertTrue(result3.getResults().get(0).getTaskType().equals(TaskType.RANGE));
		assertTrue(history.size() == 3); //the command has been pushed to the stack
		
	}

}
