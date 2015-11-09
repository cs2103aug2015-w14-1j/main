package notify.storage;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import notify.logic.command.Action;
import notify.storage.operator.Constants;
import notify.storage.operator.FilesGenerator;

public class FileGeneratorTest {
	private FilesGenerator fileGenerator;
	
	@Before
	public void setUp() throws Exception {
		
		this.fileGenerator = new FilesGenerator();
		
	}

	@After
	public void tearDown() throws Exception {
		
		this.fileGenerator = null;
		
	}

	@Test
	public void testFolderExistence() {
		
		boolean isExist = fileGenerator.getConfigFolder().exists();
		assertTrue(isExist);
		
		isExist = fileGenerator.getDataFolder().exists();
		assertTrue(isExist);
		
		isExist = fileGenerator.getConfigFolder().exists();
		assertTrue(isExist);
		
	}
	
	@Test
	public void testFileExistence() {
		
		boolean isExist = fileGenerator.getDirectoryFile().exists();
		assertTrue(isExist);
		
		isExist = fileGenerator.getDataFile().exists();
		assertTrue(isExist);
		
		for (Action action:Action.values()) {
			
			isExist = fileGenerator.getCommandFile(action).exists();
			assertTrue(isExist);
			
		}
	}
	
	@Test
	public void testFileContent() {
		
		File fileToRead = fileGenerator.getDirectoryFile();
		String expectedResult = Constants.FOLDER_CONFIG+Constants.FOLDER_DATA;
		String actualResult = fileGenerator.getFileContent(fileToRead);
		assertEquals(expectedResult, actualResult);
		
		fileToRead = fileGenerator.getDataFile();
		expectedResult = Constants.SQURE_BRACKETS;
		actualResult = fileGenerator.getFileContent(fileToRead);
		assertEquals(expectedResult, actualResult);
		
	}

}
