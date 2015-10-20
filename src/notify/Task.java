package notify;

import java.util.ArrayList;
import java.util.Calendar;

public class Task {

	private static final int UNASSIGNED_TASK = -1;

	// This stores the id that is associated with the task
	private int id = UNASSIGNED_TASK;

	// This stores the event name specified for the task
	private String name;

	// This stores the date and time specified for the task
	private DateRange dateRange;

	// This is the category that the task belongs to
	private String category = new String();

	// This stores the state of the event if it has been completed
	private boolean isCompleted = false;

	private boolean isDeleted = false;

	// This stores the task type that it belongs to
	private TaskType taskType;

	/*
	 * public Task(int id, TaskType taskType, String name) { this(id, taskType,
	 * name, null); }
	 * 
	 * public Task(int id, TaskType taskType, String name, Calendar startDate,
	 * Calendar endDate) { this(id, taskType, name, new DateRange(startDate,
	 * endDate)); }
	 * 
	 * public Task(int id, TaskType taskType, String name, DateRange dateRange)
	 * { this(id, taskType, name, dateRange, false); }
	 */
	public Task(int id, TaskType taskType, String name, DateRange dateRange, String category, boolean isCompleted) {

		assert taskType != null;
		assert name != null;  

		this.id = id;
		this.taskType = taskType;
		this.name = name;
		this.dateRange = dateRange;
		this.category = category;
		this.isCompleted = isCompleted;
	}

	/**
	 * Returns true if this task is overdue. An overdue task is a deadline task
	 * that is not finished and its end time is earlier than the time when this
	 * method is called.
	 * 
	 * @return true if this task is overdue
	 */
	/*
	 * public boolean isOverdue() {
	 * 
	 * if(taskType != TaskType.DEADLINE) { return false; }
	 * 
	 * Calendar cal = Calendar.getInstance(); // boolean isOverdue =
	 * !isCompleted && }
	 */
	public int getTaskId() {
		return this.id;
	}

	public void setTaskId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return this.name;
	}

	public void setTaskName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public DateRange getDateRange() {
		return this.dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isCompleted() {
		return this.isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	/*
	 * public boolean isBlocking() { return this.isBlocking; }
	 * 
	 * public void setBlocking(boolean isBlocking) { this.isBlocking =
	 * isBlocking; }
	 */

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return this.taskType;
	}

	public String toString() {
		return name + " " + category;
	}
}
