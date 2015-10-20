package notify;

import java.util.ArrayList;
import java.util.Calendar;

public class Task {

	private static final int UNASSIGNED_TASK = -1;
	
	// This stores the id that is associated with the task
	private int _id = UNASSIGNED_TASK;

	// This stores the event name specified for the task
	private String name;
		
	// This stores the date and time specified for the task
	private DateRange dateRange;
		
	// This stores the list of categories that the task belongs to
	private ArrayList<String> categories = new ArrayList<String>();
		
	// This stores the state of the event if it has been completed
	private boolean isCompleted = false;
		
	// This specifies if the days specified are used for blocking
	private boolean isBlocking = false;
		
	// This stores the task type that it belongs to
	private TaskType taskType;
	
	public Task(TaskType taskType, String name) {
		this(taskType, name, null);
	}

	public Task(TaskType taskType, String name, Calendar startDate, Calendar endDate) {
		this(taskType, name, new DateRange(startDate, endDate));
	}
		
	public Task(TaskType taskType, String name, DateRange dateRange) {
		this(taskType, name, dateRange, false);
	}
	
	
	public Task(TaskType taskType, String name, DateRange dateRange, boolean isCompleted) {
		
		assert taskType != null;
		assert name != null;
		
		this.taskType = taskType;
		this.name = name;
		this.dateRange = dateRange;
		this.isCompleted = isCompleted;
	}
	
	/**
	 * Returns true if this task is overdue. An overdue task is a deadline task
	 * that is not finished and its end time is earlier than the time when this
	 * method is called.
	 * 
	 * @return true if this task is overdue
	 */
	/*public boolean isOverdue() {
		
		if(taskType != TaskType.DEADLINE) { 
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
	//	boolean isOverdue = !isCompleted && 
	}*/
	
	
	
	
	
}
