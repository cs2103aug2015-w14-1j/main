/* @@author A0124072 */
package notify.storage.api;

import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;
import notify.logic.logger.LogType;
import notify.logic.logger.Writer;
import notify.storage.operator.Constants;
import notify.storage.operator.FilesGenerator;

/**
 * This Storage class is used to generate the system folders and files via
 * FileGenerator class {@see notify.storage.FileGenerator}, load and save the
 * user's tasks/data via TasksLoader class {@see notify.storage.TasksLoader} and
 * TasksSaver class {@see notify.storage.TasksSaver}. It is also used to set the
 * absolute file path of the data file via DataDirectoryManager class
 * {@see notify.storage.DataDirectoryManager} and load the command variations
 * via CommandsLoader class {@see notify.storage.CommandsLoader}.
 */
public class Storage {
	/**
	 * These variables are used to interact with other internal classes of the
	 * storage component.
	 */
	private FilesGenerator fileGenerator;
	private DataDirectoryManager dataDirectoryManager;
	private TasksSaver save;
	private TasksLoader load;
	private CommandsLoader loadCommand;
	private String dataFileAbsolutePath;
	private Writer logger;

	/**
	 * The class Constructor which initializes a newly created
	 * Storage object so that it represents an object which is used to store and
	 * retrieve data. It instantiates the following variables
	 * {@link #fileGenerator}, {@link #dataDirectoryManager} {@link #save},
	 * {@link #load}, {@link #loadCommand} and {@link #dataFileAbsolutePath}.
	 * 
	 * It invokes {@see notify.storage.DataDirectoryManager#getDataFilePath()}
	 * to retrieve the currently saved absolute file path of the data file.
	 */
	public Storage() {

		this.fileGenerator = new FilesGenerator();
		this.fileGenerator.generate();
		
		this.dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_FILE, Constants.FOLDER_CONFIG,
																Constants.FOLDER_DATA, Constants.FILE_DESTINATION));
		
		this.dataFileAbsolutePath = String.format(Constants.PATH_FILE, this.dataDirectoryManager.getDataFilePath(),
										Constants.FILE_DATA, Constants.EMPTY_STRING);
		
		this.load = new TasksLoader(this.dataFileAbsolutePath);
		
		this.loadCommand = new CommandsLoader();
		
		this.save = new TasksSaver(this.dataFileAbsolutePath);
		
		this.logger = new Writer(this.getClass().getName(), this.getFileDestination());
		
		this.logger.write(LogType.INFO, "Storage Component Started Successfully");

	}

	/**
	 * This method loads the user's tasks/data from the data file.
	 * 
	 * @return the ArrayList<Task> object which contains all the tasks/data
	 *         stored in the data file
	 */
	public ArrayList<Task> loadTasks() {

		assert(load != null);
		return this.load.execute(new ArrayList<Task>());

	}

	/**
	 * This method saves the user's tasks/data into the data file.
	 * 
	 * @param taskList_
	 *            the ArrayList<Task> object which contains the currently added
	 *            tasks/data which is to be written into the data file
	 */
	public void saveTasks(ArrayList<Task> taskList) {

		assert(taskList != null);
		assert(save != null);
		assert(dataDirectoryManager != null);

		this.save.setFilePath(String.format(Constants.PATH_FILE, dataDirectoryManager.getDataFilePath(),
				Constants.FILE_DATA, Constants.EMPTY_STRING));
		
		this.save.execute(taskList);

	}

	/**
	 * This method loads the command variations from the command files.
	 * 
	 * @return the HashMap<String, Action> object of which <Key> represents the
	 *         various command variations and <Value> represents the default
	 *         system commands {@see notify.logic.command.Action}
	 */
	public HashMap<String, Action> loadCommands() {

		assert(loadCommand != null);
		
		return this.loadCommand.execute(new ArrayList<Task>());

	}

	/**
	 * This method sets the absolute file path where the data file has to be
	 * saved/moved to.
	 * 
	 * @param newFilePath_
	 *            the String representation of the new absolute file path of the
	 *            data file
	 * 
	 * @return 'true' if the specified absolute file path is a valid directory;
	 *         'false' otherwise
	 */
	public boolean setFileDestination(String newFilePath) {

		assert(dataDirectoryManager != null);
		
		return this.dataDirectoryManager.execute(newFilePath);

	}
	
	/**
	 * returns the file destination set by user
	 */
	public String getFileDestination() {
		
		return this.dataDirectoryManager.getDataFilePath();
		
	}
	
}
