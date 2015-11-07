package notify.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import notify.Task;
import notify.logic.command.Action;

public class Storage {
	
	//private static final String[] commandFiles = {"ADD.txt", "DELETE.txt", "EDIT.txt", "SEARCH.txt", "MARK.txt", "DISPLAY.txt", "UNDO.txt", "SET.txt", "EXIT.txt"};

	private static final String PATHFILELINK = "notify.config/data/.filePath.txt";
	private static final String DATAFILE = "/tasks.txt";

	private FileGenerator fileGenerator;
	private DataDirectoryManager dataDirectoryManager;
	private FileTransferManager fileTransferManager;
	private TasksSaver save;
	private TasksLoader load;
	private LoadCommands loadCommand;
	private String filePath;
	
	public Storage() {
		fileGenerator = new FileGenerator();
		dataDirectoryManager = new DataDirectoryManager(String.format(Constants.PATH_HIDDEN_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA, File.separator, Constants.PERIOD, Constants.FILE_DIRECTORY, Constants.EXTENSION_FILE));
		filePath = dataDirectoryManager.getDataFilePath()+File.separator+Constants.FILE_DATA+Constants.EXTENSION_FILE;
		//System.out.println(filePath);
		save = new TasksSaver(filePath);
		load = new TasksLoader(filePath);
		loadCommand = new LoadCommands();
	}
	
	public ArrayList<Task> loadTasks(){
		return load.execute(new ArrayList<Task>());		
	}

	public void saveTasks(ArrayList<Task> taskList_) {	
		save.execute(taskList_);
		fileTransferManager = new FileTransferManager(filePath, dataDirectoryManager.getDataFilePath()+File.separator+Constants.FILE_DATA+Constants.EXTENSION_FILE);
		fileTransferManager.transferData();
	}
	
	public HashMap<String, Action> loadCommands() {
		return loadCommand.execute(new ArrayList<Task>());
	}
	
	public boolean setFilePath(String newFilePath) {
		return dataDirectoryManager.execute(newFilePath);
	}
}
