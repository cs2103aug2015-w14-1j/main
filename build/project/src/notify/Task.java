//@@author A0125364J
package notify;

import java.util.Calendar;

public class Task implements Comparable<Task> {

	public static final int UNASSIGNED_TASK = -1;
	
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
	 * that has not finished and its end time is earlier than the time when this
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
		
		switch (taskType) {
		
			case FLOATING:
				
				isOverdue = false;
				
				break;
				
			default:
				assert getEndDate() != null;
				
				endDateYear = getEndDate().get(Calendar.YEAR);
				endDateDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if (endDateYear < todayYear && !isCompleted && !isDeleted) {
					
					isOverdue = true;
					
				} else if (endDateYear == todayYear && endDateDay < todayDay && !isCompleted && !isDeleted) {
					
					isOverdue = true;
					 
				}
				
				break;
				
		}
		
		return isOverdue;
		
	}
	
	/**
	 * Check whether the task is on the date specified (for deadline tasks)
	 * Check whether the date specified is within the range of date the task (for range tasks)
	 * Includes task that are completed.
	 * 
	 * @param date day to be checked against 
	 * 
	 * @return true if the task is on the date specified, or within the range of date of the task. else return false
	 */
	public boolean isOn(Calendar date) {
		
		int dateYear = date.get(Calendar.YEAR);
		int dateDay = date.get(Calendar.DAY_OF_YEAR);
		
		int taskEndYear, taskEndDay, taskStartYear, taskStartDay;
		
		boolean isOnDate = false;
		
		switch (taskType) {
		
			case DEADLINE:

				taskEndYear = getEndDate().get(Calendar.YEAR);
				taskEndDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				if (dateYear == taskEndYear && dateDay == taskEndDay && !isDeleted) {
					
					isOnDate = true;
					
				}
				
				break;
				
			case RANGE:

				taskEndYear = getEndDate().get(Calendar.YEAR);
				taskEndDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				taskStartYear = getStartDate().get(Calendar.YEAR);
				taskStartDay = getStartDate().get(Calendar.DAY_OF_YEAR);
				
				if (taskStartYear < dateYear && dateYear < taskEndYear) {
					
					isOnDate = true;
					
				} else if (taskStartYear == dateYear && dateYear < taskEndYear
						&& taskStartDay <= dateDay) {
					
					isOnDate = true;
					
				} else if (taskStartYear < dateYear && dateYear == taskEndYear
						&& dateDay <= taskEndDay) {
					
					isOnDate = true;
					
				} else if (taskStartYear == dateYear && dateYear == taskEndYear
						&& taskStartDay <= dateDay && dateDay <= taskEndDay) {

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
	 * 
	 * @return true if the task is a coming soon task, else false
	 */
	public boolean isComingSoon() {
		
		Calendar today = Calendar.getInstance();
		boolean isComingSoon = true;
		
		switch (taskType) {
			
			case FLOATING:
				
				isComingSoon = false;
				
				break;
				
			default:

				for (int i = 0; i < DAYS_A_WEEK; i++) {
					
					if (isOn(today) || isOverdue() || isCompleted || isDeleted) {
						
						isComingSoon = false;
						
						break;
						
					}
					
					today.add(Calendar.DAY_OF_MONTH, 1);
					
				}
				
				break;
		
		}
		
		return isComingSoon;
		
	}
	
	/**
	 * Check whether the ranged task ends within the week .
	 * A ending soon task is a task that will end within seven days.
	 * 
	 * @return true if the task is a ending soon task, else false
	 */
	public boolean isEndingSoon() {

		Calendar today = Calendar.getInstance();
		boolean isEndingSoon = false;
		
		switch (taskType) {
		
			case RANGE:
				
				int taskEndYear = getEndDate().get(Calendar.YEAR);
				int taskEndDay = getEndDate().get(Calendar.DAY_OF_YEAR);
				
				for (int i = 0; i < DAYS_A_WEEK; i++) {

					int todayYear = today.get(Calendar.YEAR);
					int todayDay = today.get(Calendar.DAY_OF_YEAR);
					
					if (todayYear == taskEndYear && todayDay == taskEndDay && !isCompleted && !isDeleted && !isOverdue()) {
						
						isEndingSoon = true;
						
						break;
						
					}
					
					
					today.add(Calendar.DAY_OF_MONTH, 1);
					
				}
				
				break;
			
			default:
				
				isEndingSoon = false;
				
				break;
		
		}
		
		return isEndingSoon;
		
	}
	
	/**
	 * Checks whether this task has already started.
	 * Started tasks means a task that has its start date earlier than today.
	 * 
	 * @return true if the range task has already started, else false.
	 */
	public boolean isStarted() {

		Calendar today = Calendar.getInstance();
		boolean isStarted = false;
		
		int todayYear = today.get(Calendar.YEAR);
		int todayDay = today.get(Calendar.DAY_OF_YEAR);
		int taskStartYear = getStartDate().get(Calendar.YEAR);
		int taskStartDay = getStartDate().get(Calendar.DAY_OF_YEAR);
		
		switch (taskType) {
		
			case RANGE:
				
				if (!isCompleted && !isDeleted && !isOverdue()) {
					
					isStarted = (taskStartYear < todayYear) || (taskStartYear == todayYear && taskStartDay < todayDay);
					
				}
				
				
				break;
			
			default:
				
				isStarted = false;
				
				break;
		
		}
		
		return isStarted;
	}
	
	/**
	 * Check whether this task is a searched task.
	 * 
	 * @param keyWord the search keyword
	 * 
	 * @return true if the tasks is a searched task, else false.
	 */
	public boolean isSearchedTask(String keyword) {
		
		boolean result = false;
		
		if (isNull(this.category)) {
			
			if (String.valueOf(this.id).equals(keyword) || this.name.toLowerCase().contains(keyword.toLowerCase()))
				result = true;
		} else {
			
			if (String.valueOf(this.id).equals(keyword) || 
					this.name.toLowerCase().contains(keyword.toLowerCase()) ||
					this.category.toLowerCase().contains(keyword.toLowerCase())) {
				
				result = true;
				
			}
			
		}
		
		return result;
	
	}
	
	private boolean isNull(Object obj) {
		
		return obj == null;
		
	}
	
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
	
	public void setTaskType(TaskType taskType) {
		
		this.taskType = taskType;
		
	}

	public TaskType getTaskType() {
		
		return this.taskType;
		
	}
	
	public int compareTo(Task task) {
		
		if (dateRange == null) {
			
			if (task.getDateRange() == null) {
				
				return 0;
				
			} else {
				
				return 1;
				
			}
			
		}
		
		if (task.getDateRange() == null) {
			
			if (dateRange == null) {
				
				return 0;
				
			} else {
				
				return -1;
				
			}
			
		}
		
		Calendar startDate = getStartDate();
		Calendar endDate = getEndDate();
		Calendar taskStartDate = task.getStartDate();
		Calendar taskEndDate = task.getEndDate();
		
		if (startDate != null) {
			
			int startYear = startDate.get(Calendar.YEAR);
			int startDay = startDate.get(Calendar.DAY_OF_YEAR);
			
			if (taskStartDate != null) {
				
				int taskStartYear = taskStartDate.get(Calendar.YEAR);
				int taskStartDay = taskStartDate.get(Calendar.DAY_OF_YEAR);
				
				if (startYear - taskStartYear == 0) {
					
					return startDay - taskStartDay;
					
				} else {
					
					return startYear - taskStartYear;
					
				}
				
			} else if (taskEndDate != null) {
				
				int taskEndYear = taskEndDate.get(Calendar.YEAR);
				int taskEndDay = taskEndDate.get(Calendar.DAY_OF_YEAR);
				
				if (startYear - taskEndYear == 0) {
					
					return startDay - taskEndDay;
					
				} else {
					
					return startYear - taskEndYear;
					
				}
				
			} else {
				
				return -1;
				
			}
			
		} else if (endDate != null) {

			int endYear = endDate.get(Calendar.YEAR);
			int endDay = endDate.get(Calendar.DAY_OF_YEAR);
			
			if (taskStartDate != null) {
				
				int taskStartYear = taskStartDate.get(Calendar.YEAR);
				int taskStartDay = taskStartDate.get(Calendar.DAY_OF_YEAR);
				
				if (endYear - taskStartYear == 0) {
					
					return endDay - taskStartDay;
					
				} else {
					
					return endYear - taskStartYear;
					
				}
				
			} else if (taskEndDate != null) {
				
				int taskEndYear = taskEndDate.get(Calendar.YEAR);
				int taskEndDay = taskEndDate.get(Calendar.DAY_OF_YEAR);
				
				if (endYear - taskEndYear == 0) {
					
					return endDay - taskEndDay;
					
				} else {
					
					return endYear - taskEndYear;
					
				}
				
			} else {
				
				return -1;
			
			}
			
		} else {
			
			if (taskStartDate != null || taskEndDate != null) {
				
				return 1;
				
			} else {
				
				return 0;
				
			}
			
		}
		
	}
	
}
