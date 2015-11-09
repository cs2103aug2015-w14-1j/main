package notify.storage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import notify.storage.api.CommandsLoader;

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
		assertEquals("java", "java");
	}

}
