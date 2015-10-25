package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class AddCommand extends ReversibleCommand {
	
	private Task task;
	private TaskType taskType;
	private String taskName;
	private DateRange dateRange;
	private String category;
	private TaskManager manager;
	
	public AddCommand(Action commandAction,TaskManager manager, TaskType type, Stack<ReversibleCommand> historyStack){
		super(commandAction, historyStack);
		this.manager = manager;
		this.taskType = type;
	}

	public void addValues(String taskName, DateRange dateRange, String category) {
		this.taskName = taskName;
		this.dateRange = dateRange;
		this.category = category;
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

	@Override
	public Result execute(){
		Task addTask = manager.addTask(taskName, dateRange, category, taskType);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(addTask);
		Result result = new Result(Action.ADD, listOfResults);
		this.task = addTask;
		//this.task = addtask;
		pushToStack();
		return result;
	}
	
	@Override
	public Result undo(){
		Task temptask = manager.deleteTask(task.getTaskId()); //This will throw NULLPOINTEREXCEPTION is the task is not successfully created
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(temptask);
		Result result = new Result(Action.UNDO, listOfResults);
		return result;
	}
	
	
}
