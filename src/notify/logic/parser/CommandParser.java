package notify.logic.parser;

import java.util.HashMap;

import notify.DateRange;
import notify.logic.command.Action;
import notify.logic.command.Command;
import notify.storage.Storage;

import org.apache.commons.lang3.StringUtils;

public class CommandParser {
	
	private static final String KEYWORD_BY = "BY";
	
	private static final String KEYWORD_ESCAPE = "/";
	private static final int KEYWORD_ESCAPE_OFFSET = 1;
	
	public static final String COMMAND_SEPERATOR = " ";
	
	private static final int SEPERATOR_COMMAND_INDEX = 0;
	public static final int FIRST_PARAM_INDEX = 0;
	
	private static final String ERROR_INVALID_COMMAND = "Unable to parse command. Invalid command provided.";
	private static final String ERROR_INVALID_PARAMS = "Unable to recognize parameter(s) entered.";
	
	private Storage storage;
	
	public CommandParser(Storage storage) {
		this.storage = storage;
	}
	
	public Command parse(String input) {
		
		assert input != null;
		
		Command command = null;
		input = input.trim();

		String[] split = input.split(COMMAND_SEPERATOR);
		Action action = retrieveAction(split[SEPERATOR_COMMAND_INDEX]);
		
		if(action != Action.INVALID) {
			int length = split[SEPERATOR_COMMAND_INDEX].length();
			input = input.substring(length, input.length()).trim();
		}
		
		switch(action) {
			case ADD:
				//command = new AddCommand(action, null);
				String category = CategoryParser.parse(input);
				DateRange dateRange = null;
				String name = null;
				
				if(category != null) { 
					int length = category.length() + CategoryParser.KEYWORD_HASHTAG.length();
					input = input.substring(0, input.length() - length);
				}
				
				//assume start date to be now, and the info as end date
				int index = input.toUpperCase().indexOf(KEYWORD_BY);
				if(index > 0) {
					//check if escaped
					String escapedCharacter = input.substring(index - KEYWORD_ESCAPE_OFFSET, index);
					boolean isEscaped = escapedCharacter.equalsIgnoreCase(KEYWORD_ESCAPE);
					
					if(isEscaped == false) {
						String range = input.substring(index + KEYWORD_BY.length(), input.length()).trim();
						dateRange = new DateRange(null, range);
					}
					
					name = input.substring(0, index);
				}
				
				break;
			case DELETE: command = handleDeleteCommand(input); break;
			case EDIT: break;
			case SEARCH: command = handleSearchCommand(input); break;
			case MARK: command = handleMarkCommand(input); break; 
			case DISPLAY: command = handleDisplayCommand(input); break;
			case UNDO: command = handleUndoCommand(input); break;
			case SET: command = handleSetCommand(input); break;
			case EXIT: command = handleExitCommand(input); break;
			default: throw new IllegalArgumentException(ERROR_INVALID_COMMAND);			
		}
		
		
		return command;
	}
	
	private Command handleAddCommand(String input) {
		return null;
	}
	
	private Command handleDeleteCommand(String input) {
		Command command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		boolean isNumeric = StringUtils.isNumeric(split[FIRST_PARAM_INDEX]);
		if(isNumeric == false) {
			throw new IllegalArgumentException(ERROR_INVALID_PARAMS);
		}
		
		return command;
	}
	
	private Command handleMarkCommand(String input) {
		Command command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		boolean isNumeric = StringUtils.isNumeric(split[FIRST_PARAM_INDEX]);
		if(isNumeric == false) {
			throw new IllegalArgumentException(ERROR_INVALID_PARAMS);
		}
		
		return command;
	}
	
	private Command handleSearchCommand(String input) {
		Command command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		return command;
	}
	
	private Command handleDisplayCommand(String input) {
		Command command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		return command;
	}
	
	private Command handleUndoCommand(String input) {
		Command command = null;
		
		return command;
	}
	
	private Command handleSetCommand(String input) {
		Command command = null;
		
		return command;
	}
	
	private Command handleExitCommand(String input) {
		Command command = null;
		
		return command;
	}
	
	public Action retrieveAction(String action) {
		
		assert storage != null;
		
		action = action.toUpperCase();
		Action result = Action.INVALID;
		
		HashMap<String, Action> commandVariations = storage.loadCommands();
		if(commandVariations.containsKey(action)) {
			result = commandVariations.get(action); 
		}
		
		return result;
	}
}
