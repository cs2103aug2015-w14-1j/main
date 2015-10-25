package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class AddCommand extends ReversibleCommand {
	
	private Task task;
    private String taskName;
	private TaskType taskType;
	private DateRange dateRange;
	private String category;
	private TaskManager manager;
	
	public AddCommand(Action commandAction,TaskManager manager, Stack<ReversibleCommand> historyStack){
		super(commandAction, historyStack);
		this.manager = manager;
	}

	public void addValues(String taskName, TaskType taskType, DateRange dateRange, String category) {
		this.taskName = taskName;
		this.taskType = taskType;
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
