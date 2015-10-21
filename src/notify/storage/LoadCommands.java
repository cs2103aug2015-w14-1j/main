package notify.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import notify.logic.command.Action;
import notify.Task;

public class LoadCommands extends StorageOperation {
	protected LoadCommands() {
		commands = new HashMap<String, Action>();
	}
	
	protected HashMap<String, Action> execute(ArrayList<Task> emptyList) {
		boolean isReadSuccessfully = this.readFromFile();
		
		if(isReadSuccessfully) {
			return commands;
		} else {
			return null;
		}
	}

	private boolean readFromFile() {
		String currentLine;
		
		for(Action command : Action.values()) {
			try {
				filePath = "config/commands/"+command.toString()+".txt";
				System.out.println(filePath);
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
