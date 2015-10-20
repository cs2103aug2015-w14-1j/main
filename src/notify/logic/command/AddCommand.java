package notify.logic.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import notify.logic.*;
import notify.*;
import notify.logic.command.*;
import notify.logic.TaskManager;

public class AddCommand extends ReversibleCommand {
	
	private Task task;
	private TaskType type;
	private String taskName;
	private DateRange range;
	private String category;
	private TaskManager manager;
	
	
	public AddCommand(Action commandAction,String taskName, DateRange range, String category, TaskManager manager, TaskType type, Stack<ReversibleCommand> historyStack){
		
		super(commandAction, historyStack);
		this.range = range;
		this.category = category;
		this.manager = manager;
		this.type = type;
	}

	@Override
	public Result execute(){
		Task addTask = manager.addTask(taskName, range, category, type);
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
