package notify.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import notify.logic.command.Action;

public class FileGenerator {
	private File configFolder;
	private File dataFolder;
	private File commandsFolder;

	public FileGenerator() {
		configFolder = new File(Constants.FOLDER_CONFIG);
		dataFolder = new File(String.format(Constants.PATH_SUB_FOLDER , Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA));
		commandsFolder = new File(String.format(Constants.PATH_SUB_FOLDER, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_COMMANDS));
		generateConfigFolder();
		generateDataFiles();
	}

	private void generateDataFiles() {
		generateDirectoryFile();
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
		File directoryFile = new File(String.format(Constants.PATH_HIDDEN_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_DATA, File.separator, Constants.PERIOD, Constants.FILE_DIRECTORY, Constants.EXTENSION_FILE));
		if (!directoryFile.exists()) {
			writeIntoFile(directoryFile, Constants.FOLDER_DATA);
		}
	}

	private void generateCommandFiles() {
		for (Action command : Action.values()) {
			File commandFile = new File(String.format(Constants.PATH_VISIBLE_FILE, Constants.FOLDER_CONFIG, File.separator, Constants.FOLDER_COMMANDS, File.separator, command.toString().toLowerCase(), Constants.EXTENSION_FILE));
			if (!commandFile.exists()) {
				writeIntoFile(commandFile, Constants.EMPTY_STRING);
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
