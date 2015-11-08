//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

import java.util.HashMap;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;
import notify.logic.command.Action;
import notify.logic.command.AddCommand;
import notify.logic.command.BackCommand;
import notify.logic.command.Command;
import notify.logic.command.DeleteCommand;
import notify.logic.command.DisplayCommand;
import notify.logic.command.EditCommand;
import notify.logic.command.ExitCommand;
import notify.logic.command.MarkCommand;
import notify.logic.command.ReversibleCommand;
import notify.logic.command.SearchCommand;
import notify.logic.command.SetCommand;
import notify.logic.command.UndoCommand;
import notify.storage.Storage;

import org.apache.commons.lang3.StringUtils;

/**
 * This class is used to handle the inputs given and converts them into system recognized commands
 */
public class CommandParser {
	
	// These variables are used to interact with the other part of the system instantiated by Logic
	private Storage storage;
	private TaskManager taskManager;
	private Stack<ReversibleCommand> historyStack;
	
	public CommandParser(Storage storage, TaskManager taskManager, Stack<ReversibleCommand> historyStack) {
	
		assert storage != null;
		assert taskManager != null;
		assert historyStack != null;
		
		this.storage = storage;
		this.taskManager = taskManager;
		this.historyStack = historyStack;
	
	}
	
	/**
	 * This method is the main method to be called for commands to be processed
	 * 
	 * @param input is parsed as a string into a system recognized command
	 * 			the corresponding handler is called to process the method
	 * 
	 * @returns Command information is allocated into its corresponding variables 
	 * 				for further processing
	 */
	public Command parse(String input) {
		
		assert input != null;
		input = input.trim();

		Command command = null;
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		String param = split[Constants.COMMAND_LOCATION_INDEX];
		Action commandAction = retrieveAction(param);
		
		if(commandAction != Action.INVALID) {
		
			int length = split[Constants.COMMAND_LOCATION_INDEX].length();
			input = input.substring(length, input.length()).trim();
		
		}
		
		switch(commandAction) {
		
			case ADD: 
				command = handleAddCommand(commandAction, historyStack, taskManager, input); 
				break;
				
			case BACK: 
				command = handleBackCommand(commandAction, historyStack, taskManager, input); 
				break;
				
			case DELETE: 
				command = handleDeleteCommand(commandAction, historyStack, taskManager, input); 
				break;
				
			case EDIT: 
				command = handleEditCommand(commandAction,historyStack, taskManager, input); 
				break;
				
			case SEARCH: 
				command = handleSearchCommand(commandAction, taskManager, input); 
				break;
				
			case MARK: 
				command = handleMarkCommand(commandAction, historyStack, taskManager, input); 
				break;
				
			case DISPLAY: 
				command = handleDisplayCommand(commandAction, historyStack, taskManager, input); 
				break;
				
			case UNDO: 
				command = handleUndoCommand(commandAction, historyStack, input); 
				break;
				
			case SET: 
				command = handleSetCommand(commandAction, storage, input); 
				break;
				
			case EXIT: 
				command = handleExitCommand(commandAction, taskManager, input); 
				break;
				
			default: 
				throw new IllegalArgumentException(Constants.ERROR_INVALID_COMMAND);			
		
		}
		
		return command;
		
	}
	
	/**
	 * This method handles the add parsing and calls the AddCommand class to process
	 * The name, daterange and category is extracted according the the provided syntax if present
	 */
	private Command handleAddCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		String category = CategoryParser.parse(input);
		TaskType taskType = TaskType.FLOATING;
		DateRange dateRange = null;
		String name = input.trim();
		
		if(category != null) { 
			
			int length = category.length() + Constants.KEYWORD_HASHTAG.length();
			input = input.substring(0, input.length() - length);
		
		}
		
		//check if command contains any keywords
		String datePrompt = containsKeyword(input, Constants.DATETIME_PROMPT_KEYWORDS);
		String timePrompt = containsKeyword(input, Constants.DATETIME_KEYWORDS);
		String today = Constants.KEYWORD_TODAY[Constants.PARAM_FIRST_INDEX].toLowerCase();
		String[] results = null;
		
		if(datePrompt != null) { 
			
			results = parseDate(input);
						
			if(datePrompt.equalsIgnoreCase(Constants.KEYWORD_FROM)) {
				
				DateRange timeRange = DateTimeParser.parseTimeRange(results[Constants.PARAM_RESULT_DATE]);
				
				if(timeRange != null) {
					
					String processedInput = preProcessDate(input, today);
					datePrompt = containsKeyword(processedInput, Constants.DATETIME_PROMPT_KEYWORDS);
					results = parseDate(processedInput);
					
				} 
				
			}
			
		} else if(timePrompt != null) {
			
			String processedInput = preProcessDate(input, today);
			datePrompt = containsKeyword(processedInput, Constants.DATETIME_PROMPT_KEYWORDS);
			results = parseDate(processedInput);
		
		}
		
		String processedInput = handleShortHand(input, name, results);
		
		if(processedInput != null) {
			
			datePrompt = containsKeyword(processedInput, Constants.DATETIME_PROMPT_KEYWORDS);
			results = parseDate(processedInput);
			
		}
	
		if(datePrompt != null || timePrompt != null) {

			if(results != null) {

				name = results[Constants.PARAM_RESULT_NAME];
				dateRange = DateTimeParser.parseDateRange(results[Constants.PARAM_RESULT_DATE]);
			
				if(datePrompt.equalsIgnoreCase(Constants.KEYWORD_FROM)) {
					
					if(dateRange.isSameDay()) {
						
						taskType = TaskType.DEADLINE;
				
					} else {
						
						taskType = TaskType.RANGE;
				
					}
				
				} else {
			
					taskType = TaskType.DEADLINE;
				
				}
			
			} else {
				
				throw new IllegalArgumentException(Constants.ERROR_INVALID_PARAMS);	
				
			}
		
		}
		
		if(name.equalsIgnoreCase(Constants.STRING_EMPTY)) { 
			
			throw new IllegalArgumentException(Constants.ERROR_INVALID_PARAMS); 
		
		}
		
		name = name.replaceAll(Constants.STRING_ESCAPE, Constants.STRING_EMPTY);
		
		AddCommand command = new AddCommand(commandAction, taskManager, historyStack);
		command.addValues(name, taskType, dateRange, category);
		
		return command;
	
	}
	
	/**
	 * This method handles the back parsing and calls the BackCommand class to process
	 */
	private Command handleBackCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		BackCommand command = null;
		
		command = new BackCommand(commandAction, taskManager);
		
		return command;
		
	}
	
	
	/**
	 * This method handles the delete parsing and calls the DeleteCommand class to process
	 * The requested id is extracted from the command
	 * If the id extracted is invalid, an exception is thrown
	 */
	private Command handleDeleteCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		DeleteCommand command = null;
		
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		
		String param = split[Constants.PARAM_FIRST_INDEX];
		boolean isNumeric = StringUtils.isNumeric(param);
		
		if(isNumeric == false) {
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_PARAMS);
		
		}
		
		int id = Integer.parseInt(split[Constants.PARAM_FIRST_INDEX]);
		
		command = new DeleteCommand(commandAction, historyStack, taskManager);
		command.addValues(id);
		
		return command;
	
	}

	/**
	 * This method handles the edit parsing and calls the EditCommand class to process
	 * The name, daterange and category is extracted according the the provided syntax if present
	 */
	private Command handleEditCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		int id = Task.UNASSIGNED_TASK;
		TaskType taskType = null;
		DateRange dateRange = null;
		String category = CategoryParser.parse(input);
		String name = input;
		
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		
		String param = split[Constants.PARAM_FIRST_INDEX];
		boolean isNumeric = StringUtils.isNumeric(param);
		
		if(isNumeric == false) { 
		
			throw new IllegalArgumentException(Constants.ERROR_INVALID_PARAMS);
		
		}
		
		param = split[Constants.PARAM_FIRST_INDEX];
		id = Integer.parseInt(param);
		
		if(id != Task.UNASSIGNED_TASK) { 
			
			int length = String.valueOf(id).length();
			input = input.substring(length, input.length());
			name = input.trim();
			
		}
	
		if(category != null) { 
			
			int length = category.length() + Constants.KEYWORD_HASHTAG.length();
			input = input.substring(0, input.length() - length);
			
		}

		//check if command contains any keywords
		String datePrompt = containsKeyword(input, Constants.DATETIME_PROMPT_KEYWORDS);
		if(datePrompt != null) { 
			
			String[] results = parseDate(input);
			name = results[Constants.PARAM_RESULT_NAME].trim();
			dateRange = DateTimeParser.parseDateRange(results[Constants.PARAM_RESULT_DATE]);
			
			if(datePrompt.equalsIgnoreCase(Constants.KEYWORD_FROM)) {
				
				if(dateRange.isSameDay() == true) {
					
					taskType = TaskType.DEADLINE;
					
				} else {
					
					taskType = TaskType.RANGE;
					
				}
				
			} else {
				
				taskType = TaskType.DEADLINE;
				
			}
			
		}	
		
		name.replaceAll(Constants.STRING_ESCAPE, Constants.STRING_EMPTY);
		name = name.trim();
		
		if(name.equalsIgnoreCase(Constants.STRING_EMPTY)) { 
			
			name = null; 
		
		}
		
		EditCommand command = new EditCommand(commandAction, historyStack, taskManager);
		command.addValues(name, dateRange, category, id, taskType);
		
		return command;
		
	}
	
	/**
	 * This method handles the mark parsing and calls the MarkCommand class to process
	 * The requested id is extracted from the command
	 * If the id extracted is invalid, an exception is thrown
	 */
	private Command handleMarkCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		MarkCommand command = null;
		
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		
		String param = split[Constants.PARAM_FIRST_INDEX];
		boolean isNumeric = StringUtils.isNumeric(param);
		
		if(isNumeric == false) {
			
			throw new IllegalArgumentException(Constants.ERROR_INVALID_PARAMS);
		
		}
		
		int id = Integer.parseInt(split[Constants.PARAM_FIRST_INDEX]);
		
		command = new MarkCommand(commandAction, historyStack, taskManager); 
		command.addValues(id);
		
		return command;
		
	}
	
	
	/**
	 * This method handles the search parsing and calls the SearchCommand class to process
	 * The keyword is extracted and passed to SearchCommand class
	 */
	private Command handleSearchCommand(Action commandAction, TaskManager taskManager, String input) {
		
		SearchCommand command = null;
		
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		String keyword = split[Constants.PARAM_FIRST_INDEX];
		
		command = new SearchCommand(commandAction, taskManager);
		command.addValues(keyword);
		
		return command;
	
	}
	
	
	/**
	 * This method handles the display parsing and calls the DisplayCommand class to process
	 */
	private Command handleDisplayCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		DisplayCommand command = null;
		
		command = new DisplayCommand(commandAction, taskManager);
		
		return command;
	
	}
	
	/**
	 * This method handles the undo parsing and calls the UndoCommand class to process
	 */
	private Command handleUndoCommand(Action commandAction, Stack<ReversibleCommand> historyStack, String input) {
		
		UndoCommand command = new UndoCommand(commandAction, historyStack);
		
		return command;
	
	}
	
	/**
	 * This method handles the set command parsing and calls the SetCommand class to process
	 * The file path will be extracted and passed to the SetCommand
	 */
	private Command handleSetCommand(Action commandAction, Storage storage, String input) {
		
		SetCommand command = null;
		
		String[] split = input.split(Constants.COMMAND_SEPERATOR);
		String newFilePath = split[Constants.PARAM_FIRST_INDEX];
		
		command = new SetCommand(commandAction, storage);
		command.addValues(newFilePath);
		
		return command;
		
	}
	
	/**
	 * This method handles the exit command parsing and calls the ExitCommand class to process
	 */
	private Command handleExitCommand(Action commandAction, TaskManager taskManager, String input) {
		
		ExitCommand command = null;
				
		command = new ExitCommand(commandAction, taskManager);
		
		return command;
		
	}
	
	/**
	 * This method checks if the input has been added in a shorthand manner
	 * Where some keywords may be removed to increase efficiency
	 * Method corrects the command to the full form
	 * 
	 * @param input original input received by the parser
	 * 
	 * @param name original name from the earlier parsing
	 * 
	 * @param results results from the earlier parsing
	 * 
	 * @returns String full command that will be system recognized if input is correct
	 * 			returns null if invalid input is given
	 * 
	 */
	private String handleShortHand(String input, String name, String[] results) {
		
		String result = null;
		
		if(results != null) {
			
			name = results[Constants.PARAM_RESULT_NAME];
			
		}
		
		String[] params = name.split(Constants.COMMAND_SEPERATOR);
		String secondLastItem = Constants.STRING_EMPTY;
		String lastItem = Constants.STRING_EMPTY;
		
		if(params.length >= 2 * Constants.OFFSET_ARRAY) {
		
			secondLastItem = params[params.length - 2 * Constants.OFFSET_ARRAY];
		
		}
		
		if(params.length >= Constants.OFFSET_ARRAY) {
		
			lastItem = params[params.length - Constants.OFFSET_ARRAY];
			
		}
		
		if(secondLastItem.equalsIgnoreCase(Constants.KEYWORD_NEXT) || secondLastItem.equalsIgnoreCase(Constants.KEYWORD_THIS)) {
			
			lastItem = String.format(Constants.FORMAT_NEXT_KEYWORD, secondLastItem, lastItem);
			
		}
		
		String[] keywords = Helper.combineArrays(Constants.KEYWORD_ALL_SIZE, Constants.KEYWORD_TODAY, 
										Constants.KEYWORD_TOMORROW, Constants.KEYWORD_NEXT_WEEK, 
										Constants.KEYWORD_NEXT_MONTH, Constants.KEYWORD_NEXT_YEAR, 
										Constants.KEYWORD_MONDAY, Constants.KEYWORD_TUESDAY, 
										Constants.KEYWORD_WEDNESDAY, Constants.KEYWORD_THURSDAY, 
										Constants.KEYWORD_FRIDAY, Constants.KEYWORD_SATURDAY, 
										Constants.KEYWORD_SUNDAY);
		
		String keyword = containsKeyword(lastItem, keywords);
		
		if(keyword != null) {
		
			result = preProcessDate(input, keyword);
			
		}
		 
		return result;

	}
	
	/**
	 * This method checks if the input has been added in a shorthand manner
	 * Where some keywords may be removed to increase efficiency
	 * Method corrects the command to the full form
	 * 
	 * @param input original input received by the parser
	 * 
	 * @param keyword keyword that will be appended into the command
	 * 
	 * @returns String full command that will be system recognized if input is correct
	 * 			returns null if invalid input is given
	 * 
	 */
	private String preProcessDate(String input, String keyword) { 
		
		String compare = input.toUpperCase();
		keyword = keyword.toUpperCase();
		
		String atKeyword = formatKeyword(Constants.KEYWORD_AT, Constants.OPTION_KEYWORD_BOTH);
		String fromKeyword = formatKeyword(Constants.KEYWORD_FROM, Constants.OPTION_KEYWORD_BOTH);
		
		int atIndex = compare.indexOf(atKeyword);
		int fromIndex = compare.indexOf(fromKeyword);
		
		if(atIndex == Constants.KEYWORD_NOT_FOUND) {
			
			atIndex = Integer.MAX_VALUE;
		
		}
		
		if(fromIndex == Constants.KEYWORD_NOT_FOUND) {
		
			fromIndex = Integer.MAX_VALUE;
		
		}
		
		int startIndex = Math.min(atIndex, fromIndex);
		
		String result = Constants.STRING_EMPTY;
		String name = input;
		String rawDate = Constants.STRING_EMPTY;
		
		if(startIndex != Integer.MAX_VALUE) {
			
			name = input.substring(0, startIndex);
			compare = name.toUpperCase();
			
			rawDate = input.substring(startIndex, input.length());
			rawDate = rawDate.trim();

		}
		
		String delete = compare.substring(compare.length() - keyword.length() , compare.length());
		
		if(delete.equalsIgnoreCase(keyword) == true) {
			
			name = name.substring(0, compare.length() - keyword.length());
				
		}
		
		result = String.format(Constants.PARAM_PREPROCESS, name, keyword, rawDate);
		
		return result;	

	}
	
	/**
	 * This converts raw string and date inputs into separate name and daterange
	 * 
	 * @param input
	 *           the string to compute the name and daterange
	 *           
	 * @param results
	 *           the result contains two items
	 *           first item will be the name of the result
	 *           second item will be the daterange object parsed
	 *           
	 */
	private String[] parseDate(String input) {
		
		String compare = input.toUpperCase();
		
		String byKeyword = formatKeyword(Constants.KEYWORD_BY, Constants.OPTION_KEYWORD_BOTH);
		String onKeyword = formatKeyword(Constants.KEYWORD_ON, Constants.OPTION_KEYWORD_BOTH);
		String fromKeyword = formatKeyword(Constants.KEYWORD_FROM, Constants.OPTION_KEYWORD_BOTH);
		
		int byIndex = compare.indexOf(byKeyword);
		int onIndex = compare.indexOf(onKeyword);
		int fromIndex = compare.indexOf(fromKeyword);
		
		if(byIndex == Constants.KEYWORD_NOT_FOUND) {
		
			byIndex = Integer.MAX_VALUE;
		
		}
		
		if(onIndex == Constants.KEYWORD_NOT_FOUND) {
		
			onIndex = Integer.MAX_VALUE;
		
		}
		
		if(fromIndex == Constants.KEYWORD_NOT_FOUND) {
		
			fromIndex = Integer.MAX_VALUE;
		
		}
		
		String[] results = null;
		int startIndex = Math.min(Math.min(byIndex, onIndex), fromIndex);
		
		if(startIndex != Integer.MAX_VALUE) { 
		
			results = new String[Constants.PARAM_RESULT_SIZE];
			results[Constants.PARAM_RESULT_NAME] = input.substring(0, startIndex);
			results[Constants.PARAM_RESULT_DATE] = input.substring(startIndex, input.length());
		
		}
		
		return results;	
	
	}
	
	/**
	 * This method checks for keyword in the input and returns the keyword found
	 * 
	 * @param input
	 *           the string to search keywords within
	 *           
	 * @param list
	 *           array of keywords to search against
	 *           
	 */
	private String containsKeyword(String input, String[] list) {
			
		String result = null;
		
		for(int i = 0; i < list.length && result == null; i++) {

			input = input.toUpperCase();
			input = formatKeyword(input, Constants.OPTION_KEYWORD_BOTH);
			
			list[i] = list[i].trim();
			list[i] = formatKeyword(list[i], Constants.OPTION_KEYWORD_BOTH);
						
			int index = input.indexOf(list[i]);
			
			if(index != Constants.KEYWORD_NOT_FOUND) {
				
				String escape = Constants.STRING_EMPTY;
				
				if(index != Constants.KEYWORD_NOT_ESCAPED) {
					
					escape = input.substring(index - Constants.STRING_ESCAPE.length(), index);
					
				}
				
				if(escape.equalsIgnoreCase(Constants.STRING_ESCAPE) == false) {
				
					result = list[i].trim();
				
				}
			
			}
		
		}
		
		return result;
	
	}
	
	/**
	 * This method retrieves the keyword and process it into the appropriate format
	 * 
	 * @param keyword
	 *           phase to be process with padding
	 * 
	 * @param options
	 *           the option of the way the keyword should be formatted
	 *           
	 */
	public static String formatKeyword(String keyword, int options) {
		
		String result = null;
		keyword = keyword.trim();
		
		switch(options) {
		
			case Constants.OPTION_KEYWORD_FRONT:
				result = String.format(Constants.FORMAT_KEYWORD_FRONT, keyword);
				break;
		
			case Constants.OPTION_KEYWORD_BACK:
				result = String.format(Constants.FORMAT_KEYWORD_BACK, keyword);
				break;
			
			case Constants.OPTION_KEYWORD_BOTH:
				result = String.format(Constants.FORMAT_KEYWORD_BOTH, keyword);
				break;
			
			default:
				result = keyword;
			
		}
		
		return result;
		
	}
	
	/**
	 * This method retrieves the corresponding Action based on the string entered
	 * 
	 * @param action
	 *           name of the action to be processed
	 * 
	 * @return Action
	 *           the corresponding Action based on input string
	 *           invalid action is returned if no match is found
	 *           
	 */
	private Action retrieveAction(String action) {
		
		assert storage != null;
		
		Action result = Action.INVALID;
		action = action.toUpperCase();
		
		HashMap<String, Action> commandVariations = this.storage.loadCommands();

		if(commandVariations.containsKey(action)) {
		
			result = commandVariations.get(action); 
		
		}
		
		return result;
	
	}
	
}
