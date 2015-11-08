/* @@author A0124072 */
package notify.storage;

import java.io.File;

/**
 * Collected constants of storage component.
 */

public final class Constants {
	public static final String PATH_SUB_FOLDER = "%1$s%2$s";
	public static final String PATH_FILE = "%1$s%2$s%3$s";
	public static final String PATH_COMMAND_FILE = "%1$s%2$s%3$s%4$s";

	
	public static final String NAME_FOLDER_CONFIG = "notify.config";
	public static final String NAME_FOLDER_DATA = "data";
	public static final String NAME_FOLDER_COMMANDS = "commands";
	
	public static final String NAME_FILE_DATA = "tasks";
	public static final String NAME_FILE_DESTINATION = "path";
	
	public static final String EXTENSION_FILE = ".txt";
	
	public static final String EMPTY_STRING = "";
	public static final String SQURE_BRACKETS = "[]";
	public static final String PERIOD = ".";
	public static final String WHITESPACE = " ";
	
	public static final String FOLDER_CONFIG = NAME_FOLDER_CONFIG + File.separator;
	public static final String FOLDER_DATA = NAME_FOLDER_DATA + File.separator;
	public static final String FOLDER_COMMANDS = NAME_FOLDER_COMMANDS + File.separator;
	public static final String FILE_DATA = NAME_FILE_DATA + EXTENSION_FILE;
	public static final String FILE_DESTINATION = PERIOD + NAME_FILE_DESTINATION + EXTENSION_FILE;
	
	/**
	   Reference the constants using <tt>Constants.EMPTY_STRING</tt>, etc. 
	   By declaring this private constructor, the caller will be prevented from constructing objects of 
	   this class. 
	  */
	private Constants() {
		throw new AssertionError();
	}
	
}
