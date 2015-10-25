package notify.logic.parser;

import notify.logic.Logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
	
	private Logic logic;
	private CommandParser parser;
	private String expected;
	private String actual;
	
	@Before
	public void setUp() throws Exception {
		this.logic = new Logic();
		
	}

	@After
	public void tearDown() throws Exception {
	
	}
	
	@Test
	public void test() {
		
		String input = "add xyz by 01 oct at 5pm #ok";
		this.parser.parse(input);
		
		input = "delete 1";
		this.parser.parse(input);
		
	}

}
