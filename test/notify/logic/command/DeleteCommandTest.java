package notify.logic.command;

import static org.junit.Assert.*;

import java.util.Stack;

import notify.logic.Logic;
import notify.logic.parser.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class DeleteCommandTest {
	
	private Logic logic;
	private CommandParser parser;
	private DeleteCommand delCommand;
	private Stack<ReversibleCommand> history;

	@Before
	public void setUp(){
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
		this.history = logic.getHistory();
	}
	
	@Test
	public void test() {
		
		//adding the task to delete
		String addString = "add meeting with jim";
	    AddCommand addCommand = (AddCommand) this.parser.parse(addString);
		addCommand.execute();
		
		//testing for delete
		String string1 = "delete 0";
		DeleteCommand delCommand1 = (DeleteCommand) this.parser.parse(string1);
		Result result1 = delCommand1.execute();
		assertTrue(result1.getActionPerformed().equals(Action.DELETE));
		assertTrue(result1.getResults().size() == 1);
		assertTrue(result1.getResults().get(0).getTaskId() == 0);
		assertTrue(history.size() == 2); //the delete commmand has been pushed to the stack
		assertTrue(result1.isSuccess() == true); //deleted successfully
		
		String string2 = "delete 120";
		DeleteCommand delCommand2 = (DeleteCommand) this.parser.parse(string2);
		Result result2 = delCommand2.execute();
		assertTrue(result2.getActionPerformed().equals(Action.DELETE));
		assertTrue(result2.getResults().size() == 0);
		assertTrue(result2.isSuccess() == false); //not successfully deleted
		
		
		
	}

}
