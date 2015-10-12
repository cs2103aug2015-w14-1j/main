package cs.notify.storage;

import cs.notify.model.*;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;

public class DataHandler {
	private int task_id;
	private static Stack<String> actions;
	//private static HashMap<Integer, Task> tasks; //KEY = task_id, VALUE = Task Name
	private HashMap<String, ArrayList<Task>> categories; //KEY = Category, VALUE = List of Tasks that belong to that category.
	private static ArrayList<Task> cache; //Task cache
	private static HashMap<String, Constant.Command> commandVariations; //KEY = command variant, VALUE = default command
	/*
	private static JSONObject categoriesObject;
	private static JSONArray categoriesArray;
	private static JSONObject categoryObject;
	*/
	private static Gson gson;
	
	public DataHandler() {
		task_id = 0;
		actions = new Stack<String>();
		categories = new HashMap<String, ArrayList<Task>>();
		cache = new ArrayList<Task>();
		commandVariations = new HashMap<String, Constant.Command>();
		populateCommandVariations();
		loadTasks();
	}
	
	
	private void populateCommandVariations() {
		String currentLine;
		
		//Read each file
		for(Constant.Command command : Constant.Command.values()) {
			if(command.equals(Constant.Command.INVALID))
				break;
			commandVariations.put(command.toString(), command); //Add the default commands
			FileHandler fileHandler = new FileHandler("config/commands/"+command.toString()+".txt");
			fileHandler.createFileReader();
			try {
				//each line from the file, store it into the command variations.
				while((currentLine = fileHandler.getBufferedReader().readLine()) != null) {
					commandVariations.put(currentLine.toLowerCase() , command);
				}
			} catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public HashMap<String, Constant.Command> getCommandVariations(){
		return commandVariations;
	}
	
	public void addAction(String action) {
		actions.push(action);
	}
		
	//returns the most recent action
	public String getAction () {
		return actions.pop();
	}
	
	/**
	 * 
	 * @param task
	 * 			the new task which the user would like to add
	 * @param category
	 * 			the category type which the task falls under.
	 * @return True: if the task has been added successful
	 */
	public boolean addTask(Task task, String category) {
		task.setId(task_id);
		task.addToCategory(category);
		
		if(isNewCategory(category)) {
			ArrayList<Task> tasks = new ArrayList<Task>();
			tasks.add(task);
			categories.put(category, tasks);
			task_id++;
			return true;
		} else {
			if(categories.get(category).add(task)) {
				task_id++;
				return true;
			}
		}	
		
		return false;
	}
	
	public Task getTask(String taskName) {
		for(String key : categories.keySet()) {
			for(int i=0; i<categories.get(key).size(); i++) {
				if(categories.get(key).get(i).getTaskName().equals(taskName)) {
					return categories.get(key).get(i);
				}
			}
		}

		return null;
	}
	
	private boolean writeIntoCategories(Task task, String category) {
		if(isNewCategory(category)) {
			ArrayList<Task> tasks = new ArrayList<Task>();
			tasks.add(task);
			categories.put(category, tasks);
			return true;
		} else {
			return categories.get(category).add(task);
		}
	}
	
	private boolean isNewCategory(String categoryName) {
		return !categories.containsKey(categoryName);
	}
	
	//For the search function to iterate through the data I have so far by Tasks.
	public ArrayList<Task> getCache() {
		cache.clear();
		for(String key : categories.keySet()) {
			for(int i=0; i<categories.get(key).size(); i++) {
				cache.add(categories.get(key).get(i));
			}
		}
		
		return cache;
	}
	
	public ArrayList<Task> getCategory(String category) {
		return categories.get(category);
	}
	
	public boolean deleteTask(Task task) {
		for(String key : categories.keySet()) {
			for(int i=0; i<categories.get(key).size(); i++) {
				if(categories.get(key).get(i).getTaskId()==task.getTaskId()) {
					categories.get(key).remove(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean saveTasks() {
		FileHandler fileHandler = new FileHandler("data/tasks.txt");

		fileHandler.writeIntoFile(jsonizeData(categories));
		return true;
	}
	
	public void loadTasks() {
		FileHandler fileHandler = new FileHandler("data/tasks.txt");
		ArrayList<Task> tasks = fileHandler.readFromFile();
		System.out.println(tasks.size());
		for(int i=0; i<tasks.size(); i++) {
			for(int j=0; j<tasks.get(i).getCategories().size(); j++) {
				writeIntoCategories(tasks.get(i), tasks.get(i).getCategories().get(j));
			}
		}
	}
	
	
	public HashMap<String, ArrayList<Task>> getCategories() {
		return categories;
	}
	
	public String jsonizeData(HashMap<String, ArrayList<Task>> data) {
		
		gson = new Gson();
	
		String jsonDataString = gson.toJson(getCache());
		
		return jsonDataString;
		
		/*
		//categoriesArray = new JSONArray();
		//JSONArray categoryObjectArray = new JSONArray();

		gson = new Gson();
		
		for(String key : data.keySet()) {
			categoryObject = new JSONObject();
			//
			for(int i=0; i<data.get(key).size(); i++)
				categoryObjectArray.put(data.get(key).get(i));
				
			//categoryObject.put(key, data.get(key));
			categoriesArray.put(data.get(key));
		}*/
	}
}
