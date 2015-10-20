package notify.storage;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import notify.logic.command.Action;
import notify.Task;

public abstract class StorageOperation {
	
	protected Gson gson;
	protected String jsonString;
	protected String filePath;
	protected String[] commandFiles;
	protected ArrayList<Task> taskList;
	protected HashMap<String, Action> commands;
	
	abstract Object execute(ArrayList<Task> taskList_);
}
