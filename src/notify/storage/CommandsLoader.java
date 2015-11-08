package notify.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import notify.logic.command.Action;

public class CommandsLoader extends StorageOperation {	
	protected CommandsLoader() {
		commands = new HashMap<String, Action>();
	}
	
	protected HashMap<String, Action> execute(Object emptyList_) {
		boolean isReadSuccessfully = this.readFromFile();
		
		if(isReadSuccessfully) {
			return commands;
		} else {
			return null;
		}
	}
	
	protected void setFilePath(String filePath_) {
		this.filePath = filePath_;
	}
	
	private boolean readFromFile() {
		String currentLine;
		
		for(Action command : Action.values()) {
			try {
				filePath = String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_COMMANDS, command.toString().toLowerCase(), Constants.EXTENSION_FILE);
				File file = new File(filePath);
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				commands.put(command.toString().toUpperCase(), command);
				
				while((currentLine = bufferedReader.readLine()) != null) {
					commands.put(currentLine.toUpperCase() , command);
				}
				
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
	            return false;
	        }
		}
		
		return true;
	}
}
