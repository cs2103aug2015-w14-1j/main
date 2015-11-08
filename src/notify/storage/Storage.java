/* @@author A0124072 */
package notify.storage;

import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

/**
 * This Storage class is used to generate the system folders and files via FileGenerator class
 * {@see notify.storage.FileGenerator}, load and save the user's tasks/data via 
 * TasksLoader class{@see notify.storage.TasksLoader} and TasksSaver class{@see notify.storage.TasksSaver}.
 * It is also used to set the absolute file path of the data file via DataDirectoryManager class 
 * {@see notify.storage.DataDirectoryManager} and load the command variations 
 * via CommandsLoader class{@see notify.storage.CommandsLoader}
 */
public class Storage {
	/** 
	 * These variables are used to interact with other internal classes of the storage component
	 */
	private FileGenerator fileGenerator;
	private DataDirectoryManager dataDirectoryManager;
	private TasksSaver save;
	private TasksLoader load;
	private CommandsLoader loadCommand;
	private String dataFileAbsolutePath;
	
	/**
	 * This method is the class's Constructor which is used to instantiate the following variables
	 * {@link #fileGenerator} {@link #dataDirectoryManager} {@link #save} {@link #load}
	 * {@link #loadCommand} {@link #dataFileAbsolutePath}
	 * 
	 * It invokes {@see notify.storage.DataDirectoryManager#getDataFilePath()} to retrieve the 
	 * currently saved absolute file path of the data file.
	 */
	public Storage() {
		this.fileGenerator = new FileGenerator();
		this.dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA, Constants.FILE_DESTINATION));
		this.dataFileAbsolutePath = String.format(Constants.PATH_FILE, this.dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING);
		this.load = new TasksLoader(this.dataFileAbsolutePath);
		this.loadCommand = new CommandsLoader();
		this.save = new TasksSaver(this.dataFileAbsolutePath);
	}
	
	/**
	 * This method loads the user's tasks/data from the data file
	 * 
	 * @return the ArrayList<Task> object which contains all the tasks/data stored in the data file
	 */
	public ArrayList<Task> loadTasks(){
		return this.load.execute(new ArrayList<Task>());		
	}

	/**
	 * This method saves the user's tasks/data into the data file
	 * 
	 * @param taskList_		the ArrayList<Task> object which contains the currently added tasks/data
	 * 							which is to be written into the data file
	 */
	public void saveTasks(ArrayList<Task> taskList_) {
		this.save.setFilePath(String.format(Constants.PATH_FILE, dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING));
		this.save.execute(taskList_);
	}
	
	/**
	 * This method loads the command variations from the command files
	 * 
	 * @return the HashMap<String, Action> object of which <Key> represents the various command variations
	 * 			and <Value> represents the default system commands {@see notify.logic.command.Action}
	 */
	public HashMap<String, Action> loadCommands() {
		return this.loadCommand.execute(new ArrayList<Task>());
	}
	
	/**
	 * This method sets the absolute file path where the data file has to be saved/moved to
	 * 
	 * @param newFilePath_		the String representation of the new absolute file path of the data file
	 * 
	 * @return 'true' if the specified absolute file path is a valid directory; 'false' otherwise
	 */
	public boolean setFileDestination(String newFilePath_) {
		return this.dataDirectoryManager.execute(newFilePath_);
	}
}
