package cs.notify.storage;

import cs.notify.model.*;
import java.util.*;

public class Storage {
	DataHandler dh;
	
	public Storage() {
		dh = new DataHandler();
	}
	
	public HashMap<String, Constant.Command> getCommands() {
		return dh.getCommandVariations();
	}
	
	public void addAction(String action) {
		dh.addAction(action);
	}
	
	public String getLatestAction() {
		return dh.getAction();
	}
	
	public boolean addTask(Task task, String category) {
		return dh.addTask(task, category);
	}
	
	public Task getTask(String taskName) {
		return dh.getTask(taskName);
	}
	public boolean deleteTask(Task task) {
		return dh.deleteTask(task);
	}
	
	public ArrayList<Task> getAllTasks() {
		return dh.getCache();
	}
	
	public ArrayList<Task> getCategory(String category) {
		return dh.getCategory(category);
	}
	
	public boolean saveTasks() {
		return dh.saveTasks();
	}
}
