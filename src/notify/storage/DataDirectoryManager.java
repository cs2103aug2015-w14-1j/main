/* @@author A0124072 */
package notify.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataDirectoryManager extends StorageOperation{
	private String dataFilePath;
	
	/**
	 * Constructor
	 * 
	 * @param filePath_		The file which contains the absolute path of the data file where all the user's tasks are stored.
	 */
	protected DataDirectoryManager(String filePath_) {
		assert (filePath_ != null);
		this.filePath = filePath_;
		readFromFile();
	}
	
	protected Boolean execute(Object newDataFilePath_) {		
		FileTransferManager fileTrsMngr;
		
		assert newDataFilePath_ != null;
		newDataFilePath_ = appendSeperator((String) newDataFilePath_);
		
		if (isValidDirectory((String) newDataFilePath_)) {
			fileTrsMngr = new FileTransferManager(String.format(Constants.PATH_FILE,
			        getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING),
					String.format(Constants.PATH_FILE, (String)newDataFilePath_,Constants.FILE_DATA,
					Constants.EMPTY_STRING));
			fileTrsMngr.transferData();
			
			this.dataFilePath = (String)newDataFilePath_;
			this.writeIntoFile((String)newDataFilePath_);
			
			return true;
		} else {
			return false;
		}
	}
	
	protected void setFilePath(String filePath_) {
		this.filePath = filePath_;
	}
	
	public String getDataFilePath() {
		return dataFilePath;
	}
	
	private String appendSeperator(String newDataFilePath_) {
		if (!containsLastSeperator(newDataFilePath_)) {
			newDataFilePath_ = newDataFilePath_ + File.separator;
		}
		
		return newDataFilePath_;
	}
	
	private boolean containsLastSeperator(String newDataFilePath_) {
		boolean result = newDataFilePath_.substring(newDataFilePath_.length()-1, 
													newDataFilePath_.length()).equals(File.separator);
		
		return result;
	}
	
	private boolean isValidDirectory(String newFilePath_) {
	    File file = new File(newFilePath_);
	    
	    if (file.isDirectory()) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	/**
	 * Writes absolute path of the data file to the path text file.
	 */
	private void writeIntoFile(String newDataFilePath_) {
		try {
			FileWriter fileWriter = new FileWriter(this.filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(newDataFilePath_);
            
            bufferedWriter.close();
            fileWriter.close();   
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private Boolean readFromFile() {
		try {
			//log.log(Level.INFO, "Read commandStrings from: [{0}]", fileName_);
			
			File file = new File(this.filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			this.dataFilePath = bufferedReader.readLine();

			bufferedReader.close();
			fileReader.close();
			
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
}
