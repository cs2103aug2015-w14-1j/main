package notify.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import notify.logic.command.Action;

public class FileGenerator {
	private File configFolder;
	private File dataFolder;
	private File commandsFolder;
	private File directoryFile;
	private File commandFile;
	private File dataFile;
	
	public FileGenerator() {
		configFolder = new File(Constants.FOLDER_CONFIG);
		dataFolder = new File(String.format(Constants.PATH_SUB_FOLDER , Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA));
		commandsFolder = new File(String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_COMMANDS));
		generateConfigFolder();
		generateSystemFiles();
	}

	private void generateSystemFiles() {
		generateDirectoryFile();
		generateDataFile();
		generateCommandFiles();
	}

	private void generateConfigFolder() {
		if (!configFolder.exists()) {
			configFolder.mkdir();
		}

		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}

		if (!commandsFolder.exists()) {
			commandsFolder.mkdir();
		}
	}

	private void generateDirectoryFile() {
		directoryFile = new File(String.format(Constants.PATH_HIDDEN_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA, File.separator, Constants.PERIOD, Constants.FILE_DIRECTORY, Constants.EXTENSION_FILE));
		if (!directoryFile.exists()) {
			writeIntoFile(directoryFile, String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA));
		}
	}
	
	private void generateDataFile() {
		dataFile = new File(String.format(Constants.PATH_VISIBLE_FILE, readFromFile(directoryFile), File.separator, Constants.FILE_DATA, Constants.EXTENSION_FILE));
		if (!dataFile.exists()) {
			writeIntoFile(dataFile, Constants.EMPTY_ARRAY);
		}
	}

	private void generateCommandFiles() {
		for (Action command : Action.values()) {
			commandFile = new File(String.format(Constants.PATH_VISIBLE_FILE, Constants.FOLDER_CONFIG+File.separator+Constants.FOLDER_COMMANDS, File.separator, command.toString().toLowerCase(), Constants.EXTENSION_FILE));
			if (!commandFile.exists()) {
				writeIntoFile(commandFile, Constants.EMPTY_STRING);
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
