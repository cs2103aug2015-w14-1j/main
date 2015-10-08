package cs.notify.storage;

import cs.notify.model.*;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileHandler {
	//This is used to point to the actual file that the user is currently editing.
    private static File file;
    
    //This is used to read the contents(characters) of the file.
    private static FileReader reader;
    
    //This is used to buffer the input and improve the efficiency of FileReader in reading a file.
    private static BufferedReader bufferedReader;

	//This is used to write the input sentences into the file.
    private static FileWriter fileWriter;
    
	//This is used to buffer the input and improve the efficiency of FileWriter in writing a file.
    private static BufferedWriter bufferedWriter;
    
    private static Gson gson;
    /*
    private static FileOutputStream fileOutputStream;
    
    private static ObjectOutputStream objOutputStream;
    
    private static FileInputStream fileInputStream;
    
    private static ObjectInputStream objInputStream;
    */

    public FileHandler(String pathName) {
    	initFile(pathName);
    }
    
    /**
     * This operation creates the file(if it does not exist) or
     * open the file.
     *
     * @param pathName
     *           is the absolute path of the file.
     */
    private void initFile(String pathName) {
    	try {
            file = new File(pathName);
            
            if(!file.exists()) {
            	file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * This function creates the FileReader object which is used to read the contents in the file.
     */
    public void createFileReader() {
        try {
            reader = new FileReader(file.getAbsoluteFile());
            bufferedReader  = new BufferedReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * This function creates the FileWriter object which is used to write the contents into the file.
     */
    public void createFileWriter() {
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * ================GETTERS=========================
     */
    
    public File getFile(){
    	return file;
    }
    
    public BufferedWriter getBufferedWriter() {
    	return bufferedWriter;
    }
    
    public BufferedReader getBufferedReader() {
    	return bufferedReader;
    }
    
    /**
     * This operation writes the sentences stored inside the (ArrayList) into the file using BufferedWriter.
     *
     * @param sentences
     *          is the ArrayList which stores the input sentences which the user wants to write.
     *//*
    public void writeIntoFile(ArrayList<String> sentences) {
        createFileWriter();
        
        try{
            for (int i=0; i<sentences.size(); i++) {
                bufferedWriter.write(sentences.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    
    /*
    private void createFileOutputStream(File file) {
        try {
            fileOutputStream = new FileOutputStream(file);
            objOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createFileInputStream(File file) {
        try {
            fileInputStream = new FileInputStream(file);
            objInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    /*
    public void serializeData(HashMap<String, ArrayList<Task>> data) {
    	createFileOutputStream(file);
    	try {
    		objOutputStream.writeObject(data);
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Object deserializeData() {
    	createFileInputStream(file);

    	try {
    		return objInputStream.readObject();
    	} catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    	
    	return null;
    }
	*/
    public ArrayList<Task> readFromFile() {
    	createFileReader();
    	
    	gson = new Gson();
    	//ArrayList<ArrayList<Task>> categories = gson.fromJson(bufferedReader, new TypeToken<ArrayList<ArrayList<Task>>>() {}.getType());
    	
    	//Convert the json category array into Java ArrayList<Task> object   **gson.toJson(categories.get(0))
    	ArrayList<Task> categories = gson.fromJson(bufferedReader, new TypeToken<ArrayList<Task>>() {}.getType());
    	
    	/*
    	//Loop through the tasks and store each of them into the Data storage
    	for(int i=0; i<categories.size(); i++) {
    		//Convert the json task into Java Task object
    		Task task = gson.fromJson(gson.toJson(categories.get(i)), new TypeToken<Task>() {}.getType());
    		System.out.println(task.getTaskName());
    	}*/
    	return categories;
    	
    	/*
    	JSONTokener parser = new JSONTokener(bufferedReader);
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    	Gson gson = new Gson();
    	*/
    	//try {
    		//ArrayList<Task> list = gson.fromJson(obj, new TypeToken<ArrayList<Task>>() {}.getType());
    		
    		//System.out.println(list.toString());
    		/*
    		for(int i=0; i<arr.length(); i++) {
    			Task task = mapper.convertValue(arr.get(i), Task.class);
    			System.out.println(task.getTaskName());
    		}*/
			//System.out.println(task.getTaskName());
		//} catch (JSONException e) {
		//	e.printStackTrace();
		 /*catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    	
    	/*
 		createFileReader();
 		
    	parser = new JSONTokener(bufferedReader);
    	
		try {
			JSONObject obj = new JSONObject(parser);
			//JSONArray arr = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			
			//while(parser.nextValue()!=null) {
				//obj = (JSONObject) parser.nextValue();
				arr = obj.getJSONArray("SPORT");
				ArrayList<Task> list = new ArrayList<Task>();
				for(int i=0; i<arr.length(); i++) {
					Task task = mapper.readValue(arr.getJSONObject(i), Task.class);
					list.add(task);
				}
			//}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
    	return null;
    	*/
    }
    /*
	public void writeIntoFile(JSONObject jsonizedData) {
		createFileWriter(); 
    	Gson gson = new Gson();

		try {
			bufferedWriter.write(gson.toJson(jsonizedData));
		} catch (IOException e) {
			e.printStackTrace();
	    } finally {
	    	try {
	    		bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	*/
	public void writeIntoFile(String jsonDataString) {
		createFileWriter(); 
		//System.out.println(jsonDataString);
		try {
			bufferedWriter.write(jsonDataString);
		} catch (IOException e) {
			e.printStackTrace();
	    } finally {
	    	try {
	    		//bufferedWriter.flush();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
}

