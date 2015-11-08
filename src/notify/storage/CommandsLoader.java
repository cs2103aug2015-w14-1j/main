 
package notify.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import notify.logic.command.Action;

public class CommandsLoader extends StorageOperation {
	
	 
	protected CommandsLoader() {
		this.commands = new HashMap<String, Action>();
	}
	
	 
	protected HashMap<String, Action> execute(Object emptyList_) {
		assert emptyList_ != null;
		
		readFromFile();
		
		assert (this.commands.isEmpty() == false);
		return this.commands;
	}
	
	 
	protected void setFilePath (String filePath_) {
		assert filePath_ != null;
		this.filePath = filePath_;
	}
	
	 
	private void readFromFile() {
		File file;
		FileReader fileReader;
		BufferedReader bufferedReader;
		String currentLine;
		
		for (Action command : Action.values()) {
			try {
				this.filePath = String.format(Constants.PATH_COMMAND_FILE,
											  Constants.FOLDER_CONFIG,Constants.FOLDER_COMMANDS,
											  command.toString().toLowerCase(), Constants.EXTENSION_FILE);
				
				file = new File(this.filePath);
				
				assert (file.exists() == true);
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				
				assert (this.commands != null);
				
				this.commands.put(command.toString().toUpperCase(), command);
				while((currentLine = bufferedReader.readLine()) != null) {
					this.commands.put(currentLine.toUpperCase() , command);
				}
				
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
}
