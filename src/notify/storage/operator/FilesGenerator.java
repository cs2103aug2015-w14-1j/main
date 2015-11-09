/* @@author A0124072 */
package notify.storage.operator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import notify.logic.command.Action;

/**
 * FileGenerator class is responsible for generating system folders:
 * notify.config, data, commands and generating system files: tasks(data) file,
 * path file and various command(as of {@see notify.logic.command.Action})
 * files.
 */
public class FilesGenerator {
	/** These variables represent the folders and files to be generated */
	private File configFolder;
	private File dataFolder;
	private File commandsFolder;
	private File directoryFile;
	private File commandFile;
	private File dataFile;

	/**
	 * The class Constructor which instantiate the files and folders that are
	 * to be generated. It then invokes {@link #generateConfigFolder()
	 * generateConfigFolder} method to create all the necessary folders. After
	 * which it invokes {@link #generateSystemFiles() generateSystemFiles} to
	 * create necessary files.
	 */
	public FilesGenerator() {
		
		this.configFolder = new File(Constants.NAME_FOLDER_CONFIG);
		this.dataFolder = new File(
				String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA));
		this.commandsFolder = new File(
				String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, Constants.FOLDER_COMMANDS));
	
	}
	
	public void generate() {
		
		generateConfigFolder();
		generateSystemFiles();
		
	}

	/**
	 * This method returns the File object which represents the notify.config
	 * folder.
	 * 
	 * @return a File object which represents the notify.config folder
	 */
	public File getConfigFolder() {
		
		return this.configFolder;
		
	}

	/**
	 * This method returns the File object which represents the data folder.
	 * 
	 * @return a File object which represents the data folder
	 */
	public File getDataFolder() {
		
		return this.dataFolder;
		
	}

	/**
	 * This method returns the File object which represents the commands folder.
	 * 
	 * @return a File object which represents the commands folder
	 */
	public File getCommandsFolder() {
		
		return this.commandsFolder;
		
	}

	/**
	 * This method returns the File object which represents the path file.
	 * 
	 * @return a File object which represents the path file
	 */
	public File getDirectoryFile() {
		
		return this.directoryFile;
		
	}

	/**
	 * This method returns the File object which represents the data file.
	 * 
	 * @return a File object which represents the data file
	 */
	public File getDataFile() {
		
		return this.dataFile;
	
	}

	/**
	 * This method returns the File object which represents the specified
	 * command file.
	 * 
	 * @param command_
	 *            the Action object which corresponds to the command file
	 * 
	 * @return a File object which represents the specified command file
	 */
	public File getCommandFile(Action command_) {
		
		assert(command_ != null);
		File file = new File(String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG,
				Constants.FOLDER_COMMANDS, command_.toString().toLowerCase(), Constants.EXTENSION_FILE));
		
		return file;
	
	}

	/**
	 * This method returns the contents of the specified command file as a
	 * String object.
	 * 
	 * @param file_
	 *            the file to be read
	 * 
	 * @return a String object which represents the content of the first line
	 *         in the specified file
	 */
	public String getFileContent(File file_) {
		
		assert(file_.exists() == true);
		String fileContent = readFromFile(file_);

		return fileContent;
	
	}

	/**
	 * This method generates the system files: data file, path file and various
	 * commands files. It invokes {@link #generateDirectoryFile()
	 * #generateDataFile() #generateCommandFiles()} methods.
	 */
	private void generateSystemFiles() {
		
		generateDirectoryFile();
		generateDataFile();
		generateCommandFiles();
	
	}

	/**
	 * This method creates the system folders.
	 */
	private void generateConfigFolder() {
		
		if (!this.configFolder.exists()) {
			
			this.configFolder.mkdir();
			
		}

		if (!this.dataFolder.exists()) {
			
			assert(this.configFolder.exists() == true);
			this.dataFolder.mkdir();
		
		}

		if (!this.commandsFolder.exists()) {
			
			assert(this.configFolder.exists() == true);
			this.commandsFolder.mkdir();
		
		}
	}

	/**
	 * This method generate path file which is located inside the data folder If
	 * the path file does not exist, it creates new path file. It then invokes
	 * {@link #writeIntoFile(File, String)} method to write the default absolute
	 * path of the data file which is <tt>notify.config/data/</tt> into the path
	 * file.
	 */
	private void generateDirectoryFile() {
		
		assert(this.configFolder.exists() == true);
		assert(this.dataFolder.exists() == true);
		this.directoryFile = new File(String.format(Constants.PATH_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA,
				Constants.FILE_DESTINATION));

		if (!this.directoryFile.exists()) {
			
			writeIntoFile(this.directoryFile,
					String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA));
		
		}
	}

	/**
	 * This method generate data file. It invokes {@link #readFromFile(File)}
	 * method to read the content of the path file and retrieve it which
	 * represents the absolute path of the data file. It then creates a data
	 * file at the destination if it does not exist. It invokes
	 * {@link #writeIntoFile(File, String)} method to write
	 * {@value notify.storage.operator.Constants#SQURE_BRACKETS} which represents the
	 * empty task list.
	 */
	private void generateDataFile() {
		
		assert(this.configFolder.exists() == true);
		assert(this.dataFolder.exists() == true);
		this.dataFile = new File(String.format(Constants.PATH_FILE, readFromFile(this.directoryFile),
				Constants.FILE_DATA, Constants.EMPTY_STRING));

		if (!this.dataFile.exists()) {
			
			writeIntoFile(this.dataFile, Constants.SQURE_BRACKETS);
			
		}
	}

	/**
	 * This method generate command files where the commands are as of {@see notify.command.Action}
	 * It invokes {@link #writeIntoFile(File, String)} method to write
	 * {@value notify.storage.operator.Constants#EMPTY_STRING}.
	 */
	private void generateCommandFiles() {
		
		assert(this.configFolder.exists() == true);
		assert(this.commandsFolder.exists() == true);
		
		for (Action command : Action.values()) {
			
			this.commandFile = new File(String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG,
					Constants.FOLDER_COMMANDS, command.toString().toLowerCase(), Constants.EXTENSION_FILE));

			if (!this.commandFile.exists()) {
				
				writeIntoFile(this.commandFile, Constants.EMPTY_STRING);
				
			}
		}
	}

	/**
	 * This method writes specified data into the specified file.
	 */
	private void writeIntoFile(File file_, String dataToWrite_) {
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;

		try {
			
			assert(file_.exists() == false);
			fileWriter = new FileWriter(file_);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(dataToWrite_);

			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}

	/**
	 * This method reads from the specified file. It then returns the content of
	 * the file.
	 */
	private String readFromFile(File file_) {
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String dataFileAbsolutePath;

		try {
			// log.log(Level.INFO, "Read commandStrings from: [{0}]",
			// fileName_);
			assert(file_.exists() == true);
			fileReader = new FileReader(file_);
			bufferedReader = new BufferedReader(fileReader);

			dataFileAbsolutePath = bufferedReader.readLine();

			bufferedReader.close();
			fileReader.close();

			return dataFileAbsolutePath;
			
		} catch (Exception e) {
			
			return Constants.EMPTY_STRING;
			
		}
	}
}
