package notify.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import notify.Task;

public class SetFileLink extends StorageOperation{

	/**
	 * Constructor
	 * 
	 * @param pathLinkFile_		The file which contains the absolute path of the data file where all the user's tasks are stored.
	 */
	protected SetFileLink(String pathLinkFile_) {
		filePath = pathLinkFile_;
	}
	
	protected Object execute(ArrayList<Task> emptyList) {
		return null;
	}
	
	protected boolean execute(String newDataFilePath) {
		if(isValidFilePath(newDataFilePath)) {
			this.writeIntoFile(newDataFilePath);
			return true;
		} else {
			return false;
		}
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
	 * Writes absolute path of the data file to the path link file.
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
}
