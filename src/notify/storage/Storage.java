package notify.storage;

import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

public class Storage {
	
	//private static final String[] commandFiles = {"ADD.txt", "DELETE.txt", "EDIT.txt", "SEARCH.txt", "MARK.txt", "DISPLAY.txt", "UNDO.txt", "SET.txt", "EXIT.txt"};

	private static final String PATHFILELINK = "notify.config/data/.filePath.txt";
	private static final String DATAFILE = "/tasks.txt";

	private FileGenerator fileGenerator;
	private FilePathManager filePathManager;
	private FileTransferManager fileTransferManager;
	private SaveTasks save;
	private LoadTasks load;
	private LoadCommands loadCommand;
	private String filePath;
	
	public Storage() {
		fileGenerator = new FileGenerator();
		filePathManager = new FilePathManager(PATHFILELINK);
		filePath = filePathManager.getDataFilePath()+DATAFILE;
		//System.out.println(filePath);
		save = new SaveTasks(filePath);
		load = new LoadTasks(filePath);
		loadCommand = new LoadCommands();
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {	
		save.execute(taskList_);
		fileTransferManager = new FileTransferManager(filePath, filePathManager.getDataFilePath()+DATAFILE);
		fileTransferManager.transferData();
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFilePath(String newFilePath) {
		return filePathManager.execute(newFilePath);
	}
}
