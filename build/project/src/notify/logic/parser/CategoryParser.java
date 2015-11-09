//@@author A0125471L

/**
 * Author: Chua Si Hao
 * Matric No: A0125471L
 * For CS2103T - Notify
 */

package notify.logic.parser;

/**
 * This CategoryParser is a helper class to the main CommandParser
 * It assists to identify the category within the command provided
 */
public class CategoryParser {
	
	/**
	 * This method is a helper method to prepare the string into an array 
	 * ready to be parsed by the CategoryParser
	 *
	 */
	public static String parse(String input) { 
		
		String[] params = input.split(Constants.COMMAND_SEPERATOR);
		return parse(params);
	
	}
	
	/**
	 * This method searches the input to find the category hashtag within the command
	 * 
	 * @param input
	 *           the commands in an array seperated by the command seperator
	 * 
	 * @return result
	 *           the category that is found is returned
	 *           if no category is indicated, null is returned
	 *           
	 */
	public static String parse(String[] input) {
		
		String result = null;
		
		for(int i = 0; i < input.length; i++) {

			input[i] = input[i].trim();
			
			if(input[i].equalsIgnoreCase(Constants.STRING_EMPTY) == false) { 
				
				String hashtag = input[i].substring(Constants.HASHTAG_START_INDEX, Constants.HASHTAG_END_INDEX);
				
				if(hashtag.equalsIgnoreCase(Constants.KEYWORD_HASHTAG) == true) {
				
					result = input[i];
					result = result.substring(Constants.HASHTAG_END_INDEX, result.length());
					result = result.trim();
				
				}
				
			}
			
		}
		
		return result;
	
	}
}
