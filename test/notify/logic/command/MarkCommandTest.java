package notify.logic.command;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import notify.logic.Logic;
import notify.logic.parser.CommandParser;

public class MarkCommandTest {
	
	private Logic logic;
	private CommandParser parser;
	private MarkCommand markCommand;
	private Stack<ReversibleCommand> history;
	

	@Before
	public void setUp(){
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
		this.history = logic.getHistory();
	}
	
	@Test
	public void test(){
		
		//adding the task to be marked
		String addString = "add meeting with tom";
	    AddCommand addCommand = (AddCommand) this.parser.parse(addString);
		addCommand.execute();
		
		//testing mark
		String string1 = "mark 0";
		MarkCommand markCommand1 = (MarkCommand) this.parser.parse(string1);
		Result result1 = markCommand1.execute();
		assertTrue(result1.getActionPerformed().equals(Action.MARK));
		assertTrue(result1.isSuccess() == true); //task successfully marked
		assertTrue(result1.getResults().size() == 1);
		
		//testing mark
		String string2 = "mark 100";
		MarkCommand markCommand2 = (MarkCommand) this.parser.parse(string2);
		Result result2 = markCommand2.execute();
		assertTrue(result2.getActionPerformed().equals(Action.MARK)); 
		assertTrue(result2.isSuccess() == false); //task not successfuly marked
		
	}
	
	

}
