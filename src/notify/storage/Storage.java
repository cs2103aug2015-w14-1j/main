package notify.storage;

import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

public class Storage {
	private static final String PATHLINK = "data/.filePath.txt";
	private static final String FILEPATH = "data/tasks.txt";

	private SetFileLink set;
	private SaveTasks save;
	private LoadTasks load;
	private LoadCommands loadCommand;
	
	public Storage() {
		set = new SetFileLink(PATHLINK);
		save = new SaveTasks(FILEPATH);
		load = new LoadTasks(FILEPATH);
		loadCommand = new LoadCommands();
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {
		save.execute(taskList_);
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFilePath(String newFilePath) {
		return set.execute(newFilePath);
	}
}
