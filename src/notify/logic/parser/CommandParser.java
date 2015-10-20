package notify.logic.parser;

import notify.DateRange;
import notify.logic.command.Action;
import notify.logic.command.AddCommand;
import notify.logic.command.Command;

public class CommandParser {
	
	private static final String KEYWORD_BY = "BY";
	
	private static final String KEYWORD_ESCAPE = "/";
	private static final int KEYWORD_ESCAPE_OFFSET = 1;
	
	public static final String COMMAND_SEPERATOR = " ";
	
	private static final int SEPERATOR_COMMAND_INDEX = 0;
	
	private static final String ERROR_INVALID_COMMAND = "Unable to parse command. Invalid command provided.";
	
	public CommandParser() {
		
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
				command = new AddCommand(action, null);
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
			case DELETE: 
				break;
			case EDIT:
				break;
			case SEARCH:
				break;
			case MARK:
				break;
			case DISPLAY:
				break;
			case UNDO:
				break;
			case SET:
				break;
			case EXIT:
				break;
			default: throw new IllegalArgumentException(ERROR_INVALID_COMMAND);			
		}
		
		
		return command;
	}
	
	//private Command handleAddCommand(String )
	
	public Action retrieveAction(String action) {
		return Action.ADD;
	}
}
