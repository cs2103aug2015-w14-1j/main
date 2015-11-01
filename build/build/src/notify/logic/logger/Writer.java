package notify.logic.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used to write log files to a directory   
 */

public class Writer {

	public static final String STRING_LOG = "%1$s\t\t%2$s\t\t%3$s\t\t%4$s\n";
	public static final String ERROR_WRITE = "Unable to create log file. Logs will be written to console";
	
	// These are the required variables to determine the class path
	private String className;
	private String directoryPath;
	private String fileName = "log.log";
	
	private File file;
	private FileWriter writer;
	
	// These are the valid logging type that an error can be classified into
	public static enum LOGTYPE { DEBUG, ERROR, INFO, VERBOSE, WEIRD, WTF };
	
	/**
	 * This constructor creates caches the class name and directory for logging
	 * A new file is created if no existing file is found
	 * This constructor is to be instantiated at the constructor of each class
	 * 
	 * @param className
	 *           class name of the file required to be logged
	 * 
	 * @param directoryPath
	 *           location of the path to be stored
	 */
	public Writer(String className, String directoryPath) { 
		this.className = className;
		this.directoryPath = directoryPath;
		
		try {
			this.file = new File(this.directoryPath + fileName);
			this.writer = new FileWriter(this.file, true);
			
		} catch (IOException e) {
			System.out.println(ERROR_WRITE);
		}
	}
	
	/**
	 * This method writes the error log to both the log file and console
	 * 
	 * @param logType
	 *           specifies the type of log to be classified
	 *           
	 * @param message
	 *           message of the error to be written
	 */
	public String write(LOGTYPE logType, String message) {
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    
		String result = String.format(STRING_LOG, formatter.format(calendar.getTime()), logType.toString(), this.className, message);
	    if(this.writer != null) {
	    	try {
	    		this.writer.write(result);
		    	this.writer.flush();
		    	
	    	} catch (IOException e) {
				System.out.println(ERROR_WRITE);
			}
	    }
	    
	    System.out.println(result);
	   
	    return result; 
	}
	
	/**
	 * This method returns the logging file created
	 * 
	 */
	public File getFile() { 
		return this.file;
	}
	
	/**
	 * This method will attempt to close the writer if it exist
	 *
	 */
	public void close() {
		if(this.writer != null) {
			try {
				this.writer.close();
				
			} catch (IOException e) {
				System.out.println(ERROR_WRITE);
			}
		}
	}
}
