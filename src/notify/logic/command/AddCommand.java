package Logic;

import notify.DateRange;
import notify.Task;
import notify.logic.TaskManager;

public class AddCommand extends ReversibleCommand {


	//private Task task;
	private String taskName;
	private DateRange range;
	private String category;
	private int id;
	
	
	public AddCommand(Action commandAction,String taskName, DateRange range, String category, TaskManager manager){
		this.commandAction = commandAction;
		this.range = range;
		this.category = category;
		this.manager = manager;
		
		
	}
    
   @Override
	public Result execute(){
		Task addTask = manager.addTask(taskName, range, category);
		Result result = new Result(Action.ADD, addtask);
		//this.task = addtask;
		pushToHistoryStack();
		return result;
	}
	
	@Override
	public Result undo(){
		Task temptask = manager.deleteTask(id);
		Result result = new Result(Action.UNDO,temptask);
		return result;
	}
	
	
}
