package notify.storage;

import com.google.gson.Gson;
import notify.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TasksSaver extends StorageOperation {
	
	/**
	 * Constructor
	 * 
	 * @param filePath_		The path of the saved file(all the user's tasks are stored here).
	 */
	protected TasksSaver(String filePath_) {
		filePath = filePath_;
		gson = new Gson();
	}
	
	/**
	 * Executes process to save file
	 * 
	 * @param taskList_		List of Tasks
	 * @return				Returns null as logic does not require
	 * 						confirmation of successful save
	 */
	protected ArrayList<Task> execute(Object taskList_) {
		taskList = (ArrayList<Task>) taskList_;
		jsonString = this.jsonizeData(taskList);
		this.writeIntoFile();
		
		return null;
	}
	
	protected void setFilePath(String filePath_) {
		this.filePath = filePath_;
	}
	
	private String jsonizeData(ArrayList<Task> taskList_) {		
		String jsonDataString = gson.toJson(taskList_);
		
		return jsonDataString;
	}
	
	/**
	 * Writes jsonised string of the taskList to file.
	 * Each task object is stored as a json object in an json array.
	 */
	private void writeIntoFile() {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(jsonString);
            
            bufferedWriter.close();
            fileWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
