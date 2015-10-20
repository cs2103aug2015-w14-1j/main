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
	
	public TaskManager(Storage storage) {
		latest_id = 0;
		this.taskList = storage.loadTasks();
	}
	
	public Task addTask(String name, DateRange timespan, String category, TaskType taskType) {
		//logger.log(Level.INFO, "ADDING TASK");

		Task task = new Task(latest_id, taskType, name, timespan, category, false);
		taskList.add(task);
		updateLatestId(latest_id);
		
		//logger.log(Level.WARNING, "TASK CANNOT BE ADDED");
		return task;
	}
	
	public boolean deleteTask(int id) {
		Task task = getTask(id);
		
		if(task!=null) { 
			task.setDeleted(false);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateTask(int id, String newName, DateRange newDateRange, String category, TaskType newType) {
		
		//log.log(Level.INFO, "Updated task [{0}]", newName);
		
		Task task = getTask(id);
		
		if(task!=null) {
			task.setTaskName(newName);
			task.setDateRange(newDateRange);
			task.setCategory(category);
			task.setTaskType(newType);
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Task> beforeDate(Calendar date) {
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getDateRange().getStartDate().compareTo(date) < 0 && taskList.get(i).isDeleted()==false) {
				tempTaskList.add(taskList.get(i));
			}
		}
		
		return tempTaskList;
	}
	
	public ArrayList<Task> afterDate(Calendar date) {
		ArrayList<Task> tempTaskList = new ArrayList<Task>();

		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getDateRange().getStartDate().compareTo(date) > 0 && taskList.get(i).isDeleted()==false) {
				tempTaskList.add(taskList.get(i));
			}
		}
		
		return tempTaskList;
	}
	
	public ArrayList<Task> getTask(Calendar date) {
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getDateRange().getStartDate().compareTo(date) == 0 && taskList.get(i).isDeleted()==false) {
				tempTaskList.add(taskList.get(i));
			}
		}
		
		return tempTaskList;
	}
	
	public Task getTask(int id) {
		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getTaskId()==id) {
				return taskList.get(i);
			}
		}
		
		return null;
	}
	
	public ArrayList<Task> getFloatingTask() {
		ArrayList<Task> tempTaskList = new ArrayList<Task>();
		
		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getTaskType().equals(TaskType.FLOATING) && taskList.get(i).isDeleted()==false) {
				tempTaskList.add(taskList.get(i));
			}
		}
		
		return tempTaskList;
	}
	
	private void updateLatestId(int currentId) {
		latest_id = currentId + 1;
	}
}
