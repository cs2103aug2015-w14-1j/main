package notify.logic.parser;

public class Helper {

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
