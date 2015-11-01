package notify.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import notify.logic.command.Action;

public class FileGenerator {
	private static final String NOTIFY_CONFIG_FOLDER = "notify.config";
	private static final String NOTIFY_DATA_FOLDER = "notify.config/data";
	private static final String NOTIFY_COMMANDS_FOLDER = "notify.config/commands";
	
	private static final String EMPTY_STRING = "";
	
	private File configFolder;
	private File dataFolder;
	private File commandsFolder;
	
	public FileGenerator() {
		configFolder = new File(NOTIFY_CONFIG_FOLDER);
		dataFolder = new File(NOTIFY_DATA_FOLDER);
		commandsFolder = new File(NOTIFY_COMMANDS_FOLDER);
		generateConfigFolder();
		generateDataFiles();
	}
	
	private void generateDataFiles() {
		generatePathFile();
		generateCommandFiles();
	}
	
	private void generateConfigFolder() {
		if(!configFolder.exists()) {
			configFolder.mkdir();
		}
		
		if(!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		
		if(!commandsFolder.exists()) {
			commandsFolder.mkdir();
		}
	}
	
	private void generatePathFile() {
		File pathFile = new File(NOTIFY_DATA_FOLDER+"/.filePath.txt");
		if(!pathFile.exists()) {
			writeIntoFile(pathFile, NOTIFY_DATA_FOLDER);
		}
	}
	
	private void generateCommandFiles() {
		for(Action command : Action.values()) {
			File commandFile = new File(NOTIFY_COMMANDS_FOLDER+"/"+command.toString()+".txt");
			if(!commandFile.exists()) {
				writeIntoFile(commandFile, EMPTY_STRING);
			}
		}
	}
	
	private void writeIntoFile(File file, String dataToWrite) {
		try {
			FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(dataToWrite);
            
            bufferedWriter.close();
            fileWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
