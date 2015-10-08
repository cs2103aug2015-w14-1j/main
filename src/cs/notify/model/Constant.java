package cs.notify.model;

public class Constant {
	
	// These are the valid commands that the system allows
	public enum Command { CREATE, UPDATE, DELETE, SEARCH, BLOCK, UNDO, REDO, DONE, EXIT, INVALID }; 
	
	/*
     * =========STORAGE CLASS CONSTANTS============ 
     */
    private static String[] commandFile = new String[]{"CREATE.txt", "DONE.txt", "DELETE.txt", "EXIT.txt", "REDO.txt", "SEARCH.txt", "UPDATE.txt", "UNDO.txt", "BLOCK.txt"}; 
    
    /*
     * =========PARSER CLASS CONSTANTS============ 
     */

    public static final int UNASSIGNED_TASK = -1;
	
	// This are constants used for params related variables
	public static final int PARAM_PARSER_MINIMUM = 1;
	
	// These are common strings to be used for the project
	public static final String STRING_EMPTY = "";
	public static final String STRING_SPACE = " ";
	public static final String STRING_ESCAPE = "/";
	
	// These are error messages to be return by the system
	public static final String ERROR_INVALID_PARAMS = "You have insufficient number of parameters.";
	
	// These are keywords to search in user inputs
	public static final String[] KEYWORD_AT = { "AT", "@" };
	public static final String[] KEYWORD_FROM = { "FROM" };
	public static final String[] KEYWORD_TO = { "TO" };
	public static final String[] KEYWORD_ON = { "ON" };
	public static final String[] KEYWORD_HASH = { "#" };
}
