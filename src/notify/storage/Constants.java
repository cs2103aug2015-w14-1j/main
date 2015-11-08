package notify.storage;

public final class Constants {
	public static final String PATH_SUB_FOLDER = "%1$s%2$s%3$s";
	public static final String PATH_VISIBLE_FILE = "%1$s%2$s%3$s%4$s";
	public static final String PATH_HIDDEN_FILE = "%1$s%2$s%3$s%4$s%5$s%6$s%7$s";
	
	public static final String FOLDER_CONFIG = "notify.config";
	public static final String FOLDER_DATA = "data";
	public static final String FOLDER_COMMANDS = "commands";
	
	public static final String FILE_DATA = "tasks";
	public static final String FILE_DIRECTORY = "path";
	
	public static final String EXTENSION_FILE = ".txt";
	
	public static final String EMPTY_STRING = "";
	public static final String SQURE_BRACKETS = "[]";
	public static final String PERIOD = ".";
	public static final String SPACE = " ";
	
	private Constants() {
		throw new AssertionError();
	}
	
}
