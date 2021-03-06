/* @@author A0124072 */
package notify.storage.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import notify.storage.operator.Constants;
import notify.storage.operator.FileTransferManager;

/**
 * DataDirectoryManager class extends the abstract class StrorageOperation
 * {@see notify.storage.StorageOperation}. 
 * This class is responsible for:- 
 * 1.reading the file which contains the absolute path of the data file(used to
 * store all the tasks information) and retrieving the absolute path of the data
 * file.
 * 2. writing the absolute path of the data file to the specified file.
 */
public class DataDirectoryManager extends StorageOperation {

	/** Variable used to represent the absolute path of the data file. */
	private String dataFilePath;

	/**
	 * The class Constructor which sets the path of the file
	 * {@value notify.storage.api.StorageOperation#filePath} where this class
	 * supposed to read from and write to. It then invokes the
	 * {@link #readFromFile() readFromFile} method.
	 * 
	 * @param filePath_
	 *            The file which contains the absolute path of the data file
	 *            where all the user's tasks are stored. 
	 */
	protected DataDirectoryManager(String filePath) {

		assert(filePath != null);
		this.filePath = filePath;
		readFromFile();

	}

	/**
	 * This method is the main method invoked by the Storage class
	 * {@see notify.storage.Storage} in order to set the new file destination of
	 * the data file or retrieve the currently stored file destination of the
	 * data file.
	 * 
	 * @param newDataFilePath_
	 *            New absolute path of the data file.
	 * 
	 * @return a boolean object: 'true' if the new absolutely path of the data
	 *         file is valid; 'false' otherwise.
	 */
	protected Boolean execute(Object newDataFilePath) {

		FileTransferManager fileTrsMngr;
		boolean result;

		assert newDataFilePath != null;
		newDataFilePath = appendSeperator((String) newDataFilePath);

		if (isValidDirectory((String) newDataFilePath)) {

			fileTrsMngr = new FileTransferManager(
					String.format(Constants.PATH_FILE, getDataFilePath(), Constants.FILE_DATA, Constants.EMPTY_STRING),
					String.format(Constants.PATH_FILE, (String) newDataFilePath, Constants.FILE_DATA,
							Constants.EMPTY_STRING));
			fileTrsMngr.transferData();

			this.dataFilePath = (String) newDataFilePath;
			this.writeIntoFile((String) newDataFilePath);

			result = true;

		} else {

			result = false;

		}

		assert(result == true || result == false);
		
		return result;

	}

	/**
	 * This method is used to set the directory/path of the file which is read
	 * from or written to.
	 * 
	 * @param filePath_
	 *            The new file directory/path of the file which is read from or
	 *            written to.
	 */
	protected void setFilePath(String filePath) {

		this.filePath = filePath;

	}

	/**
	 * This method returns the absolute file path of the data file.
	 * 
	 * @return the absolute file path of the data file.
	 */
	public String getDataFilePath() {

		return this.dataFilePath;

	}

	/**
	 * This method concatenate the file separator("/") to the end of the new
	 * absolute file path string if it does not have one at the last index of
	 * the string.
	 * 
	 * @return the absolute file path string with the file
	 *         separator("/") at the last index of the string.
	 */
	private String appendSeperator(String newDataFilePath) {

		assert(newDataFilePath != null);

		if (!containsLastSeperator(newDataFilePath)) {

			newDataFilePath = newDataFilePath + File.separator;

		}

		return newDataFilePath;

	}

	/**
	 * This method checks if the last character of the new absolute path of the
	 * data file is the file separator("/").
	 * 
	 * @return a boolean object: 'true' if the last character of the new
	 *         absolute path of the data file is the file separator("/");
	 *         'false' otherwise.
	 */
	private boolean containsLastSeperator(String newDataFilePath_) {

		boolean result = newDataFilePath_.substring(newDataFilePath_.length() - 1, newDataFilePath_.length())
				.equals(File.separator);

		return result;

	}

	/**
	 * This method checks if the new absolute path of the data file is a valid
	 * directory.
	 * 
	 * @return a boolean object: 'true' if the new absolute path of the
	 *         data file is a valid directory; 'false' otherwise.
	 */
	private boolean isValidDirectory(String newFilePath) {

		File file = new File(newFilePath);

		if (file.isDirectory() == true) {

			return true;

		} else {

			return false;

		}
		
	}

	/**
	 * This method writes absolute path of the data file to the specified file
	 * with path instantiated in the constructor
	 * {@link #DataDirectoryManager(String)};
	 * 
	 * @param newDataFilePath_
	 *            New absolute path of the data file which is to be written into
	 *            the specified file.
	 */
	private void writeIntoFile(String newDataFilePath) {

		FileWriter fileWriter;
		BufferedWriter bufferedWriter;

		try {

			assert(new File(this.filePath).exists() == true);
			fileWriter = new FileWriter(this.filePath);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(newDataFilePath);

			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	/**
	 * This method reads specified file with path instantiated in the
	 * constructor{@link #DataDirectoryManager(String)};
	 * 
	 * @return a boolean object: 'true' if the read is successful;
	 *         'false' otherwise
	 */
	private Boolean readFromFile() {

		File file;
		FileReader fileReader;
		BufferedReader bufferedReader;
		boolean result;

		try {
			// log.log(Level.INFO, "Read commandStrings from: [{0}]",
			// fileName);

			file = new File(this.filePath);

			assert(file.exists() == true);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			this.dataFilePath = bufferedReader.readLine();

			bufferedReader.close();
			fileReader.close();

			result = true;

		} catch (Exception e) {

			result = false;

		}

		assert(result == true || result == false);
		
		return result;

	}
	
}
