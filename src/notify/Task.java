package notify;

import java.util.Calendar;

public class Task {

	private static final int UNASSIGNED_TASK = -1;
	
	private static final int DAYS_A_WEEK = 7;

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

	public Task(int id, TaskType taskType, String name) {
		this(id, taskType, name, null);
	}

	public Task(int id, TaskType taskType, String name, DateRange dateRange) {
		this(id, taskType, name, dateRange, false);
	}

	public Task(int id, TaskType taskType, String name, DateRange dateRange,
			boolean isCompleted) {

		assert taskType != null;
		assert name != null;
	}
		
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
	
	public boolean isOverdue() {
		
		Calendar today = Calendar.getInstance();
		
		int todayYear = today.get(Calendar.YEAR);
		int todayDay = today.get(Calendar.DAY_OF_YEAR);
		int endDateYear, endDateDay;
		
		boolean isOverdue = false;
		
		switch(taskType) {
		
			case FLOATING:
				
				isOverdue = false;
				
				break;
				
			default:
				
				endDateYear = getEndDate().get(Calendar.YEAR);
				endDateDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if(endDateYear < todayYear && !isCompleted && !isDeleted) {
					
					isOverdue = true;
					
				} else if(endDateYear == todayYear && endDateDay < todayDay && !isCompleted && !isDeleted) {
					
					isOverdue = true;
					
				}
				
				break;
				
		}
		
		return isOverdue;
		
	}
	
	/**
	 * Check whether the task is on the date specified (for deadline tasks)
	 * Check whether the date specified is within the range of date the task (for range tasks)
	 * @param date day to be checked against 
	 * @return true if the task is on the date specified, or within the range of date of the task. else return false
	 */
	public boolean isOn(Calendar date) {
		
		int dateYear = date.get(Calendar.YEAR);
		int dateDay = date.get(Calendar.DAY_OF_YEAR);
		
		int taskEndYear, taskEndDay, taskStartYear, taskStartDay;
		
		boolean isOnDate = false;
		
		switch(taskType) {
		
			case DEADLINE:

				taskEndYear = getEndDate().get(Calendar.YEAR);
				taskEndDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if(dateYear == taskEndYear && dateDay == taskEndDay && !isCompleted && !isDeleted) {
					
					isOnDate = true;
					
				}
				
				break;
				
			case RANGE:

				taskEndYear = getEndDate().get(Calendar.YEAR);
				taskEndDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				taskStartYear = getStartDate().get(Calendar.YEAR);
				taskStartDay = getStartDate().get(Calendar.DAY_OF_YEAR);
				
				if(taskStartYear <= dateYear && dateYear <= taskEndYear
						&& taskStartDay <= dateDay && dateDay <= taskEndDay && !isCompleted && !isDeleted) {
					
					isOnDate = true;
					
				}				
				
				break;
			
			default:
				
				isOnDate = false;
				
				break;
		
		}
		
		return isOnDate;
		
	}
	
	/**
	 * Check whether the task is coming soon.
	 * A coming soon task is a task that will happen after than seven days or more
	 * @return true if the task is a coming soon task, else false
	 */
	public boolean isComingSoon() {
		
		Calendar today = Calendar.getInstance();
		boolean isComingSoon = true;
		
		switch(taskType) {
			
			case FLOATING:
				
				isComingSoon = false;
				
				break;
				
			default:

				for(int i = 0; i < DAYS_A_WEEK; i++) {
					
					if(isOn(today) || isOverdue() || isCompleted || isDeleted) {
						
						isComingSoon = false;
						
						break;
						
					}
					
					today.add(Calendar.DAY_OF_MONTH, 1);
					
				}
				
				break;
		
		}
		
		return isComingSoon;
		
	}
	
	
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
	
	public Calendar getStartDate() {
		return this.dateRange.getStartDate();
	}
	
	public Calendar getEndDate() {
		return this.dateRange.getEndDate();
	}
	
	public Calendar getStartTime() {
		return this.dateRange.getStartTime();
	}
	
	public Calendar getEndTime() {
		return this.dateRange.getEndTime();
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
