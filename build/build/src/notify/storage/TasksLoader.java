package notify.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import notify.Task;


public class TasksLoader extends StorageOperation{
	
	/**
	 * Constructor
	 * 
	 * @param filePath_		The path of the saved file(all the user's tasks are stored here).
	 */
	protected TasksLoader(String filePath_) {
		filePath = filePath_;
		taskList = new ArrayList<Task>();
		gson = new Gson();
	}
	
	/**
	 * Execute process to load tasks from text file
	 * 
	 * @return			ArrayList of tasks
	 */
	protected ArrayList<Task> execute(Object emptyList_) {
		boolean isReadSuccessfully = this.readFromFile();
		
		if(isReadSuccessfully) {
			return taskList;
		} else {
			return null;
		}
	}
	
	/*
	protected boolean execute(String emptyString) {
		return false;
	}
	*/
	
	/**
	 * Reads from file and saves it line-by-line into taskList.
	 * 
	 * @return			True for successful operation. False for unsuccessful operation.
	 */
	private Boolean readFromFile() {
		try {
			//log.log(Level.INFO, "Read commandStrings from: [{0}]", fileName_);
			
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			taskList = gson.fromJson(bufferedReader, new TypeToken<ArrayList<Task>>() {}.getType());

			bufferedReader.close();
			fileReader.close();
			
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
}
