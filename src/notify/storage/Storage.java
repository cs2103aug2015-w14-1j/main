package notify.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

public class Storage {
	
	private FileGenerator fileGenerator;
	private DataDirectoryManager dataDirectoryManager;
	private TasksSaver save;
	private TasksLoader load;
	private CommandsLoader loadCommand;
	private String dataFilePath;
	
	public Storage() {
		fileGenerator = new FileGenerator();
		dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_HIDDEN_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA, File.separator, Constants.PERIOD, Constants.FILE_DIRECTORY, Constants.EXTENSION_FILE));
		dataFilePath = String.format(Constants.PATH_VISIBLE_FILE, dataDirectoryManager.getDataFilePath(), File.separator, Constants.FILE_DATA, Constants.EXTENSION_FILE);
		load = new TasksLoader(dataFilePath);
		loadCommand = new CommandsLoader();
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {
		updateDataFilePath();
		save = new TasksSaver(dataFilePath);
		save.execute(taskList_);
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFilePath(String newFilePath) {
		return dataDirectoryManager.execute(newFilePath);
	}
	
	private void updateDataFilePath() {
		dataFilePath = String.format(Constants.PATH_VISIBLE_FILE, dataDirectoryManager.getDataFilePath(), File.separator, Constants.FILE_DATA, Constants.EXTENSION_FILE);
	}
}
