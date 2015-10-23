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

	public Task(int id, TaskType taskType, String name) {
		this(id, taskType, name, null);
	}

	public Task(int id, TaskType taskType, String name, Calendar startDate,
			Calendar endDate) {
		this(id, taskType, name, new DateRange(startDate, endDate));
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
		
		Calendar calendar = Calendar.getInstance();
		
		int todaysYear = calendar.get(Calendar.YEAR);
		int todaysDay = calendar.get(Calendar.DAY_OF_YEAR);
		int endDateYear, endDateDay;
		
		boolean isOverdue = false;
		
		switch(taskType) {
		
			case FLOATING:
				
				isOverdue = false;
				
				break;
				
			default:
				
				endDateYear = getEndDate().get(Calendar.YEAR);
				endDateDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if(endDateYear < todaysYear && !isCompleted) {
					
					isOverdue = true;
					
				} else if(endDateYear == todaysYear && endDateDay < todaysDay && !isCompleted) {
					
					isOverdue = true;
					
				}
				
				break;
				
		}
		
		return isOverdue;
		
	}
	
	public boolean isWithinSevenDays() {
		
		Calendar calendar = Calendar.getInstance();
		Calendar nextWeek = Calendar.getInstance();
		nextWeek.add(Calendar.DAY_OF_MONTH, 7);

		int todaysYear = calendar.get(Calendar.YEAR);
		int todaysDay = calendar.get(Calendar.DAY_OF_YEAR);
		int nextWeekYear = nextWeek.get(Calendar.YEAR);
		int nextWeekDay = nextWeek.get(Calendar.DAY_OF_YEAR);
		int startDateYear, startDateDay;
		int endDateYear, endDateDay;
		
		boolean isWithinSevenDays = false;
		
		switch(taskType) {
			
			case FLOATING:
				
				isWithinSevenDays = false;
				
				break;
				
			case DEADLINE:
				
				endDateYear = getEndDate().get(Calendar.YEAR);
				endDateDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if(todaysYear <= endDateYear && endDateYear <= nextWeekYear 
						&& todaysDay <= endDateDay && endDateDay <= nextWeekDay) {
					
					isWithinSevenDays = true;
					
				}
				
				break;
				
			case RANGE:
				
				startDateYear = getStartDate().get(Calendar.YEAR);
				startDateDay = getStartDate().get(Calendar.DAY_OF_YEAR);
				endDateYear = getEndDate().get(Calendar.YEAR);
				endDateDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				// check if start date is within this week (e.g. today: 19Oct, start date: 20Oct, next week: 26Oct)
				// or end date is within this week (e.g. today: 19Oct, end date: 20Oct, next week: 26Oct)
				// or this week is part of the start and end date (e.g. start date: 17Oct, today: 19Oct, next week: 26Oct, end date: 30Oct)
				if(todaysYear <= startDateYear && startDateYear <= nextWeekYear
						&& todaysDay <= startDateDay && startDateDay <= nextWeekDay) {
					
					isWithinSevenDays = true;
					
				} else if(todaysYear <= endDateYear && endDateYear <= nextWeekYear
						&& todaysDay <= endDateDay && endDateDay <= nextWeekDay) {
	
					isWithinSevenDays = true;
				
				} else if(startDateYear <= todaysYear && nextWeekYear <= endDateYear
						&& startDateDay <= todaysDay && nextWeekDay <= endDateDay) {
					
					isWithinSevenDays = true;
					
				}
				
				break;
		
		}
		
		return isWithinSevenDays;
		
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
