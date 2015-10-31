package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class EditCommand extends ReversibleCommand {
	
	//private Task task;
	private Task oldTask;
	private TaskType type;
	private String taskName;
	private DateRange range;
	private String category;
	private int id;
	private Action commandAction;
	private TaskManager manager;
	
	public EditCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager){ 
		super(commandAction, historyStack);
		this.manager = manager;
	}
	
	public void addValues(String taskName, DateRange range, String category, int id, TaskType type){
		this.oldTask = manager.getTask(id);
		this.taskName = taskName;
		this.range = range;
		this.category = category;
		this.id = id;
		this.type = type;
	}
	
	public void checkNull(){
		//assert id!=-1 (unassigned)
		if(taskName == null){
			this.taskName = oldTask.getTaskName();
		}
		if(range == null){
			this.range = oldTask.getDateRange();
		}
		if(category == null){
			this.category = oldTask.getCategory();
		}
		
		if(type == null){
			this.type = oldTask.getTaskType();
		}
	}
	
	@Override
	public Result execute(){
		checkNull();
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
