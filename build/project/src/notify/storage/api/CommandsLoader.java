/* @@author A0124072 */
package notify.storage.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import notify.logic.command.Action;
import notify.storage.operator.Constants;

/**
 * CommandsLoader class extends the abstract class StorageOperation
 * {@see notify.storage.StorageOperation}.
 * 
 * This class is responsible for reading the command text files and storing the
 * content of those files into the HashMap<String, Action> object
 * {@value notify.storage.api.StorageOperation#commands} which contains the command
 * variations. 
 * 
 * This HashMap<String, Action> object is in turn retrieved and used
 * by the Parser component {@see notify.logic.parser.CommandParser}.
 * 
 * The command text files are located at <tt>notify.config/commands/</tt>
 * directory, which are auto-generated by the class FileGenerator
 * {@see notify.storage.FileGenerator}.
 */
public class CommandsLoader extends StorageOperation {

	/**
	 * The class Constructor which instantiate the commands object
	 * {@value notify.storage.api.StorageOperation#commands}
	 */
	protected CommandsLoader() {

		this.commands = new HashMap<String, Action>();

	}

	/**
	 * This method is the main method invoked by the Storage class
	 * {@see notify.storage.Storage} in order to pre-populate the commands
	 * object {@value notify.storage.api.StorageOperation#commands} which will
	 * contain the command variations.
	 * 
	 * @param emptyList
	 *            An empty list of Tasks.
	 * 
	 * @return a HashMap<String, Action> object which contains the
	 *         command variations
	 */
	protected HashMap<String, Action> execute(Object emptyList) {

		assert emptyList != null; 

		readFromFile();

		assert(this.commands.isEmpty() == false);
		
		return this.commands;

	}

	/**
	 * This method is used to set the directory/path of the file which is read
	 * from.
	 * 
	 * @param filePath_
	 *            The new file directory/path of the file which is read from.
	 */
	protected void setFilePath(String filePath) {

		assert filePath != null;
		this.filePath = filePath;

	}

	/**
	 * This method is used to read the contents of the command text files
	 * located at <tt>notify.config/commands/</tt> directory.
	 */
	private void readFromFile() {

		File file;
		FileReader fileReader;
		BufferedReader bufferedReader;
		String currentLine;

		for (Action command : Action.values()) {

			try {

				this.filePath = String.format(Constants.PATH_COMMAND_FILE, Constants.FOLDER_CONFIG,
						Constants.FOLDER_COMMANDS, command.toString().toLowerCase(), Constants.EXTENSION_FILE);

				file = new File(this.filePath);

				assert(file.exists() == true);
				
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);

				assert(this.commands != null);
				
				this.commands.put(command.toString().toUpperCase(), command);
				
				while ((currentLine = bufferedReader.readLine()) != null) {

					this.commands.put(currentLine.toUpperCase(), command);

				}

				bufferedReader.close();
				fileReader.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		
		}
	
	}

}
