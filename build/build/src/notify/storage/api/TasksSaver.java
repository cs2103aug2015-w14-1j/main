/* @@author A0124072 */
package notify.storage.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import notify.Task;

import com.google.gson.Gson;

public class TasksSaver extends StorageOperation {

	/**
	 * The class Constructor
	 * 
	 * @param filePath_
	 *            The path of the saved file(all the user's tasks are stored
	 *            here).
	 */
	protected TasksSaver(String filePath_) {

		this.filePath = filePath_;
		this.gson = new Gson();

	}

	/**
	 * Executes process to save file
	 * 
	 * @param taskList_
	 *            List of Tasks
	 * @return null as logic does not require confirmation of successful save
	 */
	@SuppressWarnings("unchecked")
	protected ArrayList<Task> execute(Object taskList) {

		assert(taskList != null);
		this.taskList = (ArrayList<Task>) taskList;
		this.jsonString = this.jsonizeData(this.taskList);

		this.writeIntoFile();

		return null;

	}

	protected void setFilePath(String filePath_) {

		this.filePath = filePath_;

	}

	private String jsonizeData(ArrayList<Task> taskList_) {

		assert(taskList_ != null);
		String jsonDataString = this.gson.toJson(taskList_);

		return jsonDataString;

	}

	/**
	 * Writes jsonised string of the taskList in the TaskManager class
	 * {@see notify.logic.TaskManager} to the data file.
	 * 
	 * @return a boolean object: 'true' if the data is successfully written into
	 *         the file; 'false' if the file that it is writing to does not
	 *         exist or failed in FileWriter io.
	 */
	private void writeIntoFile() {
		
		File file;
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		try {
			
			file = new File(this.filePath);
			
			assert (file.isFile() == true || file.isDirectory() == true);	
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(this.jsonString);

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			
			e.printStackTrace();
			
		}

	}

}
