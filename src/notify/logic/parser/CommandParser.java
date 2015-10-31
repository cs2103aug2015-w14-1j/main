package notify.logic.parser;

import java.util.HashMap;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;
import notify.logic.command.Action;
import notify.logic.command.AddCommand;
import notify.logic.command.Command;
import notify.logic.command.DeleteCommand;
import notify.logic.command.EditCommand;
import notify.logic.command.MarkCommand;
import notify.logic.command.ReversibleCommand;
import notify.logic.command.SearchCommand;
import notify.logic.command.SetCommand;
import notify.logic.command.UndoCommand;
import notify.storage.Storage;

public class CommandParser {
	
	public static final String COMMAND_SEPERATOR = " ";
	
	private static final int SEPERATOR_COMMAND_INDEX = 0;
	private static final int FIRST_PARAM_INDEX = 0;
	
	private static final int RESULTS_PARAM_SIZE = 2;
	private static final int RESULTS_NAME_PARAM = 0;
	private static final int RESULTS_DATE_PARAM = 1;
	
	private static final String ERROR_INVALID_COMMAND = "Unable to parse command. Invalid command provided.";
	private static final String ERROR_INVALID_PARAMS = "Unable to recognize parameter(s) entered.";
	
	private Storage storage;
	private TaskManager taskManager;
	private Stack<ReversibleCommand> history;
	
	public CommandParser(Storage storage, TaskManager taskManager, Stack<ReversibleCommand> history) {
		this.storage = storage;
		this.taskManager = taskManager;
		this.history = history;
	}
	
	public Command parse(String input) {
		
		assert input != null;
		
		Command command = null;
		input = input.trim();

		String[] split = input.split(COMMAND_SEPERATOR);
		Action commandAction = retrieveAction(split[SEPERATOR_COMMAND_INDEX]);
		
		if(commandAction != Action.INVALID) {
			int length = split[SEPERATOR_COMMAND_INDEX].length();
			input = input.substring(length, input.length()).trim();
		}
		
		switch(commandAction) {
			case ADD: command = handleAddCommand(commandAction, history, taskManager, input); break;
			case DELETE: command = handleDeleteCommand(commandAction, history, taskManager, input); break;
			case EDIT: command = handleEditCommand(commandAction,history, taskManager, input); break;
			case SEARCH: command = handleSearchCommand(commandAction, taskManager, input); break;
			case MARK: command = handleMarkCommand(commandAction, history, taskManager, input); break; 
			case DISPLAY: command = handleDisplayCommand(input); break;
			case UNDO: command = handleUndoCommand(commandAction, history, input); break;
			case SET: command = handleSetCommand(commandAction, storage, input); break;
			case EXIT: command = handleExitCommand(input); break;
			default: throw new IllegalArgumentException(ERROR_INVALID_COMMAND);			
		}
		
		return command;
	}
	
	private Command handleAddCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		String category = CategoryParser.parse(input);
		TaskType taskType = TaskType.FLOATING;
		DateRange dateRange = null;
		String name = input;
		
		if(category != null) { 
			int length = category.length() + CategoryParser.KEYWORD_HASHTAG.length();
			input = input.substring(0, input.length() - length);
		}
		
		//check if command contains any keywords
		String datePrompt = containsKeyword(input, DateTimeParser.DATETIME_PROMPT_KEYWORDS);
		if(datePrompt != null) { 
			String[] results = parseDate(input);
			name = results[RESULTS_NAME_PARAM];
			dateRange = DateTimeParser.parseDateRange(results[RESULTS_DATE_PARAM]);
			
			if(datePrompt.equalsIgnoreCase(DateTimeParser.KEYWORD_FROM)) {
				taskType = TaskType.RANGE;
			} else {
				taskType = TaskType.DEADLINE;
			}
		}	
		
		AddCommand command = new AddCommand(commandAction, taskManager, historyStack);
		command.addValues(name, taskType, dateRange, category);
		
		return command;
	}
	
	private Command handleDeleteCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		DeleteCommand command = null;
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		boolean isNumeric = StringUtils.isNumeric(split[FIRST_PARAM_INDEX]);
		if(isNumeric == false) {
			throw new IllegalArgumentException(ERROR_INVALID_PARAMS);
		}
		
		int id = Integer.parseInt(split[FIRST_PARAM_INDEX]);
		
		command = new DeleteCommand(commandAction, historyStack, taskManager);
		command.addValues(id);
		
		return command;
	}
	
	private Command handleEditCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		
		String category = CategoryParser.parse(input);
		TaskType taskType = TaskType.FLOATING;
		DateRange dateRange = null;
		String name = input;
		int id = Task.UNASSIGNED_TASK;
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		boolean isNumeric = StringUtils.isNumeric(split[FIRST_PARAM_INDEX]);
		if(isNumeric == false) { 
			throw new IllegalArgumentException(ERROR_INVALID_PARAMS);
		}
		
		id = Integer.parseInt(split[FIRST_PARAM_INDEX]);
		
		if(id != Task.UNASSIGNED_TASK) { 
			int length = String.valueOf(id).length();
			input = input.substring(length, input.length());
			name = input.trim();
		}
	
		if(category != null) { 
			int length = category.length() + CategoryParser.KEYWORD_HASHTAG.length();
			input = input.substring(0, input.length() - length);
		}
		
		//check if command contains any keywords
		String datePrompt = containsKeyword(input, DateTimeParser.DATETIME_PROMPT_KEYWORDS);
		if(datePrompt != null) { 
			String[] results = parseDate(input);
			name = results[RESULTS_NAME_PARAM].trim();
			System.out.println(results[RESULTS_DATE_PARAM]);
			dateRange = DateTimeParser.parseDateRange(results[RESULTS_DATE_PARAM]);
			
			if(datePrompt.equalsIgnoreCase(DateTimeParser.KEYWORD_FROM)) {
				taskType = TaskType.RANGE;
			} else {
				taskType = TaskType.DEADLINE;
			}
		}	
		
		EditCommand command = new EditCommand(commandAction, historyStack, taskManager);
		command.addValues(name, dateRange, category, id, taskType);
		
		return command;
	}
	
	private Command handleMarkCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager taskManager, String input) {
		MarkCommand command = null;
		
		String[] split = input.split(COMMAND_SEPERATOR);
		
		boolean isNumeric = StringUtils.isNumeric(split[FIRST_PARAM_INDEX]);
		if(isNumeric == false) {
			throw new IllegalArgumentException(ERROR_INVALID_PARAMS);
		}
		
		int id = Integer.parseInt(split[FIRST_PARAM_INDEX]);
		
		command = new MarkCommand(commandAction, historyStack, taskManager); 
		command.addValues(id);
		
		return command;
	}
	
	private Command handleSearchCommand(Action commandAction, TaskManager taskManager, String input) {
		SearchCommand command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		String keyword = split[FIRST_PARAM_INDEX];
		
		command = new SearchCommand(commandAction, taskManager);
		command.addValues(keyword);
		
		return command;
	}
	
	private Command handleDisplayCommand(String input) {
		Command command = null; //new DeleteCommand();
		
		String[] split = input.split(COMMAND_SEPERATOR);
		String keyword = split[FIRST_PARAM_INDEX];

		return command;
	}
	
	private Command handleUndoCommand(Action commandAction, Stack<ReversibleCommand> historyStack, String input) {
		UndoCommand command = new UndoCommand(commandAction, historyStack);
		
		return command;
	}
	
	private Command handleSetCommand(Action commandAction, Storage storage, String input) {
		SetCommand command = null;
		
		String[] split = input.split(COMMAND_SEPERATOR);
		String newFilePath = split[FIRST_PARAM_INDEX];
		
		command = new SetCommand(commandAction, storage);
		command.addValues(newFilePath);
		
		return command;
	}
	
	private Command handleExitCommand(String input) {
		Command command = null;
		
		return command;
	}
	
	private String[] parseDate(String input) {
		String compare = input.toUpperCase();
		
		int byIndex = compare.indexOf(COMMAND_SEPERATOR + DateTimeParser.KEYWORD_BY + COMMAND_SEPERATOR);
		int onIndex = compare.indexOf(COMMAND_SEPERATOR + DateTimeParser.KEYWORD_ON + COMMAND_SEPERATOR);
		int fromIndex = compare.indexOf(COMMAND_SEPERATOR + DateTimeParser.KEYWORD_FROM + COMMAND_SEPERATOR);
		
		if(byIndex == DateTimeParser.KEYWORD_NOT_FOUND_INDEX) {
			byIndex = Integer.MAX_VALUE;
		}
		
		if(onIndex == DateTimeParser.KEYWORD_NOT_FOUND_INDEX) {
			onIndex = Integer.MAX_VALUE;
		}
		
		if(fromIndex == DateTimeParser.KEYWORD_NOT_FOUND_INDEX) {
			fromIndex = Integer.MAX_VALUE;
		}
		
		int startIndex = Math.min(Math.min(byIndex, onIndex), fromIndex);
		
		String[] results = new String[RESULTS_PARAM_SIZE];
		results[RESULTS_NAME_PARAM] = input.substring(0, startIndex + COMMAND_SEPERATOR.length());
		results[RESULTS_DATE_PARAM] = input.substring(startIndex + COMMAND_SEPERATOR.length(), input.length());
		
		return results;	
	}
	
	private String containsKeyword(String input, String[] array) {
		String keyword = null;
		
		for(int i = 0; i < array.length && keyword == null; i++) {
			input = input.toUpperCase();
			int index = input.indexOf(array[i]);
			
			if(index != DateTimeParser.KEYWORD_NOT_FOUND_INDEX) {
				String escape = input.substring(index - DateTimeParser.ESCAPE_KEYWORD.length(), index);
				if(escape.equalsIgnoreCase(DateTimeParser.ESCAPE_KEYWORD) == false) {
					keyword = array[i].trim();
				}
			}
		}
		
		return keyword;
	}
	
	private Action retrieveAction(String action) {
		
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
