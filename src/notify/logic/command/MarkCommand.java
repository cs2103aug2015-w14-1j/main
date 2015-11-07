/**
 * Author: Sadhika Billa
 * Matric number: A0130319R
 * For CS2103 - Notify
 */
package notify.logic.command;

import java.util.ArrayList;
import java.util.Stack;

import notify.Task;
import notify.logic.TaskManager;

public class MarkCommand extends ReversibleCommand {
	
	private TaskManager manager;
	private Task task;
	private int id;
	
	public MarkCommand(Action commandAction, Stack<ReversibleCommand> historyStack, TaskManager manager){
		
		super(commandAction, historyStack);
		this.manager = manager;
	}
	
	@Override
	public Result execute() {
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task markTask = manager.markTask(id, true);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(markTask);
		Result result = new Result(Action.MARK, listOfResults);
		pushToStack();
		return result;
	}

	@Override
	public Result undo() {
		assert id != Constants.UNASSIGNED_TASK: "Task id cannot be unassigned";
		
		Task tempTask = manager.markTask(id, false);
		ArrayList<Task> listOfResults = new ArrayList<Task>();
		listOfResults.add(tempTask);
		Result result = new Result(Action.UNDO, listOfResults);
		return result;
	}
	
public void addValues(int id){
		
		this.id = id;
	}
	
	public int getId(){
		
		return this.id;
	}

}
