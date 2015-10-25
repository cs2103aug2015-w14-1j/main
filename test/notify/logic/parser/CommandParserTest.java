package notify.logic.parser;

import notify.logic.Logic;
import notify.logic.command.Command;

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
		
		String input = "add xyz by 01 oct at 5pm #ok";
		AddCommand command = this.parser.parse(input);
		
		
		
		
		//input = "delete 1";
		//this.parser.parse(input);
		
	}

}
