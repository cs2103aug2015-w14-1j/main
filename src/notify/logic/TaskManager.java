package notify.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.storage.Storage;

public class TaskManager {
	
	private int latest_id;
	private ArrayList<Task> taskList;
	private Storage storage;
	
	public TaskManager(Storage storage) {
		
		this.latest_id = 0;
		this.storage = storage;
		this.taskList = this.storage.loadTasks();
		
	}
	
	public Task addTask(String name, DateRange timespan, String category, TaskType taskType) {
		
		//logger.log(Level.INFO, "ADDING TASK");

		Task task = new Task(latest_id, taskType, name, timespan, category, false);
		taskList.add(task);
		updateLatestId(latest_id);
		
		//logger.log(Level.WARNING, "TASK CANNOT BE ADDED");
		return task;
		
	}
	
	public Task deleteTask(int id) {
		
		Task task = getTask(id);
		
		if(task != null) { 
			
			task.setDeleted(false);
			
			return task;
			
		} else {
			
			return null;
			
		}
		
	}
	
	public Task undeleteTask(int id) {
		
		Task task = getTask(id);
		
		if(task != null) {
			task.setDeleted(false);
			
			return task;
			
		} else {
			
			return null;
			
		}
		
	}
	
	public Task updateTask(int id, String newName, DateRange newDateRange, String category, TaskType newType) {
		
		//log.log(Level.INFO, "Updated task [{0}]", newName);
		
		Task task = getTask(id);
		
		if(task != null) {
			
			task.setTaskName(newName);
			task.setDateRange(newDateRange);
			task.setCategory(category);
			task.setTaskType(newType);
			
			return task;
			
		} else {
			
			return null;
			
		}
		
	}
	
	public Task markTask(int id) {
		
		Task task = getTask(id);
		
		if(task != null) {
			
			task.setCompleted(true);
			
			return task;
			
		} else {
			
			return null;
			
		}
		
	}
	
	public ArrayList<Task> displayCompletedTasks() {
		
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i = 0; i < taskList.size(); i++) {
			
			Task task = taskList.get(i);
			
			if(task.isCompleted() && !task.isDeleted()) {
				
				tempTaskList.add(task);
				
			}
			
		}
		
		return tempTaskList;
		
	}
	
	public void exit() {
		storage.saveTasks(taskList);
	}
	
	public ArrayList<Task> beforeDate(Calendar date) {
		
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i = 0; i < taskList.size(); i++) {
			
			Task task = taskList.get(i);
			
			if(task.getDateRange().getStartDate().compareTo(date) < 0 && !task.isDeleted()) {
				
				tempTaskList.add(task);
				
			}
			
		}
		
		return tempTaskList;
		
	}
	
	public ArrayList<Task> afterDate(Calendar date) {
		
		ArrayList<Task> tempTaskList = new ArrayList<Task>();

		for(int i = 0; i < taskList.size(); i++) {
			
			Task task = taskList.get(i);
			
			if(task.getDateRange().getStartDate().compareTo(date) > 0 && !task.isDeleted()) {
				
				tempTaskList.add(task);
				
			}
			
		}
		
		return tempTaskList;
		
	}
	
	public ArrayList<Task> getTask(Calendar date) {
		
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i = 0; i < taskList.size(); i++) {
			
			Task task = taskList.get(i);
			
			if(task.getDateRange().getStartDate().compareTo(date) == 0 && !task.isDeleted()) {
				
				tempTaskList.add(task);
				
			}
			
		}
		
		return tempTaskList;
		
	}
	
	public Task getTask(int id) {
		
		for(int i=0; i<taskList.size(); i++) {
			
			Task task = taskList.get(i);
			
			if(task.getTaskId() == id) {
				
				return task;
				
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<Task> getFloatingTask() {
		
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i = 0; i < taskList.size(); i++) {
			
			Task task = taskList.get(i);
					
			if(task.getTaskType().equals(TaskType.FLOATING) && !task.isDeleted()) {
				
				tempTaskList.add(task);
				
			}
			
		}
		
		return tempTaskList;
		
	}
	
	private void updateLatestId(int currentId) {
		
		latest_id = currentId + 1;
		
	}
	
}
