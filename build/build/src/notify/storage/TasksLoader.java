package notify.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import notify.Task;

public class TasksLoader extends StorageOperation {

	/**
	 * Constructor
	 * 
	 * @param filePath_
	 *            The path of the saved file(all the user's tasks are stored
	 *            here).
	 */
	protected TasksLoader(String filePath) {

		this.filePath = filePath;
		taskList = new ArrayList<Task>();
		gson = new Gson();

	}

	/**
	 * Execute process to load tasks from text file
	 * 
	 * @return ArrayList of tasks
	 */
	protected ArrayList<Task> execute(Object emptyList_) {
		
		boolean isReadSuccessfully = this.readFromFile();

		if (isReadSuccessfully) {
			
			assert (taskList != null);
			return taskList;
			
		} else {
			
			return null;
			
		}
	}

	@Override
	protected void setFilePath(String filePath_) {
		
		this.filePath = filePath_;
		
	}

	/**
	 * Reads from file and saves it line-by-line into taskList.
	 * 
	 * @return True for successful operation. False for unsuccessful operation.
	 */
	private Boolean readFromFile() {
		
		File file;
		FileReader fileReader;
		BufferedReader bufferedReader;
		boolean result;
		
		try {
			// log.log(Level.INFO, "Read commandStrings from: [{0}]",
			// fileName_);

			file = new File(filePath);
			
			assert (file.exists() == true);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			assert (gson != null);
			taskList = gson.fromJson(bufferedReader, new TypeToken<ArrayList<Task>>() {}.getType());

			bufferedReader.close();
			fileReader.close();

			result = true;
			
		} catch (Exception e) {
			
			result = false;
			
		}
		
		assert (result == true || result == false);
		return result;
		
	}
}
