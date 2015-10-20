package Logic;

import java.awt.Desktop.Action;

import notify.DateRange;
import notify.Task;
import notify.logic.TaskManager;

public class UpdateCommand {
	
	//private Task task;
	private Task oldTask;
	//private TaskType taskType;
	private String taskName;
	private DateRange range;
	private String category;
	private int id;
	
	public UpdateCommand(Action commandAction,String taskName, DateRange range, String category, int id, TaskManager manager, Task oldTask){
		this.oldTask = manager.getTask(id);
		this.commandAction = commandAction;
		this.taskName = taskName;
		this.range = range;
		this.category = category;
		this.id = id;
	}
	
	@Override
	public Result execute(){
		Task updatedTask = manager.updateTask(id,taskName,range,category);
		Result result = new Result(Action.UPDATE, updatedTask);
		pushToHistoryStack();
		return result;
		
	}
	
	@Override
	public Result undo(){
		Task beforeUpdatedTask = (oldTask.getId(), oldTask.getTaskName(), oldTask.getRange(), oldTask.getCategory());
		Result result = new Result(Action.UNDO, beforeUpdatedTask);
		return result;
	}
	

}
