/* @@authorA0124072 */
package notify.storage.operator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FileTransferManager class is responsible for transferring data from the old
 * file to the new file and then deleting the old file from the user's computer.
 */
public class FileTransferManager {
	
	/** These are the local variables used in this class */
	private String oldFilePath;
	private String newFilePath;
	private InputStream inStream;
	private OutputStream outStream;

	/**
	 * The class Constructor which instantiate the oldFilePath
	 * {@link #oldFilePath} and the newFilePath{@link #newFilePath}
	 * 
	 * @param oldFilePath
	 *            a String representation of the absolute path of the old file
	 * @param newFilePath
	 *            a String representation of the absolute path of the new file
	 */
	public FileTransferManager(String oldFilePath, String newFilePath) {
		
		this.oldFilePath = oldFilePath;
		this.newFilePath = newFilePath;
		this.inStream = null;
		this.outStream = null;
		
	}

	/* @@authorA0124072-reused */
	/**
	 * This method reads the data from the old file and writes them into the new
	 * file. It then delete the old file from the user's computer It uses buffer
	 * array to transfer the data.
	 */
	public void transferData() {
		
		if (!this.oldFilePath.equals(newFilePath)) {
			
			try {
				
				File oldFile = new File(oldFilePath);
				File newFile = new File(newFilePath);

				byte[] buffer = new byte[1024];
				int length;

				inStream = new FileInputStream(oldFile);
				outStream = new FileOutputStream(newFile);

				// copy the file content in bytes
				while ((length = inStream.read(buffer)) > 0) {
					
					outStream.write(buffer, 0, length);
					
				}

				inStream.close();
				outStream.close();

				oldFile.delete();

			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
		
		}
	
	}

}
