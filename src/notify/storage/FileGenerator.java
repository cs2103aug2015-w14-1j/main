package notify.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import notify.logic.command.Action;
import notify.logic.command.Command;

public class FileGenerator {
	private File configFolder;
	private File dataFolder;
	private File commandsFolder;
	private File directoryFile;
	private File commandFile;
	private File dataFile;
	
	public FileGenerator() {
		this.configFolder = new File(Constants.NAME_FOLDER_CONFIG);
		this.dataFolder = new File(String.format(Constants.PATH_SUB_FOLDER , Constants.FOLDER_CONFIG, Constants.FOLDER_DATA));
		this.commandsFolder = new File(String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, Constants.FOLDER_COMMANDS));
		generateConfigFolder();
		generateSystemFiles();
	}
	
	public File getConfigFolder() {
		return this.configFolder;
	}
	
	public File getDataFolder() {
		return this.dataFolder;
	}
	
	public File getCommandsFolder() {
		return this.commandsFolder;
	}
	
	public File getDirectoryFile() {
		return this.directoryFile;
	}
	
	public File getDataFile() {
		return this.dataFile;
	}
	
	public File getCommandFile(Action command_) {
		File file = new File(String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_COMMANDS, command_.toString().toLowerCase(), Constants.EXTENSION_FILE));
		return file;
	}
	
	public String getFileContent(File file_) {
		String fileContent = readFromFile(file_);
		
		return fileContent;
	}

	private void generateSystemFiles() {
		generateDirectoryFile();
		generateDataFile();
		generateCommandFiles();
	}

	private void generateConfigFolder() {
		if (!this.configFolder.exists()) {
			this.configFolder.mkdir();
		}

		if (!this.dataFolder.exists()) {
			//configFolder must exist
			this.dataFolder.mkdir();
		}

		if (!this.commandsFolder.exists()) {
			//configFolder must exist
			this.commandsFolder.mkdir();
		}
	}

	private void generateDirectoryFile() {
		//notify.config must exist
		//data folder must exist
		this.directoryFile = new File(String.format(Constants.PATH_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA, Constants.FILE_DESTINATION));
		if (!this.directoryFile.exists()) {
			writeIntoFile(this.directoryFile, String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, Constants.FOLDER_DATA));
		}
	}
	
	private void generateDataFile() {
		this.dataFile = new File(String.format(Constants.PATH_FILE, readFromFile(this.directoryFile), Constants.FILE_DATA, Constants.EMPTY_STRING));
		if (!this.dataFile.exists()) {
			writeIntoFile(this.dataFile, Constants.SQURE_BRACKETS);
		}
	}

	private void generateCommandFiles() {
		for (Action command : Action.values()) {
			this.commandFile = new File(String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG, Constants.FOLDER_COMMANDS, command.toString().toLowerCase(), Constants.EXTENSION_FILE));
			if (!this.commandFile.exists()) {
				writeIntoFile(this.commandFile, Constants.EMPTY_STRING);
			}
		}
	}

	private void writeIntoFile(File file_, String dataToWrite_) {
		try {
			FileWriter fileWriter = new FileWriter(file_);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(dataToWrite_);

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String readFromFile(File file_) {
		try {
			//log.log(Level.INFO, "Read commandStrings from: [{0}]", fileName_);
			
			File file = new File(file_.getAbsolutePath());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String dataPath = bufferedReader.readLine();

			bufferedReader.close();
			fileReader.close();
			
			return dataPath;
		} catch (Exception e) {
			return Constants.EMPTY_STRING;
		} 
	}
}
