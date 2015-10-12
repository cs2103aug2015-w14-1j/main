package cs.notify.parser;

import java.util.Date;

import cs.notify.error.ErrorHandler;
import cs.notify.model.Constant;
import cs.notify.model.Constant.Command;
import cs.notify.model.Task;

public class Parser {
	
	// This stores the original unformatted command from the user
	private String value;
	
	// This stores the command that is derived from the parse 
	private Command command = Command.INVALID;

	// This stores the task that contains the required value for the task
	private Task task = null;
	
	// This stores the error handler for notification on the ui
	private ErrorHandler errorHandler = null;
	
	public Parser() { }
	
	public void parse(String value) {
		this.value = value.trim();
		
		String[] params = this.value.split(" ");
		
		//verify the number of params required for the method
		if(params.length == Constant.PARAM_PARSER_MINIMUM) { 
			this.error(Constant.ERROR_INVALID_PARAMS);
			return;
		}
		
		this.command = Formatter.getCommand(params[0]);
		this.task = this.generateTask(params);
	}
	
	private Task generateTask(String[] params) {
		Task task = null;
		
		// invalid command, no task will be generated
		if(this.command == Command.INVALID) { return task; }
		
		if(params.length > Constant.PARAM_PARSER_MINIMUM) { 
			
			boolean isBlocking = (this.command == Command.BLOCK) ? true : false;
			Date fromDate = Formatter.getDateTime(extractFromDate(params));
			Date toDate = Formatter.getDateTime(extractToDate(params));
			Date time = Formatter.getDateTime(extractTime(params));
			String name = Formatter.getName(params);
			
			task = new Task(name);
			if(isBlocking == true) { task.isBlocking(); }
			task.setFromDate(fromDate);
			task.setToDate(toDate);
		
		}
		
		return task;
	}
	
	private String extractTime(String[] params) {
		String results = null;
		
		//check for time inputs with keywords eg. @ and at
		String[] keywords = Constant.KEYWORD_AT;
		for(int i = 0; i < keywords.length; i++) {
			for(int x = 0; x < params.length; x++) {
				//if match is found, extract the values
				if(keywords[i].equalsIgnoreCase(params[x])) {
					if((x + 1) < params.length) { 
						results = params[x + 1];
						params[x] = "";
						params[x + 1] = "";
					}
				}
			}
		}
		
		return results;
	}
	
	private String extractFromDate(String[] params) {
		String results = null; 
		
		//check for time inputs with keywords eg. @ and at
		String[] keywords = Constant.KEYWORD_FROM;
		for(int i = 0; i < keywords.length; i++) {
			for(int x = 0; x < params.length; x++) {
				//if match is found, extract the values
				if(keywords[i].equalsIgnoreCase(params[x])) {
					
					//extract from parameter
					if((x + 1) < params.length) { 
						results = params[x + 1];
						params[x] = "";
						params[x + 1] = "";
					}
					
					//check if next parameter is a time
					if((x + 2) < params.length) { 
						String time = params[x + 2];
						params[x + 2] = Constant.STRING_EMPTY;
						
						for(int j = 0; j < Constant.KEYWORD_TO.length; j++) {
							if(time.equalsIgnoreCase(Constant.KEYWORD_TO[j])) { 
								time = Constant.STRING_EMPTY; 
							}
						}
						
						results += Constant.STRING_SPACE + time;
					}
				}
			}
		}
		
		return results;
	}
	
	private String extractToDate(String[] params) {
		String results = null; 
		
		//check for time inputs with keywords eg. @ and at
		String[] keywords = Constant.KEYWORD_TO;
		for(int i = 0; i < keywords.length; i++) {
			for(int x = 0; x < params.length; x++) {
				//if match is found, extract the values
				if(keywords[i].equalsIgnoreCase(params[x])) {
					
					//extract from parameter
					if((x + 1) < params.length) { 
						results = params[x + 1];
						params[x] = "";
						params[x + 1] = "";
					}
					
					//check if next parameter is a time
					if((x + 2) < params.length) { 
						String time = params[x + 2];
						params[x + 2] = Constant.STRING_EMPTY;
						for(int j = 0; j < Constant.KEYWORD_FROM.length; j++) {
							if(time.equalsIgnoreCase(Constant.KEYWORD_FROM[j])) { 
								time = Constant.STRING_EMPTY; 
							}
						}
						
						results += Constant.STRING_SPACE + time;
					}
				}
			}
		}
		
		return results;
	}
	
	public Task getTask() { 
		return this.task;
	}
	
	public Command getCommand() {
		return this.command;
	}
	
	public void setErrorHandler(ErrorHandler error) {
		this.errorHandler = error;
	}
	
	private void error(String message) {
		if(this.errorHandler != null) { 
			this.errorHandler.notify(message);
		}
	}
}
