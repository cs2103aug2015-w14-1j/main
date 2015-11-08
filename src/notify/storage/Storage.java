package notify.storage;

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
		this.fileGenerator = new FileGenerator();
		this.dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA, Constants.FILE_DESTINATION));
		this.dataFilePath = String.format(Constants.PATH_FILE, dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING);
		this.load = new TasksLoader(dataFilePath);
		this.loadCommand = new CommandsLoader();
		this.save = new TasksSaver(dataFilePath);
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {
		save.setFilePath(String.format(Constants.PATH_FILE, dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING));
		save.execute(taskList_);
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFileDestination(String newFilePath) {
		return dataDirectoryManager.execute(newFilePath);
	}
}
