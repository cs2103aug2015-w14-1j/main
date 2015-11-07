/**
 * Author Sadhika Billa
 * Matric No: A0130319R
 * For CS2103-Notify
 */
package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;
import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class AddCommand extends ReversibleCommand {
	
	//These are variables that are required to store the fields of each task 
	private Task task;
    private String taskName;
	private TaskType taskType;
	private DateRange dateRange;
	private String category;
	private TaskManager manager;
	
	public AddCommand(Action commandAction, TaskManager manager, Stack<ReversibleCommand> historyStack){
		
		super(commandAction, historyStack);
		this.manager = manager;
	}

	public void addValues(String taskName, TaskType taskType, DateRange dateRange, String category) {
		
		this.taskName = taskName.trim();
		this.taskType = taskType;
		this.dateRange = dateRange;
		this.category = category;
	}
	
	
    @Override
	public Result execute(){
		
		assertions();
		
		Task addTask = manager.addTask(taskName, dateRange, category, taskType);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(addTask);
		Result result = new Result(Action.ADD, listOfResults);
		this.task = addTask;
		pushToStack();
		return result;
	}

	
	@Override
	public Result undo(){
		
		assert task.getTaskId() != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task temptask = manager.deleteTask(task.getTaskId()); 
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(temptask);
		Result result = new Result(Action.UNDO, listOfResults);
		return result;
	}
	
	private void assertions() {
		assert taskName != null: "Task name cannot be null";
		assert dateRange!= null: "Date range cannot be null";
		assert category != null: "Category cannot be null";
		assert taskType != null: "Task type cannot be null";
	}
	
public String getTaskName() {
		
		return this.taskName;
	}
	
	public TaskType getTaskType() { 
		
		return this.taskType;
	}
	
	public String getCategory() {
		
		return this.category;
	}
	
	public DateRange getDateRange() {
		
		return this.dateRange;
	}
	
	
	
}
