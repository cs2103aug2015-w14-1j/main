package notify.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import notify.Task;

public class DataDirectoryManager extends StorageOperation{

	private String dataFilePath;
	
	/**
	 * Constructor
	 * 
	 * @param directoryFile_		The file which contains the absolute path of the data file where all the user's tasks are stored.
	 */
	protected DataDirectoryManager(String filePath) {
		this.filePath = filePath;
		readFromFile();
	}
	
	protected Boolean execute(Object newDataFilePath) {
		if(isValidFilePath((String)newDataFilePath)) {
			dataFilePath = (String)newDataFilePath;
			this.writeIntoFile((String)newDataFilePath);
			return true;
		} else {
			return false;
		}
	}
	
	public String getDataFilePath() {
		return dataFilePath;
	}
	
	private boolean isValidFilePath(String newFilePath) {
	    File file = new File(newFilePath);
	    
	    if(file.isDirectory()) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Writes absolute path of the data file to the path text file.
	 */
	private void writeIntoFile(String newDataFilePath) {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(newDataFilePath);
            
            bufferedWriter.close();
            fileWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private Boolean readFromFile() {
		try {
			//log.log(Level.INFO, "Read commandStrings from: [{0}]", fileName_);
			
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			dataFilePath = bufferedReader.readLine();

			bufferedReader.close();
			fileReader.close();
			
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
}
