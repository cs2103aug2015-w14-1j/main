//@@author A0130319R

package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.DateRange;
import notify.Task;
import notify.TaskType;
import notify.logic.TaskManager;

public class EditCommand extends ReversibleCommand {
	
	private Task oldTask;
	private TaskType taskType;
	private String taskName;
	private DateRange dateRange;
	private String category;
	private int id;
	private TaskManager manager;
	
	public EditCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager){ 
		super(commandAction, historyStack);
		this.manager = manager;
	}
	
	public void addValues(String taskName, DateRange dateRange, String category, int id, TaskType taskType){
		this.oldTask = manager.getTask(id);
		this.taskName = taskName;
		this.dateRange = dateRange;
		this.category = category;
		this.id = id;
		this.taskType = taskType;
	}
	
	
	@Override
	public Result execute(){
		Result result = null;
		Task updatedTask = manager.updateTask(id, taskName, dateRange, category, taskType);
		ArrayList<Task> list = new ArrayList<Task>();
		
		if(updatedTask != null){
		
		checkNull();
		list.add(updatedTask);
		result = new Result(Action.EDIT, list);
		pushToStack();
		
		}
		else{
			
			result = new Result(Action.INVALID, list);
		}
		
		return result;
	}

	
	
	@Override
	public Result undo(){
		
		Task beforeUpdatedTask = manager.updateTask(oldTask.getTaskId() , oldTask.getTaskName(), oldTask.getDateRange() , oldTask.getCategory(), oldTask.getTaskType());
		ArrayList<Task> list = new ArrayList<Task>();
		list.add(beforeUpdatedTask);
		Result result = new Result(Action.UNDO, list);
		return result;
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
	
	
	public void checkNull(){
		
		assert id != Constants.UNASSIGNED_TASK : "Task id cannot be null";
		
		if(taskName == null){
			this.taskName = oldTask.getTaskName();
		}
		if(dateRange == null){
			this.dateRange = oldTask.getDateRange();
		}
		if(category == null){
			this.category = oldTask.getCategory();
		}
		
		if(taskType == null){
			this.taskType = oldTask.getTaskType();
		}
	}
	
	

}
