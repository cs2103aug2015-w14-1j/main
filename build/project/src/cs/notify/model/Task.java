package cs.notify.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {
	
	// This stores the id that is associated with the task
	private int _id = Constant.UNASSIGNED_TASK;

	// This stores the event name specified for the task
	private String name;
	
	// This stores the date and time specified for the task
	private Date fromDate, toDate;
	
	// This stores the list of categories that the task belongs to
	private ArrayList<String> categories = new ArrayList<String>();
	
	// This stores the state of the event if it has been deleted (soft delete)
	private boolean isDeleted = false;
	
	// This specifies if the days specified are used for blocking
	private boolean isBlocking = false;
	
	// This stores the task type that it belongs to
	private TaskType taskType;
	
	// This specifies the task types that a task can be classified under
	private enum TaskType { DAY, RANGE, FLOATING }
	
	public Task() {
		
	}
	public Task(String name) {
		this.name = name;
	}
	
	public int getTaskId() { 
		return this._id;
	}
	
	public void setTaskId(int id) {
		this._id = id;
	}
	
	public String getTaskName() {
		return this.name;
	}
	
	public void setTaskName(String name) {
		this.name = name; 
	}
	
	public Date getFromDate() { 
		return this.fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() { 
		return this.toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public ArrayList<String> getCategories() {
		return this.categories;
	}
	
	public void addToCategory(String category) {
		if(category == null) { return; }
		
		category = category.trim();
		if(category.equalsIgnoreCase(Constant.STRING_EMPTY) == false) { 
			this.categories.add(category);
		}
	}
	
	public void addToCategory(String[] categories) {
		// ensure that value passed is not null
		if(categories == null) { return; }
		
		//add each individual 
		for(int i = 0; i < categories.length; i++) {
			this.addToCategory(categories[i]);
		}
	}
	
	public boolean isDeleted() { 
		return this.isDeleted;
	}
	
	public void setDeleted() { 
		this.isDeleted = true;
	}
	
	public void setId(int id) {
		_id = id;
	}
	
	public boolean isBlocking() {
		return this.isBlocking;
	}
	
	public void setBlocking(boolean isBlocking) {
		this.isBlocking = isBlocking;
	}
	
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
	public TaskType getTaskType() {
		return this.taskType;
	}
	
	public String toString() {
		return name;
	}
}
