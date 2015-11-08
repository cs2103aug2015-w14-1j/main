//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */
package notify.logic.parser;

/**
 * This Helper class contains quick hand methods to manipulate data
 */
public class Helper {

	/**
	 * Takes a list of String[] and combine them into one
	 */
	public static String[] combineArrays(int size, String[]... arrays) {
		
		int count = 0;
		String[] results = new String[size];
		
		for(int i = 0; i < arrays.length; i++) { 
			
			String[] item = arrays[i];
			
			for(int x = 0; x < item.length; x++) { 
				
				results[count] = item[x];
				count++;
				
			}
			
		}
		
		return results;
		
	}
	
}
