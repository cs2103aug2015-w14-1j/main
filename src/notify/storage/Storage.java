package notify.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

public class Storage {
	
	private FileGenerator fileGenerator;
	private DataDirectoryManager dataDirectoryManager;
	private FileTransferManager fileTransferManager;
	private TasksSaver save;
	private TasksLoader load;
	private CommandsLoader loadCommand;
	private String dataFilePath;
	
	public Storage() {
		fileGenerator = new FileGenerator();
		dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_HIDDEN_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA, File.separator, Constants.PERIOD, Constants.FILE_DIRECTORY, Constants.EXTENSION_FILE));
		dataFilePath = dataDirectoryManager.getDataFilePath()+File.separator+Constants.FILE_DATA+Constants.EXTENSION_FILE;
		save = new TasksSaver(dataFilePath);
		load = new TasksLoader(dataFilePath);
		loadCommand = new CommandsLoader();
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {	
		save.execute(taskList_);
		fileTransferManager = new FileTransferManager(dataFilePath, dataDirectoryManager.getDataFilePath()+File.separator+Constants.FILE_DATA+Constants.EXTENSION_FILE);
		fileTransferManager.transferData();
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFilePath(String newFilePath) {
		return dataDirectoryManager.execute(newFilePath);
	}
}
