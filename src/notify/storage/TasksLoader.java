/* @@author A0124072 */
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
	 * The class Constructor
	 * 
	 * @param filePath_
	 *            The path of the saved file(all the user's tasks are stored
	 *            here).
	 */
	protected TasksLoader(String filePath) {

		this.filePath = filePath;
		this.taskList = new ArrayList<Task>();
		this.gson = new Gson();

	}

	/**
	 * Execute process to load tasks from text file.
	 * 
	 * @return ArrayList of tasks
	 */
	protected ArrayList<Task> execute(Object emptyList_) {
		
		boolean isReadSuccessfully = this.readFromFile();

		if (isReadSuccessfully) {
			
			assert (this.taskList != null);
			return this.taskList;
			
		} else {
			
			return null;
			
		}
	}

	protected void setFilePath(String filePath_) {
		
		this.filePath = filePath_;
		
	}

	/**
	 * Reads from file and saves it line-by-line into taskList.
	 * 
	 * @return 'true' for successful operation; 'false' for unsuccessful operation.
	 */
	private Boolean readFromFile() {
		
		File file;
		FileReader fileReader;
		BufferedReader bufferedReader;
		boolean result;
		
		try {
			// log.log(Level.INFO, "Read commandStrings from: [{0}]",
			// fileName_);

			file = new File(this.filePath);
			
			assert (file.exists() == true);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			assert (this.gson != null);
			this.taskList = this.gson.fromJson(bufferedReader, new TypeToken<ArrayList<Task>>() {}.getType());

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
