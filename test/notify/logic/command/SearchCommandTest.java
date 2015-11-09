package notify.logic.command;

import static org.junit.Assert.*;

import java.util.Stack;

import notify.logic.Logic;
import notify.logic.parser.CommandParser;

import org.junit.Before;
import org.junit.Test;

public class SearchCommandTest {

	private Logic logic;
	private CommandParser parser;
	private SearchCommand delCommand;
	private Stack<ReversibleCommand> history;
	
	@Before
	public void setUp(){
		this.logic = new Logic();
		this.parser = logic.getCommandParser();
		this.history = logic.getHistory();
	}
	
	@Test
	public void test() {
		
		//adding the tasks 
		String addString1 = "add meeting with tom";
	    AddCommand addCommand1 = (AddCommand) this.parser.parse(addString1);
		addCommand1.execute();
		
		String addString2 = "add meeting with jim on 05 nov";
		AddCommand addCommand2 = (AddCommand) this.parser.parse(addString2);
		addCommand2.execute();
		
		//testing for search using keyword meeting
		String searchString = "search meeting";
		SearchCommand search1 = (SearchCommand) this.parser.parse(searchString);
		search1.execute();
		

	}

}
