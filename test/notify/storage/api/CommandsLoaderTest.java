package notify.storage.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandsLoaderTest { 
	private CommandsLoader commandsLoader; 
	
	@Before
	public void setUp() throws Exception {
		this.commandsLoader = new CommandsLoader();
	}

	@After
	public void tearDown() throws Exception {
		this.commandsLoader = null;
	}

	@Test
	public void test() {

	}

}
