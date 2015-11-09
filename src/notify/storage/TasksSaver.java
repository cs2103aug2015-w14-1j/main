package notify.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import notify.Task;

public class TasksSaver extends StorageOperation {

	/**
	 * Constructor
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
	protected ArrayList<Task> execute(Object taskList_) {
		
		assert (taskList_ != null);
		this.taskList = (ArrayList<Task>) taskList_;
		this.jsonString = this.jsonizeData(this.taskList);
		
		this.writeIntoFile();

		return null;
		
	}

	protected void setFilePath(String filePath_) {
		
		this.filePath = filePath_;
		
	}

	private String jsonizeData(ArrayList<Task> taskList_) {
		
		assert (taskList_ != null);
		String jsonDataString = this.gson.toJson(taskList_);

		return jsonDataString;
		
	}

	/**
	 * Writes jsonised string of the taskList to file. Each task object is
	 * stored as a json object in an json array.
	 */
	private void writeIntoFile() {
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		try {
			
			assert (new File(this.filePath).exists() == true);
			fileWriter = new FileWriter(this.filePath);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(this.jsonString);

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
}
