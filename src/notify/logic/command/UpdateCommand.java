package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class UpdateCommand extends ReversibleCommand {
	
	//private Task task;
	private Task oldTask;
	private TaskType type;
	private String taskName;
	private DateRange range;
	private String category;
	private int id;
	private Action commandAction;
	private TaskManager manager;
	
	public UpdateCommand(Action commandAction, Stack<ReversibleCommand> historyStack, String taskName, DateRange range, String category, int id, TaskManager manager, Task oldTask, TaskType type){
		super(commandAction, historyStack);
		this.oldTask = manager.getTask(id);
		this.commandAction = commandAction;
		this.taskName = taskName;
		this.range = range;
		this.category = category;
		this.id = id;
		this.type = type;
	}
	
	@Override
	public Result execute(){
		Task updatedTask = manager.updateTask(id,taskName,range,category,type);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(updatedTask);
		Result result = new Result(Action.EDIT, listOfResults);
		pushToStack();
		return result;
		
	}
	
	@Override
	public Result undo(){
		Task beforeUpdatedTask = manager.updateTask(oldTask.getTaskId() , oldTask.getTaskName(), oldTask.getDateRange() , oldTask.getCategory(), oldTask.getTaskType());
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(beforeUpdatedTask);
		Result result = new Result(Action.UNDO, listOfResults);
		return result;
	}
	

}
