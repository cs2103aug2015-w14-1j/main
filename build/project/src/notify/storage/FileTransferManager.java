package notify.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTransferManager {
	private String oldFilePath;
	private String newFilePath;
	private InputStream inStream;
	private OutputStream outStream;

	public FileTransferManager(String oldFilePath, String newFilePath) {
		this.oldFilePath = oldFilePath;
		this.newFilePath = newFilePath;
		inStream = null;
		outStream = null;
	}

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
