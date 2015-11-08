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
		this.dataFilePath = String.format(Constants.PATH_FILE, this.dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING);
		this.load = new TasksLoader(this.dataFilePath);
		this.loadCommand = new CommandsLoader();
		this.save = new TasksSaver(this.dataFilePath);
	}
	
	public ArrayList<Task> loadTasks(){
		return this.load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {
		this.save.setFilePath(String.format(Constants.PATH_FILE, dataDirectoryManager.getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING));
		this.save.execute(taskList_);
	}
	
	public HashMap<String, Action> loadCommands() {
		return this.loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFileDestination(String newFilePath) {
		return this.dataDirectoryManager.execute(newFilePath);
	}
}
