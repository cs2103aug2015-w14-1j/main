package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.Task;
import notify.logic.TaskManager;


public class DeleteCommand extends ReversibleCommand {
	
	//private Task task;
	private int id;
	private TaskManager manager;
	
	
	public DeleteCommand(Action commandAction, int id, Stack<ReversibleCommand> historyStack, TaskManager manager ){
		super(commandAction, historyStack);
		this.manager = manager;
		this.id = id;
	}
	
	@Override
	public Result execute(){
		Task temptask = manager.deleteTask(id);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(temptask);
		Result result = new Result(Action.DELETE, listOfResults);
		//this.task = temptask;
		pushToHistoryStack();
		return result;
		
	}
	
	@Override 
	public Result undo(){
		Task temptask = manager.undeleteTask(id);
		ArrayList<Task> listOfResults = new ArrayList<Task>(); 
		listOfResults.add(temptask); 
		Result result = new Result(Action.UNDO, listOfResults);
		//this.task = temptask;
		return result;
	}

}

