//@@author A0130319R

package notify.logic.command;

import static org.junit.Assert.*;

import java.util.Stack;

import notify.logic.Logic;
import notify.logic.parser.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class EditCommandTest {

	private Logic logic;
	private CommandParser parser;
	private EditCommand edtCommand;
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
		String addString = "add meeting with lola";
	    AddCommand addCommand = (AddCommand) this.parser.parse(addString);
		addCommand.execute();
			
		//editing name
		String string1 = "edit 0 hello";
		EditCommand edtCommand1 = (EditCommand) this.parser.parse(string1);
		Result result1 = edtCommand1.execute();
		assertTrue(result1.getActionPerformed().equals(Action.EDIT));
		assertTrue(result1.getResults().size() == 1);
		assertTrue(result1.getResults().get(0).getTaskName().equals("hello")); //edited command successfully added
		
		//re-editing time
		String string2 = "edit 0 byebye";
		EditCommand edtCommand2 = (EditCommand) this.parser.parse(string2);
		Result result2 = edtCommand2.execute();
		assertTrue(result2.getActionPerformed().equals(Action.EDIT));
		assertTrue(result2.getResults().size()==1);
		assertTrue(result2.getResults().get(0).getTaskName().equals("byebye")); //re-edited command added 
		
	}

}
